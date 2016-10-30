package main.java.com.web;

import java.io.IOException;

import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.com.model.LoggerUtility;




@WebServlet(name = "MessageReceiver", urlPatterns = "/receiveMessage")
public class JMSReceiver extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6787438639552307192L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LoggerUtility.getReceiverlogger().debug("Inside doGet()");
		String message = receiveMessages();
		if (null!=message){
			if(message.isEmpty()) {
				request.setAttribute("tableRecieveResponse", "No message Received.");
			} else {
				request.setAttribute("tableRecieveResponse", "Message received is "+ message);
			}
		}
        RequestDispatcher rd=request.getRequestDispatcher("/view/receiveMsg.jsp");  
        rd.forward(request, response);  
		
	}

	private String receiveMessages() {
		String textMessage = null;
		try {
			InitialContext initCtx = new InitialContext();
			QueueConnectionFactory connectionFactory = (QueueConnectionFactory) initCtx
					.lookup("java:comp/env/jms/ConnectionFactory");
			QueueConnection queueConnection = connectionFactory.createQueueConnection();
			QueueSession queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = (Queue) initCtx.lookup("java:comp/env/jms/queue/TestQueue");
			QueueReceiver receiver = queueSession.createReceiver(queue);

			queueConnection.start();
			try {
				Message message = receiver.receive(10000);
				if (message == null){
					LoggerUtility.getReceiverlogger().debug("No messages have been received");
				}else {
					textMessage = ((TextMessage) message).getText();
					LoggerUtility.getReceiverlogger().debug("Messages have been received" + textMessage);
				} 
			} finally {
				queueSession.close();
				queueConnection.close();
			}
		} catch (Exception e) {
			LoggerUtility.getReceiverlogger().error("Receiving messages failed: " + e.getMessage(), e);
		}
		return textMessage;
	}

}
