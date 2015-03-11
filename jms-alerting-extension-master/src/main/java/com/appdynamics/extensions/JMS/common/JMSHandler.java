package com.appdynamics.extensions.JMS.common;

import com.appdynamics.extensions.JMS.Configuration;
import org.apache.log4j.Logger;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import java.io.IOException;
import java.util.logging.Level;

public class JMSHandler {

    public static final String HTTPS = "https";
    public static final String HTTP = "http";
    public static final String COLON = ":";
    public static final String FORWARD_SLASH = "/";

    final Configuration config;
    private static Logger logger = Logger.getLogger(JMSHandler.class);

    public JMSHandler(Configuration config) {
        this.config = config;
    }

    /**
     * Posts the data on VictorOps Endpoint.
     *
     * @param data
     * @return
     */
    public void postAlert(String data) throws IOException {
        logger.debug("Connecting to message host");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(config.getHost());
        factory.setUsername("test");
        factory.setPassword("test");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        logger.debug("Declaring queue " + config.getQueueName());
        channel.queueDeclare(config.getQueueName(), false, false, false, null);
        logger.debug("Publishing message to " + channel.toString());
        channel.basicPublish("", config.getQueueName(), null, data.getBytes());
        logger.debug("Message published");
    }
}
