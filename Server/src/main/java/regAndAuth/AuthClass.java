package regAndAuth;

import AdministratorCommonUser.Administrator;
import AdministratorCommonUser.CommonUser;
import ServerPackage.ClientDataProcessing;
import UserPackage.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AuthClass implements ClientDataProcessing {
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private Statement callableState;
    private Statement genState;
    public AuthClass(DataInputStream inputStream, DataOutputStream outputStream, Statement callableStatement, Statement genState) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.callableState = callableStatement;
        this.genState = genState;
    }

    public void StartUse() throws IOException {
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
                WriteCharToStream(status, outputStream);
                WriteCharToStream(result.getString(5), outputStream);
                if (result.getString(5).equals("0")) {
                    CommonUser commonUser = new CommonUser(outputStream, inputStream, usr, callableState);
                    commonUser.startUse();
                } else if (result.getString(5).equals("1")) {
                    Administrator administrator = new Administrator(outputStream, inputStream, usr, callableState, genState);
                    administrator.StartUse();
                }
            } else {
                WriteCharToStream("error", outputStream);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
