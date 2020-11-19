package cn.suyyy.kotlin.broadcasts.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import cn.suyyy.kotlin.R
import cn.suyyy.kotlin.broadcasts.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * 发送一条强制下线广播
         */
        forceOffline.setOnClickListener {
            val intent = Intent(getString(R.string.force_offline))
            Toast.makeText(this, "发送了一条广播 ${getString(R.string.force_offline)}", Toast.LENGTH_SHORT).show()
            sendBroadcast(intent)
        }
    }
}