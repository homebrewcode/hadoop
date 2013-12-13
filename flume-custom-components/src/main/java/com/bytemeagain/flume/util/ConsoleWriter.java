package com.bytemeagain.flume.util;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.flume.Event;

public class ConsoleWriter {

	/**
	 * @param event
	 */
	public static void printEvent(Event event) {
		if(event==null){
			System.out.println("No events to process!!");
		}else{
			System.out.println("------------------------------");
			System.out.println("######### Event header #########");
			if(event.getHeaders().size() != 0){
				Map<String,String> headers = event.getHeaders();
				for(Entry<String, String> header:headers.entrySet()){
					System.out.println(header.getKey()+" : "+ header.getValue());
					/*System.out.println("Header key	: "+header.getKey());
					System.out.println("Header value	: "+header.getValue());*/
				}
			}else{
				System.out.println("No headers to print!!");
			}
			System.out.println("######### Event body #########");
			String body = new String(event.getBody());
			System.out.println(body);
			System.out.println("------------------------------");
		}
	}
	
}
