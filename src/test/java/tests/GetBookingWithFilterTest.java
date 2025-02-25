package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.clients.APIClient;
import core.models.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetBookingWithFilterTest {

    private APIClient apiClient;
    private ObjectMapper objectMapper;
    private CreatedBooking createdBooking;
    private NewBooking newBooking;
    private int id;


    @BeforeEach
    public void setup() throws JsonProcessingException {
        apiClient = new APIClient();
        objectMapper = new ObjectMapper();

        newBooking = new NewBooking();
        newBooking.setFirstname("Mark");
        newBooking.setLastname("Joe");
        newBooking.setTotalprice(150);
        newBooking.setDepositpaid(true);
        newBooking.setBookingdates(new BookingDates("2024-01-01", "2024-01-02"));
        newBooking.setAdditionalneeds("Breakfast");

        String requestBody = objectMapper.writeValueAsString(newBooking);
        Response response = apiClient.createBooking(requestBody);

        assertThat(response.getStatusCode()).isEqualTo(200);

        String responseBody = response.asString();
        createdBooking = objectMapper.readValue(responseBody, CreatedBooking.class);
        id = createdBooking.getBookingid();
        System.out.println(id + " ID created booking");
        System.out.println();



    }

    @Test
    public void testparamId() throws JsonProcessingException {

        Response response = apiClient.getBookingById(id);

        assertThat(response.getStatusCode()).isEqualTo(200);

        String responseBody = response.getBody().asString();
        BookingById booking = objectMapper.readValue(responseBody, BookingById.class);

        assertEquals("Mark", booking.getFirstname(), "firstname не совпадает");
        assertEquals("Joe", booking.getLastname(), "lastname не совпадает");

        assertEquals(150, booking.getTotalprice(), "Totalprice не совпадает");


    }


    @AfterEach
    public void tearDown() {

        apiClient.createToken("admin", "password123");
        apiClient.deleteBooking(id);
        assertThat(apiClient.getBookingById(id).getStatusCode()).isEqualTo(404);


    }


}
