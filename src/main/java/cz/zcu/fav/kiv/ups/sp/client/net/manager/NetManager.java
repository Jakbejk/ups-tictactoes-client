package cz.zcu.fav.kiv.ups.sp.client.net.manager;

import cz.zcu.fav.kiv.ups.sp.client.net.dto.RequestMessage;
import cz.zcu.fav.kiv.ups.sp.client.net.dto.RequestPrefix;
import cz.zcu.fav.kiv.ups.sp.client.net.dto.ResponseMessage;
import cz.zcu.fav.kiv.ups.sp.client.net.dto.ResponsePrefix;
import cz.zcu.fav.kiv.ups.sp.client.net.exception.InvalidResponseException;
import javafx.application.Platform;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Hana Hrkalova on 06.01.2022
 * @version 0.0.0
 * @project UPS-client
 */
public final class NetManager {

    private static final Logger logger = LogManager.getLogger(NetManager.class);

    private static Socket singleton;

    private final static Queue<RequestMessage> requestQueue = new LinkedList<>();

    public final static String DELIMITER = "&|#";
    public final static String REGEX_DELIMITER = "&\\|#";

    private static boolean connectionProblem = true;


    private static String host;
    private static int port;


    private NetManager() {
    }

    public static void start(String host, int port) throws IOException {
        NetManager.host = host;
        NetManager.port = port;
        try {
            singleton = new Socket();
            InetAddress inetAddress = InetAddress.getByName(host);
            String resolvedHostName = inetAddress.getHostAddress();
            SocketAddress socketAddress = new InetSocketAddress(resolvedHostName, port);

            singleton.connect(socketAddress);

            connectionProblem = false;

            logger.info("Client successfully connected to server!");
        } catch (Exception e) {
            logger.warn("Client can not connect to server. Please try again!");
            connectionProblem = true;
            throw e;
        }
    }

    public static void reconnect() {
        if (isConnectionProblem()) {
            try {
                singleton = new Socket();
                InetAddress inetAddress = InetAddress.getByName(host);
                String resolvedHostName = inetAddress.getHostAddress();
                SocketAddress socketAddress = new InetSocketAddress(resolvedHostName, port);

                singleton.connect(socketAddress);

                connectionProblem = false;
            } catch (Exception e) {
                connectionProblem = true;

                logger.error("Client can not reconnect to server!", e);
            }
        }
    }

    private static Socket getSocketInstance() {
        return singleton;
    }

    public static List<ResponseMessage> receiveMessages() throws InvalidResponseException {
        List<ResponseMessage> responseMessageList = new ArrayList<>();
        try {
            Socket socket = getSocketInstance();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String message = bufferedReader.readLine();


            if (message == null) {
                connectionProblem = true;
                return new ArrayList<>();
            } else if (!ResponsePrefix.isResponseMessage(message)) {
                throw new InvalidResponseException(processMessage(message));
            }

            ResponseMessage testResponseMessage = processMessage(message);
            responseMessageList.add(testResponseMessage);

            while (bufferedReader.ready()) {
                message = bufferedReader.readLine();

                ResponseMessage responseMessage = processMessage(message);
                responseMessageList.add(responseMessage);
            }

            connectionProblem = false;
        } catch (IOException e) {
            connectionProblem = true;
            logger.error("CONNECTION_ERROR: Client can not read messages!");
        }
        return responseMessageList;
    }

    private static ResponseMessage processMessage(String message) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessage(message);
        responseMessage.setResponse(ResponsePrefix.fromStringPrefix(message));
        return responseMessage;
    }

    public static void sendMessage(RequestPrefix prefix, String message) {
        try {
            Socket socket = getSocketInstance();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write(prefix.getPrefix() + message + DELIMITER);
            bufferedWriter.flush();

            RequestMessage requestMessage = new RequestMessage();
            requestMessage.setMessage(message);
            requestMessage.setRequest(prefix);

            requestQueue.add(requestMessage);
        } catch (IOException e) {
            logger.error("CONNECTION_ERROR: Client can not send messages!");
        }
    }

    public static void closeSocket() {
        if(getSocketInstance() != null) {
            try {
                getSocketInstance().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void sendMessage(RequestPrefix prefix) {
        sendMessage(prefix, "");
    }

    public static boolean isConnectionProblem() {
        return connectionProblem;
    }

    public static RequestMessage getQueuedRequest() {
        return requestQueue.peek();
    }

    public static void deleteQueuedRequest() {
        requestQueue.poll();
    }



}
