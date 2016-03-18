import java.io.*;

public class TrainSingleton {
    private static TrainSingleton trainS;

    private static final Switch[] trainSwitch = new Switch[6];
    private static final Track[] trainTrack = new Track[9];
    private Signal signal = new Signal();

    private MCP23S17_Track gpioTrack;
    private MCP23S17_Switch gpioSwitch;

    private TrainSingleton(){
        for (int i = 0; i < 9; i++) {
            trainTrack[i] = new Track();
        }
        for (int i = 0; i < 6; i++) {
            trainSwitch[i] = new Switch();
        }

        try{
            gpioTrack = new MCP23S17_Track();
            gpioSwitch = new MCP23S17_Switch();
        } catch (Exception ex){
            System.out.println("Kein GPIO");
        }
    }

    public static synchronized TrainSingleton getInstance(){
        if (trainS == null){
            trainS = new TrainSingleton();
        }
        return trainS;
    }

    public synchronized void setSwitchChange(int i) {
        try {
            if(trainSwitch[i].getState()) {
                gpioSwitch.changeState(i+i+1);
            } else {
                gpioSwitch.changeState(i+i);
            }
        } catch(Exception ex) {

        }
        trainSwitch[i].changeState();
    } 

    public synchronized boolean getSwitchState(int i) {
        return trainSwitch[i].getState();
    }

    public synchronized void setTrackChange(int i) {
        if(getTrackState(i)) {
            gpioTrack.setInActive(i);
        } else {
            gpioTrack.setActive(i);
        }
        trainTrack[i].changeState();
    } 

    public synchronized boolean getTrackState(int i) {
        return trainTrack[i].getState();
    }

    public synchronized void setSignalState() {
        try {
            if(getSignalSate()) {
                gpioSwitch.changeState(12);
            } else {
                gpioSwitch.changeState(13);
            }
        } catch(Exception ex) {

        }
        signal.changeState();
    }

    public synchronized boolean getSignalSate() {
        return signal.getState();
    }
}