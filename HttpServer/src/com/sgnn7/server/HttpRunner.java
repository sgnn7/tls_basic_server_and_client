package com.sgnn7.server;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

class HttpRunner extends Thread {
	private static final String CRLF = "\r\n";
	private static final String CRLFx2 = CRLF + CRLF;

	Socket clientSocket;

	public HttpRunner(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	public void run() {
		try {
			debugConnectionInfo(clientSocket);

			BufferedOutputStream outputStream = new BufferedOutputStream(clientSocket
					.getOutputStream());
			ServerInputStreamReader inputStream = new ServerInputStreamReader(clientSocket
					.getInputStream());

			RequestParser request = inputStream.getRequest();
			request.debugRequest();

			if (request.isGetRequest())
				fetchFile(request, outputStream);
			
			System.out.println("Client " + clientSocket.getRemoteSocketAddress()+ ":" + clientSocket.getPort() + " done");
			
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void debugConnectionInfo(Socket client) {
		String remoteHost = client.getInetAddress().getHostName();
		String remoteAddr = client.getInetAddress().getHostAddress();
		int remotePort = client.getPort();
		System.out.println("Accepted connection to " + remoteHost + " ("
				+ remoteAddr + ")" + " on port " + remotePort + ".");
	}

	void fetchFile(RequestParser request, BufferedOutputStream outStream)
			throws IOException {
		String fileName = request.getFileName();
		File file = new File("site" + File.separator + fileName);
		// Give them the requested file
		if (file.exists())
			sendFile(outStream, file);
		else
			System.out.println("File " + file.getCanonicalPath()
					+ " does not exist.");
	}

	void sendFile(BufferedOutputStream outputStream, File filename) {
		try {
			DataInputStream dataInputStream = new DataInputStream(new FileInputStream(filename));
			byte bufferredFile[] = new byte[(int) filename.length()];
			dataInputStream.readFully(bufferredFile);
			dataInputStream.close();
			
			outputStream.write("HTTP/1.0 200 OK\r\n".getBytes());
			outputStream.write(("Content-Length: " + bufferredFile.length + "\r\n").getBytes());
			outputStream.write(("Content-Type: text/html").getBytes());
			outputStream.write(CRLFx2.getBytes());
			outputStream.write(bufferredFile);
			outputStream.flush();
			outputStream.close();
			System.out.println("File sent: " + filename.getCanonicalPath());
		} catch (Exception ex) {
			try {
				outputStream.write(("HTTP/1.0 400 " + "File not found" + CRLF).getBytes());
				outputStream.write("Content-Type: text/html".getBytes());
				outputStream.write(CRLFx2.getBytes());
			} catch (IOException e) {
			}
			System.out.println("Error! File not found: " + filename);
		}
	}

}
