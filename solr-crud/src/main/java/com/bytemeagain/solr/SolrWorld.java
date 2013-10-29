package com.bytemeagain.solr;

import java.net.MalformedURLException;

import org.apache.commons.lang.time.StopWatch;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.LBHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;

public class SolrWorld {
	private static final String FL = "fl";

	private static final String Q = "q";
	
	private static final String ROW_COUNT = "1000";
	
	public static void main(String[] args) throws MalformedURLException, SolrServerException, InterruptedException {
		String solrAsupShardUrl = "http://rtpwil-dse-qa01.rtp.netapp.com:8983/solr/R2B2.ASUP";
		LBHttpSolrServer solrServer =  new LBHttpSolrServer(solrAsupShardUrl);
		
		
		
		ModifiableSolrParams sQParams = new ModifiableSolrParams();

		sQParams.set(FL, "asup_id,asup_subject,biz_key,secure_flag,sequence_number,source_type,sys_serial_no,system_id");
		sQParams.set(Q, "*:*");
		sQParams.set("rows", ROW_COUNT);
		
		
		
		//Start stopwatch
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		QueryResponse solrResponse = solrServer.query(sQParams,METHOD.POST);		
		SolrDocumentList solrResults = solrResponse.getResults();
		
		//Print Query time
		System.out.println("############# Query Time");
		stopWatch.split();
		printTime(stopWatch.getSplitTime());
		
		
		int count = 0;		
		for (SolrDocument solrDocument : solrResults) {
			//System.out.println("Document number: "+ count++);
			count++;
			/*System.out.println(solrDocument.getFieldNames().toString());
			System.out.println(solrDocument.getFieldValue("biz_key"));*/
			System.out.println(solrDocument);
		}
		System.out.println("############# Render Time");
		stopWatch.split();
		printTime(stopWatch.getSplitTime());
		System.out.println("Total number of counts: "+count);
		stopWatch.stop();
		
		
		

	}
	
	private static void printTime(long ms){
		System.out.println("Seconds: "+ (ms/1000)%60);
		System.out.println("Minutes: "+ (ms/(1000*60))%60);
		System.out.println("Hours:   "+ (ms/(1000*60*60))%24);
	}

}
