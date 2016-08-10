package de.hanbei.dwserver;

public class State {

    private static boolean erraticModeState;
    private static int maxWaitState = 2000;

    public static void erraticMode(boolean erraticMode) {
        erraticModeState = erraticMode;
    }

    public static boolean erraticMode() {
        return erraticModeState;
    }

    public static void maxWait(int maxWait) {
        maxWaitState = maxWait;
    }

    public static int maxWait() {
        return maxWaitState;
    }
}
