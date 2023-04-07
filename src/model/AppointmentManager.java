package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppointmentManager {
    private AppointmentDAO appointmentDAO;
    private List<Appointment> appointments;

    private AppointmentFactory appointmentFactory;

    public AppointmentManager(AppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
        try {
            this.appointments = appointmentDAO.getAppointments();
        } catch (SQLException e) {
            e.printStackTrace();
            this.appointments = new ArrayList<>();
        }
    }

    public AppointmentManager(AppointmentFactory appointmentFactory, AppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
        try {
            this.appointments = appointmentDAO.getAppointments();

        } catch (SQLException e) {
            e.printStackTrace();
            this.appointments = new ArrayList<>();
        }
        this.appointmentFactory = appointmentFactory;
    }

    public AppointmentManager(AppointmentFactory appointmentFactory) throws SQLException {
        this.appointmentFactory = appointmentFactory;
        this.appointmentDAO = AppointmentDAO.getInstance();
    }

    public boolean isAppointmentAvailable(Date time) {
        for (Appointment appointment : appointments) {
            if (appointment.getTime().equals(time)) {
                return false;
            }
        }
        return true;
    }

    public void addAppointment(Date time, User user) {
        Appointment appointment = AppointmentFactory.createAppointment(time, user, new AvailableState());
        appointments.add(appointment);
        try {
            appointmentDAO.addAppointment(appointment);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addAppointment(Date time, User user, AppointmentState state) {
        Appointment appointment = AppointmentFactory.createAppointment(time, user, state);
        appointments.add(appointment);
        try {
            appointmentDAO.addAppointment(appointment);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cancelAppointment(Appointment appointment) {
        appointments.remove(appointment);
        try {
            appointmentDAO.cancelAppointment(appointment);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }
}
