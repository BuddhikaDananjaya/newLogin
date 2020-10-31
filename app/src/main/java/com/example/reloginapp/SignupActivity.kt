package com.example.reloginapp

import Extensions.isEmailValid
import Extensions.isNamevalid
import Extensions.isPassWordValid
import Extensions.startActivity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_signup.*


class SignupActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private var database = FirebaseDatabase.getInstance()
    private var myRef = database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        mAuth = FirebaseAuth.getInstance()
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        when(item.itemId){
            R.id.back -> {
                var intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    fun Validate(view: View) {

        var name:String = etFname.text.toString()
        var lasname:String = etLname.text.toString()
        var email: String = etEmail.text.toString()
        var password: String = etPassword.text.toString()
        var cpassword: String = etCpassword.text.toString()

        if(name.isEmpty())
        {
            etFname?.error = getString(R.string.name_empty)

        } else if(name.isNamevalid() != true){

            etFname?.error = getString(R.string.enter_valid_name)

        }else if(lasname.isEmpty())
        {

            etLname?.error = getString(R.string.last_name_empty)

        }  else if(lasname.isNamevalid() != true){

            etLname?.error = getString(R.string.enter_the_valid_last_name)

        } else if(email.isEmpty())
        {

            etEmail?.error = getString(R.string.email_empty)

        }else if(email.isEmailValid() != true){

            etEmail?.error = getString(R.string.enter_valid_email_address)

        }else  if(password.isEmpty()) {

            etPassword?.error = getString(R.string.password_empty)

        } else if (password.isPassWordValid() != true){

            etPassword?.error = getString(R.string.enter_valid_password)

        }
        else  if(cpassword.isEmpty()) {

            etCpassword?.error = getString(R.string.confirm_password_empty)

        } else if (email.isEmailValid() && password.isPassWordValid() && cpassword.equals(password)) {

            Toast.makeText(applicationContext, R.string.signup_success, Toast.LENGTH_SHORT).show()
            LoginToFirebase(etEmail.text.toString(), etPassword.text.toString())
            saveData()
            startActivity<Home_navAvtivity>()

        }else{

            etCpassword?.error = getString(R.string.passwords_are_not_matching)

        }
    }

    fun backtologin(view: View) {
        finish()
    }
    fun saveData(){  // Save signup page data into a shared preference

        val Fname     = etFname.text.toString()
        val Lname     = etLname.text.toString()
        val Email     = etEmail.text.toString()
        val Password  = etPassword.text.toString()
        val Cpassword = etCpassword.text.toString()

        val sharedPreferences = getSharedPreferences("sharedpref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.apply(){

            putString("SignEmail", Email)
            putString("SignPassword", Password)
            putString("SignFname", Fname)
            putString("SignLname", Lname)
            putString("SignCpassword", Cpassword)

        }.apply()
        Toast.makeText(applicationContext, "data saved in shared preference!", Toast.LENGTH_LONG).show()

    }
    fun LoginToFirebase(Email: String, Password: String)
    {
        var currentuser = mAuth!!.currentUser

        mAuth!!.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(this){ task ->

            if(task.isSuccessful)
            {
                Toast.makeText(applicationContext, "Firebase created new user!!", Toast.LENGTH_LONG).show()
            }else{
                try {
                    Toast.makeText(applicationContext, "login id faild!", Toast.LENGTH_LONG).show()
                }catch (e: Exception)
                {
                    Toast.makeText(applicationContext, "login id faild! $e", Toast.LENGTH_LONG).show()
                }


            }
        }

    }
    fun split(str: String):String{

        var name = str.split("@")
        return name[0]
    }

}