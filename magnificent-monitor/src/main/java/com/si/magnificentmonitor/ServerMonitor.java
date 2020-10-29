package com.si.magnificentmonitor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Monitors the health of a webserver (the subject of the monitor) by pinging it and regularly logging the current
 *  health of it.
 */
@Component
@RequiredArgsConstructor
@Slf4j
class ServerMonitor {

    private final Config config;
    private final PingUtility pingUtility;
    private final PingRepository pings;
    private final HealthLog healthLog;

    /**
     * Attempts to {@link Ping} the subject (webserver) of this monitor at the configured endpoint.
     *  The result of the Ping is persisted for later analysis.
     * @return
     */
    @Scheduled(fixedRateString = "#{config.pingIntervalInMilliSeconds()}")
    void pingServer() {

        log.debug("starting ping...");

        var ping = pingUtility.ping(config.getSubjectURL());

        if ( ping.getResponseStatus().equals(HttpStatus.SERVICE_UNAVAILABLE) ){
            healthLog.reportUnavailable(ping);
        }

        pings.save(ping);

        log.debug("finished ping.");
    }


    @Scheduled(fixedRateString = "#{config.reportIntervalInMilliSeconds()}")
    void reportHealthOfServer() {

        var beginningOfCurrentInterval = LocalDateTime.now().minus(Duration.ofMillis(config.reportIntervalInMilliSeconds()));

        log.debug("reporting health of server for interval starting at {}", beginningOfCurrentInterval);

        var pingsInCurrentInterval = pings.allPingsAfter(beginningOfCurrentInterval);

        var currentHealth = new Health(config.getSubjectURL().toString(), pingsInCurrentInterval);

        log.debug("health calculated, current health is {}", currentHealth);

        healthLog.report(currentHealth);

        log.debug("reported health of server.");
    }
}
