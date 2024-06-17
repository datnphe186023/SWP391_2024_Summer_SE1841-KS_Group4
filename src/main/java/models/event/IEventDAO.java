package models.event;

import java.util.List;

public interface IEventDAO {
    boolean createEvent(Event event);
    boolean sendEvent(String eventId, int roleId);
    List<Event> getAllEvent();
    List<Event> getEventByRole(int roleId);
    Event getEventById(String id);
    String generateId(String latestId);
    List<String> getReceiver(String eventId);
    Event getLastest();
    boolean checkExistEvent(String heading);

}
