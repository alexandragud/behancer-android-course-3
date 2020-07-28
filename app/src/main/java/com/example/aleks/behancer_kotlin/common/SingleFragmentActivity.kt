package com.example.aleks.behancer_kotlin.common

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.aleks.behancer_kotlin.AppDelegate
import com.example.aleks.behancer_kotlin.R
import com.example.aleks.behancer_kotlin.data.Storage
import kotlinx.android.synthetic.main.ac_swipe_container.*

abstract class SingleFragmentActivity : AppCompatActivity(),
    Storage.StorageOwner, SwipeRefreshLayout.OnRefreshListener, RefreshOwner {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_swipe_container)
        refresher.setOnRefreshListener(this)
        if (savedInstanceState == null) {
            changeFragment(getFragment())
        }
    }

    protected abstract fun getFragment(): Fragment

    override fun obtainStorage(): Storage {
        return (applicationContext as AppDelegate).storage
    }

    private fun changeFragment(fragment: Fragment){
        val addToBackStack = supportFragmentManager.findFragmentById(fragmentContainer.id)!=null
        val transaction = supportFragmentManager.beginTransaction().replace(fragmentContainer.id, fragment)
        if(addToBackStack){
            transaction.addToBackStack(fragment.javaClass.simpleName)
        }
        transaction.commit()
    }

    override fun onRefresh() {
        when(val fragment = supportFragmentManager.findFragmentById(fragmentContainer.id)){
            is Refreshable -> fragment.onRefreshData()
            else -> setRefreshState(false)
        }
    }

    override fun setRefreshState(refreshing: Boolean) {
        refresher.post { refresher.isRefreshing=refreshing }
    }


}