package cn.suyyy.kotlin.broadcasts.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import cn.suyyy.kotlin.R
import cn.suyyy.kotlin.broadcasts.activity.LoginActivity
import cn.suyyy.kotlin.broadcasts.collector.ActivityCollector

/**
 * Activity基类，添加Activity管理
 * 广播接收器
 */
open class BaseActivity : AppCompatActivity() {

    // 延迟加载内部类
    lateinit var receiver: ForceOfflineReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCollector.addActivity(this)
    }

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter()
        intentFilter.addAction(getString(R.string.force_offline))
        // 接收广播时加载
        receiver = ForceOfflineReceiver()
        // 主存广播接收器
        registerReceiver(receiver,intentFilter)

    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }

    inner class ForceOfflineReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent?) {
            Toast.makeText(context, "接收一条广播", Toast.LENGTH_SHORT).show()
            AlertDialog.Builder(context).apply {
                setTitle("Warning")
                setMessage("You are forced to be offline. Please try to login again.")
                setCancelable(false)
                setPositiveButton("OK") { _, _ ->
                    ActivityCollector.finishAll()
                    val i = Intent(context, LoginActivity::class.java)
                    context.startActivity(i)
                }
                show()
            }
        }
    }
}