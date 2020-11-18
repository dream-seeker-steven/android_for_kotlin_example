package cn.suyyy.kotlin.fragment.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cn.suyyy.kotlin.R
import kotlinx.android.synthetic.main.news_content_frag.*

class NewsContentFragment : Fragment() {

    /**
     * 创建视图
     * 传入 新闻内容碎片
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.news_content_frag, container, false)
    }

    /**
     * 装载数据
     */
    fun refresh(title: String, content: String) {
        contentLayout.visibility = View.VISIBLE
        newsTitle.text = title
        newsContent.text = content
    }
}