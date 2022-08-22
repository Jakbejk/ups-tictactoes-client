package cz.zcu.fav.kiv.ups.sp.client.net.main;

import cz.zcu.fav.kiv.ups.sp.client.net.manager.NetManager;
import cz.zcu.fav.kiv.ups.sp.client.net.runnable.GeneralThread;
import cz.zcu.fav.kiv.ups.sp.client.net.runnable.PingThread;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author Hana Hrkalova on 06.01.2022
 * @version 0.0.0
 * @project UPS-client
 */
public final class NetworkManager {

    private final static int PING_INTERVAL = 5000;
    private final static int NET_INTERVAL = 300;
    public static ScheduledFuture<?> fixrate1;
    public static ScheduledFuture<?> fixrate2;


    public static void start(String host, int port) throws IOException {
        NetManager.start(host, port);
        startNetThreads();
    }

    private static void startNetThreads() {
    fixrate1 = Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new GeneralThread(), 0, NET_INTERVAL, TimeUnit.MILLISECONDS);
    fixrate2 =  Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new PingThread(), 0, PING_INTERVAL, TimeUnit.MILLISECONDS);
    }

    public static void stopNetThreads() {
        fixrate1.cancel(true);
        fixrate2.cancel(true);
    }



}
