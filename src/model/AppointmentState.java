package model;

public abstract class AppointmentState {
    public abstract void makeAppointment(Appointment appointment);
    public abstract void cancelAppointment(Appointment appointment);
}
