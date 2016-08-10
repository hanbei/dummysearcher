package de.hanbei.dwserver;

public class State {

    private static boolean erraticModeState;

    public static void erraticMode(boolean erraticMode) {
        erraticModeState = erraticMode;
    }

    public static boolean erraticMode() {
        return erraticModeState;
    }
}
