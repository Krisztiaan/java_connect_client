package com.krisz.example.javaclient;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by Krisz on 2015.02.21..
 */
public class SocketClient {

    private Socket client;
    private BufferedReader read;
    private BufferedWriter output;
    private Thread thrd;

    private String ipAddress;
    private int ipPort;

    private String msgResponse = "";

    public SocketClient(String serverIp, int serverPort) {
        this.ipAddress = serverIp;
        this.ipPort = serverPort;
    }

    public String sendMsg(String msgToSend) {
        try {
            output.write(msgToSend + "\n");
            output.flush();
        } catch (IOException e) {
        }
        return msgResponse;
    }

    public void Connect() {
        msgResponse = "";
        try {
            client = new Socket(ipAddress, ipPort);
            read = new BufferedReader(new InputStreamReader(client.getInputStream()));
            output = new BufferedWriter(new OutputStreamWriter(client
                    .getOutputStream()));

            thrd = new Thread(new Runnable() {
                public void run() {
                    while (!Thread.interrupted()) {
                        try {
                            final String data = read.readLine();
                            if (data != null) {
                                msgResponse += data;
                                Log.d("Csatlakozás", "Adat: " + data);
                            }
                        } catch (IOException e) {
                        }
                    }
                }
            });
            thrd.start();
        } catch (IOException ioe) {
        }
    }

    public void Close() {
        if (thrd != null)
            thrd.interrupt();
        try {
            if (client != null) {
                client.getOutputStream().close();
                client.getInputStream().close();
                client.close();
            }
        } catch (IOException e) {
        }
        thrd = null;
    }
}
