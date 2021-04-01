package com.example.lovingkashmir.admin_gdc_baramulla;

public class Journal_Post {
    String postTitle,postDeatils,postauthor_Details,postauthror_photourl;

 public Journal_Post()
    {}

    public Journal_Post(String postTitle, String postDeatils, String postauthor_Details, String postauthror_photourl) {
        this.postTitle = postTitle;
        this.postDeatils = postDeatils;
        this.postauthor_Details = postauthor_Details;
        this.postauthror_photourl = postauthror_photourl;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }



    public String getPostDeatils() {
        return postDeatils;
    }

    public void setPostDeatils(String postDeatils) {
        this.postDeatils = postDeatils;
    }

    public String getPostauthor_Details() {
        return postauthor_Details;
    }

    public void setPostauthor_Details(String postauthor_Details) {
        this.postauthor_Details = postauthor_Details;
    }

    public String getPostauthror_photourl() {
        return postauthror_photourl;
    }

    public void setPostauthror_photourl(String postauthror_photourl) {
        this.postauthror_photourl = postauthror_photourl;
    }
}
