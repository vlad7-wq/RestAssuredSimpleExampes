package other_types_of_tests;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ValidateResponseTime {

    @BeforeTest
    public void setup() { RestAssured.baseURI = "https://reqres.in"; }

    @Test
    public void getResponseTime() {

        var resp = given()
                .when()
                .get("/api/users?delay=3")
                .then()
                .assertThat()
                .statusCode(200);

        Allure.description("response time: " + resp.extract().time());
    }

    @Test
    @Description("Verify response time")
    public void verifyResponseTime() {

        given()
                .when()
                .get("/api/users?page=2")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .time(lessThan(1000L));

    }
}
