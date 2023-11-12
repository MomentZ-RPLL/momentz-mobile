package com.kai.momentz.view.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kai.momentz.R
import com.kai.momentz.view.profile.ProfileFragment

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val fragmentManager = supportFragmentManager
        val registerFragment = RegisterFragment()
        val fragment = fragmentManager.findFragmentByTag(RegisterFragment::class.java.simpleName)

        if (fragment !is RegisterFragment) {
            fragmentManager
                .beginTransaction()
                .add(R.id.frame_container, registerFragment, RegisterFragment::class.java.simpleName)
                .commit()
        }
    }

}