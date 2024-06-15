package models.event;

import java.util.List;

public interface IEventDAO {
    boolean createEvent(Event event);
    boolean sendEvent(String eventId, String roleId);
    List<Event> getAllEvent();
    List<Event> getEventByRole(String roleId);
    Event getEventById(String id);
    String generateId(String latestId);

}
