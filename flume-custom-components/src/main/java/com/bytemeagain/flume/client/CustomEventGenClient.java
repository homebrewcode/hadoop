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

public class CustomEventGenClient {

	RpcClient rpcClient;
	String hostName;
	Integer portNumber;

	public CustomEventGenClient(String hostName, Integer portNumber){
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

		//Populate header
		Map<String,String> headers = new HashMap<String, String>();
		headers.put("timestamp", String.valueOf(System.currentTimeMillis()));
		newEvent.setHeaders(headers);

		//Populate body
		String randomNumber = "Random "+Math.floor(Math.abs(Math.random())*10);
		newEvent.setBody(randomNumber.getBytes());

		return newEvent;
	}

	public static void main(String[] args) throws EventDeliveryException {
		CustomEventGenClient eventGenClient = new CustomEventGenClient(NetConstants.hostName,NetConstants.portNumber);
		Event newEvent = eventGenClient.createEvent();
		ConsoleWriter.printEvent(newEvent);
		eventGenClient.postEvent(newEvent);
	}


}
