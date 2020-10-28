# The (not-so) magnificent monitor
> This monitor regularly pings an endpoint of a webserver (**henceforth: the subject of the monitor**) and reports the health of its 
    subject to the console/log.

This document contains

1. The configuration specs
1. Short API contract (log) / Output / Message format
1. Design Goals & Decisions
1. Functionality (!!TODO better wording!!)
1. Known issues
1. Architecture

## 1. Configuration Specs
This spring boot app is entirely configurable through environment variables. The specs are listed below. *If some terms
 are unclear, please refer to section **4. Functionality.***

 name          | description                                                                | default-value  
 ------------- | -------------------------------------------------------------------------  | -------------- 
 LOG_LEVEL     | Define the LOG-Severity of messages to be logged                           | INFO     
 SUBJECT_URL   | The endpoint of the web-server (subject) whichs health should be monitored | http://localhost:12345/ 
 PING_INTERVAL | The interval in seconds in which the subjects health should be monitored   | 10       
 REPORT_INTERVAL | The interval in seconds in which the app reports its subjects health     | 60                
 HEALTH_TRESHOLD | The ratio (in percent) of failed request per interval, above which the subject is reported as unhealthy | 25
 LOG | TODO Where to log to and other things which can be configured via spring | ....
 

## 2. API / Reporting / Integration / Message Format
This monitor reports its subjects health by writing to the log. 

The health of the subject is reported in the follwing format ...

If the subject of the server is unresponsive following is logged ... 

Messages published in the different log severity levels ...

## 3. Design Goals &  Decisions
The **design goals** of this app where the following:
* simplicity
* reliability & robustness
* generality & extensibility
* showcasing my coding style
* as few external dependencies as possible

Therefore the following **design decisions** were taken:
* no reliance on a database, everything is persisted in-memory using Java utilities.
* reliance on a rich domain model and services. Even though based on the few requirements a simpler architecture could be 
  chosen, this is the best approach in terms of extensibility and showcases best my preferred way of working.
* Logging format...
* Timeouts http...

## 4. Functionality
Based on the challenge and the design goals the following functionality was decided upon.
* The monitor will ping its subject (`magnificent-server`) in a configurable interval, to determine its health.
* The healthiness of the subject is determined by a configurable ratio of successful & failed pings.
    * A ping is successful if it has a 200 HTTP response code.
    * A ping is failed if it has a 500 HTTP response code. 
* The monitor will log the current health of its subject in a configurable interval.
* The monitor will immediatly report its subject as unresponsive if a ping has a 503 HTTP response code **or** times out.

## 5. Known Issues
With the abovementioned design decisions come a few drawbacks

1. In-memory
1. The monitor disregards the body of responses to determine its subjects health. Meaning the monitor does not care 
    whether an error message or a `MAGNIFICENT` response was returned. It only inspects the status codes. This seems to 
    be in line with the logic of the magnificent server. This was done in favor of `simplicity`, `generality` and 
    `clarity`. 
1. The monitor only handles the response codes 200, 500 and 503 to determine the health of its subject. 
1. Code could be shorter (scheiß punkt)
1. Im no expert in logging formats, I looked into a few standards, but could not find what I was looking for. So the 
   format of the logging output might not be up to standard when it comes to health monitoring.


## 6. Architecture