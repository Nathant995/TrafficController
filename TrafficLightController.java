import java.util.Timer;
import java.util.TimerTask;

public class TrafficLightController {

    private static final int GREEN_LIGHT_DURATION = 30; // Green light duration in seconds
    private static final int YELLOW_LIGHT_DURATION = 5; // Yellow light duration in seconds
    private static final int RED_LIGHT_DURATION = 30; // Red light duration in seconds
    private static final int PEDESTRIAN_CROSSING_DURATION = 10; // Pedestrian crossing duration in seconds
    private static final int YELLOW_PEDESTRIAN_CROSSING_DURATION = 2; // Yellow pedestrian crossing duration in seconds

    private LightState northState = LightState.GREEN;
    private LightState eastState = LightState.RED;
    private LightState southState = LightState.GREEN;
    private LightState westState = LightState.RED;

    private Timer timer = new Timer();

    public TrafficLightController() {
        startTimer();
    }

    private void startTimer() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateTrafficLightStates();
            }
        }, 0, 1000);
    }

    private void updateTrafficLightStates() {
        updateTrafficLightState(northState, southState);
        updateTrafficLightState(southState, northState);
        updateTrafficLightState(eastState, westState);
        updateTrafficLightState(westState, eastState);
    }

    private void updateTrafficLightState(LightState currentLightState, LightState oppositeLightState) {
        switch (currentLightState) {
            case GREEN:
                if (shouldChangeToYellow(currentLightState)) {
                    currentLightState = LightState.YELLOW;
                }
                break;
            case YELLOW:
                if (shouldChangeToRed(currentLightState)) {
                    currentLightState = LightState.RED;
                }
                break;
            case RED:
                if (shouldChangeToGreen(currentLightState, oppositeLightState)) {
                    currentLightState = LightState.GREEN;
                }
                break;
        }
    }

    private boolean shouldChangeToYellow(LightState currentLightState) {
        return currentLightState == LightState.GREEN && timerTicksElapsed() >= GREEN_LIGHT_DURATION;
    }

    private boolean shouldChangeToRed(LightState currentLightState) {
        return currentLightState == LightState.YELLOW && timerTicksElapsed() >= YELLOW_LIGHT_DURATION;
    }

    private boolean shouldChangeToGreen(LightState currentLightState, LightState oppositeLightState) {
        return currentLightState == LightState.RED && timerTicksElapsed() >= RED_LIGHT_DURATION && oppositeLightState != LightState.GREEN;
    }

    private int timerTicksElapsed() {
        return (int) (System.currentTimeMillis() / 1000 % 60);
    }

}
