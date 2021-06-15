package modele;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TreeSet;

public class Calendrier {

    public static TreeSet<Date> getDates(int mois) {
        mois--;
        TreeSet<Date> listeDates = new TreeSet<>();
        GregorianCalendar cal = new GregorianCalendar(2021, mois, 1);
        int day = cal.get(GregorianCalendar.DAY_OF_WEEK);

        for (int i = day; i > 1; i--) {
            listeDates.add(Date.from(cal.toInstant()));
            cal.add(GregorianCalendar.DAY_OF_MONTH, -1);
        }
        cal = new GregorianCalendar(2021, mois, 2);
        day = cal.get(GregorianCalendar.DAY_OF_WEEK);

        while (cal.get(GregorianCalendar.MONTH) == mois) {
            while (day <= 7) {
                listeDates.add(Date.from(cal.toInstant()));
                cal.add(GregorianCalendar.DAY_OF_MONTH, 1);
                day++;
            }
            day = 1;
        }
        listeDates.add(Date.from(cal.toInstant()));
        return listeDates;
    }

}
