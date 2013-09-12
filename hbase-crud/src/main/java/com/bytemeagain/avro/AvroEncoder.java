package com.bytemeagain.avro;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Parser;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;

public class AvroEncoder {
	//http://blog.voidsearch.com/bigdata/apache-avro-in-practice/

	public static void main(String args[]) throws IOException{
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
		Schema s = parser.parse(schemaDescription);


		GenericDatumWriter<GenericRecord> w = new GenericDatumWriter<GenericRecord>(s);

		GenericRecord r = new GenericData.Record(s);
		r.put("name", new org.apache.avro.util.Utf8("Doctor Who"));
		r.put("num_likes", 1);
		r.put("num_photos", 0);
		r.put("num_groups", 423);


		Encoder out = EncoderFactory.get().jsonEncoder(s, new FileOutputStream(new File("test_data.avro")));

		w.write(r, out);
		out.flush();

		System.out.println(r);



	}
}
