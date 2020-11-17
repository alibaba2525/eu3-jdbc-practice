package apitests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;//we don't define every test case RestAssured with the help of this static import

public class simpleGetRequest {

    String hrurl = "http://18.204.13.78:1000/ords/hr/regions";


    @Test
    public void test1(){

        Response response = RestAssured.get(hrurl);

        //print the status code
        System.out.println(response.statusCode());

        //print the json body
        response.prettyPrint();
    }



     /* TEST SCENARIO DOWN BELOW
        Given accept type is json
        When user sends get request to regions endpoint
        Then response status code must be 200
        and body is json format
     */
    @Test
    public void test2(){

        Response response = RestAssured.given().accept(ContentType.JSON)
                                       .when().get(hrurl);//until semicolon java understands as one line!!

        System.out.println(response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(),200);

        System.out.println(response.contentType());
        Assert.assertEquals(response.contentType(),"application/json");
    }
    //SAME SCENARIO WITH PREVIOUS ONE BUT IN 1 STATEMENT AS BDD FORMAT :
    @Test
    public void test3(){
        RestAssured.given().accept(ContentType.JSON)
                   .when().get(hrurl)
                   .then().assertThat().statusCode(200)//directly gets expected as parameter and do assertion with actual, different from response one
                   .and().contentType("application/json");//directly gets expected as parameter and do assertion with actual, different from response one

    }


    /*THIS SCENARIO FOR DOWN BELOW
           Given accept type is json
           When user sends get request to regions/2
           Then response status code must be 200
           and body is json format
           and response body contains Americas
        */
    @Test
    public void test4(){

        Response response = given().accept(ContentType.JSON)//we didn't put RestAssured here because we practiced static import top below for not writing RestAssured everytime.
                           .when().get(hrurl);

        Assert.assertEquals(response.getStatusCode(),200);

        Assert.assertEquals(response.contentType(),"application/json");

        Assert.assertTrue(response.body().asString().contains("Americas"));

    }


}
