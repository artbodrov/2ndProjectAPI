package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.clients.APIClient;
import core.models.Booking;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DeleteBookingTest {

    private APIClient apiClient;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        apiClient = new APIClient();
        objectMapper = new ObjectMapper();

    }

    @Test
    public void deleteBookingTest() throws JsonProcessingException {

        Response response = apiClient.getBooking();

        assertThat(response.getStatusCode()).isEqualTo(200);

        String responseBody = response.getBody().asString();
        List<Booking> bookings = objectMapper.readValue(responseBody, new TypeReference<List<Booking>>() {
        });

        apiClient.createToken("admin", "password123");
        Response responseDelete = apiClient.deleteBooking(bookings.get(4).getBookingId());

        assertThat(responseDelete.getStatusCode()).isEqualTo(201);

    }
}
