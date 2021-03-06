package com.madcamp.empathy_diary

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GoogleLoginActivity : AppCompatActivity(){
    //Google Login
    private var mAuth: FirebaseAuth? = null
    private var googleSignInClient : GoogleSignInClient? = null
    private val RC_STIGN_IN = 9001

    //retrofit
    private var retrofitClient = RetrofitClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_login)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("442686102202-f37u9glbti9btdranin453kovcqq02dm.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        mAuth = Firebase.auth


        if (mAuth!!.currentUser != null) {
            val intent = Intent(application, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val googleBtn = findViewById<SignInButton>(R.id.btn_googleSignIn)
            .setOnClickListener(View.OnClickListener {
                //func
                Log.d("Click", "Click!!")
                signIn();
            })
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RC_STIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            Log.d("Task", task.toString())
            try{
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            }catch (e: ApiException){
                Log.e("LoginActivity", "Google sign in failed", e)
            }
        }
    }

    private fun signIn(){
        val signInIntent = googleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, RC_STIGN_IN)
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount){
        Log.d("LoginActivity", "firebaseAuthWithGoogle:" + acct.id!!)
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.w("LoginActivity", "firebaseAuthWithGoogle 성공", task.exception)
                    var name = mAuth!!.currentUser!!.displayName
                    if(name == null){
                        name = "No name"
                    }
                    val uid = mAuth!!.currentUser!!.uid.toString()
                    val user : User = User(uid, name)
                    val call = retrofitClient.apiService.login(user)
                    call!!.enqueue(object: Callback<String>{
                        override fun onFailure(call: Call<String>, t: Throwable) {
                            Log.d("Failure", "Server Login Fail")
                        }

                        override fun onResponse(call: Call<String>, response: Response<String>) {
                            if(response.isSuccessful){
                                Log.d("Success", "Server Login Success")
                                toMainActivity()
                            }
                        }

                    }
                    )
                } else {
                    Log.w("LoginActivity", "firebaseAuthWithGoogle 실패", task.exception)
                }
            }
    }

    fun toMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()

    }
}