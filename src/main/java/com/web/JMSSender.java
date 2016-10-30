package main.java.com.web;



import javax.jms.*;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.com.model.LoggerUtility;

import java.io.IOException;

@WebServlet(name = "MessageSender", urlPatterns = "/sendMessage")
public class JMSSender extends HttpServlet {

    

	/**
	 * 
	 */
	private static final long serialVersionUID = 5809914428471420231L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LoggerUtility.getSenderlogger().debug("Inside doGet()");
		String formParam = (request.getParameter("formParam") == null) ? "" : request.getParameter("formParam");
		if(!formParam.isEmpty()){
			String name = request.getParameter("name");
			String sex = request.getParameter("sex");
			LoggerUtility.getSenderlogger().debug("Sex : "+ sex+"Name : "+ name);
	    	String message = "";
	    	if(null!=sex){ 
	    		if(sex.equalsIgnoreCase("F"))
	    			message = "Hello " + "Ms. " + name;
	    		else
	    			message = "Hello " + "Mr. " + name;
	    	}
			sendMessage(message);
			request.setAttribute("tableSendResponse", "Your Message has been sent");
		}
	        RequestDispatcher rd=request.getRequestDispatcher("/view/sendMsg.jsp");
        rd.forward(request, response);  
    }

	
    private void sendMessage(String message) {
        try {
        	LoggerUtility.getSenderlogger().debug("Inside sendMessage().");
            InitialContext initCtx = new InitialContext();
            Queue queueDestination = (Queue) initCtx.lookup("java:comp/env/jms/queue/TestQueue");
            QueueConnectionFactory  queueConnectionFactory = (QueueConnectionFactory) initCtx.lookup("java:comp/env/jms/ConnectionFactory");
            QueueConnection queueConnection = queueConnectionFactory.createQueueConnection();
            Session session = queueConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(queueDestination);
            TextMessage textMessage = session.createTextMessage();
            textMessage.setText(message);
            producer.send(textMessage);
            LoggerUtility.getSenderlogger().debug("Message sent.");
        } catch (Exception e) {
        	LoggerUtility.getSenderlogger().error("Sending JMS message failed: "+e.getMessage(), e);
        }
    }
}
