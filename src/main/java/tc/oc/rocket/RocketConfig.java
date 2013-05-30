package tc.oc.rocket;

public class RocketConfig {
    private static String ROCKET_PREFIX = "rocket.";
    static String ROCKET_PERMISSION = ROCKET_PREFIX + "rocket";
    static String EXEMPT_PERMISSION = ROCKET_PREFIX + "exempt";
    static String RESET_PERMISSION = ROCKET_PREFIX + "reset";

    static double ROCKET_VELOCITY_MOD = 30;
    static int TICK_UPDATE_DELAY = 1;

    static int FIREWORK_COUNT = 5;
    static int FIREWORK_POWER = 0;
    static boolean FIREWORK_TRAIL = false;

    static double SMOKE_RADIUS = 0.1;
    static int SMOKE_COUNT = 6;
}
