package com.bytemeagain.hbase;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

public enum HBaseCrud {
	INSTANCE;

	public boolean isTableExists(String tableName) throws IOException{		
		return HBaseConnection.INSTANCE.getHbaseAdmin().tableExists(tableName);
	}


	/**
	 * This method returns a ResultScanner object on the tableName HBase table to which 
	 * the Scan filter is applied.
	 * Note: This method is very generic, it should be designed based on the application use-case
	 *  
	 * @param tableName The table that needs to be scanned
	 * @param columnFamily The column family that needs to be queried in the table
	 * @param columnQualifier The specific column of a column family (can be null)
	 * @param scan Scan filter that needs to be applied to the table to filter the scan result
	 * @return resultScanner which can be used to scan through the table
	 * @exception IOException 
	 */
	public ResultScanner scanTable(String tableName, String columnFamily, String columnQualifier,Scan scan) throws IOException{
		/*HTable hTable = new HTable(HBaseConnection.INSTANCE.getConf(), tableName);*/
		HTableInterface hTable = HBaseConnection.INSTANCE.getHtPool().getTable(tableName);

		byte[] colFamilyByteArray;
		byte[] colQualifierByteArray;

		//If the column qualifier is not given then get all the columns in the column family
		if(columnQualifier==null){
			colFamilyByteArray = Bytes.toBytes(columnFamily);
			scan.addFamily(colFamilyByteArray);
		}else{
			colFamilyByteArray = Bytes.toBytes(columnFamily);
			colQualifierByteArray = Bytes.toBytes(columnQualifier);
			scan.addColumn(colFamilyByteArray, colQualifierByteArray);
		}

		scan.setCaching(100);		
		return hTable.getScanner(scan);

	}
	
	public Result get(String tableName,String rowKey, String columnFamily, String columnQualifier) throws IOException{
		HTableInterface hTable = HBaseConnection.INSTANCE.getHtPool().getTable(tableName);
		Get get = new Get(Bytes.toBytes(rowKey));
		get.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(columnQualifier));
		
		return hTable.get(get);
		
	}


}
