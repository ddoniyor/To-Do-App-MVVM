package com.example.todoappmvvm.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.todoappmvvm.R
import com.example.todoappmvvm.SharedPreferencesHelper
import com.example.todoappmvvm.model.repository.MainRepository
import com.example.todoappmvvm.model.retrofit.Pojo
import com.example.todoappmvvm.model.retrofit.RetrofitBuilder
import com.example.todoappmvvm.viewmodel.MainViewModel
import com.example.todoappmvvm.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setUpViewModel()
        sharedPreferencesHelper = SharedPreferencesHelper(this)


        if (sharedPreferencesHelper.fetchStateAct(false)) {
            var intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }


        button_edt_enter.setOnClickListener {
            if (username_edt_enter.text.toString()
                    .isNotEmpty() && password_edt_enter.text.toString().isNotEmpty()
            ) {
                signInUser(username_edt_enter.text.toString(), password_edt_enter.text.toString())
                sharedPreferencesHelper.saveStateAct(true)
                /*if (sharedPreferencesHelper.fetchAuthToken()!=null){
                    intent = Intent(this, ListActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "Не зарегистрированы", Toast.LENGTH_LONG).show()
                }*/
            } else {
                Toast.makeText(this, "Не ввели пароль или логин", Toast.LENGTH_LONG).show()
            }

        }

        button_edt_register.setOnClickListener {
            intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            Log.d("TAAAAAAAAAG", "${sharedPreferencesHelper.fetchAuthToken()}")
        }


        mainViewModel.signInResponse.observe(this, Observer {
            if (it.token != null) {
                sharedPreferencesHelper?.saveAuthToken(it.token!!)
                Toast.makeText(this, "this IS${it.token}", Toast.LENGTH_SHORT).show()
                intent = Intent(this, ListActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }

        })

        mainViewModel.errorMessage.observe(this, Observer {

        })
    }


    private fun signInUser(username: String, password: String) {
        val modal = Pojo.LoginRequest(username, password)
        mainViewModel.signIn(modal)


    }

    private fun setUpViewModel() {
        mainViewModel =
            ViewModelProvider(
                this,
                ViewModelFactory(MainRepository(RetrofitBuilder().getApiInterface(this)))
            ).get(
                MainViewModel::class.java
            )
    }
}