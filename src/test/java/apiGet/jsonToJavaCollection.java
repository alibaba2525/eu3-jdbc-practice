package apiGet;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class jsonToJavaCollection {  // Di-serialization way(4th way) of dealing with json response:
    //But for this way, for converting json to java collection we also need to put Gson or Jackson dependency to pom xml!!

    @BeforeClass
    public void beforeClass(){
        baseURI = ConfigurationReader.get("spartan_api_url");
    }

    @Test
    public void spartanToMap(){
        Response response = given().accept(ContentType.JSON)
                .pathParam("id", 15)
                .when().get("/api/spartans/{id}");

        assertEquals(response.statusCode(),200);

        //we will convert json response to java map
        Map<String,Object> jsonDataMap = response.body().as(Map.class);
        System.out.println("jsonDataMap = " + jsonDataMap);

        String name = (String) jsonDataMap.get("name");
        assertEquals(name,"Meta");

        //phone data was coming as different value with decimal that's why we used this BigDecimal class
        BigDecimal actualPhone = BigDecimal.valueOf((Double) jsonDataMap.get("phone"));
        BigDecimal expectedPhone = BigDecimal.valueOf(1938695106);

        assertTrue(actualPhone.compareTo(expectedPhone)==0);

        System.out.println("actualPhone = " + actualPhone);
        System.out.println("expectedPhone = " + expectedPhone);
    }

    @Test
    public void allSpartansToListOfMap(){

        Response response = given().accept(ContentType.JSON)
                .when().get("/api/spartans");

        assertEquals(response.statusCode(),200);

        //we need to de-serialize json response to list of maps
        List<Map<String,Object>> allSpartansWithInfos = response.body().as(List.class);
        System.out.println(allSpartansWithInfos);

        System.out.println(allSpartansWithInfos.get(0).get("id"));

        Map<String,Object> spartan3 = allSpartansWithInfos.get(2);
        System.out.println(spartan3);
    }

    @Test
    public void regionToMap(){

        Response response = when().get("http://52.55.102.92:1000/ords/hr/regions");

        assertEquals(response.statusCode(),200);

        //we de-serialize JSON response to Map
        Map<String,Object> regionMap = response.body().as(Map.class);

        System.out.println(regionMap.get("count"));

        System.out.println(regionMap.get("hasMore"));

        System.out.println(regionMap.get("items"));

        List<Map<String,Object>> itemsList = (List<Map<String, Object>>) regionMap.get("items");

        //print first region name
        System.out.println(itemsList.get(0).get("region_name"));


    }
}
