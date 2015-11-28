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



    public static String importStudentID(String id) {
        try {
            long stuID = Long.parseLong(id.trim());
            return "" + stuID;

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String importStudentDOB(String rawDOB) {
        //xls file is formatted (m)m/(d)d/yyyy

        rawDOB = rawDOB.trim();
        String[] partDOB;

        if (rawDOB.contains("/")) {
            partDOB = rawDOB.split("/");

            if ((partDOB[0] == null) || (partDOB[1] == null) || (partDOB[2] == null)) {
                return "";

            } else if (partDOB[0].isEmpty() || partDOB[1].isEmpty() || partDOB[2].isEmpty()) {
                return "";

            }

        } else if (rawDOB.contains("-")) {
            partDOB = rawDOB.split("-");

            if ((partDOB[0] == null) || (partDOB[1] == null) || (partDOB[2] == null)) {
                return "";

            } else if (partDOB[0].isEmpty() || partDOB[1].isEmpty() || partDOB[2].isEmpty()) {
                return "";

            }

            String mo = partDOB[1].toLowerCase().trim();

            switch(mo) {
                case "jan":
                    partDOB[1] = "01";  break;
                case "feb":
                    partDOB[1] = "02";  break;
                case "mar":
                    partDOB[1] = "03";  break;
                case "apr":
                    partDOB[1] = "04";  break;
                case "may":
                    partDOB[1] = "05";  break;
                case "jun":
                    partDOB[1] = "06";  break;
                case "jul":
                    partDOB[1] = "07";  break;
                case "aug":
                    partDOB[1] = "08";  break;
                case "sep":
                    partDOB[1] = "09";  break;
                case "oct":
                    partDOB[1] = "10";  break;
                case "nov":
                    partDOB[1] = "11";  break;
                case "dec":
                    partDOB[1] = "12";  break;
                default:
                    return "";

            }

            String temp = partDOB[0];
            partDOB[0] = partDOB[1];
            partDOB[1] = temp;

        } else {
            return "";
        }
//        if (rawDOB.length() != 8) {
//            return "";
//        }

        //TODO: Logic for birthdates with dd-mmm-yyyy AND mm/dd/yyyy

        int[] parts = new int[3];
        try {
            for (int i = 0; i < 3; i++) {
                parts[i] = Integer.parseInt(partDOB[i]);
            }

            boolean validsubsizes =
                    (partDOB[0].length() <= 2) && (partDOB[1].length() <= 2) && (partDOB[2].length() == 4);

            if (!validsubsizes) {
                return "";
            }

            if (partDOB[0].length() < 2) {
                partDOB[0] = "0" + partDOB[0];
            }
            if (partDOB[1].length() < 2) {
                partDOB[1] = "0" + partDOB[1];
            }

            return dobTOyyyymmdd(partDOB[0], partDOB[1], partDOB[2]);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


        return "";
    }

    public static String dobTOyyyymmdd(String mm, String dd, String yyyy) {
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
