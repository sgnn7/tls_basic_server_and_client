package com.sgnn7.client.tls;

import javax.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;

class CustomHostnameVerifier implements HostnameVerifier {
	
	public boolean verify(String hostName, SSLSession session) {
		System.out.println("Server: " + hostName + ":" + session.getPeerPort());
		try {
			X509Certificate[] chain = session.getPeerCertificateChain();
			for( X509Certificate cert: chain){
				
				System.out.println("DN: " + cert.getSubjectDN());
			}
		} catch (SSLPeerUnverifiedException e) {
			e.printStackTrace();
		}	
		System.out.println("-----");
		return true;
	}
}
