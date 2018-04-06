package io.github.a5anka.jms.test;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class QueueConsumer {
    public static void main(String[] args) throws NamingException, JMSException {
        String queueName = "MyQueue";
        InitialContext initialContext = ClientHelper.getInitialContextBuilder("admin", "admin", "localhost", "5672")
                                                    .withQueue(queueName)
                                                    .build();
        ConnectionFactory connectionFactory
                = (ConnectionFactory) initialContext.lookup(ClientHelper.CONNECTION_FACTORY);

        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = (Queue) initialContext.lookup(queueName);
        MessageConsumer consumer = session.createConsumer(queue);

        Message message = consumer.receive(30000);

        System.out.println("Message : " + message);

        connection.close();
    }
}
