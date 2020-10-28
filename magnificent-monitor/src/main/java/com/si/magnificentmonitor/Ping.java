package com.si.magnificentmonitor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;


/**
 * A Ping represents the result of a GET request to a webserver and holds information whether a request was successful
 *  or failed. Pings are used to determine the health of a webserver.
 */
@Getter
public class Ping {

    private final LocalDateTime responseTime;

    private HttpStatus responseStatus;
    private String destination;


    Ping(String destination, ResponseEntity<String> response) {

        // TODO is the response time enough or do we also need the starting time?
        // this is just an approximation to the actual response time.
        this.responseTime = LocalDateTime.now();

        this.setDestination(destination);
        this.setResponseStatus(response.getStatusCode());
    }


    boolean isFailed(){
        return ! responseStatus.equals(HttpStatus.OK);
    }

    private void setResponseStatus(HttpStatus responseStatus) {
        if (responseStatus == null){
            throw new RuntimeException("The status code of a Ping may not be null!");
        }

        boolean statusCodeIsAsExpected = responseStatus.equals(HttpStatus.OK) || responseStatus.equals(HttpStatus.SERVICE_UNAVAILABLE) || responseStatus.equals(HttpStatus.INTERNAL_SERVER_ERROR);

        if ( ! statusCodeIsAsExpected ){
            throw new RuntimeException(String.format("The monitor can currently not handle the status code: %s ", responseStatus.toString()));
        }

        this.responseStatus = responseStatus;
    }


    private void setDestination(String destination) {
        if(StringUtils.isEmpty(destination)){
            throw new RuntimeException("The destination of a Ping may not be empty!");
        }
        this.destination = destination;
    }
}
