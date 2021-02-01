package com.nttdata.lumileds.otmm.assetready.utility;

import com.artesia.entity.TeamsIdentifier;

public class MetadataConstants {
	
	public static final TeamsIdentifier PRODUCT_CODE = new TeamsIdentifier("ARTESIA.FIELD.DOCUMENT SUBJECT");
	public static final TeamsIdentifier ASSET_SOURCE = new TeamsIdentifier("ARTESIA.FIELD.DOCUMENT AUTHOR");
	
	public static final TeamsIdentifier[] FIELD_IDS = 
		{
			PRODUCT_CODE,
			ASSET_SOURCE		
		};
	
	public static final String hybrisURL = 
			"https://api.c8enfaq4fs-lumiledsl2-p2-private.model-t.cc.commerce.ondemand.com/lumiledswebservices/v2/lumileds/print-rest/assetReady";
	//"https://hybris.corp.lumileds.org/lumiledswebservices/v2/lumileds/print-rest/assetReady";
	//"https://qa.corp.lumileds.org/lumiledswebservices/v2/lumileds/print-rest/assetReady";
	//"https://api.c8enfaq4fs-lumiledsl2-s2-private.model-t.cc.commerce.ondemand.com/lumiledswebservices/v2/lumileds/print-rest/assetReady";

	public static final String QMARK = "?";
	public static final String EQUALS = "=";
	public static final String AMPERSAND = "&";

	public static final String GET = "GET";

	public static final String ACCEPT = "Accept";

	public static final String XML_MIME_TYPE = "application/xml";

//	public static final String PRINT_INDUSTRIAL_FOLDER_ID = "98329293b9160c17cbefea7529c936b991bc1371";
//	public static final String PRINT_COMMERCIAL_FOLDER_ID = "4df56ecc3afdb6566d51a90a8b1f097234d91447";
//	
	public static final String PRINTSUITE = "PRINTSUITE";
	public static final int CONNECT_TIMEOUT = 30000;
	public static final int READ_TIMEOUT = 30000;
	public static final int RETRY_COUNT = 3;
	public static final CharSequence DRAFT = "DRAFT";
	public static final CharSequence PRS = "PRS";
	public static final CharSequence CPIS = "CPIS";
	public static final CharSequence PIS = "PIS";
	
	public static final String DELETE = "DELETE";
	
	//This needs to be changed per environment i.e., for qa/prod
//	public static final String DRAFT_FOLDER_ID="4f4a65e3174e9b1b17f12b089d811cf54db3ad93";
//	public static final String PRS_FOLDER_ID="be8c40c3336212ce7011cb11246e9cbaba4ba0cf";
//	public static final String CPIS_FOLDER_ID="4c20490d8aba58f67c72ce65074b5bb8461497c3";
//	public static final String PIS_FOLDER_ID="ab5ecb55cd19cb0e3079036004fe1018983fe7be";
	
	public static final String DRAFT_FOLDER_ID="3b30430318a33b485b20a4f747075fde0740ec26";
	public static final String PRS_FOLDER_ID="a8bba8c5f1a389d86245582628011edb628deb55";
	public static final String CPIS_FOLDER_ID="3e25d66f8fb8d530b302ecf7b6ae477d372d6fdb";
	public static final String PIS_FOLDER_ID="3e25d66f8fb8d530b302ecf7b6ae477d372d6fdb";
	

}
