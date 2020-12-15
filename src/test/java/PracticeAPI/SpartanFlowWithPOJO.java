package PracticeAPI;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

public class SpartanFlowWithPOJO {

    int id;


    @BeforeTest
    public void beforeTest(){
        baseURI= ConfigurationReader.get("spartan_api_url");
    }


    @Test(priority = 1)
    public void POSTNewSpartan(){
        SpartanPOJO spartanPOJO = new SpartanPOJO();
        spartanPOJO.setName("Mike");
        spartanPOJO.setGender("Male");
        spartanPOJO.setPhone(1234567891l);

        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)

                .when().body(spartanPOJO).post("/api/spartans");

        assertEquals(response.statusCode(), 201);

        id = response.path("data.id");



    }

    @Test(priority = 2)
    public void PUTExistingSpartan(){
        SpartanPOJO spartanPOJO = new SpartanPOJO();
        spartanPOJO.setName("Jack");
        spartanPOJO.setGender("Male");
        spartanPOJO.setPhone(1234567891l);

        given().contentType(ContentType.JSON)
                .and().pathParam("id",id)
                .when().body(spartanPOJO).put("/api/spartans/{id}")

                .then().assertThat().statusCode(204);



    }

    @Test(priority = 3)
    public void PATCHExistingSpartan(){
        Map<String,Object> patchMap = new HashMap<>();
        patchMap.put("name","Alan");

        given().contentType(ContentType.JSON)
                .and().pathParam("id",id)

                .when().body(patchMap).patch("/api/spartans/{id}")

                .then().assertThat().statusCode(204);


    }

    @Test(priority = 4)
    public void GETThatSpartan(){

        given().accept(ContentType.JSON)
                .and().pathParam("id",id)

                .when().get("/api/spartans/{id}")

                .then().assertThat().statusCode(200)
                .and().assertThat().body("name",equalTo("Alan"));



    }

    @Test(priority = 5)
    public void DELETEThatSpartan(){

        given().pathParam("id",id)

                .when().delete("/api/spartans/{id}")

                .then().assertThat().statusCode(204);


    }

    @Test(priority = 6)
    public void AfterDeleteGETThatSpartan(){

        given().accept(ContentType.JSON)
                .and().pathParam("id",id)

                .when().get("/api/spartans/{id}")

                .then().assertThat().statusCode(404);


    }

}
