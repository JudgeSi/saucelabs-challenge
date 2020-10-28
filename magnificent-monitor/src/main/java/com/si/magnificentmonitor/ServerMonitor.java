package com.si.magnificentmonitor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Monitors the health of a webserver (the subject of the monitor) and regularly logs the current health of it.
 */
@Component
@RequiredArgsConstructor
@Slf4j
class ServerMonitor {

    private final Config config;
    private final PingUtility pingUtility;
    private final PingRepository pings;

    /**
     * Attempts to {@link Ping} the subject (webserver) of this monitor at the configured endpoint.
     *  The result of the Ping is persisted for later analysis.
     * @return
     */
    @Scheduled(fixedRateString = "#{config.pingIntervalInMilliSeconds()}")
    void pingServer() {

        var ping = pingUtility.ping(config.getSubjectURL());

        if ( ping.getResponseStatus().equals(HttpStatus.SERVICE_UNAVAILABLE) ){
            log.warn("Server at: {} is unavailable!", config.getSubjectURL());
        }

        pings.save(ping);
    }


    @Scheduled(fixedRateString = "#{config.reportIntervalInMilliSeconds()}")
    void reportHealthOfServer() {

        var beginningOfCurrentInterval = LocalDateTime.now().minus(Duration.ofMillis(config.pingIntervalInMilliSeconds()));

        var pingsInCurrentInterval = pings.allPingsAfter(beginningOfCurrentInterval);

        var failedPings = countFailedPingsIn(pingsInCurrentInterval);

        var failedPingsInPercent = (failedPings / pingsInCurrentInterval.size() ) * 100;

        if (failedPingsInPercent > config.getHealthThreshold()){
            log.info("Server at: {} is unhealthy! {}% of the last {} requests failed!", config.getSubjectURL(), failedPingsInPercent, pingsInCurrentInterval.size());
        }
        else{
            log.info("Server at: {} is healthy!", config.getSubjectURL());
        }
    }

    private long countFailedPingsIn(List<Ping> pings) {
        return pings.stream()
                .filter(Ping::isFailed)
                .count();
    }
}
