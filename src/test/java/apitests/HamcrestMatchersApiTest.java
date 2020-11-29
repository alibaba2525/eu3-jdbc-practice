package apitests;

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
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.testng.Assert.*;

public class HamcrestMatchersApiTest { //Hamcrest-Matchers is coming with RestAssured Library
                                      //This is another way to do verification with response and json body
                                      //And also known as chaining request and response way !


    /*
     given accept type is Json
     And path param id is 15
     When user sends a get request to spartans/{id}
     Then status code is 200
     And content type is Json
     And json data has following
         "id": 15,
         "name": "Meta",
         "gender": "Female",
         "phone": 1938695106
      */
    @Test
    public void OneSpartanWithHamcrest(){

        given().accept(ContentType.JSON)
                .and().pathParam("id",15)
                .when().get("http://18.204.13.78:8000/api/spartans/{id}") //request till here

                .then().assertThat().statusCode(200)//response verifications from here till the end
                .and().assertThat().contentType("application/json;charset=UTF-8")
                .and().assertThat().body("id",equalTo(15),
                "name",equalTo("Meta"),
                "gender",equalTo("Female"),
                "phone",equalTo(1938695106));
    }

    @Test
    public void teacherData(){
        given().accept(ContentType.JSON)
                .and().pathParam("id",8261)
                .when().log().all().get("http://api.cybertektraining.com/teacher/{id}")

                .then().assertThat().statusCode(200)
                .and().contentType(equalTo("application/json"))
                .and().header("Content-Length",equalTo("240"))
                .and().header("Connection",equalTo("Keep-Alive"))
                .and().header("Date",notNullValue())
                .and().assertThat().body("teachers.firstName[0]",equalTo("James"),
                "teachers.lastName[0]",equalTo("Bond"),
                "teachers.gender[0]",equalTo("Male"))

                .log().all();
    }

    @Test
    public void test3(){

    }

}
