package com.college.baramulla.govtdegreecollegebaramulla;

public class Grievance {
    String gName,gEmail,gMobile,gCourse,gGrievance;

    public Grievance()
    {



    }


    public Grievance(String gName, String gEmail, String gMobile, String gCourse, String gGrievance) {
        this.gName = gName;
        this.gEmail = gEmail;
        this.gMobile = gMobile;
        this.gCourse = gCourse;
        this.gGrievance = gGrievance;
    }

    public String getgName() {
        return gName;
    }

    public void setgName(String gName) {
        this.gName = gName;
    }

    public String getgEmail() {
        return gEmail;
    }

    public void setgEmail(String gEmail) {
        this.gEmail = gEmail;
    }

    public String getgMobile() {
        return gMobile;
    }

    public void setgMobile(String gMobile) {
        this.gMobile = gMobile;
    }

    public String getgCourse() {
        return gCourse;
    }

    public void setgCourse(String gCourse) {
        this.gCourse = gCourse;
    }

    public String getgGrievance() {
        return gGrievance;
    }

    public void setgGrievance(String gGrievance) {
        this.gGrievance = gGrievance;
    }
}
