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

    @Test
    public void tc2(){

        Response response = given().accept(ContentType.JSON)
                .and().queryParam("gender", "Female")
                .and().queryParam("nameContains", "r")
                .when().get("/api/spartans/search");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json;charset=UTF-8");

        JsonPath jsonPath = response.jsonPath();

        List<String> genders = jsonPath.getList("content.gender");
        for (String gender : genders) {
            assertEquals(gender , "Female");
        }

        List<String> names = jsonPath.getList("content.name");
        for (String name : names) {
            assertTrue(name.toLowerCase().contains("r"));
        }

        assertEquals(jsonPath.getInt("size") , 20);
        assertEquals(jsonPath.getInt("totalPages") , 1);

        assertEquals(jsonPath.getBoolean("sort.sorted") , false);


    }

    @Test
    public void tc3(){

        Response response = given().accept(ContentType.JSON)
                .when().get("/api/spartans/search");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json;charset=UTF-8");

        JsonPath jsonPath = response.jsonPath();

        //print the name which is 'Fidole'
        String name = jsonPath.getString("content.findAll {it.name==\"Fidole\"}.name");
        System.out.println(name);

        //print the names which starts with 'M' or 'N' :
        List<String> names = jsonPath.getList("content.findAll {it.name.startsWith(\"M\") || it.name.startsWith(\"N\")}.name");
        System.out.println(names);





    }







}
