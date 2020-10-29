package com.si.magnificentmonitor;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
class InMemoryPingRepository implements PingRepository {

    // very suboptimal for looking up Pings by date.
    // will run out of memory at some point
    private final List<Ping> pings = new ArrayList<>();

    @Override
    public Ping save(Ping ping) {
        this.pings.add(ping);
        return ping;
    }

    @Override
    public List<Ping> allPingsAfter(LocalDateTime someTime) {

        // this is horribly inefficent
        return this.pings.stream()
                .filter(pingIsAfter(someTime))
                .collect(Collectors.toList());
    }

    private static final Predicate<? super Ping> pingIsAfter(LocalDateTime timeAtBeginningOfLatestInterval) {
        return ping -> ping.getResponseTime().isAfter(timeAtBeginningOfLatestInterval);
    }
}
