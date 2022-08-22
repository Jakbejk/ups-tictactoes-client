package cz.zcu.fav.kiv.ups.sp.client.ui.controller.abstracts;

import cz.zcu.fav.kiv.ups.sp.client.net.dto.ResponseMessage;

/**
 * @author honza on 07.01.2022
 * @version 0.0.0
 * @project UPS-client
 */
public interface ILoginEvent {

    default void onLoginSuccess(ResponseMessage responseMessage) {

    }

    default void onLoginFailure(ResponseMessage responseMessage) {

    }

}
