package com.example.todoappmvvm.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.todoappmvvm.R
import com.example.todoappmvvm.model.repository.MainRepository
import com.example.todoappmvvm.model.retrofit.Pojo
import com.example.todoappmvvm.model.retrofit.RetrofitBuilder
import com.example.todoappmvvm.viewmodel.MainViewModel
import com.example.todoappmvvm.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setupViewModel()

        button_edt.setOnClickListener {
            signUpUser(
                name_edt.text.toString(),
                username_edt.text.toString(),
                password_edt.text.toString()
            )
        }

        mainViewModel.signUpResponse.observe(this, Observer {
            Toast.makeText(this, "$it", Toast.LENGTH_LONG).show()
        })

    }

    private fun signUpUser(name: String, username: String, password: String) {
        val modal = Pojo.UserInfoRegister(name, username, password)
        mainViewModel.signUp(modal)
    }

    private fun setupViewModel() {
        mainViewModel =
            ViewModelProvider(this, ViewModelFactory(MainRepository(RetrofitBuilder().getApiInterface(this)))).get(
                MainViewModel::class.java
            )
    }
}