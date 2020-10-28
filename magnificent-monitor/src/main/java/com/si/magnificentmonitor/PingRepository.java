package com.si.magnificentmonitor;

import java.time.LocalDateTime;
import java.util.List;

interface PingRepository {

    Ping save(Ping ping);

    List<Ping> allPingsAfter(LocalDateTime timeAtBeginningOfLatestInterval);
}
