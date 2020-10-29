package com.si.magnificentmonitor;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

@Configuration
@Setter
public class Config {

    @Getter
    @Value("${SUBJECT_URL:http://localhost:12345/}")
    private URI subjectURL;

    @Value("${PING_INTERVAL:10}")
    private int pingInterval;

    @Value("${REPORT_INTERVAL:60}")
    private int reportInterval;

    public void setSubjectURL(String subjectURL) {
        // TODO throw pretty exception.
        this.subjectURL = URI.create(subjectURL);
    }

    public long pingIntervalInMilliSeconds() {
        return pingInterval * 1000;
    }

    public long reportIntervalInMilliSeconds() {
        return reportInterval * 1000;
    }
}
