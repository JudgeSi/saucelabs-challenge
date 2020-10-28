package com.si.magnificentmonitor;

/**
 * Monitors the health of a webserver (the subject of the monitor).
 *  The Subject should be determined by the {@link Config}.
 */
interface Monitor {

    /**
     * Attempts to {@link Ping} the subject (webserver) of this monitor at the configured endpoint.
     *  The result of the Ping is persisted for later analysis.
     * @return
     */
    Ping pingSubject();

    /**
     * Log the health of the subject for the configured intervall.
     *
     * TODO: should the interval for which to report the health of the subject be passed to this service?
     */
    void reportHealthOfSubject();
}
