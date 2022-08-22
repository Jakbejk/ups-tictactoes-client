package cz.zcu.fav.kiv.ups.sp.client.ui.controller.abstracts;

import cz.zcu.fav.kiv.ups.sp.client.net.dto.ResponseMessage;

/**
 * @author honza on 08.01.2022
 * @version 0.0.0
 * @project UPS-client
 */
public interface ILobbyEvent {

    default void onRoomListSuccess(ResponseMessage responseMessage) {

    }

    default void onRoomListError(ResponseMessage responseMessage) {

    }

    default void onLobbyJoinSuccess(ResponseMessage responseMessage) {

    }

    default void onLobbyJoinError(ResponseMessage responseMessage) {

    }

    default void onLobbyCreateSuccess(ResponseMessage responseMessage) {

    }

    default void onLobbyCreateError(ResponseMessage responseMessage) {

    }

}
