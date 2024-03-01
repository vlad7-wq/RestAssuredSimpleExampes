package testData;

import com.github.javafaker.Faker;
import org.json.simple.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BookingData {

    // set fake data
    private static Faker faker = new Faker();
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    public static String firstName = faker.name().firstName();
    public static String lastName = faker.name().lastName();
    public static int totalPrice = faker.number().numberBetween(100, 5000);
    public static boolean depositPaid = true;
    public static String checkIn = df.format(faker.date().past(5, TimeUnit.DAYS));
    public static String checkOut = df.format(faker.date().future(60, 30, TimeUnit.DAYS));
    public static String additionalNeeds = "breakfast";

    public static String getBookingData() {
        JSONObject bookingdates = new JSONObject();
        JSONObject jsonObj = new JSONObject();

        // nested JSON
        bookingdates.put("checkin", checkIn);
        bookingdates.put("checkout", checkOut);

        jsonObj.put("firstname", firstName);
        jsonObj.put("lastname", lastName);
        jsonObj.put("totalprice", totalPrice);
        jsonObj.put("depositpaid", depositPaid);
        jsonObj.put("bookingdates", bookingdates);
        jsonObj.put("additionalneeds", additionalNeeds);

        return jsonObj.toString();
    }
}
