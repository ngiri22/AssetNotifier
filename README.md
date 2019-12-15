# AssetNotifier

It is the implementation of AssetInterceptor.
Intercepts the asset on import and checks if it is coming from PriintSuite.

If the file is from PriintSuite:
	Checks if the asset is imported into priintsuite-drafts folder, if there already exists an asset with same name, it makes an entry into PRIINT_DUPLICATES folder.
	Makes a call to Hybris to associate the asset with Product Code.
	
##### Make sure the folder id of the drafts folder and hybris urls are set correctly in the MetadataConstants.java file according to the environment.
Also the table priint_duplicates should be created with below definition:

CREATE TABLE PRIINT_DUPLICATES(
	ASSET_ID nvarchar(40) NOT NULL,
	ASSET_NAME nvarchar(100) NULL,
	DEL_FLAG nvarchar(20) NULL
);
	
