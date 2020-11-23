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


public class HW1 {

    @BeforeClass
    public void setupUrl(){
        baseURI = ConfigurationReader.get("hr_api_url");
    }

    @Test
    public void tc1(){
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("country_id", "US")
                .when().get("/countries/{country_id}");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");

        //String country_id = response.path("country_id");
        //assertEquals(country_id,"US");


        JsonPath jsonPath = response.jsonPath();

        assertEquals(jsonPath.getString("country_id"), "US");
        assertEquals(jsonPath.getString("country_name"), "United States of America");
        assertEquals(jsonPath.getInt("region_id"),2);
        assertEquals(jsonPath.getString("links.href[0]"),"http://18.204.13.78:1000/ords/hr/countries/US");

    }

    @Test
    public void tc2(){

        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"department_id\":80}")
                .when().get("/employees");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");

        JsonPath jsonPath = response.jsonPath();

        List<String> list = jsonPath.getList("items.findAll {it.job_id.startsWith('SA')}.job_id");
        System.out.println(list.toString());

        for (String s : list) {
            assertTrue(s.startsWith("SA"));
        }

        List<Integer> list2 = jsonPath.getList("items.findAll {it.department_id==80}.department_id");
        System.out.println(list2);

        for (Integer id : list2) {
            assertTrue(id==80);
        }

        assertEquals(jsonPath.getInt("count"), 25);


        //another way with path():

//        List<String> job_ids = response.path("items.job_id");
//        List<String> job_idsWithSA = new ArrayList<>();
//
//        for (String job_id : job_ids) {
//            if(job_id.startsWith("SA")){
//                job_idsWithSA.add(job_id);
//                assertTrue(job_id.startsWith("SA"));
//            }
//        }
//        System.out.println(job_idsWithSA);


    }

    @Test
    public void tc3(){

        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"region_id\":3}")
                .when().get("/countries");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");

        JsonPath jsonPath = response.jsonPath();

        List<Object> regionIDs = jsonPath.getList("items.findAll {it.region_id==3}.region_id");
        for (Object regionID : regionIDs) {
            assertEquals(regionID,3);
        }

        assertEquals(jsonPath.getInt("count") , 6);
        assertEquals(jsonPath.getBoolean("hasMore") , false);

        List<String> actualCountryNames = jsonPath.getList("items.country_name");

        List<String> expectedCountryNames = new ArrayList<>();
        expectedCountryNames.add("Australia");
        expectedCountryNames.add("China");
        expectedCountryNames.add("India");
        expectedCountryNames.add("Japan");
        expectedCountryNames.add("Malaysia");
        expectedCountryNames.add("Singapore");


        assertEquals(actualCountryNames , expectedCountryNames);
        System.out.println(actualCountryNames);
        System.out.println(expectedCountryNames);

    }




}
