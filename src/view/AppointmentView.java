package view;

import controller.AppointmentController;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;


public class AppointmentView implements Observer {
    private AppointmentManager appointmentManager;
    private AvailableTimeOptions availableTimeOptions;
    private JFrame frame;
    private JComboBox<Date> timeComboBox;
    private JTextField nameTextField;
    private JTextField emailTextField;
    private JTextField phoneTextField;
    private JList<Appointment> appointmentList;
    private JLabel statusLabel;
    private SimpleDateFormat dateFormat;

    public AppointmentView(AppointmentManager appointmentManager, AvailableTimeOptions availableTimeOptions) {
        this.appointmentManager = appointmentManager;
        this.availableTimeOptions = availableTimeOptions;
        this.frame = new JFrame("Цаг бүртгэл");
        this.timeComboBox = new JComboBox<>(availableTimeOptions.getAvailableTimes().toArray(new Date[0]));
        this.nameTextField = new JTextField(30);
        this.emailTextField = new JTextField(30);
        this.phoneTextField = new JTextField(30);
        this.appointmentList = new JList<>();
        this.statusLabel = new JLabel(" ");
        this.dateFormat = new SimpleDateFormat("E HH:mm");

        updateAppointmentList();

        appointmentList.setFixedCellHeight(10);
        appointmentList.setVisibleRowCount(5);

        JPanel panel = new JPanel(new GridLayout(5, 2, 0, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(new JLabel("Нэр:"));
        panel.add(nameTextField);
        panel.add(new JLabel("Мэйл:"));
        panel.add(emailTextField);
        panel.add(new JLabel("Утас:"));
        panel.add(phoneTextField);
        panel.add(new JLabel("Цаг:"));
        panel.add(timeComboBox);
        panel.add(new JLabel("Захиалга:"));
        panel.add(new JScrollPane(appointmentList));

        JButton makeAppointmentButton = new JButton("Цаг захиалах");
        makeAppointmentButton.addActionListener(e -> {
            String name = nameTextField.getText();
            String email = emailTextField.getText();
            String phone = phoneTextField.getText();
            Date selectedTime = (Date) timeComboBox.getSelectedItem();
            if (appointmentManager.isAppointmentAvailable(selectedTime)) {
                AppointmentState state = new BookedState();
                appointmentManager.addAppointment(selectedTime, new User(name, email, phone), state);
                clearFields();
                statusLabel.setText("Цаг авлаа.");
                updateAppointmentList();
            } else {
                statusLabel.setText("Цаг хүнтэй байна.");
            }
        });

        JButton cancelAppointmentButton = new JButton("Цаг цуцлах");
        cancelAppointmentButton.addActionListener(e -> {
            Appointment selectedAppointment = appointmentList.getSelectedValue();
            if (selectedAppointment != null) {
                appointmentManager.cancelAppointment(selectedAppointment);
                clearFields();
                statusLabel.setText("Цаг амжилттай цуцаллаа.");
                updateAppointmentList();
            } else {
                JOptionPane.showMessageDialog(frame, "Цуцлах цагаа сонгоно уу.");
            }
        });

        timeComboBox.setRenderer(new DefaultListCellRenderer() {
            DateFormat dateFormat = new SimpleDateFormat("E HH:mm", new Locale("mn"));

            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                dateFormat.setTimeZone(TimeZone.getDefault());
                JLabel renderer = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                Date time = (Date) value;
                String timeStr = dateFormat.format(time);
                renderer.setText(timeStr);
                return renderer;
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(makeAppointmentButton);
        buttonPanel.add(cancelAppointmentButton);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(statusLabel, BorderLayout.CENTER);


        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        availableTimeOptions.addObserver(this);
    }

    private void updateAppointmentList() {
        List<Appointment> appointments = appointmentManager.getAppointments();
        Appointment[] appointmentArray = appointments.toArray(new Appointment[0]);
        appointmentList.setListData(appointmentArray);

        SimpleDateFormat timeFormat = new SimpleDateFormat("E HH:mm", new Locale("mn"));
        timeFormat.setTimeZone(TimeZone.getDefault());

        appointmentList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel renderer = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                Appointment appointment = (Appointment) value;
                Date time = appointment.getTime();
                String timeStr = String.format("%s - %s (%s)", timeFormat.format(time), appointment.getUser().getName(), appointment.getUser().getPhoneNumber());
//                String timeStr = String.format("%s %02d:%02d", weekdays[time.getDay()], time.getHours(), time.getMinutes());
                renderer.setText(timeStr);
                return renderer;
            }
        });
    }private void clearFields() {
        nameTextField.setText("");
        emailTextField.setText("");
        phoneTextField.setText("");
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == availableTimeOptions) {
            List<Date> availableTimes = availableTimeOptions.getAvailableTimes();
            timeComboBox.removeAllItems();
            for (Date availableTime : availableTimes) {
                timeComboBox.addItem(availableTime);
            }
        }
    }

}