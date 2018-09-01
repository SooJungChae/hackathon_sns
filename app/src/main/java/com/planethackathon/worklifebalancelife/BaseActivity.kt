package com.planethackathon.worklifebalancelife

import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore



/**
 * Created by minseok on 2018. 9. 1..
 * WorkLifeBalanceLife.
 */
open class BaseActivity : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()

    fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}