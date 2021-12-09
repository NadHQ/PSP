package regAndAuth;


import ServerPackage.ClientDataProcessing;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RegClass implements ClientDataProcessing {
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private Statement callableState;

    public RegClass(DataInputStream inputStream, DataOutputStream outputStream, Statement callableState) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.callableState = callableState;
    }

    public void start() {
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
}
