package com.sgnn7.server;

import java.net.ServerSocket;

public interface IServer {
	abstract ServerSocket getServerSocket() throws Exception;
}