package yt.inventory.utility;

/**
 * Created by Ninjaxin on 7/29/15.
 */
public class Checks {



    public boolean isValid(int checkme) {
        if (checkme < 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isValid(String checkme) {
        if ((checkme == null) || (checkme.isEmpty())) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isValid(Object checkme) {
        if ( (checkme == null) || (checkme.toString().isEmpty()) ) {
            return false;
        } else {
            return true;
        }
    }

}