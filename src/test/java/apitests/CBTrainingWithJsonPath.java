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
import static org.testng.Assert.*;

public class CBTrainingWithJsonPath {

    @BeforeClass
    public void setupUrl(){
        baseURI = ConfigurationReader.get("cbt_api_url");
    }

    @Test
    public void test1(){
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 17982)
                .when().get("/student/{id}");

        assertEquals(response.getStatusCode(),200);
        assertEquals(response.getContentType(),"application/json;charset=UTF-8");

        JsonPath jsonPath = response.jsonPath();
        String firstName = jsonPath.getString("students.firstName[0]");
        assertEquals(firstName,"Vera");

        String lastName = jsonPath.getString("students.lastName[0]");
        assertEquals(lastName,"Jakson");

        String phone = jsonPath.getString("students.contact[0].phone");
        assertEquals(phone,"7738557985");

        int zipCode = jsonPath.getInt("students.company[0].address.zipCode");
        assertEquals(zipCode,60606);

        String city = jsonPath.getString("students.company[0].address.city");
        assertEquals(city , "Chicago");

        //assigning list-array to String; with jsonPath it allows and just returns the array in toString-array structure
                                       //but path() doesn't allow and gives error that's why commented out down below!
           //with jsonPath
           String firstname2 = jsonPath.getString("students.firstName");
           System.out.println("firstname2 = " + firstname2);

           //with path()
//        String firstname3 =response.path("students.firstName");
//        System.out.println("firstname3 = " + firstname3);

    }
}
