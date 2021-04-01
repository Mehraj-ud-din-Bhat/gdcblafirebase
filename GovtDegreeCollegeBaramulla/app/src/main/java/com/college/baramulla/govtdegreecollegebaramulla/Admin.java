package com.college.baramulla.govtdegreecollegebaramulla;

public class Admin {
    String adminDetails,adminPhotourl,adminEmail;
    public  Admin()
    {


    }

    public Admin(String adminDetails, String adminPhotourl, String adimEmail) {
        this.adminDetails = adminDetails;
        this.adminPhotourl = adminPhotourl;
        this.adminEmail = adimEmail;
    }

    public String getAdminDetails() {
        return adminDetails;
    }

    public void setAdminDetails(String adminDetails) {
        this.adminDetails = adminDetails;
    }

    public String getAdminPhotourl() {
        return adminPhotourl;
    }

    public void setAdminPhotourl(String adminPhotourl) {
        this.adminPhotourl = adminPhotourl;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdimEmail(String adimEmail) {
        this.adminEmail = adimEmail;
    }
}
