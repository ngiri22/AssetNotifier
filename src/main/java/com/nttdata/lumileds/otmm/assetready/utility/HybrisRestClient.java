package com.nttdata.lumileds.otmm.assetready.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HybrisRestClient {

	private static final Log log = LogFactory.getLog(HybrisRestClient.class);


	public void callHybrisNotifier(Map<String, String> productCodeMap) {

		for (Map.Entry<String, String> productCodeEntrySet : productCodeMap.entrySet()) {

			String hybrisURLString = 
					MetadataConstants.hybrisURL +				
					MetadataConstants.QMARK +
					"productCode" +
					MetadataConstants.EQUALS +
					productCodeEntrySet.getValue() +
					MetadataConstants.AMPERSAND +
					"assetId" +
					MetadataConstants.EQUALS +
					productCodeEntrySet.getKey() ;

			log.debug("Assetready workflow URL is: " + hybrisURLString);


			for (int i=0 ;  i < MetadataConstants.RETRY_COUNT ; i++) {

				try {

					URL hybrisURL = new URL (
							hybrisURLString);

					HttpURLConnection conn = (HttpURLConnection) 
							hybrisURL.openConnection();


					conn.setRequestMethod(MetadataConstants.GET);
					conn.setRequestProperty(MetadataConstants.ACCEPT, 
							MetadataConstants.XML_MIME_TYPE);

					conn.setConnectTimeout(MetadataConstants.CONNECT_TIMEOUT);
					conn.setReadTimeout(MetadataConstants.READ_TIMEOUT);


					if (conn.getResponseCode() != 200) {

						log.error("Retry Count : " + i);
						log.error("Failed : HTTP error code : "
								+ conn.getResponseCode());
						continue;

					}

					BufferedReader br = new BufferedReader(
							new InputStreamReader(
									(conn.getInputStream())
									)
							);

					String output;

					log.debug("Output from Server .... \n");

					while ((output = br.readLine()) != null) {
						log.debug(output);
					}

					conn.disconnect();

					break;

				} catch (SocketTimeoutException sockEx) {

					log.error("Retry Count : " + i);
					log.error("Socket Timeout exception : " + sockEx.getMessage());
					continue;

				} catch (MalformedURLException malEx) {

					log.error("Malformed URL Exception : " + malEx.getMessage());
					break;

				}
				catch (IOException ioEx) {

					log.error("Retry Count : " + i);
					log.error("Input Output Exception : " + ioEx.getMessage());
					continue;


				}
			}
		}

	}

}
