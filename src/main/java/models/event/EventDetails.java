package models.event;

public class EventDetails {
    private String eventId;
    private int participant;

    public EventDetails(String eventId, int participant) {
        this.eventId = eventId;
        this.participant = participant;
    }

    public EventDetails() {
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public int getParticipant() {
        return participant;
    }

    public void setParticipant(int participant) {
        this.participant = participant;
    }
}
