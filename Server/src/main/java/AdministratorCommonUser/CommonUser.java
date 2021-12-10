package AdministratorCommonUser;

import ServerPackage.ClientDataProcessing;
import UserPackage.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CommonUser implements ClientDataProcessing {
    private DataOutputStream outputStream;
    private DataInputStream inputStream;
    private User usr;
    private Statement callableState;

    public CommonUser(DataOutputStream outputStream, DataInputStream inputStream, User usr, Statement callableState) {
        this.outputStream = outputStream;
        this.inputStream = inputStream;
        this.usr = usr;
        this.callableState = callableState;
    }
    public void startUse() throws IOException {
        while (true) {
            Integer ch = inputStream.readInt();
            if(ch == 1){
                MakePrediction();
            }
            else if(ch == 2){
                System.out.println("DONE");
                SendStat();
            }
        }
    }
    public Double PredictionFunc(String string) throws IOException, ParseException {
        StringBuilder result = new StringBuilder();
        JSONParser parser = new JSONParser();
        URL url = new URL("http://127.0.0.1:5000/helloworld");
        JSONObject senddata = new JSONObject();
        senddata.put("data", string);
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
    public void MakePrediction(){
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
    public void SendStat(){
        ResultSet resultSet;
        System.out.println("DONE2");
        Integer userId = usr.getId();
        try {
            resultSet = callableState.executeQuery(String.format("select * from \"user\" where id = %s", userId));
            while(resultSet.next()){
                for (int i = 2; i <= 5; i++) {
                    WriteCharToStream(resultSet.getString(i), outputStream);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            resultSet = callableState.executeQuery(String.format("select count(user_id) from \"data1\" where user_id = %s", userId));
            while (resultSet.next()){
                WriteCharToStream(resultSet.getString(1), outputStream);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
