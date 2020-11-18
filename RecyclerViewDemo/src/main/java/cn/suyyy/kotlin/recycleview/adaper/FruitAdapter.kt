package cn.suyyy.kotlin.recycleview.adaper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import cn.suyyy.kotlin.R
import cn.suyyy.kotlin.recycleview.data.Fruit

class FruitAdapter(val fruitList: List<Fruit>) : RecyclerView.Adapter<FruitAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fruitImage: ImageView = view.findViewById(R.id.fruit_image)
        val fruitName: TextView = view.findViewById(R.id.fruit_name)
    }

    /**
     * 创建视图时缓存策略
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // attachToRoot 如果为false，则root仅用于为XML中的根视图创建LayoutParams的正确子类。
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fruit_item, parent, false)
        val viewHolder = ViewHolder(view)
        // 为子类view设置点击事件
        viewHolder.itemView.setOnClickListener {
            // item位置
            val position = viewHolder.adapterPosition
            val fruit = fruitList[position]
            // 通知在父级的上下文
            Toast.makeText(parent.context, "你点击了view ${fruit.name}", Toast.LENGTH_SHORT).show()
        }
        viewHolder.fruitImage.setOnClickListener {
            val position = viewHolder.adapterPosition
            val fruit = fruitList[position]
            Toast.makeText(parent.context, "你点击了image ${fruit.name}", Toast.LENGTH_SHORT).show()
        }
        return viewHolder
    }

    /**
     * 子类数量
     */
    override fun getItemCount() = fruitList.size

    /**
     * 为每个子项赋值
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fruit = fruitList[position]
        holder.fruitImage.setImageResource(fruit.imageId)
        holder.fruitName.text = fruit.name
    }
}