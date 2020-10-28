package com.si.magnificentmonitor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;


/**
 * A Ping represents a GET request-response pair to a webserver and holds information whether a request was successful
 *  or failed. Pings are used to determine the health of a webserver.
 */
@Getter
public class Ping {

    private final LocalDateTime responseTime;

    private HttpStatus httpStatus;
    private String destination;


    Ping(String destination, ResponseEntity<String> response) {

        // this is just an approximation to the actual response time.
        this.responseTime = LocalDateTime.now();

        this.setDestination(destination);
        this.setHttpStatus(response.getStatusCode());
    }


    boolean isFailed(){
        return ! httpStatus.equals(HttpStatus.OK);
    }

    private void setHttpStatus(HttpStatus httpStatus) {
        if (httpStatus == null){
            throw new RuntimeException("The status code of a Ping may not be null!");
        }

        boolean statusCodeIsAsExpected = httpStatus.equals(HttpStatus.OK) || httpStatus.equals(HttpStatus.SERVICE_UNAVAILABLE) || httpStatus.equals(HttpStatus.INTERNAL_SERVER_ERROR);

        if ( ! statusCodeIsAsExpected ){
            throw new RuntimeException(String.format("The monitor can currently not handle the status code: %s ", httpStatus.toString()));
        }

        this.httpStatus = httpStatus;
    }


    private void setDestination(String destination) {
        if(StringUtils.isEmpty(destination)){
            throw new RuntimeException("The destination of a Ping may not be empty!");
        }
        this.destination = destination;
    }
}
