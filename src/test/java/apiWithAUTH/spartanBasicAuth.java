package apiWithAUTH;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class spartanBasicAuth {

    @Test
    public void test1(){
        given()
                .accept(ContentType.JSON)
                .and()
                .auth().basic("admin","admin")//for this api it was built with Basic Auth that's why we chose basic after auth!

                .when()
                .get("http://54.198.216.176:8000/api/spartans")

                .then().log().all()
                .statusCode(200);
    }
}

