package com.nttdata.lumileds.otmm.assetready.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nttdata.lumileds.otmm.assetready.utility.MetadataConstants;

public class SQLRepository {
	
	private static final Log log = LogFactory.getLog(SQLRepository.class);
	
//	public void insertAssetID(Connection conn, List<String> assetIDList) {
//		
//		//TODO 
//		//Accept assetid list and insert into DB table with a Flag
//		//This table would be further used to delete assets and will
//		// be useful in handling broken connection issue with Hybris.
//	}
//	
//	public String getDuplicateAssetID(Connection conn, String assetID) {
//		
//		
//		return null;
//	}

	public void insertPreviousDraftAssetID(
			Connection conn, 
			String assetID,
			String assetName
			)	{
		
		log.debug("Asset ID added to delete list: " + assetID);
		
		try
		{

			String insertAssetID = " INSERT INTO"
					+ " PRIINT_DUPLICATES"
					+ " VALUES "
					+ " (?,?,"
					+ " '" + MetadataConstants.DELETE
					+ "') ";

			// create our java prepared statement using a sql update query
			PreparedStatement insertAssetIDStatement = 
					conn.prepareStatement(insertAssetID);

			// set the prepared statement parameters
			insertAssetIDStatement.setString(1,assetID);
			insertAssetIDStatement.setString(2,assetName);
			

			// call executeUpdate to execute our sql update statement
			insertAssetIDStatement.executeUpdate();
			insertAssetIDStatement.close();
		}
		catch (SQLException sqlEx)
		{
			sqlEx.printStackTrace();
		}
		
	}

	public ResultSet getVersions(Connection conn, 
			String assetName,
			String parentFolderID) {
		
		ResultSet rs = null;
		
		String versionAssets = "select " +
				"		 a.uoi_id " +
				"	from " + 
				"		 uois a, " + 
				"		 link_matrixes b " + 
				"	where " + 
				"		a.logical_uoi_id = b.CHILD_ID and " + 
				"		b.parent_id=? and " + 
				"		a.name=?";

		PreparedStatement versionAssetsStatement;

		try {

			versionAssetsStatement = conn.prepareStatement(versionAssets);

			versionAssetsStatement.setString(1,parentFolderID);
			
			versionAssetsStatement.setString(2,assetName);

			rs = versionAssetsStatement.executeQuery();

		} catch (SQLException sqlEx) {
			log.error("Exception while fetching asset Versions: {} ", sqlEx);
		}

		return rs;
		
	}


}
