package com.college.baramulla.govtdegreecollegebaramulla;

/**
 * Created by MEHRAJ UD DIN BHAT on 3/30/2019.
 */

public class Notification {
    String notificationtitle;

    String noticeLink;

    public Notification()
    {}

    public Notification(String notificationtitle, String noticeLink) {
        this.notificationtitle = notificationtitle;
        this.noticeLink = noticeLink;
    }

    public String getNoticeLink() {
        return noticeLink;
    }

    public void setNoticeLink(String noticeLink) {
        this.noticeLink = noticeLink;
    }

    public String getNotificationtitle() {
        return notificationtitle;
    }

    public void setNotificationtitle(String notificationtitle) {
        this.notificationtitle = notificationtitle;
    }
}
