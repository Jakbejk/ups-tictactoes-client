package cz.zcu.fav.kiv.ups.sp.client.net.utils.timer_utils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * @author honza on 17.01.2022
 * @version 0.0.0
 * @project temp
 */
public final class OpponentReconnectUtil {

    private final static int DELAY = 40;
    private final static ChronoUnit DELAY_UNIT = ChronoUnit.SECONDS;
    private final static OpponentReconnectUtil instance = new OpponentReconnectUtil(DELAY, DELAY_UNIT);

    private LocalDateTime connectionProblemStart;
    private LocalDateTime connectionProblemEnd;

    private boolean shutdown;

    private final int delay;
    private final ChronoUnit delayUnit;

    private OpponentReconnectUtil(int delay, ChronoUnit delayUnit) {
        this.delay = delay;
        this.delayUnit = delayUnit;
    }

    public static OpponentReconnectUtil getInstance() {
        return instance;
    }


    public boolean isStarted() {
        return this.connectionProblemStart != null && this.connectionProblemEnd != null;
    }

    public boolean isStopped() {
        return !this.isStarted();
    }

    public void start() {
        this.connectionProblemStart = LocalDateTime.now();
        this.connectionProblemEnd = this.connectionProblemStart.plus(delay, delayUnit);
    }

    public boolean isTimeExpired() {
        return isStarted() && LocalDateTime.now().until(connectionProblemEnd, delayUnit) <= 0;
    }

    public void shutdown() {
        this.shutdown = true;
    }

    public boolean isShutdown() {
        return this.shutdown;
    }


    public void stop() {
        this.connectionProblemStart = null;
        this.connectionProblemEnd = null;
    }
}
