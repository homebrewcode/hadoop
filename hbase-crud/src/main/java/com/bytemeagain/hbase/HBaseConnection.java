package com.bytemeagain.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;

public enum HBaseConnection {	
	INSTANCE,
	INSTANCE2,
	INSTANCE3;
	
	
	private  Configuration conf;
	private HBaseAdmin hbaseAdmin;

	private HBaseConnection() {
		
		conf = HBaseConfiguration.create();
		conf.addResource(new Path("src/main/resources/hbase-site.xml"));

		try {
			hbaseAdmin = new HBaseAdmin(conf);
		} catch (MasterNotRunningException e) {
			e.printStackTrace();
		} catch (ZooKeeperConnectionException e) {
			e.printStackTrace();
		}

	}

	public Configuration getConf() {
		return conf;
	}

	public void setConf(Configuration conf) {
		this.conf = conf;
	}

	public HBaseAdmin getHbaseAdmin() {
		return hbaseAdmin;
	}

	public void setHbaseAdmin(HBaseAdmin hbaseAdmin) {
		this.hbaseAdmin = hbaseAdmin;
	}

	
}
