package com.quiz.api.jersey.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "attendee")
class AttendeesBean extends UserBean{
    private int attendeeScore;
    private String attendeeResult;


    public AttendeesBean() {
      	super();
    }

    public int getAttendeeScore() {
        return attendeeScore;
    }

    public void setAttendeeScore(int attendeeScore) {
        this.attendeeScore = attendeeScore;
    }

    public void setAttendeeResult(String attendeeResult) {
        this.attendeeResult = attendeeResult;
    }
}
