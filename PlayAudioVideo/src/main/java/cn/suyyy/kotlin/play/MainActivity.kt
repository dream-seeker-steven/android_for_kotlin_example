package cn.suyyy.kotlin.play

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    private val mediaPlayer = MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        playAudio()
        playVideo()
    }

    private fun playAudio(){
        initMediaPlayer()
        aPlay.setOnClickListener {
            if (!mediaPlayer.isPlaying) {
                // 开始播放
                mediaPlayer.start()
            }
            Log.d(TAG, "video is playing")
        }
        aPause.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                // 暂停播放
                mediaPlayer.pause()
            }
        }
        aStop.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                // 停止播放
                mediaPlayer.reset()
                initMediaPlayer()
            }
        }
    }
    private fun playVideo(){
        // 初始化
        val uri = Uri.parse("android.resource://$packageName/${R.raw.video}")
        videoView.setVideoURI(uri)

        vPlay.setOnClickListener {
            if (!videoView.isPlaying) {
                videoView.start() // 开始播放
            }
            Log.d(TAG, "video is playing")
        }

        vPause.setOnClickListener {
            if (videoView.isPlaying) {
                videoView.pause() // 暂停播放
            }
        }

        vReplay.setOnClickListener {
            if (videoView.isPlaying) {
                videoView.resume() // 重新播放
            }
        }
    }

    private fun initMediaPlayer() {
        // 获取 AssetManager 实例
        val assetManager = assets
        val fd = assetManager.openFd("music.mp3")
        mediaPlayer.setDataSource(fd.fileDescriptor, fd.startOffset, fd.length)
        mediaPlayer.prepare()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        mediaPlayer.release()
        videoView.suspend()
    }
}