package com.testingtech.ttworkbench.play.simulation.car;




import java.util.concurrent.Executors;

import javax.xml.ws.Service;

import com.google.*;

import com.google.protobuf.BlockingRpcChannel;
import com.google.protobuf.Descriptors.MethodDescriptor;
import com.google.protobuf.RpcController;
import com.googlecode.protobuf.socketrpc.RpcChannels;
import com.googlecode.protobuf.socketrpc.RpcConnectionFactory;
import com.googlecode.protobuf.socketrpc.RpcServer;
import com.googlecode.protobuf.socketrpc.ServerRpcConnectionFactory;
import com.googlecode.protobuf.socketrpc.SocketRpcConnectionFactories;
import com.googlecode.protobuf.socketrpc.SocketRpcController;
import com.googlecode.protobuf.socketrpc.SocketRpcProtos.Request;
import com.testingtech.ttworkbench.play.generated.PROTO_API;
import com.testingtech.ttworkbench.play.generated.PROTO_API.ACTIONS.BlockingInterface;


public class Socket {

	/**
	 * @param args
	 * run in car
	 */
	Car car;
	
	public Socket(Car car) {
		this.car = car;
	}

	void test(){
		int port = 13333;
		startActionService(port);
		createEventsClient(port, "localhost");
	}

	//Client connection to the server "API"
	//event
	//We return values to "API"
	public void createEventsClient(int port, String host) {
		// Create channel
		RpcConnectionFactory connectionFactory = SocketRpcConnectionFactories
		    .createRpcConnectionFactory(host, port);
		BlockingRpcChannel channel = RpcChannels.newBlockingRpcChannel(connectionFactory);

		// Call service
		PROTO_API.EVENTS.BlockingInterface service = PROTO_API.EVENTS.newBlockingStub(channel);
		RpcController rpcController = new SocketRpcController();
		/*
		 * message Request {
		 * 
		 * // RPC service full name
		 * required string service_name = 1;
		 * 
		 * // RPC method name
		 * required string method_name = 2;
		 * 
		 * // RPC request proto
		 * required bytes request_proto = 3;
		 * }
		 */
		
		
		
		
		MyResponse myResponse = service.aPICarStatusType(rpcController, request);

		
		// Check success
		if (rpcController.failed()) {
		  System.err.println(String.format("Rpc failed %s",
		      rpcController.errorText()));
		}		
	}

	//Server, the "API" connects to us 
	//action
	//"API" sets values
	public void startActionService(int port) {
		// Start server
		ServerRpcConnectionFactory rpcConnectionFactory = SocketRpcConnectionFactories.createServerRpcConnectionFactory(port);
		RpcServer server = new RpcServer(rpcConnectionFactory, Executors.newFixedThreadPool(10), true);
		ActionsServiceImpl asi = new ActionsServiceImpl(car);
		server.registerBlockingService(PROTO_API.ACTIONS.newReflectiveBlockingService(asi)); // For blocking impl
		server.run();
	}
}

