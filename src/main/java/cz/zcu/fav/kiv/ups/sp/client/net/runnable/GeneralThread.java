package cz.zcu.fav.kiv.ups.sp.client.net.runnable;

import cz.zcu.fav.kiv.ups.sp.client.net.dto.RequestMessage;
import cz.zcu.fav.kiv.ups.sp.client.net.dto.RequestPrefix;
import cz.zcu.fav.kiv.ups.sp.client.net.dto.ResponseMessage;
import cz.zcu.fav.kiv.ups.sp.client.net.dto.ResponsePrefix;
import cz.zcu.fav.kiv.ups.sp.client.net.exception.InvalidResponseException;
import cz.zcu.fav.kiv.ups.sp.client.net.main.NetworkManager;
import cz.zcu.fav.kiv.ups.sp.client.net.manager.NetManager;
import cz.zcu.fav.kiv.ups.sp.client.net.utils.timer_utils.OpponentReconnectUtil;
import cz.zcu.fav.kiv.ups.sp.client.net.utils.timer_utils.ReconnectTimerUtil;
import cz.zcu.fav.kiv.ups.sp.client.net.utils.timer_utils.ScheduleExecutor;
import cz.zcu.fav.kiv.ups.sp.client.ui.controller.ControllerType;
import cz.zcu.fav.kiv.ups.sp.client.ui.controller.manager.ControllerManager;
import cz.zcu.fav.kiv.ups.sp.client.ui.element.alert.Alerts;
import javafx.application.Platform;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @author Hana Hrkalova on 06.01.2022
 * @version 0.0.0
 * @project UPS-client
 */
public class GeneralThread implements Runnable {

    private final static Logger logger = LogManager.getLogger(GeneralThread.class);

    private final static int SHUTDOWN_DELAY = 3;

    @Override
    public void run() {

        try {
            resolveConnectionProblem();
            resolveOpponentConnectionProblem();

            List<ResponseMessage> responseMessageList = NetManager.receiveMessages();
            for (ResponseMessage message : responseMessageList) {
                resolveResponse(message);
            }
        } catch (InvalidResponseException e) {
            logger.error("Receiving invalid message. [" + e.getResponseMessage() + "].");
            NetManager.sendMessage(RequestPrefix.DISCONNECT);
            ControllerManager.changeTo(ControllerType.NET);
            NetManager.closeSocket();
            NetworkManager.stopNetThreads();

        }
    }

    private void resolveResponse(ResponseMessage message) throws InvalidResponseException {
        RequestMessage requestMessage = NetManager.getQueuedRequest();

        if (message.getResponse() == ResponsePrefix.NEW_ROUND) {
            ResponsePrefix.NEW_ROUND.resolve(message);
            return;
        } else if (message.getResponse() == ResponsePrefix.END_GAME) {
            ResponsePrefix.END_GAME.resolve(message);
            return;
        } else if (message.getResponse() == ResponsePrefix.NEW_GAME) {
            ResponsePrefix.NEW_GAME.resolve(message);
            return;
        } else if (message.getResponse() == ResponsePrefix.INACTIVE_OPPONENT) {
            ResponsePrefix.INACTIVE_OPPONENT.resolve(message);
            OpponentReconnectUtil.getInstance().start();
            return;
        } else if (message.getResponse() == ResponsePrefix.OPPONENT_RECONNECT) {
            ResponsePrefix.OPPONENT_RECONNECT.resolve(message);
            OpponentReconnectUtil.getInstance().stop();
            return;
        }

        requestMessage.getRequest().resolve(message);

        NetManager.deleteQueuedRequest();
    }

    private void resolveConnectionProblem() {
        ReconnectTimerUtil reconnectUtil = ReconnectTimerUtil.getInstance();

        if (NetManager.isConnectionProblem()) {

            if (reconnectUtil.isStopped()) {
                reconnectUtil.start();
                ScheduleExecutor.scheduleAtFixedRateSeconds(3, NetManager::reconnect, () -> !NetManager.isConnectionProblem());

                Platform.runLater(ControllerManager.getController()::onConnectionLost);
            }
            if (reconnectUtil.isTimeExpired() && !reconnectUtil.isShutdown()) {
                reconnectUtil.shutdown();

                Platform.runLater(Alerts::showConnectionLostAlert);
                ScheduleExecutor.scheduleAfterSeconds(SHUTDOWN_DELAY, () -> ControllerManager.changeTo(ControllerType.NET));
            }
        } else {
            if (reconnectUtil.isStarted()) {
                Platform.runLater(ControllerManager.getController()::onReconnect);
            }
            reconnectUtil.stop();
        }
    }

    private void resolveOpponentConnectionProblem() {
        OpponentReconnectUtil opponentReconnectUtil = OpponentReconnectUtil.getInstance();
        if (opponentReconnectUtil.isTimeExpired() && !opponentReconnectUtil.isShutdown()) {
            opponentReconnectUtil.shutdown();

            Platform.runLater(Alerts::showOpponentConnectionLostAlert);
            ScheduleExecutor.scheduleAfterSeconds(SHUTDOWN_DELAY, () -> NetManager.sendMessage(RequestPrefix.GAME_CLOSE));
        }
    }

}
