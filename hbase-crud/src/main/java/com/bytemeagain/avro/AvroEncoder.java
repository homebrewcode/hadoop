package com.bytemeagain.avro;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Parser;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.io.JsonEncoder;

public class AvroEncoder {
	//http://blog.voidsearch.com/bigdata/apache-avro-in-practice/

	public static void main(String args[]) throws IOException{

		AvroEncoder.jsonEncode();
		AvroEncoder.binaryEncode();


	}


	private static void jsonEncode() throws IOException{
		System.out.println("JSON encoding avro data..");
		
		//Schema
		String schemaDescription =
			" {    \n" +
			" \"name\": \"FacebookUser\", \n" +
			" \"type\": \"record\",\n" +
			" \"fields\": [\n" +
			"   {\"name\": \"name\", \"type\": \"string\"},\n" +
			"   {\"name\": \"num_likes\", \"type\": \"int\"},\n" +
			"   {\"name\": \"num_photos\", \"type\": \"int\"},\n" +
			"   {\"name\": \"num_groups\", \"type\": \"int\"} ]\n" +
			"}";

		Parser parser = new Parser(); 
		Schema schema = parser.parse(schemaDescription);

		//Data that needs to be written
		GenericRecord record = new GenericData.Record(schema);
		record.put("name", new org.apache.avro.util.Utf8("Doctor Who"));
		record.put("num_likes", 1);
		record.put("num_photos", 0);
		record.put("num_groups", 423);

		//Writer
		GenericDatumWriter<GenericRecord> writer = new GenericDatumWriter<GenericRecord>(schema);
		Encoder jsonEncoder = EncoderFactory.get().jsonEncoder(schema, new FileOutputStream(new File("test_data.txt")));

		writer.write(record, jsonEncoder);
		jsonEncoder.flush();
		
		System.out.println("JSON encoding avro data. DONE!");
		
	}

	private static void binaryEncode() throws IOException{
		System.out.println("Binary encoding avro data");
		
		//Create a parser for an avro format
		Parser parser = new Parser(); 
		Schema schema = parser.parse(new File("src/main/resources/facebook.avsc"));

		//Create a writer 
		GenericDatumWriter<GenericRecord> genericWriter = new GenericDatumWriter<GenericRecord>(schema);

		//Create a record to be written
		GenericRecord record = new GenericData.Record(schema);
		record.put("name", new org.apache.avro.util.Utf8("Doctor Who"));
		record.put("num_likes", 1);
		record.put("num_photos", 0);
		record.put("num_groups", 423);
		
		//Create an encoder 
		FileOutputStream fileStream = new FileOutputStream(new File("binary_test_data.bin"));
		JsonEncoder jsonEncoder = EncoderFactory.get().jsonEncoder(schema, fileStream);
		BinaryEncoder binaryEncoder = EncoderFactory.get().binaryEncoder(fileStream, null);
		
		genericWriter.write(record, binaryEncoder);
		
		binaryEncoder.flush();
		jsonEncoder.flush();
		System.out.println("Binary encoding avro data. DONE!");
	}


	

}
