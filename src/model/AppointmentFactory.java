package model;

import java.util.Date;

public class AppointmentFactory {
    public static Appointment createAppointment(Date time, User user) {

        return new Appointment(time, user);
    }

    public static Appointment createAppointment(Date time, User user, AppointmentState state) {
        Appointment appointment = new Appointment(time, user);
        appointment.setState(state);
//        appointment.makeAppointment();
        return appointment;
    }
}
