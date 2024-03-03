package testData;

import com.github.javafaker.Faker;
import org.json.simple.JSONObject;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class BookingDataProvider {

    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private static Faker faker = new Faker();

    public static BookingData getValidBookingData() {

        BookingData bookingData = new BookingData();

        bookingData.setCheckIn(df.format(faker.date().past(5, TimeUnit.DAYS)));
        bookingData.setCheckOut(df.format(faker.date().future(30, 10, TimeUnit.DAYS)));
        bookingData.setFirstName(faker.name().firstName());
        bookingData.setLastName(faker.name().lastName());
        bookingData.setTotalPrice(faker.number().numberBetween(100, 5000));
        bookingData.setDepositPaid(true);
        bookingData.setAdditionalNeeds("breakfast");

        return bookingData;
    }

    public static PartialBookingData getPartialBookingData() {

        PartialBookingData partialBookingData = new PartialBookingData();

        partialBookingData.setCheckIn(df.format(faker.date().past(5, TimeUnit.DAYS)));
        partialBookingData.setCheckOut(df.format(faker.date().future(30, 10, TimeUnit.DAYS)));
        partialBookingData.setAdditionalNeeds("lunch");

        return partialBookingData;
    }

    public static String bookingToJson(BookingData bookingData) {

        JSONObject bookingDates = new JSONObject();
        JSONObject jsonObj = new JSONObject();

        // nested JSON
        bookingDates.put("checkin", bookingData.getCheckIn());
        bookingDates.put("checkout", bookingData.getCheckOut());

        jsonObj.put("firstname", bookingData.getFirstName());
        jsonObj.put("lastname", bookingData.getLastName());
        jsonObj.put("totalprice", bookingData.getTotalPrice());
        jsonObj.put("depositpaid", bookingData.isDepositPaid());
        jsonObj.put("bookingdates", bookingDates);
        jsonObj.put("additionalneeds", bookingData.getAdditionalNeeds());

        return jsonObj.toString();
    }

    public static String partialBookingToJson(PartialBookingData partialBookingData) {

        JSONObject bookingDates = new JSONObject();
        JSONObject jsonObj = new JSONObject();

        // nested JSON
        bookingDates.put("checkin", partialBookingData.getCheckIn());
        bookingDates.put("checkout", partialBookingData.getCheckOut());

        jsonObj.put("bookingdates", bookingDates);
        jsonObj.put("additionalneeds", partialBookingData.getAdditionalNeeds());

        return jsonObj.toString();
    }
}
