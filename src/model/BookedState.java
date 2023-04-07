package model;

public class BookedState extends AppointmentState {
    public void makeAppointment(Appointment appointment) {

    }

    public void cancelAppointment(Appointment appointment) {
        appointment.setState(new AvailableState());
    }
}