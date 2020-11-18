package cn.suyyy.kotlin.fragment.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import cn.suyyy.kotlin.R
import cn.suyyy.kotlin.fragment.fragment.NewsContentFragment
import kotlinx.android.synthetic.main.activity_news_content.*


class NewsContentActivity : AppCompatActivity() {

    /**
     * 静态代码款，添加参数 传递数据
     */
    companion object {
        fun actionStart(context: Context, newsTitle: String, newsContent: String) {
            val intent = Intent(context, NewsContentActivity::class.java).apply {
                putExtra("newsTitle", newsTitle)
                putExtra("newsContent", newsContent)
            }
            context.startActivity(intent)
        }
    }

    /**
     * 创建时加载数据
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_content)
        val title = intent.getStringExtra("newsTitle")
        val content = intent.getStringExtra("newsContent")
        // 刷新数据
        if (title != null && content != null) {
            val fragment = news_content_fragment as NewsContentFragment
            fragment.refresh(title, content)
        }

    }
}