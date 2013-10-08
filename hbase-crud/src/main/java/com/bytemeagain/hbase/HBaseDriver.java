package com.bytemeagain.hbase;

import java.io.ByteArrayInputStream;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import com.bytemeagain.avro.AvroDecoder;

public class HBaseDriver {

	public static void main(String[] args) throws Exception{
		//C-Create a table
		//C-Create a row i.e. Insert elements into the row
		
		//R-Remove a row
		//R-Remove a table
		
		//U-Update a record by row-key
		
		//D-Delete a table
		
		//Check if the table exists
		
		
		Result hBaseResult = HBaseCrud.INSTANCE.get("ASUP_HISTORY_LOG_DATA", "C|00000000-0000-0000-0000-000000000000|8619241019|4034389-06-2", "OBJECT", "EVENTS");
		
		byte[] result = hBaseResult.getValue(Bytes.toBytes("OBJECT"), Bytes.toBytes("EVENTS"));
		//System.out.println(Bytes.toString(result));
		
		System.out.println("\n\n\n\n\n");
		System.out.println(AvroDecoder.binaryDecoder(new ByteArrayInputStream(result), "src/main/resources/EVENTS_1.0.avsc"));
		
		
		
	}

}
