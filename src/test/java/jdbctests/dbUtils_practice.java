package jdbctests;

import org.testng.annotations.Test;
import utilities.DButils;

import java.util.List;
import java.util.Map;

public class dbUtils_practice {

    @Test
    public void test1(){

        //create connections method from DBUtils
        DButils.createConnection();

        //getQueryResultMap method : returns List of Maps
        List<Map<String, Object>> queryResult = DButils.getQueryResultMap("select * from departments");
        for (Map<String, Object> map : queryResult) {
            System.out.println(map.toString());
        }

        System.out.println("===============");

        //getRowMap method : returns Map of String,Object
        Map<String,Object> rowMap = DButils.getRowMap("select first_name from employees where employee_id=100");
        System.out.println(rowMap);

        //close connections method from DBUtils
        DButils.destroy();
    }
}
