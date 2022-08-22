package cz.zcu.fav.kiv.ups.sp.client.net.utils.timer_utils;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Hana Hrkalova on 12.01.2022
 * @version 0.0.0
 * @project UPS-client
 */
public final class ScheduleExecutor {

    public static void scheduleAfterSeconds(int delay, Runnable runnable) {
        Executors.newSingleThreadScheduledExecutor().schedule(runnable, delay, TimeUnit.SECONDS);
    }

    public static void scheduleAtFixedRateSeconds(int delay, Runnable runnable, IConditional conditional) {
        if (conditional.isEnd()) {
            return;
        }
        Executors.newSingleThreadScheduledExecutor().schedule(() -> {
            runnable.run();
            scheduleAtFixedRateSeconds(delay, runnable, conditional);
        }, delay, TimeUnit.SECONDS);
    }

    @FunctionalInterface
    public interface IConditional {
        boolean isEnd();
    }

}
