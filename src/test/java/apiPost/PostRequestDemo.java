package apiPost;

import apiGetPOJO.Spartan;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.baseURI;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class PostRequestDemo {

    @BeforeClass
    public void beforeClass() {

        baseURI = ConfigurationReader.get("spartan_api_url");
    }

        /*
    Given accept type and Content type is JSON
    And request json body is:
    {
      "gender":"Male",
      "name":"MikeEU",
      "phone":8877445596
   }
    When user sends POST request to '/api/spartans'
    Then status code 201
    And content type should be application/json
    And json payload/response should contain:
    "A Spartan is Born!" message
    and same data what is posted
 */

    @Test
    public void PostNewSpartan(){ //First way with sending post request json body as directly String:

        String jsonBody ="{\n" +
                "      \"gender\":\"Male\",\n" +
                "      \"name\":\"MikeEU\",\n" +
                "      \"phone\":8877445596\n" +
                "   }";

        Response response = given().log().all()
                .accept(ContentType.JSON)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .body(jsonBody)
                .when()
                .post("/api/spartans");

        response.prettyPrint();
        //verify status code 201 for post
        assertEquals(response.statusCode(),201);

        //verify content type
        assertEquals(response.contentType(),"application/json");

        //verify successful message
        String actualMessage = response.path("success");
        String expectedMessage = "A Spartan is Born!";

        assertEquals(actualMessage,expectedMessage);
        //short way
        // assertEquals(response.path("success"),"A Spartan is Born!");

        //assertion for spartan data
        String name = response.path("data.name");
        String gender = response.path("data.gender");
        long phone = response.path("data.phone");

        assertEquals(name,"MikeEU");
        assertEquals(gender,"Male");
        assertEquals(phone,8877445596l);


    }

    @Test
    public void PostNewSpartan2(){ //Second way with sending post request json body as Map with Gson:

        //create a map to keep request json body information
        Map<String,Object> requestMap = new HashMap<>();
        //adding value that we want to post
        requestMap.put("name","MikeEU2");
        requestMap.put("gender","Male");
        requestMap.put("phone",8877445596l);

        //post request
        given().log().all()
                .accept(ContentType.JSON)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .body(requestMap)//as() with Get request for de-serialization , this body() with Post-Put-Patch requests for serialization!!!

         .when()
                .post("/api/spartans")

         //response
         .then().log().all()
                .statusCode(201)
                .and()
                .contentType("application/json")
                .and()
                .body("success", is("A Spartan is Born!"),
                        "data.name",equalTo("MikeEU2"),
                        "data.gender",equalTo("Male"),
                        "data.phone",equalTo(8877445596l));

    }

    @Test
    public void PostNewSpartan3(){

        Spartan spartanEU = new Spartan();
        spartanEU.setName("MikeEU3");
        spartanEU.setGender("Male");
        spartanEU.setPhone(8877445596l);


        given().log().all()
                .accept(ContentType.JSON)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .body(spartanEU)
                .when()
                .post("/api/spartans")
                .then().log().all()
                .statusCode(201)
                .and()
                .contentType("application/json")
                .and()
                .body("success", is("A Spartan is Born!"),
                        "data.name",equalTo("MikeEU3"),
                        "data.gender",equalTo("Male"),
                        "data.phone",equalTo(8877445596l));

    }


    @Test
    public void PostNewSpartan4(){

        Spartan spartanEU = new Spartan();
        spartanEU.setName("MikeEU3");
        spartanEU.setGender("Male");
        spartanEU.setPhone(8877445596l);


        Response response = given().log().all()
                .accept(ContentType.JSON)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .body(spartanEU)
                .when()
                .post("/api/spartans");

        //END OF POST REQUEST


        //get request
        int idFromPost = response.path("data.id");
        System.out.println("id = " + idFromPost);
        //after post request, send a get request to generated spartan
        given().accept(ContentType.JSON)
                .pathParam("id",idFromPost)
                .when().get("/api/spartans/{id}")
                .then().statusCode(200).log().all();


    }

}
