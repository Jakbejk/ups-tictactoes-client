package cz.zcu.fav.kiv.ups.sp.client.net.dto;

import cz.zcu.fav.kiv.ups.sp.client.net.exception.InvalidResponseException;
import cz.zcu.fav.kiv.ups.sp.client.net.response.IResolver;
import cz.zcu.fav.kiv.ups.sp.client.net.response.game.*;
import cz.zcu.fav.kiv.ups.sp.client.net.response.other.UnknownResolver;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Arrays;

/**
 * @author Hana Hrkalova on 15.11.2021
 * @version 0.0.0
 * @project ups
 */
public enum ResponsePrefix {

    OK("ok"),
    INACTIVE_OPPONENT("inactive_opponent", new InactiveOpponentResolver()),
    OPPONENT_RECONNECT("opponent_reconnect", new OpponentReconnectResolver()),
    ERROR("error"),
    WAITING("waiting"),
    NEW_GAME("game:", new GameStartResolver()),
    END_GAME("end_game:", new GameEndResolver()),
    NEW_ROUND("play:", new RoundStartResolver()),
    LOBBY_LIST("game_list:"),
    UNKNOWN("", new UnknownResolver());

    private final static Logger logger = LogManager.getLogger(ResponsePrefix.class);

    private final String status;
    private final IResolver resolver;

    ResponsePrefix(String status, IResolver resolver) {
        this.status = status;
        this.resolver = resolver;
    }

    ResponsePrefix(String status) {
        this.status = status;
        this.resolver = null;
    }

    public String getStatus() {
        return this.status;
    }

    public static boolean isResponseMessage(String message) {
        ResponsePrefix responsePrefix = message == null ? UNKNOWN : Arrays.stream(values()).filter(prefix -> message.startsWith(prefix.status)).findFirst().orElse(UNKNOWN);
        return responsePrefix != UNKNOWN;
    }

    public void resolve(ResponseMessage responseMessage) throws InvalidResponseException {
        if (resolver == null) {
            logger.warn(String.format("%s has not resolver function.", super.toString()));
            return;
        }
        resolver.resolve(responseMessage);
    }

    public static ResponsePrefix fromStringPrefix(String statusPrefix) {
        return statusPrefix == null ? UNKNOWN : Arrays.stream(values()).filter(prefix -> statusPrefix.startsWith(prefix.status)).findFirst().orElse(UNKNOWN);
    }
}
