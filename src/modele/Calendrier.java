package modele;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TreeSet;

public class Calendrier {

    private final Collection<Date> treeSetDate;

    public Calendrier(int mois) {
        treeSetDate = new TreeSet<>();
        GregorianCalendar cal = new GregorianCalendar(2021, mois - 1, 1);
        int day = cal.get(GregorianCalendar.DAY_OF_WEEK);

        for (int i = day; i > 1; i--) {
            System.out.println(Date.from(cal.toInstant()));
            treeSetDate.add(Date.from(cal.toInstant()));
            cal.add(GregorianCalendar.DAY_OF_MONTH, -1);
        }
        cal = new GregorianCalendar(2021, mois - 1, 2);
        day = cal.get(GregorianCalendar.DAY_OF_WEEK);
        System.out.println(day);
        while (cal.get(GregorianCalendar.MONTH) == mois - 1) {
            while (day <= 7) {
                System.out.println(day + " " + cal.get(GregorianCalendar.DAY_OF_MONTH));
                treeSetDate.add(Date.from(cal.toInstant()));
                cal.add(GregorianCalendar.DAY_OF_MONTH, 1);
                day++;
            }
            day = 1;
        }
        treeSetDate.add(Date.from(cal.toInstant()));
        System.out.println(treeSetDate.size());

    }

    public Collection<Date> getDates() {
        return treeSetDate;
    }

    @Override
    public String toString() {
        return treeSetDate.toString();
    }

}
