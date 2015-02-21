package com.krisz.example.javaclient;

import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Krisz on 2015.02.21..
 */
public class SocketClient {

    private Socket client;
    private PrintWriter printwriter;

    private String ipAddress;
    private int ipPort;

    private String msgResponse = "";
    private String msgToSend = "";

    public SocketClient(String serverIp, int serverPort) {
        this.ipAddress = serverIp;
        this.ipPort = serverPort;
    }

    public String sendMsg(String msgToSend) {
        this.msgToSend = msgToSend;
        //TODO: küldésfogadás
        return msgResponse;
    }


}
