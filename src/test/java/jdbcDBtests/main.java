package jdbcDBtests;

import java.sql.*;

public class main {

    public static void main(String[] args) throws SQLException {

        String dbUrl = "jdbc:oracle:thin:@18.204.13.78:1521:xe";
        String dbUsername = "hr";
        String dbPassword = "hr";

        //create connection
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);

        //create statement object
        Statement statement = connection.createStatement();

        //run query and get the result in resultset object
        ResultSet resultSet = statement.executeQuery("select first_name,last_name,salary from employees");

        //move pointer because first row is columns' row(column index starts from 1 but row index starts from 0 !!)
//        resultSet.next();

        //There are 2 ways to get the informations:
        //getting information with column name
        //getting information with column index (column index starts from 1 but row index starts from 0 !!)
//        System.out.println(resultSet.getString(1)+"-"+resultSet.getString("last_name"));
//        System.out.println(resultSet.getString("first_name")+"-"+resultSet.getString(2));
//
//        resultSet.next();
//
//        System.out.println(resultSet.getString(1)+"-"+resultSet.getString("last_name"));
//        System.out.println(resultSet.getString("first_name")+"-"+resultSet.getString(2));


    //But everytime 'next()' way is not dynamic and next method is returning boolean that's why we gonna use while loop:
        while (resultSet.next()){ //it understands the rows and loops it until rows are finished!
            System.out.println(resultSet.getString(1)+" "+resultSet.getString(2)+" - "+resultSet.getString(3));
        }

        //close all connections
        resultSet.close();
        statement.close();
        connection.close();


    }
}
