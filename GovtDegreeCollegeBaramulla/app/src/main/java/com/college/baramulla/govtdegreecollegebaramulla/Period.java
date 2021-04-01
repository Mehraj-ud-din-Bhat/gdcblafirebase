package com.college.baramulla.govtdegreecollegebaramulla;

/**
 * Created by MEHRAJ UD DIN BHAT on 3/21/2019.
 */

public class Period {
    String startTime;
    String endTime;
    String periodDetail;
    public Period()
       {}
    public Period(String startTime, String endTime, String periodDetail) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.periodDetail = periodDetail;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPeriodDetail() {
        return periodDetail;
    }

    public void setPeriodDetail(String periodDetail) {
        this.periodDetail = periodDetail;
    }
}
