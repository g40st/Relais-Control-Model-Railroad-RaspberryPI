import java.io.IOException;
import java.nio.ByteBuffer;

import java.util.List;

import javax.websocket.OnOpen;
import javax.websocket.OnMessage;
import javax.websocket.OnClose;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.EncodeException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@ServerEndpoint("/train")
public class TrainEndpoint {
    TrainSingleton trainSingleton = TrainSingleton.getInstance();

    private static final int SWITCH1 = 0;
    private static final int SWITCH2 = 1;
    private static final int SWITCH3 = 2;
    private static final int SWITCH4 = 3;
    private static final int SWITCH5 = 4;
    private static final int SWITCH6 = 5;

    private static final int TRACK1 = 0;
    private static final int TRACK2 = 1;
    private static final int TRACK3 = 2;
    private static final int TRACK4 = 3;
    private static final int TRACK5 = 4;
    private static final int TRACK6 = 5;
    private static final int TRACK7 = 6; 
    private static final int TRACK8 = 7;
    private static final int TRACK9 = 8;

    Thread trainBroadcast = new BroadcastThread();

    @OnOpen
    public void opened(Session session) {
        ConnectionManager.addTmpSession(session);
        if(!trainBroadcast.isAlive()) {
            trainBroadcast.start();
        }
    }

    @OnMessage
    public void receiveMessage(Session session, String message, boolean last) throws ParseException, IOException, EncodeException {
        JSONObject jsonMessage = (JSONObject)new JSONParser().parse(message);
        int msgType = Integer.parseInt((String) jsonMessage.get("Type"));
        System.out.println("Nachricht von Client: " + jsonMessage);
        if(msgType == 1) {
            int msgLength = Integer.parseInt((String) jsonMessage.get("Length").toString());
            if(msgLength == 1) {
                String msgContent = (String) jsonMessage.get("Content").toString();
                switch(msgContent) {
                    case "track1":
                        trainSingleton.setTrackChange(TRACK1);
                        sendJSON(sendTrackResponse(msgContent, TRACK1));
                        break;
                    case "track2":
                        trainSingleton.setTrackChange(TRACK2);
                        sendJSON(sendTrackResponse(msgContent, TRACK2));
                        break;
                    case "track3":
                        trainSingleton.setTrackChange(TRACK3);
                        sendJSON(sendTrackResponse(msgContent, TRACK3));
                        break;
                    case "track4":
                        trainSingleton.setTrackChange(TRACK4);
                        sendJSON(sendTrackResponse(msgContent, TRACK4));
                        break;
                    case "track5":
                        trainSingleton.setTrackChange(TRACK5);
                        sendJSON(sendTrackResponse(msgContent, TRACK5));
                        break;
                    case "track6":
                        trainSingleton.setTrackChange(TRACK6);
                        sendJSON(sendTrackResponse(msgContent, TRACK6));
                        break;
                    case "track7":
                        trainSingleton.setTrackChange(TRACK7);
                        sendJSON(sendTrackResponse(msgContent, TRACK7));
                        break;
                    case "track8":
                        trainSingleton.setTrackChange(TRACK8);
                        sendJSON(sendTrackResponse(msgContent, TRACK8));
                        break;
                    case "track9":
                        trainSingleton.setTrackChange(TRACK9);
                        sendJSON(sendTrackResponse(msgContent, TRACK9));
                        break;     
                    }

            }
        } else if(msgType == 2) {
            int msgLength = Integer.parseInt((String) jsonMessage.get("Length").toString());
            if(msgLength == 1) {
                String msgContent = (String) jsonMessage.get("Content").toString();
                switch(msgContent) {
                    case "switch1":
                        trainSingleton.setSwitchChange(SWITCH1);
                        sendJSON(sendSwitchResponse(msgContent, SWITCH1));
                        break;
                    case "switch2":
                        trainSingleton.setSwitchChange(SWITCH2);
                        sendJSON(sendSwitchResponse(msgContent, SWITCH2));
                        break;
                    case "switch3":
                        trainSingleton.setSwitchChange(SWITCH3);
                        sendJSON(sendSwitchResponse(msgContent, SWITCH3));
                        break;
                    case "switch4":
                        trainSingleton.setSwitchChange(SWITCH4);
                        sendJSON(sendSwitchResponse(msgContent, SWITCH4));
                        break;
                    case "switch5":
                        trainSingleton.setSwitchChange(SWITCH5);
                        sendJSON(sendSwitchResponse(msgContent, SWITCH5));
                        break;
                    case "switch6":
                        trainSingleton.setSwitchChange(SWITCH6);
                        sendJSON(sendSwitchResponse(msgContent, SWITCH6));
                        break;
                }
            }
        } else if(msgType == 3) {
            int msgLength = Integer.parseInt((String) jsonMessage.get("Length").toString());
            if(msgLength == 1) {
                String msgContent = (String) jsonMessage.get("Content").toString();
                if(msgContent.equals("signal")) {
                    trainSingleton.setSignalState();
                    
                    JSONObject trainSignalList = new JSONObject();
                    trainSignalList.put("Type", "3");
                    trainSignalList.put("Length", "1");
                    JSONArray signals = new JSONArray();
                    JSONObject tmp = new JSONObject();
                    tmp.put("Signal", msgContent);
                    tmp.put("State", trainSingleton.getSignalSate());
                    signals.add(tmp);   
                    trainSignalList.put("Content", signals);
                    sendJSON(trainSignalList);
                }
            }
        }  
    }

