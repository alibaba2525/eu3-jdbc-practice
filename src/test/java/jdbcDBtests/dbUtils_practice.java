package jdbcDBtests;

import org.testng.annotations.Test;
import utilities.DBUtils;

import java.util.List;
import java.util.Map;

public class dbUtils_practice {

    @Test
    public void test1(){

        //create connections method from DBUtils
        DBUtils.createConnection();

        //getQueryResultMap method : returns List of Maps
        List<Map<String, Object>> queryResult = DBUtils.getQueryResultMap("select * from employees");
        for (Map<String, Object> map : queryResult) {
            System.out.println(map.toString());
        }

        System.out.println("===============");

        //getRowMap method : returns Map of String,Object
        Map<String,Object> rowMap = DBUtils.getRowMap("select first_name from employees where employee_id=100",0);
        System.out.println(rowMap);

        //close connections method from DBUtils
        DBUtils.destroy();
    }
}
