package com.bytemeagain.hbase.test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.NavigableMap;
import java.util.Set;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bytemeagain.avro.AvroDecoder;
import com.bytemeagain.hbase.HBaseConnection;
import com.bytemeagain.hbase.HBaseCrud;

public class HBaseTest {


	@BeforeClass
	public static void setupTestSuite(){	
		System.out.println("Init!!");
		//Before running this test your Hbase should have the table
		//Refer to README.txt for DDL and DML's to run this test

	}


	@Test
	public void scanHBaseUsingRowKey() throws IOException{
		//Check if 'FB_PROFILE' exists
		Assert.assertTrue(HBaseCrud.INSTANCE.isTableExists("FB_PROFILE"));

		Scan scan = new Scan();
		scan.setStartRow(Bytes.toBytes("124163"));
		scan.setStopRow(Bytes.toBytes("124167"));

		//Dump the result on the console
		ResultScanner scanner =HBaseCrud.INSTANCE.scanTable("FB_PROFILE", "BASIC", null, scan);
		for (Result result : scanner) {
			System.out.println("--------- Scan using row key ---------");
			NavigableMap<byte[], byte[]> familyMap = result.getFamilyMap(Bytes.toBytes("BASIC"));
			Set<byte[]> qualifiers = familyMap.keySet();
			for (byte[] qualifier : qualifiers) {
				System.out.println("Qualifier: "+Bytes.toString(qualifier)+" Value: "+Bytes.toString(familyMap.get(qualifier)));
			}
			System.out.println("--------------------------");			
		}

	}

	@Test
	public void scanHBaseUsingTimeStamp() throws IOException{

		//Check if 'FB_PROFILE' exists
		Assert.assertTrue(HBaseCrud.INSTANCE.isTableExists("FB_PROFILE"));

		Scan scan = new Scan();
		scan.setTimeRange(new Long("1379909970667"), new Long("1379909990976"));


		//Dump the result on the console
		ResultScanner scanner =HBaseCrud.INSTANCE.scanTable("FB_PROFILE", "BASIC", null, scan);
		for (Result result : scanner) {
			System.out.println("--------- Scan using timestamp ---------");
			NavigableMap<byte[], byte[]> familyMap = result.getFamilyMap(Bytes.toBytes("BASIC"));
			Set<byte[]> qualifiers = familyMap.keySet();
			for (byte[] qualifier : qualifiers) {
				System.out.println("Qualifier: "+Bytes.toString(qualifier)+" Value: "+Bytes.toString(familyMap.get(qualifier)));
			}
			System.out.println("--------------------------");			
		}

	}

	@Test
	public void checkIfTheTableExists() throws IOException{

		//Check if 'FB_PROFILE' exists
		Assert.assertTrue(HBaseCrud.INSTANCE.isTableExists("FB_PROFILE"));

		//Check if the 'fb_profile' is active
		Assert.assertTrue(HBaseConnection.INSTANCE.getHbaseAdmin().isTableEnabled("fb_profile"));

	}

	@Test
	public void getAvroDataAndDecode() throws Exception{

		Result hBaseResult = HBaseCrud.INSTANCE.get("ASUP_HISTORY_LOG_DATA", "C|00000000-0000-0000-0000-000000000000|8619241019|4034389-06-2", "OBJECT", "EVENTS");
		byte[] result = hBaseResult.getValue(Bytes.toBytes("OBJECT"), Bytes.toBytes("EVENTS"));

		System.out.println("\n\n\n\n\n");
		System.out.println(AvroDecoder.binaryDecoder(new ByteArrayInputStream(result), "src/main/resources/EVENTS_1.0.avsc"));


	}

	@Test
	public void copyFromOneTableToAnother() throws Exception{
		
		String sourceTable = "ASUP_CURRENT_WEEKLY";
		String sourceRowKey = "C|731660A9-D2CD-11DE-B05D-123478563412|700000466913";
		String targetTable = "ASUP_CURRENT_WEEKLY123";

		//Read from source table
		Result sourceHBaseResult = HBaseCrud.INSTANCE.get(sourceTable, sourceRowKey, null, null);
		
		//Write to the target table
		if(HBaseCrud.INSTANCE.writeToAnotherTable(targetTable,  sourceHBaseResult)){
			System.out.println("Data successfully stored!!");
		}
	}

	//Test using timestamp scan
	//Test using other scan filters




}
