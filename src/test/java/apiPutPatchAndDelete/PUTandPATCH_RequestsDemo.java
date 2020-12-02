package apiPutPatchAndDelete;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;

public class PUTandPATCH_RequestsDemo {
    @BeforeClass
    public void beforeClass(){
        baseURI= ConfigurationReader.get("spartan_api_url");
    }

    @Test
    public void Put(){
        //Create one map for the put request json body
        Map<String,Object> putRequestMap = new HashMap<>();
        putRequestMap.put("name","PutName");
        putRequestMap.put("gender","Male");
        putRequestMap.put("phone",1231312321l);

        given().log().all()
                .and()
                .contentType(ContentType.JSON)
                .and()
                .pathParam("id",90)
                .and()
                .body(putRequestMap).
                when()
                .put("/api/spartans/{id}")
                .then().log().all()
                .assertThat().statusCode(204);

        //you can send get request to verify body

    }

    @Test
    public void PatchTest(){

        //Create one map for the patch request json body
        Map<String,Object> putRequestMap = new HashMap<>();

        putRequestMap.put("name","TJ");//only send with the ones you want to update because patch(partial update)

        given().log().all()
                .and()
                .contentType(ContentType.JSON)
                .and()
                .pathParam("id",90)
                .and()
                .body(putRequestMap).
                when()
                .patch("/api/spartans/{id}")
                .then().log().all()
                .assertThat().statusCode(204);


        //you can send get request to verify body

    }
}