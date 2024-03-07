package other_types_of_tests;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class FindItemInResponse {

    @BeforeTest
    public void setup() { RestAssured.baseURI = "https://jsonplaceholder.typicode.com"; }

    @Test
    @Description("Verify that specific item is present in response")
    public void findItemByTitle() {

        var resp = given()
                .when()
                .get("/todos")
                .then()
                .assertThat()
                .statusCode(200);

        String expectedResult = "sed ab consequatur";
        String actualResult = resp.extract().path("title").toString();

        assertThat(actualResult, containsString(expectedResult));
    }
}
