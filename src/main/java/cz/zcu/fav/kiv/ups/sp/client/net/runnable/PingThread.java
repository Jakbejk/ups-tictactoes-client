package cz.zcu.fav.kiv.ups.sp.client.net.runnable;

import cz.zcu.fav.kiv.ups.sp.client.net.dto.RequestPrefix;
import cz.zcu.fav.kiv.ups.sp.client.net.manager.NetManager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author Hana Hrkalova on 06.01.2022
 * @version 0.0.0
 * @project UPS-client
 */
public class PingThread implements Runnable {

    private final static Logger logger = LogManager.getLogger(PingThread.class);

    @Override
    public void run() {
        NetManager.sendMessage(RequestPrefix.PING);
    }
}
