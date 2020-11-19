package cn.suyyy.kotlin.broadcasts.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import cn.suyyy.kotlin.R
import cn.suyyy.kotlin.broadcasts.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // SharedPreferences Activity中的，默认以当前活动的类名作为文件名
        val prefs = getPreferences(Context.MODE_PRIVATE)
        val isRemember = prefs.getBoolean(R.string.remember_password.toString(), false)

        // 若是记住了密码，则直接导入
        if (isRemember) {
            val account = prefs.getString(R.string.account.toString(), "")
            val password = prefs.getString(R.string.password.toString(), "")
            accountEdit.setText(account)
            passwordEdit.setText(password)
            rememberPass.isChecked = true
        }

        login.setOnClickListener {
            val account = accountEdit.text.toString()
            val password = passwordEdit.text.toString()
            if ("admin" == account && "123456" == password) {

                val editor = prefs.edit()
                if (rememberPass.isChecked) {
                    editor.putBoolean(R.string.remember_password.toString(), true)
                    editor.putString(R.string.account.toString(), account)
                    editor.putString(R.string.password.toString(), password)
                } else {
                    editor.clear()
                }
                editor.apply()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "account or password is invalid", Toast.LENGTH_SHORT).show()
            }
        }
    }

}