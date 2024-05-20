package models.notification;

public class NotificationDetails {
    private String notificationId;
    private int receiver;

    public NotificationDetails(String notificationId, int receiver) {
        this.notificationId = notificationId;
        this.receiver = receiver;
    }

    public NotificationDetails() {
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public int getReceiver() {
        return receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }
}
