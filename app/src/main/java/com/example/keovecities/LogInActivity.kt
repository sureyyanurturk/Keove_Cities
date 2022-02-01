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
import com.example.keovecities.models.UserLoginResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class LogInActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var logInBtn : Button
    lateinit var userNameEdit : EditText
    lateinit var passwordEdit : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        logInBtn = findViewById(R.id.logInBtn)
        userNameEdit = findViewById(R.id.userNameEdit)
        passwordEdit = findViewById(R.id.passwordEdit)

        logInBtn.setOnClickListener(this)


    }

    fun signInClick(view: android.view.View) {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    override fun onClick(v: View?) {
        val requestBody = UserLoginModel(userNameEdit.text.toString(), passwordEdit.text.toString())

        val retrofit: Retrofit = ApiUrl().getClient(this)
        val apiService = retrofit.create(ApiService::class.java).also {

            it.getUserLogin(Defaults.TOKEN,requestBody).enqueue( object :Callback<UserLoginResponseModel>{
                override fun onResponse(
                    call: Call<UserLoginResponseModel>?,
                    response: Response<UserLoginResponseModel>?
                ) {

                    if (response!!.code() ==200 )   {

                        Defaults().setSpData(this@LogInActivity,response.body().token,response.body().refreshToken,response.body().id,response.body().isGuest,System.currentTimeMillis())
                        Toast.makeText(this@LogInActivity, "Başarılı.", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LogInActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                    else {
                        Toast.makeText(this@LogInActivity, "Hatalı Kullanıcı Veya Şifre.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<UserLoginResponseModel>?, t: Throwable?) {
                    Log.e("Response", ": " + t.toString())
                }

            })
        }
    }


}