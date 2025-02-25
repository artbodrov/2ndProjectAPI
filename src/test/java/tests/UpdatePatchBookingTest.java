package tests;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.clients.APIClient;
import core.models.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdatePatchBookingTest {

    private APIClient apiClient;
    private ObjectMapper objectMapper;
    private CreatedBooking createdBooking;
    private UpdatePatchBooking updatePatchBooking;
    private NewBooking newBooking;
    private int newbookingId;

    @BeforeEach
    public void setup() throws JsonProcessingException {
        apiClient = new APIClient();
        objectMapper = new ObjectMapper();

        newBooking = new NewBooking();
        newBooking.setFirstname("John");
        newBooking.setLastname("Doe");
        newBooking.setTotalprice(150);
        newBooking.setDepositpaid(true);
        newBooking.setBookingdates(new BookingDates("2024-01-01", "2024-01-05"));
        newBooking.setAdditionalneeds("Breakfast");

        objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(newBooking);
        Response response = apiClient.createBooking(requestBody);

        assertThat(response.getStatusCode()).isEqualTo(200);

        String responseBody = response.asString();
        createdBooking = objectMapper.readValue(responseBody, CreatedBooking.class);
        newbookingId = createdBooking.getBookingid();


    }

    @Test
    public void putBooking() throws JsonProcessingException{


        newBooking.setLastname("Joe");
        newBooking.setTotalprice(250);
        newBooking.setDepositpaid(true);


        apiClient.createToken("admin", "password123");

        objectMapper = new ObjectMapper();
        String requestPutBody = objectMapper.writeValueAsString(newBooking);
        Response putResponse = apiClient.updatePatchBooking(requestPutBody, newbookingId);

        assertThat(putResponse.getStatusCode()).isEqualTo(200);

        String responseBody = putResponse.asString();
        updatePatchBooking = objectMapper.readValue(responseBody, UpdatePatchBooking.class);

    }

    @AfterEach
    public void tearDown() {

        apiClient.createToken("admin", "password123");

        apiClient.deleteBooking(createdBooking.getBookingid());

        assertThat(apiClient.getBookingById(createdBooking.getBookingid()).getStatusCode()).isEqualTo(404);

    }

}
