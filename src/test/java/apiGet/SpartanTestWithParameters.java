package apiGet;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;//we don't define every test case RestAssured with the help of this static import
import static org.testng.Assert.*;


public class SpartanTestWithParameters {

    @BeforeClass
    public void beforeClass(){
        baseURI = "http://18.204.13.78:8000/";//RestAssured provides us defining one time, don't forget staticly imported as well
    }


    /*
         Given accept type is Json
         And Id parameter value is 5
         When user sends GET request to /api/spartans/{id}
         Then response status code should be 200
         And response content-type: application/json;charset=UTF-8
         And "Blythe" should be in response payload(means 'body' just funny word)
      */
    @Test
    public void getSpartanID_Positive_PathParam(){
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 5)
                .when().get("api/spartans/{id}");//in postman ':id' but in RestAssured library '{id}'

        //Assert class is staticly imported at the beginning
        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json;charset=UTF-8");
        assertTrue(response.body().asString().contains("Blythe"));
    }


    /*
        TASK
        Given accept type is Json
        And Id parameter value is 500
        When user sends GET request to /api/spartans/{id}
        Then response status code should be 404
        And response content-type: application/json;charset=UTF-8
        And "Spartan Not Found" message should be in response payload
     */
    @Test
    public void getSpartanID_Negative_PathParam(){
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 500)
                .when().get("api/spartans/{id}");

        //Assert class is staticly imported at the beginning
        assertEquals(response.statusCode(),404);
        assertEquals(response.contentType(),"application/json;charset=UTF-8");
        assertTrue(response.body().asString().contains("Spartan Not Found"));
    }


    /*
        Given accept type is Json
        And query parameter values are :
        gender|Female
        nameContains|e
        When user sends GET request to /api/spartans/search
        Then response status code should be 200
        And response content-type: application/json;charset=UTF-8
        And "Female" should be in response payload
        And "Janette" should be in response payload
     */
    @Test
    public void positiveTestWithQueryParam(){
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("gender", "Female")
                .and().queryParam("nameContains", "e")
                .when().get("api/spartans/search");

        //Assert class is staticly imported at the beginning
        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json;charset=UTF-8");
        assertTrue(response.body().asString().contains("Janette") && response.body().asString().contains("Female"));

    }


    //Same scenario with previous one but here we gonna use queryParams with map!
    @Test
    public void positiveTestWithQueryParamsWithMap(){

        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("gender","Female");
        queryMap.put("nameContains","e");

        Response response = given().accept(ContentType.JSON)
                .and().queryParams(queryMap)//queryParams with map
                .when().get("api/spartans/search");

        //Assert class is staticly imported at the beginning
        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json;charset=UTF-8");
        assertTrue(response.body().asString().contains("Janette") && response.body().asString().contains("Female"));

    }


}
