package com.bytemeagain.flume.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.flume.Event;
import org.apache.flume.event.EventBuilder;
import org.apache.flume.event.SimpleEvent;
import org.apache.flume.source.avro.AvroFlumeEvent;

public class AvroEventHelper {

	public static String getStringBody(AvroFlumeEvent avroEvent){
		return new String(avroEvent.getBody().array());
	}

	public static Map<String,String> getStringHeaderMap(AvroFlumeEvent avroEvent){
		Map<String,String> stringHeaderMap = new HashMap<String, String>();

		for (Entry<CharSequence, CharSequence> avroHeader : avroEvent
				.getHeaders().entrySet()) {
			//Populating the String HashMap
			stringHeaderMap.put(String.valueOf(avroHeader.getKey()),
					String.valueOf(avroHeader.getValue()));

		}
		return stringHeaderMap;
	}

	public static Event getSimpleEvent(AvroFlumeEvent avroEvent){
		if(avroEvent!=null && avroEvent.getBody() !=null){
			return EventBuilder.withBody(avroEvent.getBody().array(),
					getStringHeaderMap(avroEvent));
		}
		return new SimpleEvent();
	}

}
