package com.testingtech.ttworkbench.play.simulation.car;

import java.util.concurrent.Executors;

import com.google.protobuf.BlockingRpcChannel;
import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;
import com.googlecode.protobuf.socketrpc.RpcChannels;
import com.googlecode.protobuf.socketrpc.RpcConnectionFactory;
import com.googlecode.protobuf.socketrpc.RpcServer;
import com.googlecode.protobuf.socketrpc.ServerRpcConnectionFactory;
import com.googlecode.protobuf.socketrpc.SocketRpcConnectionFactories;
import com.googlecode.protobuf.socketrpc.SocketRpcController;
import com.testingtech.ttworkbench.play.generated.PROTO_API;
import com.testingtech.ttworkbench.play.generated.PROTO_API.carStatusType;

public class Socket {

	/**
	 * @param args
	 *            run in car
	 */
	Car car;

	public Socket(Car car) {
		this.car = car;
	}

	void run(int clientPort, String clientHost, int serverPort) {
		startActionService(serverPort);
		createEventsClient(clientPort, clientHost);
	}
	//-------------------------------------
	// FIXME car.update should be limited somehow to every second or so, maybe
	// this function should be limited in total to be called only every second
	// to minimize traffic and let the car actually drive a bit
	//-------------------------------------
	// Client connection to the server "API"
	// event
	// We return values to "API"
	public void createEventsClient(int port, String host) {
		// Create channel
		RpcConnectionFactory connectionFactory = SocketRpcConnectionFactories
				.createRpcConnectionFactory(host, port);
		BlockingRpcChannel channel = RpcChannels
				.newBlockingRpcChannel(connectionFactory);

		// Call service
		PROTO_API.EVENTS.BlockingInterface service = PROTO_API.EVENTS
				.newBlockingStub(channel);
		RpcController rpcController = new SocketRpcController();

		// Call the cars update methode before the widget needs new information
		// about the car
		car.update();
		/*
		 * TODO
		 * 		if (car.destroyCar)
		 * 			kill this Socket instance
		 */
		// parse the car into a carStatusType message and make the rpc call
		carStatusType request = CarStatusTypeParser.parseToStatusType(car);

		try {
			Object myResponse = service
					.aPICarStatusType(rpcController, request);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			System.out
					.println("some error with sending the parsed package the car");
			e.printStackTrace();
		}

		// Check success
		if (rpcController.failed()) {
			System.err.println(String.format("Rpc failed %s",
					rpcController.errorText()));
		}
	}

	// Server, the "API" connects to us
	// action
	// "API" sets values
	public void startActionService(int port) {
		// Start server
		ServerRpcConnectionFactory rpcConnectionFactory = SocketRpcConnectionFactories
				.createServerRpcConnectionFactory(port);
		RpcServer server = new RpcServer(rpcConnectionFactory,
				Executors.newFixedThreadPool(10), true);
		ActionsServiceImpl asi = new ActionsServiceImpl(car);
		server.registerBlockingService(PROTO_API.ACTIONS
				.newReflectiveBlockingService(asi)); // For blocking impl
		server.run();
		
	}
}
