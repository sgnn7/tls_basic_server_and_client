package com.sgnn7.server.tls;

import java.io.FileInputStream;
import java.net.ServerSocket;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.net.ServerSocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.TrustManagerFactory;

import com.sgnn7.server.HttpServer;

public class TLSSecuredServer extends HttpServer {
	static int SSL_PORT = 443;
	
	String serverKeystore = "server.keystore";
	char[] keystorePassword = "changeit".toCharArray();
	char[] keyPassword = "changeit".toCharArray();

	String serverTruststore = "server.truststore";
	char[] truststorePassword = "changeit".toCharArray();

	boolean requireClientAuthentication;

	public static void main(String args[]) {
		TLSSecuredServer server = new TLSSecuredServer();
		
		try {
			for (String protocol : SSLContext.getDefault()
					.getSupportedSSLParameters().getProtocols()) {
				System.out.println("Protocol supported: " + protocol);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		server.start();
	}

	public TLSSecuredServer(String name, int port,
			boolean requireClientAuthentication) {
		super(name, port);
		this.requireClientAuthentication = requireClientAuthentication;
	}

	public TLSSecuredServer() {
		this("TLS_Server", SSL_PORT, true);
	}

	public ServerSocket getServerSocket() throws Exception {
		KeyStore keystore = KeyStore.getInstance("JKS");
		keystore.load(new FileInputStream(serverKeystore), keystorePassword);
		KeyManagerFactory keyManagerFactory = KeyManagerFactory
				.getInstance("SunX509");
		keyManagerFactory.init(keystore, keyPassword);

		SSLContext sslContext = SSLContext.getInstance("TLSv1");

		KeyStore trustStore = KeyStore.getInstance("JKS");
		trustStore.load(new FileInputStream(serverTruststore),
				truststorePassword);
		TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
		trustManagerFactory.init(trustStore);

		sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory
				.getTrustManagers(), new SecureRandom());
		ServerSocketFactory secureSocketFactory = sslContext
				.getServerSocketFactory();

		SSLServerSocket serverSocket = (SSLServerSocket) secureSocketFactory
				.createServerSocket(listenerPort);
		serverSocket.setNeedClientAuth(requireClientAuthentication);

		return serverSocket;
	}
}
