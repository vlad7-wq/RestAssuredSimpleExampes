package restful_booker;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class BaseSetup {

    public RequestSpecification setup() {


        RequestSpecification reqSpec = new RequestSpecBuilder()
                .setBaseUri("https://restful-booker.herokuapp.com")
                .addHeader("Content-Type", "application/json")
                .build();

        return reqSpec;
    }
}
