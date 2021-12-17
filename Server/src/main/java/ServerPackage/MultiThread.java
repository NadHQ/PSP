package ServerPackage;

import regAndAuth.AuthClass;
import regAndAuth.RegClass;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MultiThread implements Runnable{
    static final String DB_URL = "jdbc:postgresql://localhost:5432/kursovaya";
    static final String DB_USER = "admin";
    static final String DB_PASS = "daniel300";
    static Statement genState;
    private Socket sock;
    public MultiThread(Socket socket) {
        this.sock = socket;
    }

    @Override
    public void run() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("DRIVER NOT FOUND! PLEASE INSTALL JDBC DRIVER");
            e.printStackTrace();
        }
        Connection connection = null;
        DataOutputStream outputStream = null;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            genState = connection.createStatement();
            Statement callableState = connection.createStatement();
            outputStream = new DataOutputStream(sock.getOutputStream());
            DataInputStream inputStream = new DataInputStream(sock.getInputStream());
            while (true) {
                Integer choose = null;
                try {
                    choose = inputStream.readInt();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    }
