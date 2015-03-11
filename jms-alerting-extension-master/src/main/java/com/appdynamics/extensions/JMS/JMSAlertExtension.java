package com.appdynamics.extensions.JMS;

import com.appdynamics.extensions.alerts.customevents.Event;
import com.appdynamics.extensions.alerts.customevents.EventBuilder;
import com.appdynamics.extensions.alerts.customevents.HealthRuleViolationEvent;
import com.appdynamics.extensions.alerts.customevents.OtherEvent;
import com.appdynamics.extensions.JMS.api.AlertBuilder;
import com.appdynamics.extensions.JMS.common.ConfigUtil;
import com.appdynamics.extensions.JMS.common.JMSHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;


public class JMSAlertExtension {

    public static final String CONFIG_FILENAME =  "." + File.separator + "conf" + File.separator + "config.yaml";
    private static Logger logger = Logger.getLogger(JMSAlertExtension.class);

    //To create the AppDynamics Health Rule Violation event
    final EventBuilder eventBuilder = new EventBuilder();
    //To create a jms alert
    final AlertBuilder alertBuilder = new AlertBuilder();
    //To load the config files
    final static ConfigUtil<Configuration> configUtil = new ConfigUtil<Configuration>();
    //holds the configuration from config.yaml
    Configuration config;

    public static void main(String[] args){
        if(args == null || args.length == 0){
            logger.error("No arguments passed to the extension, exiting the program.");
            return;
        }
        Configuration config = null;
        try {
            config = configUtil.readConfig(CONFIG_FILENAME, Configuration.class);
            JMSAlertExtension alertExtension = new JMSAlertExtension(config);
            boolean status = alertExtension.processAnEvent(args);
            if(status){
                logger.info( "JMS Alerting Extension completed successfully.");
                return;
            }

        } catch (FileNotFoundException e) {
            logger.error( "Config file not found." + e);
        } catch(Exception e){
            logger.error( "Error processing an event" + e);
        }
        logger.error( "JMS Alerting Extension completed with errors");
    }

    public JMSAlertExtension(Configuration config){
        String msg = "JMSAlertExtension Version ["+getImplementationTitle()+"]";
        logger.info(msg);
        System.out.println(msg);
        this.config = config;
    }

    /**
     * Creates an AppDynamics health rule event from the command line arguments, builds an jms
     * Alert from the health rule event and posts it to jms.
     * @param args
     * @return false incase of an error else true;
     */
    public boolean processAnEvent(String[] args) throws IOException {
        Event event = eventBuilder.build(args);
        if (event != null) {
            String alert = null;
            if(event instanceof HealthRuleViolationEvent) {
                HealthRuleViolationEvent violationEvent = (HealthRuleViolationEvent) event;
                alert = alertBuilder.buildAlertFromHealthRuleViolationEvent(violationEvent);
            }
            else{
                OtherEvent otherEvent = (OtherEvent) event;
//                alert = alertBuilder.buildAlertFromOtherEvent(otherEvent);
            }
            if (alert != null) {               
                JMSHandler handler = new JMSHandler(config);                    
                logger.debug("Json posted to VO ::" +alert);
                handler.postAlert(alert);               
            }
        }
        return false;
    }

    private String getImplementationTitle(){
        return this.getClass().getPackage().getImplementationTitle();
    }
}
