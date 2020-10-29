package com.si.magnificentmonitor;

import lombok.ToString;

import java.util.List;

/**
 * This class represents the health of a server based on a number of pings.
 */
@ToString
public class Health {
    
    private final String pingedEndpoint;
    
    private final long successfulPings;
    
    private final long failedPings;
    
    private final double failedPingsInPercent;
    
    
    public Health(String pingedEndpoint, List<Ping> pings) {

        this.pingedEndpoint = pingedEndpoint;

        this.successfulPings = countSuccessfulPingsIn(pings);

        this.failedPings = pings.size() - this.successfulPings;

        this.failedPingsInPercent = calculateRatioBetween(this.failedPings, pings.size());
    }
    
    
    private long countSuccessfulPingsIn(List<Ping> pings) {
        return pings.stream()
                .filter(Ping::wasSuccessful)
                .count();
    }

    
    private double calculateRatioBetween(long thePart, long theWhole) {

        if( theWhole == 0 ){
            return 0;
        }

        return ((double) thePart / theWhole ) * 100;
    }
}
