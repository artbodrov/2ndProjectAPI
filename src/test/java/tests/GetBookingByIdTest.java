package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.clients.APIClient;
import core.models.BookingById;
import core.models.BookingDates;
import core.models.CreatedBooking;
import core.models.NewBooking;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


public class GetBookingByIdTest {

    private APIClient apiClient;
    private ObjectMapper objectMapper;


    @BeforeEach
    public void setup() {
        apiClient = new APIClient();
        objectMapper = new ObjectMapper();
    }

    @ParameterizedTest
    @CsvSource({
            "1",
            "2",
            "3",
            "4"
    })
    public void testGetParamBookingById(int id) throws JsonProcessingException {

        Response response = apiClient.getBookingById(id);

        assertThat(response.getStatusCode()).isEqualTo(200);

        String responseBody = response.getBody().asString();
        BookingById booking = objectMapper.readValue(responseBody, BookingById.class);

        assertNotNull(booking.getLastname());
        assertNotNull(booking.getFirstname());
        assertNotNull(booking.getTotalprice());


    }
    @Test
    public void testGetBookingById() throws JsonProcessingException {

        Response response = apiClient.getBookingById(1);

        assertThat(response.getStatusCode()).isEqualTo(200);

        String responseBody = response.getBody().asString();
        BookingById booking = objectMapper.readValue(responseBody, BookingById.class);

        assertNotNull(booking.isDepositpaid());

    }

}
