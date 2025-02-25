package core.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdatePatchBooking {

    private int bookingid;
    private NewBooking booking;

    public int getBookingid() {
        return bookingid;
    }

    public void setBookingid(int bookingid) {
        this.bookingid = bookingid;
    }

    public NewBooking getBooking() {
        return booking;
    }

    public void setBooking(NewBooking booking) {
        this.booking = booking;
    }
}
