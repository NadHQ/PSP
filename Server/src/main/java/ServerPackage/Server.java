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
    static final String DB_URL = "jdbc:postgresql://localhost:5432/kursovaya";
    static final String DB_USER = "admin";
    static final String DB_PASS = "daniel300";
    static Statement genState;

    public static void main(String[] args) throws IOException, ParseException, SQLException {
        Socket sock = null;
        ServerSocket socket = null;
        Server serv = new Server();
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("DRIVER NOT FOUND! PLEASE INSTALL JDBC DRIVER");
            e.printStackTrace();
        }
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        genState = connection.createStatement();
        Statement callableState = connection.createStatement();
        try {
            socket = new ServerSocket(8888);
            System.out.println("Waiting for the client...");
            sock = socket.accept();
            System.out.println("Client connected");
        } catch (IOException e) {
            e.printStackTrace();
        }
        DataOutputStream outputStream = new DataOutputStream(sock.getOutputStream());
        DataInputStream inputStream = new DataInputStream(sock.getInputStream());
        while (true) {
            Integer choose = inputStream.readInt();
            System.out.println(choose);
            if (choose == 1) {
                System.out.println("Registration");
                RegClass reg = new RegClass(inputStream, outputStream, callableState);
                reg.start();
            } else if (choose == 2) {
                System.out.println("Auth");
                AuthClass authClass = new AuthClass(inputStream, outputStream, callableState, genState);
                authClass.StartUse();
            } else if (choose == 3) {
            }
        }
    }


}

//[18.0846,0.,18.1,0.,0.679,6.434,100.,1.8347,24.,666.,20.2,27.25,29.05]