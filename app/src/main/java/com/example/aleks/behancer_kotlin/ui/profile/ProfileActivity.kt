package com.example.aleks.behancer_kotlin.ui.profile

import androidx.fragment.app.Fragment
import com.example.aleks.behancer_kotlin.common.SingleFragmentActivity
import java.lang.IllegalStateException

class ProfileActivity : SingleFragmentActivity() {

    companion object {
        const val usernameKey = "USERNAME_KEY"
    }

    override fun getFragment(): Fragment {
        if (intent != null)
            return ProfileFragment.newInstance(intent.getBundleExtra(usernameKey))
        throw IllegalStateException("getIntent cannot be null")
    }
}