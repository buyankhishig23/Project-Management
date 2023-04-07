package model;

import java.util.Date;

public class Appointment {
    private Date time;
    private User user;
    private AppointmentState state;

    public Appointment(Date time, User user) {
        this.time = time;
        this.user = user;
        this.state = new AvailableState();
    }

    public Date getTime() {
        return time;
    }

    public User getUser() {
        return user;
    }

    public void setState(AppointmentState state) {
        this.state = state;
    }

    public void makeAppointment() {
        state.makeAppointment(this);
    }

    public void cancelAppointment() {
        state.cancelAppointment(this);
    }
}
