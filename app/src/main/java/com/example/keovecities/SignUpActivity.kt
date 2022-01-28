package com.example.keovecities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.keovecities.api.ApiService
import com.example.keovecities.api.ApiUrl
import com.example.keovecities.models.UserLoginModel
import com.example.keovecities.models.UserSignUpModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SignUpActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var signUpBtn : Button
    lateinit var userNameEdit : EditText
    lateinit var passwordEdit : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signUpBtn = findViewById(R.id.signUpBtn)
        userNameEdit = findViewById(R.id.userNameEdit)
        passwordEdit = findViewById(R.id.passwordEdit)

        signUpBtn.setOnClickListener(this)
    }

    fun logInClick(view: android.view.View) {
        val intent = Intent(this@SignUpActivity, LogInActivity::class.java)
        startActivity(intent)
    }

    override fun onClick(v: View?) {
        val pref = applicationContext.getSharedPreferences("koeveCities", 0)
        val editor = pref.edit()

            val retrofit: Retrofit = ApiUrl().getClient()
            val apiService = retrofit.create(ApiService::class.java).also {

                it.getUserSignUp(userNameEdit.text.toString(),passwordEdit.text.toString()).enqueue( object : Callback<UserSignUpModel> {
                    override fun onResponse(
                        call: Call<UserSignUpModel>?,
                        response: Response<UserSignUpModel>?
                    ) {
                        val signUpToken = response!!.body().token
                        editor.putString("signUpToken", signUpToken).apply()
                        Defaults().SIGNUPTOKEN = "Bearer " + signUpToken
                        Log.e("signUpToken", ": "+ Defaults().SIGNUPTOKEN )
                        Toast.makeText(this@SignUpActivity, "Başarılı bir şekilde üye olundu.", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@SignUpActivity, LogInActivity::class.java)
                        startActivity(intent)
                    }

                    override fun onFailure(call: Call<UserSignUpModel>?, t: Throwable?) {
                        Log.e("Response", ": " + t.toString())
                    }

                })
            }
        }

    }
