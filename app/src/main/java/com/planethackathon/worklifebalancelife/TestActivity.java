package com.planethackathon.worklifebalancelife;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.planethackathon.worklifebalancelife.common.History;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        final Button btn_add = (Button) findViewById(R.id.btn_add);
        final Button btn_statistics = (Button) findViewById(R.id.btn_statistics);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final TextView workIntervalTextView = (TextView) findViewById(R.id.workIntervalTextView);
        final TextView lifeIntervalTextView = (TextView) findViewById(R.id.lifeIntervalTextView);

        if(user != null) {
            final CollectionReference logsRef = db.collection("users").document("Y3YGpTFg0Sb0kQb0BHfP").collection("logs");

            //READ
            Query dayQuery = logsRef.whereEqualTo("date", "2018-09-01");

            dayQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        List<History> historyList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            History history = new History(document.get("date").toString(), document.get("startTime").toString()
                            , document.get("endTime").toString(), document.get("tag").toString(), (Long) document.get("interval"));
                            historyList.add(history);
                            Log.d("RESULT", history.toString());
                        }

                    } else {
                        Log.e("FAIL", "Error getting documents: ", task.getException());
                    }
                }
            });

            //ADD
            final History history = new History("2018-09-31", "2018-09-31 09:00:00",
                    "2018-09-31 18:00:00", "work", 32400l);

            btn_add.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View view) {
                    logsRef.document().set(history)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("SUCCESS", "success add");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("FAIL", "fail add");
                                }
                            });

                }
            });

            // STATISTICS
            btn_statistics.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Calendar calendar = Calendar.getInstance();
                    Calendar weekStart = Calendar.getInstance();
                    Calendar weekEnd = Calendar.getInstance();
                    weekStart.set(Calendar.DATE, calendar.getFirstDayOfWeek());

                    weekEnd.setTime(weekStart.getTime());
                    weekEnd.add(Calendar.DATE, 6);

                    if (weekEnd.get(Calendar.MONTH) != calendar.get(Calendar.MONTH)) {
                        weekEnd.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
                        weekEnd.set(Calendar.MONTH, calendar.getActualMaximum(Calendar.MONTH));
                        weekEnd.set(Calendar.YEAR, calendar.getActualMaximum(Calendar.YEAR));
                    }

                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    Query dayQuery = logsRef.whereGreaterThanOrEqualTo("date", dateFormat.format(weekStart))
                            .whereLessThanOrEqualTo("date", dateFormat.format(weekEnd))
                            .whereEqualTo("tag", "work");


                    dayQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                Long result = 0l;
                                final List<History> historyList = new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    final History history = new History(document.get("date").toString(), document.get("startTime").toString()
                                            , document.get("endTime").toString(), document.get("tag").toString(), (Long) document.get("interval"));
                                    historyList.add(history);
                                    result += history.getInterval();
                                    Log.d("WEEK RESULT", history.toString());
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Long workInterval = result;
                                            Long hour = workInterval/3600;
                                            workInterval -= hour * 3600;
                                            Long min = workInterval / 60;
                                            workInterval -= min * 60;

                                            workIntervalTextView.setText( hour + "\"" + min + "\'" + workInterval + ".");

                                            Long lifeInterval = historyList.size() * 3600L * 24 - result;
                                            Long hour2 = lifeInterval/3600;
                                            lifeInterval -= hour2 * 3600;
                                            Long min2 = lifeInterval / 60;
                                            lifeInterval -= min2 * 60;

                                            lifeIntervalTextView.setText( hour2 + "\"" + min2 + "\'" + lifeInterval + ".");
                                        }
                                    });
                                }

                                Log.d("WEEK", result.toString());

                            } else {
                                Log.e("STATISTICS RESULT", "Error getting documents: ", task.getException());
                            }
                        }
                    });


                }
            });

        }
    }


}

