package apitests.HW;

import io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class HW2 {

    @BeforeClass
    public void setupUrl(){
        baseURI = ConfigurationReader.get("spartan_api_url");
    }

    @Test
    public void tc1(){

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 20)
                .when().get("/api/spartans/{id}");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json;charset=UTF-8");

        assertTrue(response.headers().hasHeaderWithName("Date"));

        assertEquals(response.header("Transfer-Encoding") , "chunked");


        JsonPath jsonPath = response.jsonPath();
        assertEquals(jsonPath.getInt("id") , 20);
        assertEquals(jsonPath.getString("name") , "Lothario");
        assertEquals(jsonPath.getString("gender") , "Male");
        assertEquals(jsonPath.getLong("phone") , 7551551687l);


    }
}
