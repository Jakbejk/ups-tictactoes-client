package cz.zcu.fav.kiv.ups.sp.client.ui.controller.abstracts;

import cz.zcu.fav.kiv.ups.sp.client.net.dto.ResponseMessage;

/**
 * @author honza on 05.01.2022
 * @version 0.0.0
 * @project UPS-client
 */
public interface IGameEvent {

    default void onRoundStart(ResponseMessage responseMessage) {

    }

    default void onRoundUnmount(ResponseMessage responseMessage) {

    }

    default void onGameStart(ResponseMessage responseMessage) {

    }

    default void onGameEnd(ResponseMessage responseMessage) {

    }

    default void onOpponentConnectionLost() {

    }

    default void onOpponentReconnect() {

    }


}
