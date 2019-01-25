package com.example.daniel.fitgo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.daniel.fitgo.*
import com.example.daniel.fitgo.activities.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUp : AppCompatActivity() {

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        buttonGoLogIn.setOnClickListener {
            goToActivity<LoginActivity> {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }

        btnSignIn.setOnClickListener {
            val email = editTextNombre.text.toString()
            val password = editTextPassword.text.toString()
            val confirmPassword = editTextConfirmPassword.text.toString()
            if (isValidEmail(email) && isValidPassword(password) && isValidConfirmPassword(password, confirmPassword)) {
                signUpByEmail(email, password)
            } else {
                toast("Por favor, verifique que los datos sean correctos.")

            }
        }

        editTextNombre.validate {
            editTextNombre.error = if (isValidEmail(it)) null else "Email no válido"
        }

        editTextPassword.validate {
            editTextPassword.error = if (isValidPassword(it)) null else "Password debe contener como mínimo: 1 número, 1 letra y una longitud mínima de 6 caracteres"
        }

        editTextConfirmPassword.validate {
            editTextConfirmPassword.error = if (isValidConfirmPassword(editTextPassword.text.toString(), it)) null else "Las contraseñas no coinciden"
        }

    }

    private fun signUpByEmail(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                mAuth.currentUser!!.sendEmailVerification().addOnCompleteListener(this){
                    toast("Se ha enviado un email al correo registrado, por favor confirma para empezar.")
                    goToActivity<LoginActivity> {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                }
            } else {
                toast("Ha ocurrido un error, por favor intente de nuevo.")
            }
        }
    }


}
