package cn.suyyy.jetpackdemo.activity

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.edit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import cn.suyyy.jetpackdemo.R
import cn.suyyy.jetpackdemo.factory.MainViewModelFactory
import cn.suyyy.jetpackdemo.server.MyObServer
import cn.suyyy.jetpackdemo.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycle.addObserver(MyObServer())

        sp = getPreferences(Context.MODE_PRIVATE)
        val countReserved = sp.getInt("count_reserved", 0)
        // 使用工厂传输数据
        viewModel = ViewModelProviders.of(this, MainViewModelFactory(countReserved))
            .get(MainViewModel::class.java)
        plusOneBtn.setOnClickListener {
            viewModel.plusOne()
        }
        clearBtn.setOnClickListener {
            viewModel.clear()
        }

        // 当数据更新时，会回调到这里
        viewModel.counter.observe(this) {
            infoText.text = it.toString()
        }

    }

    override fun onPause() {
        super.onPause()
        sp.edit() {
            putInt("count_reserved", viewModel.counter.value ?: 0)
        }
    }

    private fun refreshCounter() {
        infoText.text = viewModel.counter.toString()
    }
}