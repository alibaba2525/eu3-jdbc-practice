package jdbctests;

import org.testng.annotations.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class dynamicListOfMap {

    String dbUrl = "jdbc:oracle:thin:@18.204.13.78:1521:xe";
    String dbUsername = "hr";
    String dbPassword = "hr";

    @Test
    public void listOfMapExamples() throws SQLException {

        //create connection
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        //create statement object
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        //run query and get the result in resultset object
        ResultSet resultSet = statement.executeQuery("select * from departments");


        //get the resultset MetaData
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();


        //list for keeping all rows a map
        List<Map<String,Object>> queryData = new ArrayList<>();

        //number of columns
        int colCount = resultSetMetaData.getColumnCount();

        //loop through each row
        while(resultSet.next()){

            Map<String,Object> row = new HashMap<>();

            for (int i = 1; i <= colCount; i++) {

                row.put(resultSetMetaData.getColumnName(i) , resultSet.getObject(i));//getString koymadik int cikarsa-
                                                                                  //-getObject hepsini kapsasin diye!
            }
            System.out.println(row);

            //add your map to your list
            queryData.add(row);
        }





        //close all connections
        resultSet.close();
        statement.close();
        connection.close();

    }
}
