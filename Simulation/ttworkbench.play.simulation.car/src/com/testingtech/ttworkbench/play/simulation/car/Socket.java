package com.testingtech.ttworkbench.play.simulation.car;

import com.google.protobuf.BlockingRpcChannel;
import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;
import com.googlecode.protobuf.socketrpc.PersistentRpcConnectionFactory;
import com.googlecode.protobuf.socketrpc.RpcChannels;
import com.googlecode.protobuf.socketrpc.RpcConnectionFactory;
import com.googlecode.protobuf.socketrpc.SocketRpcConnectionFactories;
import com.googlecode.protobuf.socketrpc.SocketRpcController;
import com.testingtech.ttworkbench.play.generated.PROTO_API;
import com.testingtech.ttworkbench.play.generated.PROTO_API.carStatusType;

public class Socket implements Runnable {

	/**
	 * @param args
	 *            run in car
	 */
	Car car;

	private int clientPort;
	private String clientHost;

	private RpcController rpcController;

	private PROTO_API.EVENTS.BlockingInterface service;

	public Socket(Car car, int clientPort, String clientHost) {
		this.car = car;
		this.clientHost = clientHost;
		this.clientPort = clientPort;
	}

	public void run() {
		
		createEventsClient(clientPort, clientHost);
		while(!car.isCarDisposed()){
			sendUpdate();
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				break;
			}
		}
		cleanupEventsClient();
	}


	private void cleanupEventsClient() {
		// TODO close the client service and rpccontr
	}

	// Client connection to the server "API"
	// event
	// We return values to "API"
	public void createEventsClient(int port, String host) {
		// Create channel
		RpcConnectionFactory connectionFactory = 
				PersistentRpcConnectionFactory.createInstance(
				SocketRpcConnectionFactories.createRpcConnectionFactory(host, port));

		BlockingRpcChannel channel = RpcChannels.newBlockingRpcChannel(connectionFactory);
		service = PROTO_API.EVENTS.newBlockingStub(channel);
		rpcController = new SocketRpcController();
		
	}

	public void sendUpdate() {
		// Call the cars update methode before the widget needs new information
		// about the car
		car.update();
		// parse the car into a carStatusType message and make the rpc call
		carStatusType request = CarStatusTypeParser.parseToStatusType(car);

		try {
			@SuppressWarnings("unused")
			Object myResponse = service.aPICarStatusType(rpcController, request);
		} catch (ServiceException e) {
			System.out.println("some error with sending the parsed package the car");
			car.disposeCar();
		}

		// Check success
		if (rpcController.failed()) {
			System.err.println(String.format("Rpc failed %s",
					rpcController.errorText()));
		}

	}
}
