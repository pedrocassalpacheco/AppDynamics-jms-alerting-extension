package com.appdynamics.extensions.JMS.api;

import com.appdynamics.extensions.alerts.customevents.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.log4j.Logger;

/**
 * Builds an Alert from Health Rule violation event.
 */

public class AlertBuilder {

    public static final String DASH_SEPARATOR = "-";
    public static final String SLASH_SEPARATOR = "/";
    public static final String APP_DYNAMICS = "AppDynamics";
    public static final String DASH = "-";
    public static final String POLICY_CLOSE = "POLICY_CLOSE";
    public static final String RESOLVE = "resolve";
    public static final String TRIGGER = "trigger";
    private static Logger logger = Logger.getLogger(AlertBuilder.class);

    public String buildAlertFromHealthRuleViolationEvent(HealthRuleViolationEvent violationEvent) throws JsonProcessingException {
        if(violationEvent != null){
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(violationEvent);
            return json;
        }
        return "";
    }

   
}
