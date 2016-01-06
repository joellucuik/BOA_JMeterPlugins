package com.boa.jmeter.reporters;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.BlockingQueue;

import org.apache.jmeter.protocol.jdbc.config.DataSourceElement;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.samplers.SampleEvent;
import org.apache.jmeter.testelement.TestStateListener;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

import com.mysql.jdbc.Driver;

public class MySQLResultsSaver extends ResultCollector implements Runnable, TestStateListener {

    private static final Logger log = LoggingManager.getLoggerForClass();
    // The name of the property used to hold our data
 	public final static String DATA = "MySQLResultsSaver.data"; //$NON-NLS-1$
    
    public MySQLResultsSaver() {
        super();
    }

    @Override
    public void testStarted(String host) {
        super.testStarted(host);
    }

    @Override
    public void testEnded(String host) {
        super.testEnded(host);
    }


    @Override
    public void sampleOccurred(SampleEvent event) {
        super.sampleOccurred(event);
        try {
        	//connect to mysql and store result
        	
        	//get jdbc connection information from JMeter JDBC Connection information
        	Connection conn = (Connection) DataSourceElement.getConnection("jdbcConfig");
        	
        	String threadName = event.getResult().getThreadName();
        	String runAndThread = threadName.split(" ")[2]; //"Thread Group 1-2"
        	String[] runAndThreadArray = runAndThread.split("-");
        	String run = runAndThreadArray[0];
        	String thread = runAndThreadArray[1];
        	Statement sqlStatement = conn.createStatement();
        	sqlStatement.execute(
    			String.format(
	            	"INSERT INTO grinder_data.grinder_test_data " +
	            	"(Thread," +
	            	"RUN," +
	            	"est," +
	            	"StartTimeEPOC," +
	            	"TestTime," +
	            	"Errors," +
	            	"HTTPRespCode," +
	            	"HTTPRestLength," +
	            	"HTTPRespErrors," +
	            	"TimeToResolveHost," +
	            	"TimeToEstablishConnection," +
	            	"TimeToFirstByte," +
	            	"NewConnections," +
	            	"ScenarioID," +
	            	"TransactionName," +
	            	"TestBedID," +
	            	"TestRunID," +
	            	"LoadTypeID," +
	            	"Iteration)" +
	            	"VALUES (%s, %s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)",
	            	"\"" + thread + "\"", /*Thread*/
	            	run, //RUN event.getResult().
	            	"0", 
	            	event.getResult().getStartTime(), //"FROM_UNIXTIME(stattimeepoc / 1000)"
	            	event.getResult().getTime(),
	            	event.getResult().getErrorCount(), //<{Errors: }>,
	            	Integer.parseInt(event.getResult().getResponseCode()), //<{HTTPRespCode: }>,
	            	event.getResult().getResponseData().length, //<{HTTPRestLength: }>,
	            	"0", //<{HTTPRespErrors: }>, "What is this?"
	            	event.getResult().getConnectTime(), //<{TimeToResolveHost: }>, "Not sure this is correct"
	            	event.getResult().getConnectTime(), //<{TimeToEstablishConnection: }>, //"Not sure this is correct"
	            	"0", //<{TimeToFirstByte: }>,
	            	"0", //<{NewConnections: }>,
	            	"\"\"", //<{ScenarioID: }>,
	            	"\"\"", //<{TransactionName: }>,
	            	"0", //<{TestBedID: }>,
	            	"0", //<{TestRunID: }>,
	            	"0", //<{LoadTypeID: }>,
	            	event.getResult().getSampleCount())); //<{Iteration); 
	            	// 1)); //<{Iteration);

        	
        	conn.close();
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public void run() {
    }


}
