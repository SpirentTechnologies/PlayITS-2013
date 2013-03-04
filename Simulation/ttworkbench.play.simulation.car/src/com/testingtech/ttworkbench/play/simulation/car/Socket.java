package com.testingtech.ttworkbench.play.simulation.car;



import java.util.concurrent.Executors;

import com.google.protobuf.BlockingRpcChannel;
import com.google.protobuf.RpcController;
import com.googlecode.protobuf.socketrpc.RpcChannels;
import com.googlecode.protobuf.socketrpc.RpcConnectionFactory;
import com.googlecode.protobuf.socketrpc.RpcServer;
import com.googlecode.protobuf.socketrpc.ServerRpcConnectionFactory;
import com.googlecode.protobuf.socketrpc.SocketRpcConnectionFactories;
import com.googlecode.protobuf.socketrpc.SocketRpcController;
import com.testingtech.ttworkbench.play.generated.PROTO_SUTAPI;




public class Socket {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


	void test(){
		int port = 13333;
		startActionService(port);
		createEventsClient(port, host);
	}


	private void createEventsClient(int port, String host) {
		// Create channel
		RpcConnectionFactory connectionFactory = SocketRpcConnectionFactories
		    .createRpcConnectionFactory(host, port);
		BlockingRpcChannel channel = RpcChannels.newBlockingRpcChannel(connectionFactory);

		// Call service
		PROTO_SUTAPI.EVENTS.BlockingInterface service = PROTO_SUTAPI.EVENTS.newBlockingStub(channel);
		RpcController rpcController = new SocketRpcController();
		MyResponse myResponse = service.sendStatus(rpcController, myRequest);

		// Check success
		if (rpcController.failed()) {
		  System.err.println(String.format("Rpc failed %s",
		      rpcController.errorText()));
		}		
	}


	private void startActionService(int port) {
		// Start server
		ServerRpcConnectionFactory rpcConnectionFactory = SocketRpcConnectionFactories.createServerRpcConnectionFactory(port);
		RpcServer server = new RpcServer(rpcConnectionFactory, 
		    Executors.newFixedThreadPool(10), true);
		server.registerBlockingService(PROTO_SUTAPI.ACTIONS.newReflectiveBlockingService(new ActionsServiceImpl())); // For blocking impl
		server.run();
	}
}

