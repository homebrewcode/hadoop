package com.bytemeagain.avro;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Parser;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;




public class AvroDecoder {
	//http://blog.voidsearch.com/bigdata/apache-avro-in-practice/
	
	public static void main(String[] args) throws Exception {
		FileInputStream fileInputStream = new FileInputStream(new File("binary_test_data.bin"));
		String facebookAvroFile = "src/main/resources/facebook.avsc";		
		System.out.println(AvroDecoder.binaryDecoder(fileInputStream,facebookAvroFile));
		
		
	}
	
	
	public static Object binaryDecoder(InputStream in, String avroFileURI) throws Exception{
		//Create a schema to parse the avro data		
		Parser parser = new Parser();
		Schema schema = parser.parse(new File(avroFileURI));

		//Create a generic data reader
		GenericDatumReader<Object> recordReader = new GenericDatumReader<Object>(schema);
		
		/*FileInputStream fileInputStream = new FileInputStream(new File("binary_test_data.bin"));*/
		Decoder decoder = DecoderFactory.get().binaryDecoder(in, null);
		
		/*GenericRecord record = new GenericData.Record(schema);*/		
		return recordReader.read(null, decoder);
		
	}
	
	
}
