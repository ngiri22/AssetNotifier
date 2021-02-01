package com.nttdata.lumileds.otmm.assetready;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.artesia.asset.Asset;
import com.artesia.common.exception.BaseTeamsException;
import com.artesia.metadata.MetadataCollection;
import com.artesia.metadata.MetadataValue;
import com.artesia.security.SecuritySession;
import com.artesia.server.asset.imprt.AssetImportInterceptor;
import com.artesia.server.storage.StorageContext;
import com.nttdata.lumileds.otmm.assetready.repository.SQLRepository;
import com.nttdata.lumileds.otmm.assetready.utility.HybrisRestClient;
import com.nttdata.lumileds.otmm.assetready.utility.MetadataConstants;


public class AssetReadyNotifier implements AssetImportInterceptor{

	private static final Log log = LogFactory.getLog(AssetReadyNotifier.class);

	@Override
	public void examineAssets(List<Asset> assets, 
			StorageContext context, 
			SecuritySession session) throws BaseTeamsException {

		boolean priintFlag = false ;
		
		Map<String, String> productCodeMap = new HashMap<String, String>();		

		SQLRepository sqlRepository = new SQLRepository();

		for (Asset asset : assets ) {

			String assetID = asset.getAssetId().asString();

			//			MetadataCollection assetMetadata = 
			//					AssetMetadataServices.getInstance().
			//					retrieveMetadataForAsset
			//					(
			//							asset.getAssetId(),
			//							MetadataConstants.FIELD_IDS,
			//							session
			//							);

			log.debug("Asset ID is : " + assetID);

			MetadataCollection assetMetadata = asset.getMetadata();



			MetadataValue printFieldValue = assetMetadata.getValueForField
					(MetadataConstants.ASSET_SOURCE);

			MetadataValue productCode = assetMetadata.getValueForField
					(MetadataConstants.PRODUCT_CODE);


			//			List<ContainerPath> containerPathList = asset.getPathList();
			//
			//			for ( ContainerPath containerPath : containerPathList ) {
			//
			//				List<LightContainer> lightContainerList = containerPath.getParents();
			//
			//				for (LightContainer lightContainer : lightContainerList) {
			//
			//					log.info("Name : "+ lightContainer.getName());
			//					log.info("Id : " + lightContainer.getName());
			//					log.info("^^^^^^^^^^^^^^^^^^^");
			//
			//				}
			//			}
			//
			//
			//
			//
			//			List<InheritedMetadataCollection> inheritedMetadataList = assetWithData.getInheritedMetadataCollections();
			//
			//			for ( InheritedMetadataCollection inheritedMetadata : inheritedMetadataList) {
			//
			//				containerId = inheritedMetadata.getContainerId();
			//
			//				log.info("Container Id: " + containerId.asString());
			//				log.info("Container Name: " + inheritedMetadata.getContainerName());
			//				log.info("---------------------");
			//
			//			}



			// Check if the asset is from Priint
			// Call the Hybris notifier only if its not a version
			
			if (
					(
							null != printFieldValue &&
							null != printFieldValue.getStringValue() &&	
							printFieldValue.getStringValue().equals(MetadataConstants.PRINTSUITE) &&
							null != productCode &&
							null != productCode.getStringValue()

							)
					)
			{

				log.debug("Print Field Value : " + printFieldValue.getStringValue());
				log.debug("Product Code : " + productCode.getStringValue());

				String parentFolderID ;

				if ( asset.getName().contains(MetadataConstants.DRAFT)) {

					parentFolderID = MetadataConstants.DRAFT_FOLDER_ID;
				}
				else if ( asset.getName().contains(MetadataConstants.PRS)) {

					parentFolderID = MetadataConstants.PRS_FOLDER_ID;
				}
				else if ( asset.getName().contains(MetadataConstants.CPIS)) {

					parentFolderID = MetadataConstants.CPIS_FOLDER_ID;
				}
				else {
					parentFolderID = MetadataConstants.PIS_FOLDER_ID;
				}



				ResultSet rs = sqlRepository.getVersions(
						context.getJDBCConnection(), 
						asset.getName(),
						parentFolderID);

				try {

					Set<String> assetIDSet = new HashSet<>();

					while ( rs.next()) {

						assetIDSet.add(rs.getString(1));

					}

					log.debug("Asset ID count: " + assetIDSet.size());
					
					//Check if result set is containing more than one asset
					if (assetIDSet.size() > 1) {

						log.debug("More than one asset found with "
								+ "the same name: "+ asset.getName());
					}
					else {

						productCodeMap.put(assetID, productCode.getStringValue());
						
						priintFlag = true;
						
					}

						//						for ( String dbAssetID : assetIDSet) {
						//
						//							if (
						//									//Skip the current asset
						//									dbAssetID != assetID
						//									) {
						//
						//								sqlRepository.insertPreviousDraftAssetID(
						//										context.getJDBCConnection(),
						//										rs.getString(1),
						//										asset.getName());
						//							}
						//
						//						}
					} catch (SQLException sqlEx) {

					log.error("Exception while checking the size of "
							+ "ResultSet for Version Assets: {} ", sqlEx);
				}


			}
		
		
		}
	
		if (priintFlag) {
			HybrisRestClient hybrisRestClient = new HybrisRestClient();

			hybrisRestClient.callHybrisNotifier(productCodeMap);
		}
	}
}

