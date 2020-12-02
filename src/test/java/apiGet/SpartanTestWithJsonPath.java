package apiGet;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class SpartanTestWithJsonPath {

    @BeforeClass
    public void beforeClass(){

        baseURI= ConfigurationReader.get("spartan_api_url");//RestAssured provides us defining one time, don't forget staticly imported as well

    }

    @Test
    public void test1(){
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 11)
                .when().get("/api/spartans/{id}");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json;charset=UTF-8");

        //verify id and name with ' path() '(one of the 4 ways of getting values from json body)
        int id = response.path("id");
        String name = response.path("name");
        assertEquals(id,11);
        assertEquals(name,"Nona");


        //second way getting values from json body is ' JsonPath ' : it returns the datas with their data types in ready methods from jsonPath
        //firstly we assign response to JsonPath and then retrieve the values from json body
        JsonPath jsonPath = response.jsonPath();
        assertEquals(jsonPath.getString("name"),"Nona");
        assertEquals(jsonPath.getInt("id"),11);
        assertEquals(jsonPath.getString("gender"),"Female");
        assertEquals(jsonPath.getLong("phone"),7959094216l);


        //NOTES:
//        there are multiple different ways of navigating through json body and response
//                -using path() method
//                -using JsonPath
//                -using Hamcrest Matchers way
//                -using deseralization to collection
//                -using desearalization to Java Custom Classes(POJO)

//        path() and JsonPath they both use Gpath syntax !!


    }
}
