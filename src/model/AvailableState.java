package model;

public class AvailableState extends AppointmentState {
    public void makeAppointment(Appointment appointment) {
        appointment.setState(new BookedState());
    }

    public void cancelAppointment(Appointment appointment) {
        appointment.setState(new CancelledState());
    }
}
