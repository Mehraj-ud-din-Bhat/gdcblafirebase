package com.college.baramulla.govtdegreecollegebaramulla;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class GovtDegreeCollegeBaramulla extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
