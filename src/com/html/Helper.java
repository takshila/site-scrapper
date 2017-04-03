package com.html;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Helper {

	/*
	 * Get Valid url. 
	 * Append http:// transport protocol if missing.
	 */
	public static String getValidURL(String url){
		url = url.toLowerCase();
		if(!url.startsWith("http://") && !url.startsWith("https://")){
			url = "http://"+url;
		}
		return url;
	}

	/*
	 * Check if a url is valid.
	 */
	public static boolean isValidURL(String url){
		String[] schemes = {"http","https"};
		UrlValidator urlValidator = new UrlValidator(schemes);
		if (urlValidator.isValid(url)) {
			return true;
		}
		return false;
	}

	/*
	 * Get Document after connecting to the url.
	 */
	public static Document getDocument(String url) throws IOException, KeyManagementException, NoSuchAlgorithmException{

		if(url.startsWith("https://")){
			return getHTTPSDocument(url);
		}

		return Jsoup.connect(url)
				.userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
				.get();
	}

	/*
	 * Get Document from HTTPS URL
	 */
	public static Document getHTTPSDocument(String urlString) throws IOException, KeyManagementException, NoSuchAlgorithmException{
		// Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
        };

		// Install the all-trusting trust manager
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		// Create all-trusting host name verifier
		HostnameVerifier allHostsValid = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};

		// Install the all-trusting host verifier
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		
		URL url = new URL(urlString);
		URLConnection con = url.openConnection();
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0");
		
		return Jsoup.parse(con.getInputStream(), null, urlString);
	}
}