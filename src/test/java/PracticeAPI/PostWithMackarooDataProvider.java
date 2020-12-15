package PracticeAPI;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class PostWithMackarooDataProvider {

    @BeforeTest
    public void beforeTest(){
        baseURI= ConfigurationReader.get("spartan_api_url");
    }



    @DataProvider(name="getRegistrations")
    public Iterator<Object []> provider( ) throws InterruptedException, IOException {

        String line = "";
        List<Object []> testCases = new ArrayList<>();
        Object[] data= null;


        BufferedReader br = new BufferedReader(new FileReader("src/test/resources/MOCK_DATA.csv"));
        while ((line = br.readLine()) != null) {

            data= line.split(",");
            testCases.add(data);
        }

        return testCases.iterator();
    }



    @Test(dataProvider = "getRegistrations")
    public void getRegistrations(Object name, Object gender, Object phone) throws IOException {

        Map<String,Object> spartan = new HashMap<>();
        spartan.put("name",name);
        spartan.put("gender",gender);
        spartan.put("phone",phone);

        System.out.println(spartan);

        Response response = given().accept(ContentType.JSON).and().contentType(ContentType.JSON)
                .body(spartan).post("/api/spartans");
        Assert.assertEquals( response.statusCode(),201);
        System.out.println(response.statusCode());

        int path = response.path("data.id");

        Response response2 = given().pathParam("id",path).when().delete("/api/spartans/{id}");
        System.out.println(response2.statusCode());


    }
}
