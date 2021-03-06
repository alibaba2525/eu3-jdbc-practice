package apiPutPatchAndDelete;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.Random;

import static io.restassured.RestAssured.baseURI;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

public class DELETE_RequestDemo {

    @BeforeClass
    public void beforeclass(){
        baseURI= ConfigurationReader.get("spartan_api_url");
    }

    @Test
    public void test1(){
        Random rd = new Random();
        int idToDelete = rd.nextInt(200)+1;
        System.out.println("This spartan id: " + idToDelete +" will be deleted.Say good bye !");

        given().
                pathParam("id",idToDelete)
                .when()
                .delete("/api/spartans/{id}")//after delete this method's id should change because previous is gone!
                .then()
                .statusCode(204).log().all();
    }

}