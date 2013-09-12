package com.bytemeagain.hbase;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

public enum HBaseCrud {
	INSTANCE;

	public boolean isTableExists(String tableName) throws IOException{
		return HBaseConnection.INSTANCE.getHbaseAdmin().tableExists(tableName);
	}

	//Create Read Update Delete
	//Read data from a table
	public void scanTable(String tableName, String columnFamily, String columnQualifier) throws IOException{
		HTable hTable = new HTable(HBaseConnection.INSTANCE.getConf(), tableName);
		Scan scan = new Scan();

		byte[] colFamilyByteArray = Bytes.toBytes(columnFamily);
		byte[] colQualifierByteArray = Bytes.toBytes(columnQualifier);

		scan.addColumn(colFamilyByteArray, colQualifierByteArray);
		//Setting filters
		scan.setTimeStamp(new Long("1366071965709"));
		scan.setStartRow(Bytes.toBytes("N|DC068407-A630-911C-DB42-3B86529546FC|8667945998|130734922"));
		scan.setStopRow(Bytes.toBytes("N|DC068407-A630-911C-DB42-3B86529546FC|8667945998|130734922"));

		ResultScanner scanner = hTable.getScanner(scan);

		int count=0;
		for (Result result : scanner) {
			/*byte[] valueBytes = result.getValue(colFamilyByteArray, colQualifierByteArray);
			String value = new String(valueBytes);
			System.out.println(value);*/
			System.out.println("--");
			count++;			
		}
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+count);
	}


}
