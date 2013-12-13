package com.bytemeagain.flume.client.test;

import java.net.InetSocketAddress;

import org.apache.avro.ipc.NettyServer;
import org.apache.avro.ipc.Responder;
import org.apache.avro.ipc.specific.SpecificResponder;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.source.avro.AvroSourceProtocol;

import com.bytemeagain.flume.client.RandomEventGenClient;
import com.bytemeagain.flume.common.NetConstants;
import com.bytemeagain.flume.util.ConsoleWriter;


public class TestRandomEventGenClient {
	public static void main(String[] args) throws EventDeliveryException {
		
		NettyServer server=null;
		try {
			System.out.println("Opening server connection");
			//Starting the server!!
			Responder responder = new SpecificResponder(AvroSourceProtocol.class, new TestRandomEventGenClient());
			server = new NettyServer(responder, new InetSocketAddress(NetConstants.hostName, NetConstants.portNumber));
			server.start();

			RandomEventGenClient eventGenClient = new RandomEventGenClient(NetConstants.hostName,NetConstants.portNumber);
			Event newEvent = eventGenClient.createEvent();
			eventGenClient.postEvent(newEvent);
			System.out.println("Posted event");
			ConsoleWriter.printEvent(newEvent);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(server!=null){
				System.out.println("Closing server connection");
				server.close();
			}
		}

		
		
	}
}
