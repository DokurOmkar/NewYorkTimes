package com.omkar.newyorktimes.eventlistfragment;

/**
 * Created by omkardokur on 1/2/16.
 */
public class Event {
    private String eventId;
    private String eventName;
    private String eventLocation;
    private String eventDescription;
    private String eventDetailUrl;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventDetailUrl() {
        return eventDetailUrl;
    }

    public void setEventDetailUrl(String eventDetailUrl) {
        this.eventDetailUrl = eventDetailUrl;
    }


}
