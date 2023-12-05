package com.kai.momentz.view.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kai.momentz.R
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
            fragmentManager.popBackStackImmediate();
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