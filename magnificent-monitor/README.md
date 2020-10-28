# Magnificent Monitor Documentation
This document contains

1. The configuration specs
1. Short API contract (log) / Functionality
1. Design Decisions
1. Known issues
1. Architecture

## 1. Configuration Specs
This spring boot app is entirely configurable through environment variables. The specs are listed below.

| name          | description                                      | default-value      |
| ------------- | -------------------------------------------------| ------------------ |
| LOG_LEVEL     | Define the LOG-Severity of messages to be logged | INFO               |
| PING_INTERVAL | The interval in seconds in which `magnificents` health should be checked| 10       |
| REPORT_INTERVAL | The interval in seconds in which the app regularly reports `magnificents` health | 60 |          


## 2. Design Decisions
The design goals of this app where the following:
* simplicity
* reliability & robustness
* extensibility
* showcasing my coding style
* as few external dependencies as possible

Therefore the following design decision were taken:
* no reliance on a database, everything is persisted in-memory using Java utilities.
* reliance on a rich domain model and services. Even though based on the few requirements a simpler architecture could be 
  chosen, this is the best approach in terms of extensibility and showcases best my preferred way of working.
* Logging format...
* Timeouts http...

## 3. Known Issues
With the abovementioned design decisions come a few drawbacks

1. In-memory
2. Code could be shorter (scheiß punkt)
3. Im no expert in logging formats, I looked into a few standards, but could not find what I was looking for. So the 
   format of the logging output might not be up to par.

