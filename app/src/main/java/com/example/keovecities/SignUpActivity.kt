package com.example.keovecities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.keovecities.api.ApiService
import com.example.keovecities.api.ApiUrl
import com.example.keovecities.models.UserSignUpModel
import com.example.keovecities.models.UserSignUpResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SignUpActivity : AppCompatActivity() {

    lateinit var signUpBtn : Button
    lateinit var userNameEdit : EditText
    lateinit var passwordEdit : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signUpBtn = findViewById(R.id.signUpBtn)
        userNameEdit = findViewById(R.id.userNameEdit)
        passwordEdit = findViewById(R.id.passwordEdit)


    }

    fun logInClick(view: android.view.View) {
        val intent = Intent(this@SignUpActivity, LogInActivity::class.java)
        startActivity(intent)
    }


    fun onClickSignUp(view: android.view.View) {

        val requestBody = UserSignUpModel(userNameEdit.text.toString(), passwordEdit.text.toString())

        val retrofit: Retrofit = ApiUrl().getClient(this)
        val apiService = retrofit.create(ApiService::class.java).also {

            it.getUserSignUp(Defaults.TOKEN,requestBody).enqueue( object : Callback<UserSignUpResponseModel> {
                override fun onResponse(
                    call: Call<UserSignUpResponseModel>?,
                    response: Response<UserSignUpResponseModel>?
                ) {
                    if ( response != null && response.code() == 200 ){
                        Defaults().setSpData(this@SignUpActivity, response.body().token,response.body().refreshToken,response.body().id,response.body().isGuest,System.currentTimeMillis())
                        Toast.makeText(this@SignUpActivity, "Başarılı bir şekilde üye olundu.", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@SignUpActivity, LogInActivity::class.java)
                        startActivity(intent)
                    }else if(response != null && response.code() == 409){
                        Toast.makeText(this@SignUpActivity, "Kullanıcı zaten bulunmaktadır.", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this@SignUpActivity, "Network Error", Toast.LENGTH_LONG).show()
                    }

                }

                override fun onFailure(call: Call<UserSignUpResponseModel>?, t: Throwable?) {
                    Log.e("Response", ": " + t.toString())
                }

            })
        }
    }

}
