package com.sgnn7.ssl;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

public class CustomTrustManager implements X509TrustManager {

	@Override
	public void checkClientTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		for (int j = 0; j < chain.length; j++) {
			System.out.println("Client certificate information:");
			System.out.println("  Subject DN: " + chain[j].getSubjectDN());
			System.out.println("  Issuer DN: " + chain[j].getIssuerDN());
			System.out
					.println("  Serial number: " + chain[j].getSerialNumber());
			System.out.println("");
		}

	}

	@Override
	public void checkServerTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		for (int j = 0; j < chain.length; j++) {
			System.out.println("Client certificate information:");
			System.out.println("  Subject DN: " + chain[j].getSubjectDN());
			System.out.println("  Issuer DN: " + chain[j].getIssuerDN());
			System.out
					.println("  Serial number: " + chain[j].getSerialNumber());
			System.out.println("");
		}

	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		//Trust
		
		//			Hashtable<String,Vector<X509Certificate>> CAs= store.getCAs();
//			ArrayList<X509Certificate> certs= new ArrayList<X509Certificate>(CAs.size());
//			for (Enumeration<Vector<X509Certificate>> certVectors= CAs.elements(); certVectors.hasMoreElements();) {
//			Vector<X509Certificate> certVector= certVectors.nextElement();
//			certs.addAll(certVector);
//			}
//			X509Certificate[] array= new X509Certificate[certs.size()];
//			return certs.toArray(array); 
//		return {new X509Certificate()};
		return null;
	}
	
//	public boolean checkClientTrusted(java.security.cert.X509Certificate[] chain){
//		return true;
//		}
//		public boolean isServerTrusted(java.security.cert.X509Certificate[] chain){
//		return true;
//		}
//		public boolean isClientTrusted(java.security.cert.X509Certificate[] chain){
//		return true;
//		}
//		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//		return null;
//		}
//		public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {}
//		public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {}
//		}

}
