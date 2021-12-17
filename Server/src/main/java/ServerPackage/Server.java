package ServerPackage;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;

import UserPackage.User;
import regAndAuth.AuthClass;
import regAndAuth.RegClass;

public class Server implements ClientDataProcessing {


    public static void main(String[] args) throws IOException, ParseException, SQLException {
        Socket sock = null;
        ServerSocket socket = null;

        try {
            socket = new ServerSocket(8888);
            System.out.println("Waiting for the client...");
            sock = socket.accept();
            System.out.println("Client connected");
            new Thread(new MultiThread(sock)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
}}

//[18.0846,0.,18.1,0.,0.679,6.434,100.,1.8347,24.,666.,20.2,27.25,29.05]