package apitests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;//we don't define every test case RestAssured with the help of this static import
import static org.testng.Assert.*;

public class SpartanTestWithPath {

    @BeforeClass
    public void beforeClass(){

        baseURI="http://18.204.13.78:8000/";//RestAssured provides us defining one time, don't forget staticly imported as well

    }



     /*
   Given accept type is json
   And path param id is 10
   When user sends a get request to "api/spartans/{id}"
   Then status code is 200
   And content-type is "application/json;charset=UTF-8"
   And response payload values match the following:
           id is 10,
           name is "Lorenza",
           gender is "Female",
           phone is 3312820936
    */

    @Test
    public void getOneSpartanWith_path(){
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 10)
                .when().get("api/spartans/{id}");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json;charset=UTF-8");

        //save json key values, with path method datas are coming from json as "T"(we can use all data types with T )
        int id =  response.path("id");
        String name = response.path("name");
        String gender = response.path("gender");
        long phone = response.path("phone");

        //assert one by one
        assertEquals(id,10);
        assertEquals(name,"Lorenza");
        assertEquals(gender,"Female");
        assertEquals(phone,3312820936l);


    }
}
