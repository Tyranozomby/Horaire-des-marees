package modele;

import java.time.LocalDate;
import java.util.TreeSet;

public class Calendrier {

    /**
     * Seule méthode de la classe. Elle crée un TreeSet de LocalDate en commençant le lundi de la première semaine du mois et finissant 6 semaines après.
     *
     * @param mois correspondant au mois voulu.
     * @return le TreeSet créé avec 42 dates dedans.
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
