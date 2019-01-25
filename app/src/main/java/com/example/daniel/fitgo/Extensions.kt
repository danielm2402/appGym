package com.example.daniel.fitgo

import android.app.Activity
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast



fun Activity.toast(message: CharSequence, duration: Int= Toast.LENGTH_SHORT)= Toast.makeText(this, message, duration).show()
fun Activity.toast(resourceId: Int, duration: Int = Toast.LENGTH_SHORT)= Toast.makeText(this, resourceId, duration).show()
fun ViewGroup.inflate(layoutId: Int) = LayoutInflater.from(context).inflate(layoutId, this, false)!!

inline fun <reified T:Activity> Activity.goToActivity(noinline init: Intent.()-> Unit={}){
    val intent = Intent(this,T::class.java)
    intent.init()
    startActivity(intent)
}

//edit text

fun EditText.validate(validation: (String)->Unit){
    this.addTextChangedListener((object: TextWatcher {
        override fun afterTextChanged(editable: Editable) {
            validation(editable.toString())
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

    }))
}

 fun Activity.isValidEmail(email: String): Boolean{
    val pattern= Patterns.EMAIL_ADDRESS
    return pattern.matcher(email).matches()
}
 fun Activity.isValidPassword(password: String ): Boolean{
    //Necesita contener 1 num/ 1 minuscula  / min 4 caracteres
    val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{4,}$"
    val pattern= java.util.regex.Pattern.compile(passwordPattern)
    return pattern.matcher(password).matches()

}
 fun Activity.isValidConfirmPassword(password: String,confirmPassword: String): Boolean{
    return password==confirmPassword
}

fun Activity.isNoNull(valor: String ): Boolean{
    //Necesita contener 1 num/ 1 minuscula  / min 4 caracteres
    val valPattern = "^(?=.*[a-z]).{1,}$"
    val pattern= java.util.regex.Pattern.compile(valPattern)
    return pattern.matcher(valor).matches()

}


