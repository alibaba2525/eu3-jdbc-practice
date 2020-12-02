package jdbcDBtests;

import org.testng.annotations.Test;

import java.sql.*;

public class jdbc_example {


    String dbUrl = "jdbc:oracle:thin:@18.204.13.78:1521:xe";
    String dbUsername = "hr";
    String dbPassword = "hr";


    @Test
    public void resultSetMethodsForRowsAndColumns() throws SQLException {

        //create connection
        Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        //create statement object
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);//this parameter provides us so many methods for result set methods
        //run query and get the result in resultset object
        ResultSet resultSet = statement.executeQuery("select * from departments");

        //How to find how many rows are there in the table
        //go to the last row
        resultSet.last();
        //get the current row number
        int numberOfRows = resultSet.getRow();
        System.out.println("There are " + numberOfRows + " rows in total on the table.");

        //print second column on the table
        //we are on the last row we need to go back to starting point(row 0) then use while loop
        resultSet.beforeFirst();//it goes to row 0 and then it starts while loop and starts to move next
        while (resultSet.next()) {
            System.out.println(resultSet.getString(2));
        }

        System.out.println("==============");

//you can create new query like this and it is gonna be new object and resultset(reference) will point this object and everything will be new-fresh for this query.
//        resultSet = statement.executeQuery("select * from regions");
//        while (resultSet.next()) {
//            System.out.println(resultSet.getString(2));
// }

            //close all connections
            resultSet.close();
            statement.close();
            connection.close();

    }



        @Test
        public void metaDataExamples() throws SQLException {

            //create connection
            Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
            //create statement object
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            //run query and get the result in resultset object
            ResultSet resultSet = statement.executeQuery("select * from departments");

            //get the database related data inside the databaseMetaData object
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            System.out.println("databaseMetaData.getUserName() = " + databaseMetaData.getUserName());
            System.out.println("databaseMetaData.getDatabaseProductName() = " + databaseMetaData.getDatabaseProductName());
            System.out.println("databaseMetaData.getDatabaseProductVersion() = " + databaseMetaData.getDatabaseProductVersion());
            System.out.println("databaseMetaData.getDriverName() = " + databaseMetaData.getDriverName());
            System.out.println("databaseMetaData.getDriverVersion() = " + databaseMetaData.getDriverVersion());

            //get the resultset MetaData
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            //how many columns we have ?
            int numberOfColumns = resultSetMetaData.getColumnCount();
            System.out.println(numberOfColumns+" rows in the table.");
            //how to get column names ?
            System.out.println(resultSetMetaData.getColumnName(1));
            System.out.println(resultSetMetaData.getColumnName(2));
            //print all column names ?
            for (int i = 1; i <= numberOfColumns; i++) {
                System.out.println(resultSetMetaData.getColumnName(i));
            }


            //close all connections
            resultSet.close();
            statement.close();
            connection.close();

            }



}
