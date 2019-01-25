package com.example.daniel.fitgo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.daniel.fitgo.*
import com.example.daniel.fitgo.activities.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {

    private val mAuth: FirebaseAuth by lazy {FirebaseAuth.getInstance()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        editTextNombre.validate {
            editTextNombre.error = if (isValidEmail(it)) null else "Email inválido"
        }

        buttonGoLogIn.setOnClickListener{
            goToActivity<LoginActivity>{
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right )
        }

        buttonForgot.setOnClickListener(){
            val email = editTextNombre.text.toString()
            if(isValidEmail(email))
            {
                mAuth.sendPasswordResetEmail(email).addOnCanceledListener(this) {
                    toast("Se ha enviado un email para reestablecer la contraseña.")
                    goToActivity<LoginActivity>{
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right )
                }
            }else{

                toast("Por favor verifique que el email ingresado es correcto.")
            }
        }
    }
}
