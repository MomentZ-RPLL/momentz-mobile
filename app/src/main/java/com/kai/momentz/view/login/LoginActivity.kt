package com.kai.momentz.view.login

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.kai.momentz.R
import com.kai.momentz.customview.DefaultButton
import com.kai.momentz.customview.LoginInputEditText
import com.kai.momentz.databinding.ActivityLoginBinding
import com.kai.momentz.model.datastore.User
import com.kai.momentz.view.ViewModelFactory
import com.kai.momentz.view.home.HomeActivity
import com.kai.momentz.view.register.RegisterActivity
import com.kai.momentz.view.register.RegisterFragment
import com.kai.momentz.view.splash.SplashFragment

class LoginActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val fragmentManager = supportFragmentManager
        val registerFragment = SplashFragment()
        val loginFragment = LoginFragment()

        val fromHome = intent.getStringExtra("fromHome")

        if(fromHome=="tes"){
            fragmentManager.beginTransaction()
                .add(R.id.frame_container, loginFragment, LoginFragment::class.java.simpleName)
                .commit()
        }else{
            fragmentManager.beginTransaction()
                .add(R.id.frame_container, registerFragment, SplashFragment::class.java.simpleName)
                .commit()
        }
    }

}