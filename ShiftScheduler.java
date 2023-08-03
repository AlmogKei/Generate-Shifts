package JavaSeasonByearB;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Shift {
    String name;
    int startHour;
    int endHour;

    public Shift(String name, int startHour, int endHour) {
        this.name = name;
        this.startHour = startHour;
        this.endHour = endHour;
    }
}

public class SecurityGuardSchedule {

    private static final int MIN_HOURS = 8;
    private static final int MAX_HOURS = 12;

    public static void main(String[] args) {
        List<Shift> shifts = createWeeklySchedule();
        printWeeklySchedule(shifts);
    }

    private static void printWeeklySchedule(List<Shift> shifts) {
    }

    private static List<Shift> createWeeklySchedule() {
        List<Shift> shifts = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        // יצירת מערך של המבטחים באותו מתקן
        String[] guardNames = new String[]{"Guard 1", "Guard 2", "Guard 3", "Guard 4", "Guard 5", "Guard 6"};
        List<List<Integer>> guardPreferences = new ArrayList<>();

        // יצירת לולאה מקוננת שדורשת מכל מאבטח את האילוצים לאותו שבוע לפי אותו יום
        for (int i = 0; i < guardNames.length; i++) {
            System.out.println("Guard " + (i + 1) + ":");
            List<Integer> preferences = new ArrayList<>();
            System.out.println("Enter shift option for each day (1 to 5):");
            System.out.println("1. Short Morning (7 am to 3 pm)");
            System.out.println("2. Short Evening (3 pm to 11 pm)");
            System.out.println("3. Short Night (11 pm to 7 am)");
            System.out.println("4. Long Morning (7 am to 7 pm)");
            System.out.println("5. Long Night (7 pm to 7 am)");

            for (int day = 1; day <= 7; day++) {
                System.out.print("Day " + day + ": ");
                int choice = scanner.nextInt();
                preferences.add(choice);
            }
            guardPreferences.add(preferences);
            System.out.println();
        }

        // יצירת לולאה מקוננת שמחזירה הדפסה על פי האילוצים שהוגשו
        for (int day = 1; day <= 7; day++) {
            System.out.println("Day " + day + ":");
            List<Integer> shuffledGuardIndices = new ArrayList<>();
            for (int i = 0; i < guardNames.length; i++) {
                shuffledGuardIndices.add(i);
            }
            Collections.shuffle(shuffledGuardIndices);

            Shift morningShift = createShift("Short Morning", 7, 15);
            shifts.add(morningShift);
            assignShiftToGuard(morningShift, guardNames[shuffledGuardIndices.get(0)], guardPreferences.get(shuffledGuardIndices.get(0)).get(day - 1));

            Shift additionalHoursShift = createShift("Long Morning", 7, 19);
            shifts.add(additionalHoursShift);
            assignShiftToGuard(additionalHoursShift, guardNames[shuffledGuardIndices.get(1)], guardPreferences.get(shuffledGuardIndices.get(1)).get(day - 1));

            Shift eveningShift = createShift("Short Evening", 15, 23);
            shifts.add(eveningShift);
            assignShiftToGuard(eveningShift, guardNames[shuffledGuardIndices.get(2)], guardPreferences.get(shuffledGuardIndices.get(2)).get(day - 1));

            Shift nightShift = createShift("Short Night", 23, 7);
            shifts.add(nightShift);
            assignShiftToGuard(nightShift, guardNames[shuffledGuardIndices.get(3)], guardPreferences.get(shuffledGuardIndices.get(3)).get(day - 1));

            Shift longNightShift = createShift("Long Night", 19, 7);
            shifts.add(longNightShift);
            assignShiftToGuard(longNightShift, guardNames[shuffledGuardIndices.get(4)], guardPreferences.get(shuffledGuardIndices.get(4)).get(day - 1));

            System.out.println();
        }

        return shifts;
    }

    // פונקציה המדפיסה את השם את שעה ההתחלה והשעת סיום של משמרת
    private static void assignShiftToGuard(Shift shift, String guardName, int preference) {
        System.out.println(shift.name + ": " + guardName + " (Start: " + formatTime(shift.startHour) + ", End: " + formatTime(shift.endHour) + ")" + " [Preference: " + preference + "]");
    }

    // פונקציה שמחזירה את שם המשמרת ואת האורך שלה
    private static Shift createShift(String name, int startHour, int endHour) {
        return new Shift(name, startHour, endHour);
    }

    // פונקציה המחזירה את השעה
    private static String formatTime(int hour) {
        return (hour < 10 ? "0" : "") + hour + ":00";
    }
}
