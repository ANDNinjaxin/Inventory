package yt.inventory.logic;

/**
 * Created by Ninjaxin on 11/25/2015.
 */
public class BookLogic {

    public static String identifyLevel(String identify) {
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

}
