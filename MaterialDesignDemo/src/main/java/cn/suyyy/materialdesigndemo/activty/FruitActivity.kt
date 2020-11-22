package cn.suyyy.materialdesigndemo.activty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.suyyy.materialdesigndemo.R

class FruitActivity : AppCompatActivity() {

    companion object {
        const val FRUIT_NAME = "fruit_name"
        const val FRUIT_IMAGE_ID = "fruit_image_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fruit)
    }
}