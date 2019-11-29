package it.eng.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/*
 *  
 */
@Configuration("jaegerProperties")
@ConfigurationProperties(prefix = "configuration.jaegeropentracing")
public class JaegerOpentracing {
    private String appname;
    private String endpoint;
    private int samplerconfiguration;
    private boolean logspans;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public int getSamplerconfiguration() {
        return samplerconfiguration;
    }

    public void setSamplerconfiguration(int samplerconfiguration) {
        this.samplerconfiguration = samplerconfiguration;
    }

    public boolean getLogspans() {
        return logspans;
    }

    public void setLogspans(boolean logspans) {
        this.logspans = logspans;
    }
}
