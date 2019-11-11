package com.spartaglobal.RestassuredPostcodes;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;

import static io.restassured.RestAssured.*;   //all the methods in rest assured are static
import static org.hamcrest.Matchers.*;

public class MultiplePostcodes {

     private  static String postcodeJSON = "{\"postcodes\": [\"cr0 6ht\", \"se26 6xw\"]}";
   private static  JsonPath multiplePCResponse;  // i don't want to make multiple calls in my test


    @BeforeClass
    public static void setup() {
        baseURI = "https://api/postcodes.io";
        basePath = "/postcodes/";

        multiplePCResponse =  //creating post
                given()
                .contentType(ContentType.JSON)
                .body(postcodeJSON)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath();  //set it to json path
    }

    @Test
    public void exampleJSONPath () {
        System.out.println(multiplePCResponse.get("status").toString());
        Assert.assertEquals(200, multiplePCResponse.getLong("status"));
    }

    @Test
    public void checkPostcodeResponse () {
        Assert.assertEquals("cr06ht", multiplePCResponse.getString("result[0].result.postcode"));
    }
}
