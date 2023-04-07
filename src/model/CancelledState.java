package model;

public class CancelledState extends AppointmentState {
    public void makeAppointment(Appointment appointment) {
        appointment.setState(new BookedState());
    }

    public void cancelAppointment(Appointment appointment) {

    }
}
