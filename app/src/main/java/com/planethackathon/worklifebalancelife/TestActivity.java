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

import java.util.ArrayList;
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
                    Query dayQuery = logsRef.whereGreaterThanOrEqualTo("date", "2018-08-27")
                            .whereLessThanOrEqualTo("date", "2018-08-30")
                            .whereEqualTo("tag", "work");


                    dayQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                Long result = 0l;
                                List<History> historyList = new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    History history = new History(document.get("date").toString(), document.get("startTime").toString()
                                            , document.get("endTime").toString(), document.get("tag").toString(), (Long) document.get("interval"));
                                    historyList.add(history);
                                    result += history.getInterval();
                                    Log.d("WEEK RESULT", history.toString());
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

