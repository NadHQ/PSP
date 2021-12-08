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
                serv.RegistrationFunction(inputStream, callableState, outputStream);
            } else if (choose == 2) {
                serv.AuthFunction(inputStream, callableState, outputStream);
            } else if (choose == 3) {

            }
        }
    }


    public Double PredictionFunc(String StringData) throws IOException, ParseException {
        StringBuilder result = new StringBuilder();
        JSONParser parser = new JSONParser();
        URL url = new URL("http://127.0.0.1:5000/helloworld");
        JSONObject senddata = new JSONObject();
        senddata.put("data", StringData);
        System.out.println(senddata);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setDoOutput(true);
        OutputStream out = conn.getOutputStream();
        byte[] out1 = senddata.toJSONString().getBytes(StandardCharsets.UTF_8);
        out.write(out1);
        out.close();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        }
        String res = result.toString();
        JSONObject object = (JSONObject) parser.parse(res);
        Double dat = (Double) object.get("name");
        return dat;
    }


    public void RegistrationFunction(DataInputStream inputStream, Statement callableState, DataOutputStream outputStream) {
        try {
            ResultSet result;
            String login = ReadCharToString(inputStream);
            String pass = ReadCharToString(inputStream);
            String state = String.format("SELECT * FROM \"user\" where log = '%s'", login);
            result = callableState.executeQuery(state);
            if (result.next()) {
                outputStream.writeInt(0);
            } else {
                outputStream.writeInt(1);
                state = String.format("INSERT INTO \"user\"(log, pass, status, role) values ('%s','%s','t',0);", login, pass);
                callableState.executeUpdate(state);
                System.out.println(state);
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void AuthFunction(DataInputStream inputStream, Statement callableState, DataOutputStream dataOutputStream) throws IOException {
        User usr = new User();
        ResultSet result;
        String login = ReadCharToString(inputStream);
        String pass = ReadCharToString(inputStream);
        System.out.println(login);
        System.out.println(pass);
        String state = String.format("SELECT * FROM \"user\" where log = '%s'", login);
        try {
            result = callableState.executeQuery(state);
            if (result.next()) {
                String status = result.getString(4);
                System.out.println(status);
                usr.setId(result.getInt(1));
                usr.setLogin(result.getString(2));
                WriteCharToStream(status, dataOutputStream);
                WriteCharToStream(result.getString(5), dataOutputStream);
                if (result.getString(5).equals("0")) {
                    UserFunc(inputStream, dataOutputStream, usr, callableState);
                } else if (result.getString(5).equals("1")) {
                    AdminFunc(inputStream, dataOutputStream, usr, callableState);
                }
            } else {
                WriteCharToStream("error", dataOutputStream);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void AdminFunc(DataInputStream inputStream, DataOutputStream dataOutputStream, User usr, Statement callableState) throws IOException {
        while (true) {
            Integer ch = inputStream.readInt();
            if (ch == 1) {
                ViewUsers(dataOutputStream, usr, callableState);
            } else if (ch == 2) {
                Edit(dataOutputStream, inputStream, usr, callableState);
            } else if (ch == 3) {
                DeleteUser(inputStream, dataOutputStream, usr, callableState);
            } else if (ch == 4) {
                AddUser(dataOutputStream, callableState);
            } else if (ch == 5) {
                BlockUser(dataOutputStream, usr, callableState);
            }
        }
    }

    private void AddUser(DataOutputStream dataOutputStream, Statement callableState) {
    }

    private void DeleteUser(DataInputStream inputStream, DataOutputStream dataOutputStream, User usr, Statement callableState) {
        Integer DeleteId = null;
        try {
            DeleteId = inputStream.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            callableState.executeUpdate("DELETE from \"data1\" where user_id = " + DeleteId + ";");
            callableState.executeUpdate("DELETE from \"data2\" where user_id = " + DeleteId + ";");
            callableState.executeUpdate("DELETE from \"data3\" where user_id = " + DeleteId + ";");
            callableState.executeUpdate("DELETE from \"result\" where user_id = " + DeleteId + ";");
            callableState.executeUpdate("DELETE from \"user\" where id = " + DeleteId + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void Edit(DataOutputStream dataOutputStream, DataInputStream inputStream, User usr, Statement callableState) {
    }

    private void ViewUsers(DataOutputStream dataOutputStream, User usr, Statement callableState) {
        ResultSet resultSet, resultSet1;
        try {
            System.out.println("123");
            resultSet = callableState.executeQuery("select * from \"user\";");
            resultSet1 = genState.executeQuery("select count(*) from \"user\";");
            while (resultSet1.next()) {
                dataOutputStream.writeInt(resultSet1.getInt(1));
            }
            while (resultSet.next()) {
                WriteCharToStream(resultSet.getString(1), dataOutputStream);
                WriteCharToStream(resultSet.getString(2), dataOutputStream);
                WriteCharToStream(resultSet.getString(3), dataOutputStream);
                WriteCharToStream(resultSet.getString(4), dataOutputStream);
                WriteCharToStream(resultSet.getString(5), dataOutputStream);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void BlockUser(DataOutputStream dataOutputStream, User usr, Statement callableState) {

    }

    public void UserFunc(DataInputStream inputStream, DataOutputStream outputStream, User usr, Statement callableState) {
        while (true) {
            try {
                ArrayList<String> arrayList = new ArrayList<>();

                inputStream.readInt();
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < 13; i++) {
                    arrayList.add(ReadCharToString(inputStream));
                    System.out.println(i);
                }
                stringBuilder.append('[');
                for (int i = 0; i < 13; i++) {
                    if (i == 12) {
                        stringBuilder.append(arrayList.get(i));
                    } else
                        stringBuilder.append(arrayList.get(i) + ",");
                }
                stringBuilder.append("]");
                Double predictionVar = PredictionFunc(stringBuilder.toString());
                arrayList.add(String.valueOf(predictionVar));
                System.out.println(predictionVar);
                System.out.println(arrayList);
                WriteCharToStream(predictionVar.toString(), outputStream);
                callableState.executeUpdate(String.format("insert into \"data1\"(user_id,crim,zn,indus,chas) values (%d,%s,%s,%s,%s);", usr.getId(), arrayList.get(0), arrayList.get(1), arrayList.get(2), arrayList.get(3)));
                callableState.executeUpdate(String.format("insert into \"data2\"(user_id,nox,rm,age,dis) values (%d,%s,%s,%s,%s);", usr.getId(), arrayList.get(4), arrayList.get(5), arrayList.get(6), arrayList.get(7)));
                callableState.executeUpdate(String.format("insert into \"data3\"(user_id,rad,tax,ptration,blck,lstat) values (%d,%s,%s,%s,%s,%s);", usr.getId(), arrayList.get(8), arrayList.get(9), arrayList.get(10), arrayList.get(11), arrayList.get(12)));
                callableState.executeUpdate(String.format("insert into \"result\"(user_id,predict) values (%d,%s);", usr.getId(), arrayList.get(13)));
            } catch (IOException | ParseException | SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

//[18.0846,0.,18.1,0.,0.679,6.434,100.,1.8347,24.,666.,20.2,27.25,29.05]