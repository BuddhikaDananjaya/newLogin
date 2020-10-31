package com.example.reloginapp

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.etEmail
import kotlinx.android.synthetic.main.activity_main.etPassword
import kotlinx.android.synthetic.main.activity_signup.*


class LoginActivity : AppCompatActivity() {
    var mProgress: ProgressDialog? = null
    var runnable: Runnable? = null
    private val handler = Handler()
    private var mAuth:FirebaseAuth? = null
    private var database = FirebaseDatabase.getInstance()
    private var myRef = database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mProgress = ProgressDialog(this);
        mProgress!!.setTitle("Processing...");
        mProgress!!.setMessage("Please wait...");
        mProgress!!.setCancelable(false);
        mProgress!!.setIndeterminate(true);

        mAuth = FirebaseAuth.getInstance()

        loadData(false)

        if(loadData(true)) {
            Toast.makeText(
                applicationContext,
                R.string.login_success, Toast.LENGTH_SHORT
            ).show()


            runnable = Runnable {
                if (!isFinishing) {
                    var intent = Intent(this, Home_navAvtivity::class.java)
                    startActivity(intent)
                    mProgress!!.dismiss()
                    finish()
                }
            }
            mProgress!!.show();
            handler.postDelayed(runnable!!, 1000)
        }
    }
    fun Validate(view: View) {

        var email: String = etEmail.text.toString()
        var password: String = etPassword.text.toString()

        LoadMain()

       /* if (email.isEmpty()) {
            etEmail?.error = getString(R.string.email_empty)

        } else if (email.isEmailValid() != true) {

            etEmail?.error = getString(R.string.enter_valid_email_address)

        } else if (password.isEmpty()) {


            etPassword?.error = getString(R.string.password_empty)

        } else if (password.isPassWordValid() != true) {

            etPassword?.error = getString(R.string.enter_valid_password)

        } else if (email.isEmailValid() && password.isPassWordValid()) {
            Toast.makeText(
                applicationContext,
                R.string.login_success, Toast.LENGTH_SHORT
            ).show()

            runnable = Runnable {
                if (!isFinishing) {
                    startActivity<Home_navAvtivity>()
                    mProgress!!.dismiss()
                    finish()
                }
            }
            mProgress!!.show();
            handler.postDelayed(runnable!!, 1000)
        }*/

    }

    fun btnSignup(view: View) {
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
    }
    fun loadData(boolean: Boolean):Boolean{

        val sharedPreferences = getSharedPreferences("sharedpref", Context.MODE_PRIVATE)
        val SignEmail = sharedPreferences.getString("SignEmail", null)
        val SignPassword = sharedPreferences.getString("SignPassword", null)
        if(SignEmail != null  && SignPassword != null)
        {
            return true
        }else
            return false
    }

    /*override fun onStart() {
        super.onStart()
        LoadMain()
    }*/

    fun LoadMain(){

        var currentUser = mAuth!!.currentUser
       myRef.child("Users").child(split(currentUser!!.email.toString())).child("Request").setValue(currentUser.email)

       /* val map: HashMap<String, Any> = HashMap()
        map["Fname"] = etFname.text.toString()
        map["Lname"] = etLname.text.toString()
        map["email"] = etEmail.text.toString()
        //myRef.setValue(map)*/


        if(currentUser != null)
        {
            var intent = Intent(this, Home_navAvtivity::class.java)
            intent.putExtra("email", currentUser.email)
            intent.putExtra("uid", currentUser.uid)


            startActivity(intent)
        }
    }
    fun split(str: String):String{

        var name = str.split("@")
        return name[0]
    }

}