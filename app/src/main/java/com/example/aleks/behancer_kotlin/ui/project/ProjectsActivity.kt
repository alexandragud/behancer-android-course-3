package com.example.aleks.behancer_kotlin.ui.project

import android.util.Log
import androidx.fragment.app.Fragment
import com.example.aleks.behancer_kotlin.common.SingleFragmentActivity

class ProjectsActivity : SingleFragmentActivity() {


    override fun getFragment(): Fragment = ProjectsFragment.newInstance()

}