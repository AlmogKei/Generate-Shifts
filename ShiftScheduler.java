package MahatClass;

import java.util.*;

public class ShiftScheduler {
    private final String[] daysOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    private final String[] shifts = {"Day", "Night"};

    private final Map<String, Shift> schedule;

    public ShiftScheduler() {
        schedule = new HashMap<>();
        initializeSchedule();
    }

    private void initializeSchedule() {
        for (String day : daysOfWeek) {
            for (String shift : shifts) {
                schedule.put(day + "-" + shift, new Shift(shift));
            }
        }
    }

    public void addNameToShift(String name, String day, String shift, int id) {
        String key = day + "-" + shift;
        if (schedule.containsKey(key)) {
            Shift shiftObj = schedule.get(key);
            if (shiftObj.getId() != 0) {
                System.out.println("Shift already assigned to ID: " + shiftObj.getId());
                return;
            }

            // Check if the ID already has a shift on the same day
            int shiftsInDay = countShiftsInDay(id, day);
            if (shiftsInDay >= 1) {
                System.out.println("Shift already assigned to ID: " + id + " on " + day);
                return;
            }

            // Check if the ID already has a shift on the previous day (night shift)
            String prevDay = getPreviousDay(day);
            Shift prevShift = schedule.get(prevDay + "-Night");
            if (prevShift.getId() == id) {
                System.out.println("Cannot assign day shift to ID: " + id + " after night shift on the previous day");
                return;
            }

            shiftObj.setName(name);
            shiftObj.setId(id);
        } else {
            System.out.println("Invalid shift: " + key);
        }
    }

    private int countShiftsInDay(int id, String day) {
        int count = 0;
        for (String shift : shifts) {
            Shift shiftObj = schedule.get(day + "-" + shift);
            if (shiftObj.getId() == id) {
                count++;
            }
        }
        return count;
    }

    private String getPreviousDay(String day) {
        int dayIndex = -1;
        for (int i = 0; i < daysOfWeek.length; i++) {
            if (daysOfWeek[i].equals(day)) {
                dayIndex = i;
                break;
            }
        }

        if (dayIndex != -1) {
            int prevDayIndex = (dayIndex - 1 + daysOfWeek.length) % daysOfWeek.length;
            return daysOfWeek[prevDayIndex];
        }

        return "";
    }

    private static class Shift {
        private final String shift;
        private String name;
        private int id;

        public Shift(String shift) {
            this.shift = shift;
            this.name = "";
            this.id = 0;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static void main(String[] args) {
        ShiftScheduler scheduler = new ShiftScheduler();

        scheduler.addNameToShift("Almog", "Sunday", "Night", 1);
        scheduler.addNameToShift("Almog", "Monday", "Day", 1);
        scheduler.addNameToShift("Almog", "Tuesday", "Day", 1);
        scheduler.addNameToShift("Almog", "Wednesday", "Night", 1);
        scheduler.addNameToShift("Almog", "Thursday", "Night", 1);
        scheduler.addNameToShift("Almog", "Friday", "Night", 1);
        scheduler.addNameToShift("Almog", "Saturday", "Day", 1);

        scheduler.addNameToShift("Rotem", "Sunday", "Night", 2);
        scheduler.addNameToShift("Rotem", "Monday", "Night", 2);
        scheduler.addNameToShift("Rotem", "Tuesday", "Night", 2);
        scheduler.addNameToShift("Rotem", "Wednesday", "Day", 2);
        scheduler.addNameToShift("Rotem", "Thursday", "Day", 2);
        scheduler.addNameToShift("Rotem", "Friday", "Night", 2);
        scheduler.addNameToShift("Rotem", "Saturday", "Day", 2);

        scheduler.addNameToShift("Shahaf", "Sunday", "Night", 3);
        scheduler.addNameToShift("Shahaf", "Monday", "Day", 3);
        scheduler.addNameToShift("Shahaf", "Tuesday", "Night", 3);
        scheduler.addNameToShift("Shahaf", "Wednesday", "", 3);
        scheduler.addNameToShift("Shahaf", "Thursday", "Day", 3);
        scheduler.addNameToShift("Shahaf", "Friday", "Night", 3);
        scheduler.addNameToShift("Shahaf", "Saturday", "Day", 3);

        scheduler.addNameToShift("Aviv", "Sunday", "Day", 4);
        scheduler.addNameToShift("Aviv", "Monday", "Night", 4);
        scheduler.addNameToShift("Aviv", "Tuesday", "Night", 4);
        scheduler.addNameToShift("Aviv", "Wednesday", "Day", 4);
        scheduler.addNameToShift("Aviv", "Thursday", "", 4);
        scheduler.addNameToShift("Aviv", "Friday", "Night", 4);
        scheduler.addNameToShift("Aviv", "Saturday", "", 4);

        scheduler.addNameToShift("Ben C", "Sunday", "Day", 5);
        scheduler.addNameToShift("Ben C", "Monday", "Night", 5);
        scheduler.addNameToShift("Ben C", "Tuesday", "Day", 5);
        scheduler.addNameToShift("Ben C", "Wednesday", "Night", 5);
        scheduler.addNameToShift("Ben C", "Thursday", "", 5);
        scheduler.addNameToShift("Ben C", "Friday", "Night", 5);
        scheduler.addNameToShift("Ben C", "Saturday", "", 5);

        scheduler.addNameToShift("Ben D", "Sunday", "Night", 6);
        scheduler.addNameToShift("Ben D", "Monday", "Night", 6);
        scheduler.addNameToShift("Ben D", "Tuesday", "Day", 6);
        scheduler.addNameToShift("Ben D", "Wednesday", "Night", 6);
        scheduler.addNameToShift("Ben D", "Thursday", "Night", 6);
        scheduler.addNameToShift("Ben D", "Friday", "Night", 6);
        scheduler.addNameToShift("Ben D", "Saturday", "Night", 6);

        // Place names in shifts


        // Assign specific shifts to each name


        // Print the names and full week's shifts
        System.out.println("Names and Full Week's Shifts");
        System.out.println("---------------------------");
        System.out.print("      Sunday        |      Monday       |      Tuesday      |    Wednesday      |    Thursday       |      Friday      |      Saturday      |");
        System.out.println();
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
        for (String shift : scheduler.shifts) {
            for (String day : scheduler.daysOfWeek) {
                Shift shiftObj = scheduler.schedule.get(day + "-" + shift);
                String shiftName = shiftObj.getName().isEmpty() ? "" : shiftObj.getName();
                System.out.print("| " + shiftName );
                int padding = 18 - shiftName.length();
                for (int i = 0; i < padding; i++) {
                    System.out.print(" ");
                }
            }
            System.out.println("|");
        }
    }

}


