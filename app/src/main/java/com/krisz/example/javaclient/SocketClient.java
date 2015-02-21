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
    private Thread thread;

    private String ipAddress;
    private int ipPort;

    private String msgResponse = "";

    public SocketClient(String serverIp, int serverPort) {
        Log.d("Csatlakozás", "Konstruktor meghívva.");
        this.ipAddress = serverIp;
        this.ipPort = serverPort;
    }

    public String sendMsg(String msgToSend) {
        Log.d("Csatlakozás", "sendMsg() meghívva.");
        try {
            output.write(msgToSend + "\n");
            output.flush();
        } catch (IOException e) {
        }
        return msgResponse;
    }

    public void Connect() {
        Log.d("Csatlakozás", "Connect() meghívva.");
        msgResponse = "";
        try {
            Log.d("Csatlakozás", "Try beléptetve.");
            client = new Socket(ipAddress, ipPort);
            Log.d("Csatlakozás", "Socket hozzárendelve.");
            read = new BufferedReader(new InputStreamReader(client.getInputStream()));
            Log.d("Csatlakozás", "BufferedReader StreamReaderhez.");
            output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            Log.d("Csatlakozás", "thread jön.");
            thread = new Thread(new Runnable() {
                public void run() {
                    Log.d("Csatlakozás", "Belépve a run()-ba.");
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
            Log.d("Csatlakozás", "Jön a thread.start().");
            thread.start();
        } catch (IOException ioe) {
        }
    }

    public void Close() {
        if (thread != null)
            thread.interrupt();
        try {
            if (client != null) {
                client.getOutputStream().close();
                client.getInputStream().close();
                client.close();
            }
        } catch (IOException e) {
        }
        thread = null;
    }
}
