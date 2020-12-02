package apiGet;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class hrApiWithPath {

    @BeforeClass
    public void beforeClass(){

        baseURI= ConfigurationReader.get("hr_api_url");//RestAssured provides us defining one time, don't forget staticly imported as well

    }

    @Test
    public void getCountriesWithPath(){
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"region_id\":2}")
                .when().get("/countries");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");

        //print limit value(there is only 1 limit in json of hr)
        Object limit = response.path("limit");
        System.out.println(limit);

        //print hasMore value(there is only 1 limit in json of hr)
        Object hasMore = response.path("hasMore");
        System.out.println(hasMore);

        //get first countryId from items array(from json for hr database)
        String firstCountryId = response.path("items.country_id[0]");//'.' is going one down, same logic with '/' in xpath!
        System.out.println(firstCountryId);

        //get second countryName from items array(from json for hr database)
        String secondCountryName = response.path("items.country_id[1]");//'.' is going one down, same logic with '/' in xpath!
        System.out.println(secondCountryName);

        //get second links and get first href from links even links have only one value because it is inside an array!!!
        Object secondLinkFirstHref = response.path("items.links[2].href[0]");
        System.out.println(secondLinkFirstHref);

        //assert that if all region_id s are equal to 2
        List<Integer> region_ids = response.path("items.region_id");
        for (int region_id : region_ids) {
            assertEquals(region_id,2);
        }

    }


    @Test
    public void TASK(){
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"job_id\":\"IT_PROG\"}")
                .when().get("/employees");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");

        //make sure we have only IT_PROG as a job_id
        List<String> jobIds = response.path("items.job_id");
        for (String jobId : jobIds) {
            assertEquals(jobId,"IT_PROG");
        }

    }



}
