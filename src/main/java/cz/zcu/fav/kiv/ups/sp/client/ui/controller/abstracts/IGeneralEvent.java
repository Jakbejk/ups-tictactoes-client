package cz.zcu.fav.kiv.ups.sp.client.ui.controller.abstracts;

import cz.zcu.fav.kiv.ups.sp.client.net.dto.ResponseMessage;

/**
 * @author honza on 05.01.2022
 * @version 0.0.0
 * @project UPS-client
 */
public interface IGeneralEvent {

    default void onSuccess(ResponseMessage responseMessage) {
    }

    default void onFailure(ResponseMessage responseMessage) {
    }

    default void onConnectionLost() {

    }

    default void onReconnect() {

    }

}
