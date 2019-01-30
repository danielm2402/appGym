package com.example.daniel.fitgo.activities.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.daniel.fitgo.*
import com.example.daniel.fitgo.activities.ForgotPasswordActivity
import com.example.daniel.fitgo.activities.SignUp
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {


    private  val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val mGoogleApiClient: GoogleApiClient by lazy {getGoogleApiClient()}
    private val RC_GOOGLE_SIGN_IN=99

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnSignIn.setOnClickListener{
            val email= editTextNombre.text.toString()
            val password = editTextPassword.text.toString()
            if (isValidEmail(email) && isValidPassword(password))
            {
                logInByEmail(email,password)
            } else {
                toast("Por favor, verifique que los datos sean correctos.")

            }

        }

        buttonLogInGoogle.setOnClickListener{
            val sigInIntent= Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
            startActivityForResult(sigInIntent, RC_GOOGLE_SIGN_IN)
        }

        textViewForgotPassword.setOnClickListener{
            goToActivity<ForgotPasswordActivity>()
            overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right )
        }

        buttonCreateAccount.setOnClickListener{
            goToActivity<SignUp>()
            overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right )
        }


        editTextNombre.validate {
            editTextNombre.error = if (isValidEmail(it)) null else "Email no válido"
        }

    }

    private fun logInByEmail(email: String, password: String)
    {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this){task ->
            if (task.isSuccessful){
                if(mAuth.currentUser!!.isEmailVerified)
                {
                    toast("Usuario logged in.")
                    goToActivity<MainActivity> {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                }
                else
                {
                    toast("Por favor confirma tu email primero.")
                }
            }else{
                toast("Error inesperado, intente de nuevo.")
            }
        }
    }

    private fun getGoogleApiClient(): GoogleApiClient{




       val gsp = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
               .requestIdToken("424639339416-flga19ueah0dhh7392p6rb9mkm6qr7hf.apps.googleusercontent.com")
               .requestEmail()
               .build()
        return GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API, gsp).build()

    }

    private fun loginByGoogleAccount(googleAccount: GoogleSignInAccount){

        val credential = GoogleAuthProvider.getCredential(googleAccount.idToken, null)
        mAuth.signInWithCredential(credential).addOnCompleteListener(this){
            if(mGoogleApiClient.isConnected)
            {
                Auth.GoogleSignInApi.signOut(mGoogleApiClient)
            }
            goToActivity<MainActivity> {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RC_GOOGLE_SIGN_IN)
        {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if(result.isSuccess){
                val account = result.signInAccount
                loginByGoogleAccount(account!!)
            }
            else{
                toast("ERROR")
            }
        }
    }
    override fun onConnectionFailed(p0: ConnectionResult) {
        toast("Conexión fallida, intente de nuevo o revise su conexión a red")
    }
}
