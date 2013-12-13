package com.bytemeagain.flume.source;

import java.net.InetSocketAddress;
import java.util.List;

import org.apache.avro.AvroRemoteException;
import org.apache.avro.ipc.NettyServer;
import org.apache.avro.ipc.Responder;
import org.apache.avro.ipc.Server;
import org.apache.avro.ipc.specific.SpecificResponder;
import org.apache.flume.ChannelException;
import org.apache.flume.Context;
import org.apache.flume.EventDrivenSource;
import org.apache.flume.conf.Configurable;
import org.apache.flume.source.AbstractSource;
import org.apache.flume.source.avro.AvroFlumeEvent;
import org.apache.flume.source.avro.AvroSourceProtocol;
import org.apache.flume.source.avro.Status;

import com.bytemeagain.flume.common.NetConstants;
import com.bytemeagain.flume.util.AvroEventHelper;

public class CustomSource extends AbstractSource implements Configurable, EventDrivenSource, AvroSourceProtocol{

	Server server;

	public void configure(Context context) {
		//Nothing to configure :(

	}

	@Override
	public synchronized void start() {
		//This method is called by flume lifecycle manager
		//Creating and starting a server
		Responder responder = new SpecificResponder(AvroSourceProtocol.class, this);
		server = new NettyServer(responder, new InetSocketAddress(NetConstants.hostName, NetConstants.portNumber));
		server.start();
		
		super.start();
	}

	@Override
	public synchronized void stop() {
		//This method is called by flume lifecycle manager
		if(server!=null){
			server.close();
		}
		super.stop();
	}


	public Status append(AvroFlumeEvent event) throws AvroRemoteException {
		try {
			getChannelProcessor().processEvent(AvroEventHelper.getSimpleEvent(event));
		} catch (ChannelException ex) {
			return Status.FAILED;
		}
		return Status.OK;
	}

	public Status appendBatch(List<AvroFlumeEvent> events)
			throws AvroRemoteException {
		//This method is called by flume lifecycle manager
		// No implementation because the client is not submitting the events in
		// batches
		return Status.UNKNOWN;
	}

}
