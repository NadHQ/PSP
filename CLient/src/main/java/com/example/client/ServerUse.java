package com.example.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public final class ServerUse {
    private static ServerUse instance;
    Socket sock = null;
    private ServerUse() {
        try {
            sock = new Socket("localhost", 8888);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ServerUse getInstance() {
        if (instance == null) {
            instance = new ServerUse();
        }
        return instance;
    }

    public void sendInt(Integer i){
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(sock.getOutputStream());
            dataOutputStream.writeInt(i);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Integer getInt(){
        try {
            DataInputStream dataInputStream = new DataInputStream(sock.getInputStream());
            Integer ret = dataInputStream.readInt();
            return ret;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void WriteCharToStream(String str) throws IOException {
        {
            DataOutputStream outputStream = new DataOutputStream(sock.getOutputStream());
            outputStream.writeInt(str.length());
            for (int i = 0; i < str.length(); i++) {
                outputStream.writeChar(str.charAt(i));
            }
        }
    }
    public String ReadCharToString() throws IOException {
        DataInputStream inputStream = new DataInputStream(sock.getInputStream());
        Integer var1 = inputStream.readInt();
        StringBuilder str = new StringBuilder("");
        for (int i = 0; i < var1; i++) {
            str.append(inputStream.readChar());
        }
        String str1 = str.toString();
        return str1;
    }
}

