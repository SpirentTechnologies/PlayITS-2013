package com.testingtech.ttworkbench.play.simulation.car;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcChannel;
import com.google.protobuf.RpcController;
import com.googlecode.protobuf.socketrpc.RpcChannels;
import com.googlecode.protobuf.socketrpc.SocketRpcConnectionFactories;
import com.googlecode.protobuf.socketrpc.SocketRpcController;

public class Socket {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void test() {

		// Create channel
		RpcConnectionFactory connectionFactory = SocketRpcConnectionFactories
				.createRpcConnectionFactory(host, port);
		BlockingRpcChannel channel = RpcChannels
				.newBlockingRpcChannel(connectionFactory);

		// Call service
		BlockingInterface service = MyService.newBlockingStub(channel);
		RpcController controller = new SocketRpcController();
		MyResponse myResponse = service.myMethod(controller, myRequest);

		// Check success
		if (rpcController.failed()) {
			System.err.println(String.format("Rpc failed %s : %s",
					rpcController.errorReason(), rpcController.errorText()));
		}
	}
}
