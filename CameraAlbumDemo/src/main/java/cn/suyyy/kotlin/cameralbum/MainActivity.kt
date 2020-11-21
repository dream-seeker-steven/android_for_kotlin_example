package cn.suyyy.kotlin.cameralbum

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

/**
 * 1. 关联缓存目录 /sdcard/Android/data/<package name>/cache
 * 2. 创建一个空文件
 * 3. 将空文件构造成url
 * 4. 启动相机，指定image输入的url为刚刚创建的
 * 5. 根据活动结果回调，若是照片发送旋转则调整回来
 * 6. 渲染图片
 * 7. 在清单文件中申明访问文件
 * 8. 指定url的共享路径
 */
class MainActivity : AppCompatActivity() {

    private val takePhoto = 1;
    private val fromAlbum = 2;
    lateinit var imageUrl: Uri
    lateinit var outputImage: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * 拍照
         */
        takePhotoBtn.setOnClickListener {
            outputImage = File(externalCacheDir, "output_image.jpg")
            // 若存在则删除
            if (outputImage.exists()) {
                outputImage.delete()
            }
            outputImage.createNewFile()
            imageUrl = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                FileProvider.getUriForFile(
                    this,
                    "cn.suyyy.kotlin.cameralbum.fileprovider",
                    outputImage
                )
            } else {
                Uri.fromFile(outputImage)
            }
            val intent = Intent("android.media.action.IMAGE_CAPTURE")
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUrl)
            startActivityForResult(intent, takePhoto)
        }

        fromAlbumBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            // 打开系统文件管理器
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            // 指定显示的图片
            intent.type = "image/*"
            startActivityForResult(intent, fromAlbum)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            takePhoto -> {
                if (resultCode == Activity.RESULT_OK) {
                    val bitmap =
                        BitmapFactory.decodeStream(contentResolver.openInputStream(imageUrl))
                    imageView.setImageBitmap(rotateIfRequired(bitmap))
                }
            }
            fromAlbum -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    data.data?.let { uri ->
                        // 将选择的照片显示
                        val bitmap = getBitmapFromUri(uri)
                        imageView.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }

    private fun getBitmapFromUri(uri: Uri) = contentResolver.openFileDescriptor(uri, "r")?.use {
        BitmapFactory.decodeFileDescriptor(it.fileDescriptor)
    }

    private fun rotateIfRequired(bitmap: Bitmap): Bitmap {
        // 获取图片的可交换图像文件的tag
        val exif = ExifInterface(outputImage.path)
        // 获取这个tag的方向，默认值是正常
        val orientation =
            exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateBitmap(bitmap, 90)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateBitmap(bitmap, 180)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateBitmap(bitmap, 270)
            else -> bitmap
        }
    }

    /**
     * 旋转bitmap
     */
    private fun rotateBitmap(bitmap: Bitmap, degree: Int): Bitmap {
        // 旋转矩阵
        val matrix = Matrix().apply {
            postRotate(degree.toFloat())
        }
        val rotateBitmap =
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        // 回收不再需要的 Bitmap
        bitmap.recycle()
        return rotateBitmap
    }
}