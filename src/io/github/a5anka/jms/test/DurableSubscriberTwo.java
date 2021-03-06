package io.github.a5anka.jms.test;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class DurableSubscriberTwo {
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
        TopicSubscriber sub2 = session.createDurableSubscriber(topic, "sub2");

        Message message = sub2.receive();

        System.out.println("Message : " + message);

        connection.close();
    }
}
