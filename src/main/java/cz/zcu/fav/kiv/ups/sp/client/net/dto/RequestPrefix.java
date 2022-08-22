package cz.zcu.fav.kiv.ups.sp.client.net.dto;

import cz.zcu.fav.kiv.ups.sp.client.net.exception.InvalidResponseException;
import cz.zcu.fav.kiv.ups.sp.client.net.response.IResolver;
import cz.zcu.fav.kiv.ups.sp.client.net.response.PingResolver;
import cz.zcu.fav.kiv.ups.sp.client.net.response.game.GameCloseResolver;
import cz.zcu.fav.kiv.ups.sp.client.net.response.game.GameDisconnectResolver;
import cz.zcu.fav.kiv.ups.sp.client.net.response.game.RoundResultResolver;
import cz.zcu.fav.kiv.ups.sp.client.net.response.lobby.LobbyCreateResolver;
import cz.zcu.fav.kiv.ups.sp.client.net.response.lobby.LobbyJoinResolver;
import cz.zcu.fav.kiv.ups.sp.client.net.response.lobby.LobbyListResolver;
import cz.zcu.fav.kiv.ups.sp.client.net.response.login.LoginResolver;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author Hana Hrkalova on 14.11.2021
 * @version 0.0.0
 * @project ups
 */
public enum RequestPrefix {

    LOGIN("login:", new LoginResolver()),
    LOBBY_CREATE("game_crt:", new LobbyCreateResolver()),
    LOBBY_JOIN("game_join:", new LobbyJoinResolver()),
    LOBBY_LIST("game_list", new LobbyListResolver()),
    DISCONNECT("disconnect", new GameDisconnectResolver()),
    GAME_CLOSE("game_end", new GameCloseResolver()),
    PLAY_ROUND("play:", new RoundResultResolver()),
    PING("ping", new PingResolver());

    private final static Logger logger = LogManager.getLogger(RequestPrefix.class);

    private final String prefix;
    private final IResolver resolver;

    RequestPrefix(String prefix, IResolver resolver) {
        this.resolver = resolver;
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void resolve(ResponseMessage responseMessage) throws InvalidResponseException {
        if (resolver == null) {
            logger.warn(String.format("%s has not resolver function.", super.toString()));
            return;
        }
        resolver.resolve(responseMessage);
    }
}
