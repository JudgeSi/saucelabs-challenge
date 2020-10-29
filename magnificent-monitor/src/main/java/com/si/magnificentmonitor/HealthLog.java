package com.si.magnificentmonitor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * Utility which reports the current health of a server based and allows for modification and testability of logging.
 */
@Slf4j
@Component
class HealthLog {

    void reportUnavailable(Ping ping){

        if(ping == null){
            throw new RuntimeException("ping to report may not be null.");
        }

        if( ! ping.getResponseStatus().equals(HttpStatus.SERVICE_UNAVAILABLE)){
            throw new RuntimeException("a server may only be reported as unavailable if the ping indicates the same.");
        }

        log.warn("server at: {} is unavailable!", ping.getDestination());
    }


    void report(Health health){

        if(health == null){
            throw new RuntimeException("health to report may not be null.");
        }

        log.info(health.toString());
    }
}
