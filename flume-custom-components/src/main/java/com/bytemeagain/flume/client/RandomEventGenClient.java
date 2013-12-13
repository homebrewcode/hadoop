package com.bytemeagain.flume.client;

import java.util.HashMap;
import java.util.Map;

import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.api.RpcClient;
import org.apache.flume.api.RpcClientFactory;
import org.apache.flume.event.SimpleEvent;

import com.bytemeagain.flume.common.NetConstants;
import com.bytemeagain.flume.util.ConsoleWriter;

public class RandomEventGenClient {

	RpcClient rpcClient;
	String hostName;
	Integer portNumber;
	
	private static String TS = "timestamp";
	private static String ROUTE_TAG_1 = "tag_1";
	private static String ROUTE_TAG_2 = "tag_2";
	private static String ROUTE_ID_1 = "route_1";
	private static String ROUTE_ID_2 = "route_2";
	
	/*
	 All possible routes
		 tag_1 = route_1_one
		 tag_1 = route_1_two
		 tag_2 = route_2_one
		 tag_2 = route_2_two
	 */

	public RandomEventGenClient(String hostName, Integer portNumber){
		this.hostName = hostName;
		this.portNumber = portNumber;
		rpcClient = RpcClientFactory.getDefaultInstance(hostName, portNumber);
	}

	public void start(){
		//Create thread to keep posting the events timely
	}

	public void stop(){
		//All shutdown event(s) to bring down the thread(s) gracefully
	}

	public void postEvent(Event event){
		try {
			if(rpcClient!=null){
				rpcClient.append(event);
			}else{
				System.out.println("RPC Connection not setup!");
			}
		} catch (EventDeliveryException e) {
			if(rpcClient!=null){
				rpcClient.close();
			}
			rpcClient = RpcClientFactory.getDefaultInstance(hostName, portNumber);
		}
	}

	public Event createEvent(){
		Event newEvent = new SimpleEvent();

		//Populate body
		String randomNumber = "Random "+Math.floor(Math.abs(Math.random())*10);
		newEvent.setBody(randomNumber.getBytes());
		
		//Populate header
		Map<String,String> headers = new HashMap<String, String>();
		headers.put(TS, String.valueOf(System.currentTimeMillis()));
		headers.put(ROUTE_TAG_1, getBitRandomRoute(ROUTE_ID_1));
		headers.put(ROUTE_TAG_2,getBitRandomRoute(ROUTE_ID_2));
		newEvent.setHeaders(headers);


		return newEvent;
	}

	private String getBitRandomRoute(String routeId) {
		if(1==getRandomOneOrZero()){
			return routeId+"_one";
		}
		return routeId+"_two";
	}
	
	private int getRandomOneOrZero(){
		int db = (int) Math.floor(Math.abs(Math.random())*10);
		return db%2;
	}

	public static void main(String[] args)  {
		RandomEventGenClient eventGenClient = new RandomEventGenClient(NetConstants.hostName,NetConstants.portNumber);
		Event newEvent = eventGenClient.createEvent();
		System.out.println("Posting event");
		ConsoleWriter.printEvent(newEvent);
		eventGenClient.postEvent(newEvent);
	}


}
