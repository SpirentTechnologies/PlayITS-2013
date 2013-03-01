package com.testingtech.ttworkbench.play.simulation.car;



import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;




public class Socket {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	void test(){
	
	// Create a thread pool
	ExecutorService threadPool = Executor.newFixedThreadPool(1);

	// Create channel
	RpcConnectionFactory connectionFactory = SocketRpcConnectionFactories
	    .createRpcConnectionFactory(host, port);
	RpcChannel channel = RpcChannels.newRpcChannel(connectionFactory, threadPool);

	// Call service
	MyService myService = MyService.newStub(channel);
	RpcController controller = new SocketRpcController();
	myService.myMethod(rpcController, myRequest,
	    new RpcCallback<MyResponse>() {
	      public void run(MyResponse myResponse) {
	        System.out.println("Received Response: " + myResponse);
	      }
	    });
	    
	// Check success
	if (rpcController.failed()) {
	  System.err.println(String.format("Rpc failed %s : %s", rpcController.errorReason(),
	      rpcController.errorText()));
	}
	}
}

}
