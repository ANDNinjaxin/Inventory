package yt.inventory.logic;

/**
 * Created by Ninjaxin on 11/25/2015.
 */
public class Logic {

    public static String identifyBookLevel(String identify) {
        identify = identify.trim();

        if (identify.length() < 4) {
            return "";
        }

        String first = "" + identify.charAt(0);
        String firstTwo = identify.substring(0, 2);
        String firstThree = identify.substring(0, 3);
        String third = "" + identify.charAt(2);

        String TwoThree = identify.substring(1, 3);

        if (first.equalsIgnoreCase("0")) {
            return TwoThree;

        } else if (third.equalsIgnoreCase("0")) {
            return firstTwo;

        } else {
            return firstThree;
        }

    }

    public static String identifyBookID(String identify) {
        identify = identify.trim();

        if (identify.length() < 4) {
            return "";
        }

        return identify.substring(3, identify.length());
    }

    public static int identifyStudentID(String id) {
        id = id.trim();
        int parsedID = -1;

        if (id.length() != 13) {
            return -1;
        }

        try {
            parsedID = Integer.parseInt(id);
            return parsedID;

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return -1;
    }



    public static String inputStudentID(String id) {
        try {
            int stuID = Integer.parseInt(id.trim());
            return "" + stuID;

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String inputStudentDOB(String rawDOB) {
        rawDOB = rawDOB.trim();
        String[] partDOB = rawDOB.split("/");
        if (rawDOB.length() != 8) {
            return "";
        }

        if ((partDOB[0] == null) || (partDOB[1] == null) || (partDOB[2] == null) || (partDOB[3] != null)) {
            return "";

        } else if (partDOB[0].isEmpty() || partDOB[1].isEmpty() || partDOB[2].isEmpty()) {
            return "";

        }

        int[] parts = new int[3];
        try {
            for (int i = 0; i < 3; i++) {
                parts[i] = Integer.parseInt(partDOB[i]);
            }

            return dobTOyyyymmdd(partDOB[0], partDOB[1], partDOB[2]);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String dobTOyyyymmdd(String yyyy, String mm, String dd) {
        return (yyyy + mm + dd);
    }

    public static String mmddyyyyTOyyyymmdd(String mmddyyyy) {
        String mm = mmddyyyy.substring(0, 2);
        String dd = mmddyyyy.substring(2, 4);
        String yyyy = mmddyyyy.substring(4, mmddyyyy.length());

        return (yyyy + mm + dd);
    }

    public static String yyyymmddTOmmddyyyy(String yyyymmdd) {
        String yyyy = yyyymmdd.substring(0, 4);
        String mm = yyyymmdd.substring(4, 6);
        String dd = yyyymmdd.substring(6, yyyymmdd.length());

        return (mm + dd + yyyy);
    }

}
