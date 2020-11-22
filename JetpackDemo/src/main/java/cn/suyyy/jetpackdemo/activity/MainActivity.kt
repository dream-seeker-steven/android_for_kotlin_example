package cn.suyyy.jetpackdemo.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.work.BackoffPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import cn.suyyy.jetpackdemo.R
import cn.suyyy.jetpackdemo.factory.MainViewModelFactory
import cn.suyyy.jetpackdemo.server.MyObServer
import cn.suyyy.jetpackdemo.viewmodel.MainViewModel
import cn.suyyy.jetpackdemo.work.SimpleWorker
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit


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

        doWorkBtn.setOnClickListener {
            // 执行一次任务,多次PeriodicWorkRequest
            val request = OneTimeWorkRequest.Builder(SimpleWorker::class.java)
                    // 延迟5分钟执行
                .setInitialDelay(5, TimeUnit.MINUTES)
                    // 添加标签，可以去取消后台任务请求，不过也可以通过id，也可以一次性取消所有后台任务
                .addTag("simple")
                    // 失败后以线性 10 秒 抽重新执行，随着失败的次数，重试也会延迟
                .setBackoffCriteria(BackoffPolicy.LINEAR,10,TimeUnit.SECONDS)
                .build()
            WorkManager.getInstance(this)
                .enqueue(request)
        }

      /*  getUserBtn.setOnClickListener {
            val userId = (0..10000).random().toString()
            viewModel.getUser(userId)
        }
        *//**
         * 回调给observer,更新数据
         *//*
        viewModel.user.observe(this, Observer { user ->
            Log.d("log-MainActivity", "observe")
            infoText.text = user.firstName
        })*/

        /*val userDao = AppDatabase.getDatabase(this).userDao()
        val user1 = User("Tom", "Brady", 40)
        val user2 = User("Tom", "Hanks", 63)

        addDataBtn.setOnClickListener {
            thread {
                user1.id = userDao.insertUser(user1)
                user2.id = userDao.insertUser(user2)
            }
        }
        updateDataBtn.setOnClickListener {
            thread {
                user1.age = 42
                userDao.updateUser(user1)
            }
        }
        deleteDataBtn.setOnClickListener {
            thread {
                userDao.deleteUserByLastName("Hanks")
            }
        }
        queryDataBtn.setOnClickListener {
            thread {
                for (user in userDao.loadAllUsers()) {
                    Log.d("MainActivity", user.toString())
                }
            }
        }*/

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