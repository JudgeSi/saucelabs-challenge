package com.si.magnificentmonitor;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
// TODO I think it makes sense to throw out the interface.
class ConfiguredMonitor implements Monitor{

    private final Config config;
    private final PingUtility pingUtility;
    private final PingRepository pings;

    @Override
    @Scheduled(fixedRateString = "#{config.pingIntervalInMilliSeconds()}")
    public Ping pingSubject() {

        var ping = pingUtility.ping(config.getSubjectURL());

        // TODO test for 503 and if 503 Log service unavailable. Question: do we not save Pings with 503?

        return pings.save(ping);
    }


    @Override
    @Scheduled(fixedRateString = "#{config.reportIntervalInMilliSeconds()}")
    public void reportHealthOfSubject() {

        var timeAtBeginningOfCurrentInterval = LocalDateTime.now().minus(Duration.ofMillis(config.pingIntervalInMilliSeconds()));

        var pingsForCurrentInterval = pings.allPingsAfter(timeAtBeginningOfCurrentInterval);

        var failedPings = countFailedPingsIn(pingsForCurrentInterval);

        var failedPingsInPercent = (failedPings / pingsForCurrentInterval.size() ) * 100;

        if (failedPingsInPercent > config.getHealthThreshold()){
            // Log unhealthy
        }
        else{
            // Log healthy
        }
    }

    private long countFailedPingsIn(List<Ping> pingsForCurrentInterval) {
        return pingsForCurrentInterval.stream()
                .filter(Ping::isFailed)
                .count();
    }
}
