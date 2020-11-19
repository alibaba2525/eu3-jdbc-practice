package apitests;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class hrApiWithJsonPath {

    @BeforeClass
    public void beforeClass(){

        baseURI= ConfigurationReader.get("hr_api_url");//RestAssured provides us defining one time, don't forget staticly imported as well

    }


    @Test
    public void test1(){

        Response response = get("/countries");

        //assign response to JsonPath
        JsonPath jsonPath = response.jsonPath();

        //get all country ids
        List<String> countryIds = jsonPath.getList("items.country_id");
        System.out.println(countryIds);

        //get all country ids where their region_id is equal to 2(for this situation we can use params or we can use Gpath syntax like down below!)
        List<String> countryIDsWithRegionId2 = jsonPath.getList("items.findAll {it.region_id==2}.country_id");
        System.out.println(countryIDsWithRegionId2);

    }

    @Test
    public void test2(){

        Response response = given().accept(ContentType.JSON)
                .when().get("employees/");

        JsonPath jsonPath = response.jsonPath();

        //get me all emails of employees who is working as IT_PROG
        List<String> allEmailsIT_PROG = jsonPath.getList("items.findAll {it.job_id==\"IT_PROG\"}.email");
        System.out.println(allEmailsIT_PROG);

        //get me all firstname of employees who is making more than 10000 salary
        List<String> firstNames = jsonPath.getList("items.findAll {it.salary>10000}.first_name");
        System.out.println(firstNames);

        //get me first name of who is making highest salary
        String firstNameMaxSalary = jsonPath.getString("items.max {it.salary}.first_name");
        System.out.println(firstNameMaxSalary);

        //get me first name of who is making lowest salary
        String firstNameMinSalary = jsonPath.getString("items.min {it.salary}.first_name");
        System.out.println(firstNameMinSalary);


    }


    //NOTES:
//        there are multiple different ways of navigationg through json body/payload
//                -using path() method
//                -using JsonPath
//                -using deseralization to collection
//                -using desearalization to Java Custom Classes(POJO)

//        path() and JsonPath they both use Gpath syntax !! ( Gpath(G = groovy) is the syntax that we did up below!! )

}
