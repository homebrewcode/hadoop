package com.bytemeagain.flume.sink;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.flume.Channel;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.conf.Configurable;
import org.apache.flume.sink.AbstractSink;

public class CustomSink extends AbstractSink implements Configurable{

	public Status process() throws EventDeliveryException {
		Channel channel = getChannel();
		try{
			//Start a transaction
			channel.getTransaction().begin();

			Event event = channel.take();
			if(event==null){
				System.out.println("No events to process!!");
			}else{
				System.out.println("######### Event header #########");
				if(event.getHeaders().size() != 0){
					Map<String,String> headers = event.getHeaders();
					for(Entry<String, String> header:headers.entrySet()){
						System.out.println("Header key		: "+header.getKey());
						System.out.println("Header value	: "+header.getValue());
					}
				}else{
					System.out.println("No headers to print!!");
				}
				System.out.println("######### Event body #########");
				System.out.println(Arrays.toString(event.getBody()));
			}

			//Commit transaction
			channel.getTransaction().commit();


		}catch(Exception e){
			return Status.BACKOFF;
		}finally{
			channel.getTransaction().close();
		}

		return Status.READY;
	}

	public void configure(Context context) {
		System.out.println("Nothing to configure!!");
	}

}