    @OnClose
    public void closed(Session session) {
        ConnectionManager.removeTmpSession(session);
    }

    @SuppressWarnings("unchecked")
    public JSONObject sendTrackResponse(String msgContent, int i) {
        JSONObject trainTrackList = new JSONObject();
        trainTrackList.put("Type", "1");
        trainTrackList.put("Length", "1");
        JSONArray switches = new JSONArray();
        JSONObject tmp = new JSONObject();
        tmp.put("Track", msgContent);
        tmp.put("State", trainSingleton.getTrackState(i));
        switches.add(tmp);   
        trainTrackList.put("Content", switches);
        return trainTrackList;
    }

    @SuppressWarnings("unchecked")
    public JSONObject sendSwitchResponse(String msgContent, int i) {
        JSONObject trainSwitchList = new JSONObject();
        trainSwitchList.put("Type", "2");
        trainSwitchList.put("Length", "1");
        JSONArray switches = new JSONArray();
        JSONObject tmp = new JSONObject();
        tmp.put("Switch", msgContent);
        tmp.put("State", trainSingleton.getSwitchState(i));
        switches.add(tmp);   
        trainSwitchList.put("Content", switches);
        return trainSwitchList;
    }

    public static synchronized void sendJSON(JSONObject tmp) {
        List<Session> Sessions = ConnectionManager.getTmpSessions();
        if(Sessions.size() > 0) {
            for (Session tempS : Sessions) {
                try {
                    tempS.getBasicRemote().sendText(tmp.toJSONString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class BroadcastThread extends Thread {
    private TrainEndpoint trainEndpoint;
    TrainSingleton trainSingleton = TrainSingleton.getInstance();

    BroadcastThread() {}
    
    @SuppressWarnings("unchecked")
    public void run() {
        JSONObject trainSwitchList = new JSONObject();
        trainSwitchList.put("Type", "2");
        trainSwitchList.put("Length", "6");
        JSONArray switches = new JSONArray();
        for (int i = 0; i < 6; i++) {
            JSONObject tmp = new JSONObject();
            tmp.put("Switch", "switch" + (i +1));
            tmp.put("State", trainSingleton.getSwitchState(i));
            switches.add(tmp);
        }   
        trainSwitchList.put("Content", switches);
        TrainEndpoint.sendJSON(trainSwitchList);

        JSONObject trainTrackList = new JSONObject();
        trainTrackList.put("Type", "1");
        trainTrackList.put("Length", "9");
        JSONArray tracks = new JSONArray();
        for (int i = 0; i < 9; i++) {
            JSONObject tmp = new JSONObject();
            tmp.put("Track", "track" + (i +1));
            tmp.put("State", trainSingleton.getTrackState(i));
            tracks.add(tmp);
        }   
        trainTrackList.put("Content", tracks);
        TrainEndpoint.sendJSON(trainTrackList);

        JSONObject trainSignalList = new JSONObject();
        trainSignalList.put("Type", "3");
        trainSignalList.put("Length", "1");
        JSONArray signals = new JSONArray();
        JSONObject tmp = new JSONObject();
        tmp.put("Signal", "signal");
        tmp.put("State", trainSingleton.getSignalSate());
        signals.add(tmp);   
        trainSignalList.put("Content", signals);
        TrainEndpoint.sendJSON(trainSignalList);
    }   
}