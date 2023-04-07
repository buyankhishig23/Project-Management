package controller;

import model.AppointmentDAO;
import model.AppointmentFactory;
import model.AppointmentManager;
import view.AppointmentView;
import view.AvailableTimeOptions;
import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AppointmentController {
    private AppointmentDAO appointmentDAO;
    private AvailableTimeOptions availableTimeOptions;
    private AppointmentView appointmentView;

    public AppointmentController() throws SQLException {
        this.appointmentDAO = AppointmentDAO.getInstance();

        AppointmentManager appointmentManager = new AppointmentManager(new AppointmentFactory(), appointmentDAO);

        this.availableTimeOptions = new AvailableTimeOptions(appointmentManager);

        this.appointmentView = new AppointmentView(appointmentManager, availableTimeOptions);
    }
}
