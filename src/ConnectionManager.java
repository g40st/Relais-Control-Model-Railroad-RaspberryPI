import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.Session;


public class ConnectionManager {
	private static List<Session> sessionList = Collections.synchronizedList(new ArrayList<Session>());
    
    public static synchronized int sessionCount() {
        return sessionList.size();
    }
    
    public static synchronized void addTmpSession(Session session) {
    	sessionList.add(session);
    }
    
    public static synchronized boolean removeTmpSession(Session session) {
    	if(sessionList.remove(session)) {
    		return true;
    	} 
    	return false;
    }
    
    public static synchronized List<Session> getTmpSessions() {
    	return sessionList;
    }
}
