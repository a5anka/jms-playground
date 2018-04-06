package io.github.a5anka.jms.test;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TopicPublisher {
    public static void main(String[] args) throws NamingException, JMSException {
        String topicName = "MyTopic";
        InitialContext initialContext = ClientHelper.getInitialContextBuilder("admin", "admin", "localhost", "5672")
                                                    .withTopic(topicName)
                                                    .build();
        ConnectionFactory connectionFactory
                = (ConnectionFactory) initialContext.lookup(ClientHelper.CONNECTION_FACTORY);

        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = (Topic) initialContext.lookup(topicName);
        MessageProducer producer = session.createProducer(topic);

        for (int i = 0; i < 1; i++) {
            producer.send(session.createTextMessage("Message " + (i + 1)));
        }

        connection.close();
        System.out.println("Message publisher closed");
    }
}
