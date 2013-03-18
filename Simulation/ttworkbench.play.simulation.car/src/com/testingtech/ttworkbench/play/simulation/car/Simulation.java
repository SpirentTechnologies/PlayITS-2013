package com.testingtech.ttworkbench.play.simulation.car;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;

import com.googlecode.protobuf.socketrpc.RpcServer;
import com.googlecode.protobuf.socketrpc.ServerRpcConnectionFactory;
import com.googlecode.protobuf.socketrpc.SocketRpcConnectionFactories;
import com.testingtech.ttworkbench.play.generated.PROTO_API;

public class Simulation {

	private RpcServer server;

	private static ArrayList<GPSposition> getMap(File mapFile) {
		try {
			return KMLparser.parseFile(mapFile);
		} catch (NumberFormatException e) {
			System.out.println(MessageFormat.format(
					"The file format was wrong", mapFile.getAbsolutePath()));
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.out
					.println(MessageFormat.format(
							"Map file does the file exist: ",
							mapFile.getAbsolutePath()));
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Some I/O operations went wrong");
			e.printStackTrace();
		} catch (CannotParseFileException e) {
			System.out.println("The Mapfile format is not supported");
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {

		if (args.length == 1) {
			int index = 0;

			int serverPort = parsePort(args, index++);

			// a service implementation should be able to handle multiple cars
			// that's why map file, client host and port should be
			// delivered by the initCarType or another init function

			new Simulation().startServerBlocking(serverPort);

		} else {
			System.out
					.println("Usage: java -jar Simulation.jar <serverPort>");
		}
	}

	private void startServerBlocking(int serverPort) {
		createServer(serverPort);
		server.run();
	}

	private static int parsePort(String[] args, int argIndex)
			throws IllegalArgumentException {
		if (argIndex >= args.length) {
			throw new IllegalArgumentException(
					"Missing port argument at index " + argIndex);
		}
		try {
			int portNumber = Integer.parseInt(args[argIndex]);
			return portNumber;
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid port " + args[argIndex]);
		}
	}

	public void startServer(int p_serverPort) throws IOException {
		createServer(p_serverPort);
		server.startServer();
	}

	private void createServer(int p_serverPort) {
		CarModel carModel = new CarModel();

		ServerRpcConnectionFactory rpcConnectionFactory = SocketRpcConnectionFactories
				.createServerRpcConnectionFactory(p_serverPort);
		server = new RpcServer(rpcConnectionFactory,
				Executors.newFixedThreadPool(10), true);
		ActionsServiceImpl asi = new ActionsServiceImpl(carModel);
		server.registerBlockingService(PROTO_API.ACTIONS
				.newReflectiveBlockingService(asi)); // For blocking impl
	}

	public void stopServer() throws IOException {
		if (server != null) {
			server.shutDown();
		}
		server = null;
	}

}
