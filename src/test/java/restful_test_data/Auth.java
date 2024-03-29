package restful_test_data;

import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import restful_booker_e2e_test.BaseSetup;

import static io.restassured.RestAssured.given;

public class Auth {

    public static String getAuthToken() {

        BaseSetup baseSetup = new BaseSetup();
        RequestSpecification reqSpec = baseSetup.setup();

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("username", "admin");
        jsonObj.put("password", "password123");

        var token = given(reqSpec)
                .body(jsonObj.toString())
                .when()
                .post("/auth")
                .then()
                .extract()
                .path("token");

        return token.toString();

    }
}
