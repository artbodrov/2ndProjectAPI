package core.clients;

import core.setting.ApiEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class APIClient {

    private final String baseurl;

    public APIClient() {
        this.baseurl = determineBaseurl();
    }

    private String determineBaseurl() {

        String environment = System.getProperty("env", "test");
        String configFileName = "application-" + environment + ".properties";

        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(configFileName)) {
            if (input == null) {
                throw new IllegalStateException("Configuration file not found " + configFileName);
            }
            properties.load(input);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load configuration file "+configFileName, e);
        }
        return properties.getProperty("baseUrl");
    }
    private RequestSpecification getRequestSpec(){
        return RestAssured.given()
                .baseUri(baseurl)
                .header("Content-Type","application/json")
                .header("Accept","application/json");
    }
    public Response ping(){
        return getRequestSpec()
                .when()
                .get(ApiEndpoints.PING.getPath())
                .then()
                .statusCode(201)
                .extract()
                .response();
    }
    public Response getBooking(){
        return getRequestSpec()
                .when()
                .get(ApiEndpoints.BOOKING.getPath())
                .then()
                .statusCode(200)
                .extract()
                .response();
    }
    public Response getBookingById(int p){

        return getRequestSpec()
                .when()
                .get(ApiEndpoints.BOOKING.getPath()+"/"+p)
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

}
