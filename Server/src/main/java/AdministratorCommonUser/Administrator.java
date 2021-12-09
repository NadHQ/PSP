package AdministratorCommonUser;

import ServerPackage.ClientDataProcessing;
import UserPackage.User;
import org.json.simple.parser.ParseException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Administrator implements ClientDataProcessing {
    private DataOutputStream outputStream;
    private DataInputStream inputStream;
    private User usr;
    private Statement callableState;
    private Statement getCallableState2;
    public Administrator(DataOutputStream outputStream, DataInputStream inputStream, User usr, Statement callableState, Statement getCallableState2) {
        this.outputStream = outputStream;
        this.inputStream = inputStream;
        this.usr = usr;
        this.callableState = callableState;
        this.getCallableState2 = getCallableState2;
    }
    public void StartUse() throws IOException {
        while (true) {
            Integer ch = inputStream.readInt();
            if (ch == 1) {
                ViewUsers(outputStream, usr, callableState, getCallableState2);
            } else if (ch == 2) {
                System.out.println("edit func");
                Edit(outputStream, inputStream, usr, callableState);
            } else if (ch == 3) {
                DeleteUser(inputStream, outputStream, usr, callableState);
            } else if (ch == 4) {
                AddUser(outputStream, callableState, inputStream);
            } else if (ch == 5) {
                BlockUser(inputStream, usr, callableState);
            } else if (ch == 6) {
                UnblockUser(inputStream, usr, callableState);
            }
        }
    }
    private void UnblockUser(DataInputStream inputStream, User usr, Statement callableState) {
        Integer UnblockId = null;
        try {
            UnblockId = inputStream.readInt();
            callableState.executeUpdate(String.format("update \"user\" set status = 't' where id = %s", UnblockId));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void AddUser(DataOutputStream dataOutputStream, Statement callableState , DataInputStream inputStream) {
        User AddUser = new User();
        try {
            AddUser.setLogin(ReadCharToString(inputStream));
            AddUser.setPass(ReadCharToString(inputStream));
            AddUser.setStatus(ReadCharToString(inputStream));
            AddUser.setRole(ReadCharToString(inputStream));
            callableState.executeUpdate(String.format("insert into \"user\"(log,pass,status,role) values ('%s','%s','%s','%s');", AddUser.getLogin(),AddUser.getPass(),AddUser.getStatus(),AddUser.getRole()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
        System.out.println("DONE1");
        try {
            String Idvar = ReadCharToString(inputStream);
            System.out.println(Idvar);
            String Login = ReadCharToString(inputStream);
            System.out.println(Login);
            String Pass = ReadCharToString(inputStream);
            System.out.println(Pass);
            String Status = ReadCharToString(inputStream);
            System.out.println(Status);
            String Role = ReadCharToString(inputStream);
            System.out.println(Role);

            callableState.executeUpdate(String.format("update \"user\" set log = '%s', pass = '%s', status = '%s', role = '%s' where id = %s;", Login, Pass, Status, Role, Idvar));
            System.out.println("DONE");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void ViewUsers(DataOutputStream dataOutputStream, User usr, Statement callableState, Statement getCallableState2) {
        ResultSet resultSet, resultSet1;
        try {
            System.out.println("123");
            resultSet = callableState.executeQuery("select * from \"user\";");
            resultSet1 = getCallableState2.executeQuery("select count(*) from \"user\";");
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

    private void BlockUser(DataInputStream inputStream, User usr, Statement callableState) {
        Integer BlockId = null;
        try {
            BlockId = inputStream.readInt();
            callableState.executeUpdate(String.format("update \"user\" set status = 'f' where id = %s", BlockId));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
