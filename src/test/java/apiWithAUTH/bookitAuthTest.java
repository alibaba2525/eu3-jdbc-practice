package apiWithAUTH;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;
public class bookitAuthTest {

    @BeforeClass
    public void before(){
        baseURI = "https://cybertek-reservation-api-qa2.herokuapp.com";
    }

    String accessToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI1NyIsImF1ZCI6InN0dWRlbnQtdGVhbS1sZWFkZXIifQ.a_N9URDBPGOMcDdEVoaMHsJtk3jOnig0v0SCtSWcsGE";

    @Test
    public void getAllCampuses(){

        Response response = given().header("Authorization",accessToken).
                when().get("/api/campuses");

        response.prettyPrint();
        System.out.println(response.statusCode());

    }
}