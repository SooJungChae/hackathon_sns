package com.planethackathon.worklifebalancelife.common;

import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static DateHelper instance;
    private DateHelper() {}

    public DateHelper getInstance() {
        if (instance == null) {
            instance = new DateHelper();
        }
        return instance;
    }

}
