package modele;

import java.time.LocalDate;
import java.util.TreeSet;

public class Calendrier {

    /**
     * Only method of the class. It creates a TreeSet of LocalDates starting from the monday of the first week and ending 6 weeks after
     *
     * @param mois corresponding to the month you want
     * @return the created TreeSet with 42 dates in it
     */
    public static TreeSet<LocalDate> getDates(int mois) {
        TreeSet<LocalDate> listeDates = new TreeSet<>();
        LocalDate date = LocalDate.of(2021, mois, 1);
        int day = date.getDayOfWeek().getValue();

        for (int i = day; i > 0; i--) {
            listeDates.add(date);
            date = date.minusDays(1);
        }
        date = LocalDate.of(2021, mois, 2);
        day = date.getDayOfWeek().getValue();

        while (listeDates.size() < 42) { // 6×7
            while (day <= 7) {
                listeDates.add(date);
                date = date.plusDays(1);
                day++;
            }
            day = 1;
        }
        return listeDates;
    }

}
