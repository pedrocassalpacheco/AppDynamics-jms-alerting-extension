package com.appdynamics.extensions.JMS;

import com.appdynamics.extensions.JMS.JMSAlertExtension;
import com.appdynamics.extensions.JMS.Configuration;
import com.appdynamics.extensions.JMS.common.ConfigUtil;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

public class JMSAlertExtensionTest {

    EventArgs eventArgs = new EventArgs();
    ConfigUtil<Configuration> configUtil = new ConfigUtil<Configuration>();

    @Test
    public void canPostHRViolationEventToJMS() throws FileNotFoundException, IOException {
        Configuration configuration = configUtil.readConfig(this.getClass().getResource("/conf/config.yaml").getFile(),Configuration.class);
        JMSAlertExtension alertExtension = new JMSAlertExtension(configuration);
        alertExtension.processAnEvent(eventArgs.getHealthRuleViolationEventWithMultipleEvalEntityAndMultipleTriggerBaseline());
    }

}
