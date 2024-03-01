package restful_booker;

import io.qameta.allure.*;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import testData.BookingData;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


@Feature("Creating E2E test example")
public class E2ETest {

    private RequestSpecification reqSpec;
    private int id;


    @BeforeTest
    public void setup() {
        BaseSetup baseSetup = new BaseSetup();
        reqSpec = baseSetup.setup();
    }

    @Test
    @Description("Create booking with POST example")
    @Severity(SeverityLevel.CRITICAL)
    @Step("Create booking")
    public void createBooking() {
         id = given(reqSpec)
                 .body(BookingData.getBookingData())
                 .when()
                 .post("/booking")
                 .then()
                 .assertThat()
                 .statusCode(200)
                 .and()
                 .body("bookingid", notNullValue())
                 .body("booking.firstname", equalTo(BookingData.firstName))
                 .body("booking.lastname", equalTo(BookingData.lastName))
                 .body("booking.totalprice", greaterThanOrEqualTo(100))
                 .body("booking.totalprice", lessThanOrEqualTo(5000))
                 .body("booking.depositpaid", equalTo(BookingData.depositPaid))
                 .body("booking.bookingdates.checkin", equalTo(BookingData.checkIn))
                 .body("booking.bookingdates.checkout", equalTo(BookingData.checkOut))
                 .body("booking.additionalneeds", equalTo(BookingData.additionalNeeds))
                 .extract().path("bookingid");
    }
}
