package com.testingtech.ttworkbench.play.simulation.car;

import java.io.Closeable;

import org.eclipse.swt.widgets.Display;

import com.google.protobuf.BlockingRpcChannel;
import com.google.protobuf.RpcController;
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

	private BlockingRpcChannel channel;

	private RpcConnectionFactory connectionFactory;

	public Socket(Car carInit, int clientPort, String clientHost) {
		this.car = carInit;
		this.clientHost = clientHost;
		this.clientPort = clientPort;

	
	}

	public void run() {
		
		createEventsClient(clientPort, clientHost);
		while(!car.isCarDisposed()){
			// Call the cars update method before the widget needs new information
			// about the car
			car.update();
			//send updated information to widget
			sendUpdate();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				break;
			}
		}
		cleanupEventsClient();
	}


	private void cleanupEventsClient() {

	    if (service != null) {
	      try {
	        if (connectionFactory instanceof Closeable) {
	          ((Closeable) connectionFactory).close();
	        }
	        connectionFactory = null;
	        channel = null;
	        service = null;
	        rpcController = null;
	      } catch (Throwable e) {
	        // ignore exception
	      }
	    }

	}

	// Client connection to the server "API"
	// event
	// We return values to "API"
	public void createEventsClient(int port, String host) {
		connectionFactory = PersistentRpcConnectionFactory.createInstance(
		SocketRpcConnectionFactories.createRpcConnectionFactory(host, port));

		channel = RpcChannels.newBlockingRpcChannel(connectionFactory);
		service = PROTO_API.EVENTS.newBlockingStub(channel);
		rpcController = new SocketRpcController();
	}

	public void sendUpdate() {
		if (car.isCarDisposed() || rpcController == null) {
			return;
		}

		// parse the car into a carStatusType message and make the rpc call
		carStatusType request = CarStatusTypeParser.parseToStatusType(car);

		if (car.isCarDisposed() || rpcController == null) {
			return;
		}
		try {
			service.aPICarStatusType(rpcController, request);

			// Check success
			if (rpcController.failed()) {
				System.err.println(String.format("Rpc failed %s",
						rpcController.errorText()));
			}
		} catch (Throwable e) {
			System.out.println("some error with sending the parsed package the car");
			car.disposeCar();
			cleanupEventsClient();
		}

	}
}
