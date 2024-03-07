package restful_booker_e2e_test;

import io.qameta.allure.*;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import restful_test_data.Auth;
import restful_test_data.BookingData;
import restful_test_data.BookingDataProvider;
import restful_test_data.PartialBookingData;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Feature("Creating E2E test example")
public class E2ETest {

    private RequestSpecification reqSpec;
    private int id;
    private BookingData bookingData;
    private String token;

    @BeforeTest
    public void setup() {

        BaseSetup baseSetup = new BaseSetup();
        bookingData = BookingDataProvider.getValidBookingData();
        reqSpec = baseSetup.setup();
        token = Auth.getAuthToken();

    }

    @Test(priority = -1)
    @Description("Create booking with POST example")
    @Severity(SeverityLevel.CRITICAL)
    @Step("Create booking")
    public void createBooking() {

         id = given(reqSpec)
                 .body(BookingDataProvider.bookingToJson(bookingData))
                 .when()
                 .post("/booking")
                 .then()
                 .assertThat()
                 .statusCode(200)
                 .and()
                 .body("bookingid", notNullValue())
                 .body("booking.firstname", equalTo(bookingData.getFirstName()))
                 .body("booking.lastname", equalTo(bookingData.getLastName()))
                 .body("booking.totalprice", greaterThanOrEqualTo(100))
                 .body("booking.totalprice", lessThanOrEqualTo(5000))
                 .body("booking.depositpaid", equalTo(bookingData.isDepositPaid()))
                 .body("booking.bookingdates.checkin", equalTo(bookingData.getCheckIn()))
                 .body("booking.bookingdates.checkout", equalTo(bookingData.getCheckOut()))
                 .body("booking.additionalneeds", equalTo(bookingData.getAdditionalNeeds()))
                 .extract().path("bookingid");

    }

    @Test(dependsOnMethods = {"createBooking"})
    @Description("Get booking by ID with GET example")
    @Severity(SeverityLevel.CRITICAL)
    @Step("Verify that newly created booking is exist by ID")
    public void getBookingById() {

        given(reqSpec)
                .when()
                .get(String.format("/booking/%d", id))
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("firstname", equalTo(bookingData.getFirstName()))
                .body("lastname", equalTo(bookingData.getLastName()))
                .body("totalprice", greaterThanOrEqualTo(100))
                .body("totalprice", lessThanOrEqualTo(5000))
                .body("depositpaid", equalTo(bookingData.isDepositPaid()))
                .body("bookingdates.checkin", equalTo(bookingData.getCheckIn()))
                .body("bookingdates.checkout", equalTo(bookingData.getCheckOut()))
                .body("additionalneeds", equalTo(bookingData.getAdditionalNeeds()));

    }

    @Test(priority = 1)
    @Description("Update booking with PUT example")
    @Severity(SeverityLevel.NORMAL)
    @Step("Verify updating booking")
    public void updateBooking() {

        BookingData newBookingData = BookingDataProvider.getValidBookingData();

        given(reqSpec)
                .body(BookingDataProvider.bookingToJson(newBookingData))
                .header("Cookie", "token=" + token)
                .when()
                .put(String.format("/booking/%d", id))
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("firstname", equalTo(newBookingData.getFirstName()))
                .body("lastname", equalTo(newBookingData.getLastName()))
                .body("totalprice", greaterThanOrEqualTo(100))
                .body("totalprice", lessThanOrEqualTo(5000))
                .body("depositpaid", equalTo(newBookingData.isDepositPaid()))
                .body("bookingdates.checkin", equalTo(newBookingData.getCheckIn()))
                .body("bookingdates.checkout", equalTo(newBookingData.getCheckOut()))
                .body("additionalneeds", equalTo(newBookingData.getAdditionalNeeds()));

    }

    @Test(priority = 2)
    @Description("Update booking with PATCH example")
    @Severity(SeverityLevel.NORMAL)
    @Step("Verify partially booking update")
    public void partiallyUpdateBooking() {

        PartialBookingData partialBookingData = BookingDataProvider.getPartialBookingData();

        given(reqSpec)
                .body(BookingDataProvider.partialBookingToJson(partialBookingData))
                .header("Cookie", "token=" + token)
                .when()
                .patch(String.format("/booking/%d", id))
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("bookingdates.checkin", equalTo(partialBookingData.getCheckIn()))
                .body("bookingdates.checkout", equalTo(partialBookingData.getCheckOut()))
                .body("additionalneeds", equalTo(partialBookingData.getAdditionalNeeds()));

    }

    @Test(priority = 3)
    @Description("Delete booking with DELETE example")
    @Severity(SeverityLevel.NORMAL)
    @Step("Verify deleting booking")
    public void deleteBooking() {

        given(reqSpec)
                .header("Cookie", "token=" + token)
                .when()
                .delete(String.format("/booking/%d", id))
                .then()
                .assertThat()
                .statusCode(201);

    }

    @Test(priority = 4)
    @Description("Verifying that booking was deleted")
    @Severity(SeverityLevel.NORMAL)
    @Step("Verify that deleted booking is no longer exist")
    public void verifyDeletedBooking() {

        given(reqSpec)
                .when()
                .get(String.format("/booking/%d", id))
                .then()
                .assertThat()
                .statusCode(404)
                .and()
                .body(equalTo("Not Found"));

    }
}
