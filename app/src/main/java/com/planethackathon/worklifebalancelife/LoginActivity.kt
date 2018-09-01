package com.planethackathon.worklifebalancelife

import android.content.Intent
import android.os.Bundle
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInApi
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class LoginActivity : BaseActivity(), GoogleApiClient.OnConnectionFailedListener {
    internal val RC_SIGN_IN = 8001

    /* Google */
    lateinit var mAuth: FirebaseAuth
    lateinit var mGoogleApiClient: GoogleApiClient
    lateinit var mGoogleSignInClient: GoogleSignInApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                failToLogin()
            }

        }
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        failToLogin()
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        mAuth = FirebaseAuth.getInstance()

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        successToLogin()
                    } else {
                        failToLogin()
                    }
                }
    }

    private fun successToLogin() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)

        finish()
    }

    fun onLoginWithGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        mGoogleSignInClient = Auth.GoogleSignInApi
        mGoogleApiClient = GoogleApiClient.Builder(this@LoginActivity)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()

        val signInIntent = mGoogleSignInClient.getSignInIntent(mGoogleApiClient)
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun failToLogin() {
        toast("로그인에 실패했습니다.")
    }
}
