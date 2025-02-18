package core.models;

import java.util.Calendar;
import java.util.Date;

public class Bookindates {

    private Calendar checkin;
    private Calendar checkout;

    public Calendar getCheckin() {
        return checkin;
    }

    public void setCheckin(Calendar checkin) {
        this.checkin = checkin;
    }

    public Calendar getCheckout() {
        return checkout;
    }

    public void setCheckout(Calendar checkout) {
        this.checkout = checkout;
    }
}
