package cn.suyyy.kotlin.fragment.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.suyyy.kotlin.R
import cn.suyyy.kotlin.fragment.activity.NewsContentActivity
import cn.suyyy.kotlin.fragment.common.times
import cn.suyyy.kotlin.fragment.data.News
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.news_title_frag.*

class NewsTitleFragment : Fragment() {

    private var isTwoPane = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.news_title_frag, container, false)
    }

    /**
     * 使用匿名内部类 实现适配器
     */
    inner class NewsAdapter(val newsList: List<News>) :
        RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

        /**
         * 设定缓存的数据组件 title
         */
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val newsTitle: TextView = view.findViewById(R.id.newsTitle)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
            // 找到这个标题布局
            val holder = ViewHolder(view)
            // 为item设置点击事件
            holder.itemView.setOnClickListener {
                // 传入点击item的位置
                val news = newsList[holder.adapterPosition]
                Log.d("NewsTitleFragment", "onCreateViewHolder: $news")
                if (isTwoPane) {
                    // 双页，直接在另一个碎片刷新数据
                    val fragment = newsContentFrag as NewsContentFragment
                    fragment.refresh(news.title, news.content)
                } else {
                    // 调用静态方法，再当前页跳转至新闻内容页
                    NewsContentActivity.actionStart(parent.context, news.title, news.content)
                }
            }
            return holder
        }

        override fun getItemCount() = newsList.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val news = newsList[position]
            holder.newsTitle.text = news.title

        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // 查找是否有内容碎片布局，有则开启双页
        isTwoPane = activity?.findViewById<View>(R.id.newsContentLayout) != null
        val layoutManager  = LinearLayoutManager(activity)
        newsTitleRecyclerView.layoutManager = layoutManager
        val adapter = NewsAdapter(getNews())
        newsTitleRecyclerView.adapter = adapter
    }

    private fun getNews(): List<News> {
        val newsList = ArrayList<News>()
        for (i in 1..50) {
            val news = News("This is news title $i", getRandomLengthString("This is news content $i. \n"))
            newsList.add(news)
        }
        return newsList
    }

    private fun getRandomLengthString(str: String): String {
        // 使用扩展函数
        return str * (1..20).random()
    }
}