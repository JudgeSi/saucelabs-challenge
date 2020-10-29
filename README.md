# The (not-so) Magnificent Monitor
> This is my solution to the coding challenge of sauce-labs.

To view the **documentation** (configuration specs, design decisions, architecture, etc) of my solution, please refer to the 
[README of the magnificent-monitor](magnificent-monitor/README.md)


## Prerequisits
Required to build and run without docker:
* `java 11`
* `mvn`
* `python3` `python2`

Required to build and run via docker:
* `mvn`
* `docker`
* `docker-compose`

## Installation

#### with Docker
```
# build the jar
mvn clean package -f magnificent-monitor/pom.xml
```

```
# build the docker images
docker-compose build
```
```
# get everything up an running
docker-compose up
```



#### Run without Docker
```
# build the jar
mvn clean package -f magnificent-monitor/pom.xml
```

````
# install python-twisted
pip install twisted
````

```
# run magnificent
python magnificent-server/server.py
```

```
# run the monitor
java -jar magnificent-monitor/target/magnificent-monitor-0.0.1-SNAPSHOT.jar
```

**NOTE:**

By default the monitor expects the magnificent-server to be available at localhost:12345. For more extensive 
configuration management see [the README of the monitor](magnificent-monitor/README.md)
