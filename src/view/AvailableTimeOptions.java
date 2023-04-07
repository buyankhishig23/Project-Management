package view;

import model.AppointmentFactory;
import model.AppointmentManager;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;

public class AvailableTimeOptions extends Observable {
    private List<Date> availableTimes;
    private AppointmentManager appointmentManager;
    private AppointmentFactory appointmentFactory;

    public AvailableTimeOptions() {
        this.availableTimes = new ArrayList<>();
        updateAvailableTimes();
    }

    public AvailableTimeOptions(AppointmentManager appointmentManager) {
        this.appointmentManager = appointmentManager;
        this.availableTimes = new ArrayList<>();
        updateAvailableTimes();
    }

    public List<Date> getAvailableTimes() {
        return availableTimes;
    }

    public void updateAvailableTimes() {
        availableTimes.clear();
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        for (int i = 0; i < 7; i++) {
            if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                for (int j = 8; j < 17; j++) {
                    calendar.set(Calendar.HOUR_OF_DAY, j);
                    Date time = calendar.getTime();
                    availableTimes.add(time);
                }
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        setChanged();
        notifyObservers();
    }
}
