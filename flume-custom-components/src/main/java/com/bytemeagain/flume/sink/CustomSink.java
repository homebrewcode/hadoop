package com.bytemeagain.flume.sink;

import org.apache.flume.Channel;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.conf.Configurable;
import org.apache.flume.sink.AbstractSink;

import com.bytemeagain.flume.util.ConsoleWriter;

public class CustomSink extends AbstractSink implements Configurable{
	
	public void configure(Context context) {
		System.out.println("Nothing to configure!!");
	}
	
	public Status process() throws EventDeliveryException {
		Channel channel = getChannel();
		try{
			//Start a transaction
			channel.getTransaction().begin();

			Event event = channel.take();
			//TODO: Process the event here
			ConsoleWriter.printEvent(event);

			//Commit transaction
			channel.getTransaction().commit();


		}catch(Exception e){
			return Status.BACKOFF;
		}finally{
			channel.getTransaction().close();
		}

		return Status.READY;
	}


	

}
