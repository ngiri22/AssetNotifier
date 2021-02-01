<h2> AssetNotifier</h2>

It is the implementation of AssetInterceptor interface.

Intercepts the asset on import and checks if it is coming from PriintSuite.

If the file is from PriintSuite:
	<li> Checks if the asset is imported into priintsuite-drafts or PIS or PRS or CPIS folder.
	<li> If there already exists an asset with the same name in these folders, it does not take any action (i.e., it is a version).
	<li> If it is not a version and it's a original asset, it makes a call to Hybris rest api to associate the asset with Product Code.
	
<h4> Make sure the folder id of the folders and hybris urls are set correctly in the MetadataConstants.java file according to the environment.<h4>

<!--
Also the table priint_duplicates should be created with below definition:

CREATE TABLE PRIINT_DUPLICATES(
	ASSET_ID nvarchar(40) NOT NULL,
	ASSET_NAME nvarchar(100) NULL,
	DEL_FLAG nvarchar(20) NULL
);
-->
