package other_types_of_tests;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;


public class TestJsonSchema {

    @BeforeTest
    public void setup() { RestAssured.baseURI = "https://bookstore.demoqa.com"; }

    @Test
    @Description("Verify JSON schema, single object")
    public void verifySingleSchema() {

        given()
                .when()
                .get("/BookStore/v1/Book?ISBN=9781449331818")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("test_json_schema.json"));

    }
}
