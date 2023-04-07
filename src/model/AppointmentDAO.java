package model;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppointmentDAO {
    private static AppointmentDAO instance = null;
    private Connection connection;

    private AppointmentDAO() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clinical_appointments", "root", "password");
    }

    public static AppointmentDAO getInstance() throws SQLException {
        if (instance == null) {
            instance = new AppointmentDAO();
        }
        return instance;
    }

    public void addAppointment(Appointment appointment) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("INSERT INTO appointments (time, name, email, phone) VALUES (?, ?, ?, ?)");
            statement.setTimestamp(1, new Timestamp(appointment.getTime().getTime()));
            statement.setString(2, appointment.getUser().getName());
            statement.setString(3, appointment.getUser().getEmail());
            statement.setString(4, appointment.getUser().getPhoneNumber());
            statement.executeUpdate();
        } finally {
            close(statement, resultSet);
        }
    }

    public void cancelAppointment(Appointment appointment) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("DELETE FROM appointments WHERE time = ?");
            statement.setTimestamp(1, new Timestamp(appointment.getTime().getTime()));
            statement.executeUpdate();
        } finally {
            close(statement, resultSet);
        }
    }

    public List<Appointment> getAppointments() throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM appointments ORDER BY time");
            resultSet = statement.executeQuery();
            List<Appointment> appointments = new ArrayList<>();
            while (resultSet.next()) {
                Date time = new Date(resultSet.getTimestamp("time").getTime());
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                User user = new User(name, email, phone);
                Appointment appointment = new Appointment(time, user);
                appointments.add(appointment);
            }
            return appointments;
        } finally {
            close(statement, resultSet);
        }
    }

    private void close(Statement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}