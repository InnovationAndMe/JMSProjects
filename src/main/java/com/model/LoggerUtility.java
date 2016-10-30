package main.java.com.model;



import main.java.com.web.JMSReceiver;
import main.java.com.web.JMSSender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtility {

	private static final Logger senderLogger = LoggerFactory.getLogger(JMSSender.class);
	private static final Logger receiverLogger = LoggerFactory.getLogger(JMSReceiver.class);

	public static Logger getSenderlogger() {
		return senderLogger;
	}

	public static Logger getReceiverlogger() {
		return receiverLogger;
	}
}
