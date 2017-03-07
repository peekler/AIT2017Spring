package hu.ait.twoactivitiesdemo;

/**
 * Created by Peter on 2017. 03. 02..
 */

public class Preferences {

    private static Preferences instance = null;

    private Preferences(){

    }

    public static Preferences getInstance() {
        if (instance == null) {
            instance = new Preferences();
        }

        return instance;
    }



    private int value = 4;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
