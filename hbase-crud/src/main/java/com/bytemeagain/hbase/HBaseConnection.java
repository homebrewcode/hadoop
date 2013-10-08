package com.bytemeagain.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.log4j.Logger;

public enum HBaseConnection {	
	INSTANCE;
	
	private Logger logger = Logger.getLogger(HBaseConnection.class);

	private  Configuration conf;
	private HBaseAdmin hbaseAdmin;
	private HTablePool htPool;

	private HBaseConnection() {
		try {
			System.out.println(">>>>>>>>>>>>>>>>>>>> Creating a new HBase connection");
			conf = HBaseConfiguration.create();
			conf.addResource(new Path("src/main/resources/hbase-site.xml"));
			htPool = new HTablePool(conf,10);
			hbaseAdmin = new HBaseAdmin(conf);
			
			htPool.close();
			
		} catch (MasterNotRunningException e) {
			logger.error("Hbase master not running, unable to connect to HBase!", e);
		} catch (ZooKeeperConnectionException e) {
			logger.error("Zookeeper error, unable to connect to HBase!", e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Configuration getConf() {
		return conf;
	}

	public HBaseAdmin getHbaseAdmin() {
		return hbaseAdmin;
	}

	public HTablePool getHtPool() {
		return htPool;
	}
	
}
