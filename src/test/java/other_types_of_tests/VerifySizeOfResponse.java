package other_types_of_tests;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class VerifySizeOfResponse {

    @BeforeTest
    public void setup() { RestAssured.baseURI = "https://reqres.in"; }

    @Test
    @Description("Verify size of the response")
    public void verifyResponseSize() {

        var resp = given()
                .when()
                .get("/api/users?page=1")
                .then()
                .assertThat()
                .statusCode(200);

        JsonPath jsonPath = new JsonPath(resp.extract().body().asString());

        int sizePerPage = resp.extract().path("per_page");
        int actualSize = jsonPath.getInt("data.size()");

        assertThat(sizePerPage, equalTo(actualSize));
    }
}
