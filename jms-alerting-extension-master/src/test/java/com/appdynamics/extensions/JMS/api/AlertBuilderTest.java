package com.appdynamics.extensions.JMS.api;

import com.appdynamics.extensions.alerts.customevents.HealthRuleViolationEvent;
import com.appdynamics.extensions.JMS.Configuration;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Assert;
import org.junit.Test;

public class AlertBuilderTest {

    AlertBuilder alertBuilderTest = new AlertBuilder();

    @Test
    public void canSerializeHealthRuleViolationIntoAlertJson() throws JsonProcessingException {
        HealthRuleViolationEvent hrv = createHealthRuleViolationEvent();
        Configuration config = createConfig();
        String alert;
        alert = alertBuilderTest.buildAlertFromHealthRuleViolationEvent(hrv);        
        Assert.assertTrue(alert != null);
    }

    private Configuration createConfig() {
        Configuration config = new Configuration();
        config.setHost("localhost");
        config.setHost("test");
        return config;
    }

    private HealthRuleViolationEvent createHealthRuleViolationEvent() {
        HealthRuleViolationEvent hrv = new HealthRuleViolationEvent();
        hrv.setAffectedEntityName("affectedEntityName");
        hrv.setEventType("eventType");
        hrv.setAppName("appName");
        hrv.setAffectedEntityType("affectedEntityType");
        hrv.setIncidentID("incidentId");
        hrv.setSummaryMessage("summaryMessage");
        hrv.setDeepLinkUrl("url");
        hrv.setHealthRuleID("ruleId");
        hrv.setHealthRuleName("ruleName");
        hrv.setPvnAlertTime("pvnAlertTime");
        hrv.setPvnTimePeriodInMinutes("period");
        hrv.setAppID("124");
        hrv.setAppName("Appname");
        hrv.setPriority("HIGH");
        hrv.setSeverity("ALERT");
        hrv.setTag("Appd");
        hrv.setAffectedEntityID("AffEntityID");
        return hrv;
    }


}
