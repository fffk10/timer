package com.example.timer

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import android.widget.Button
import java.util.Locale
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    private val START_TIME = 10000

    private lateinit var mTextViewCountDown: TextView
    private lateinit var mButtonStartPause: Button
    private lateinit var getmButtonReset: Button

    private lateinit var mCountDownTimer: CountDownTimer
    private var mTimerRunning by Delegates.notNull<Boolean>()

    private var mTimeLeftInMillis = START_TIME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // R は プロジェクト内のRクラスを呼び出している
        mTextViewCountDown = findViewById(R.id.text_view_countdown)
        mButtonStartPause = findViewById(R.id.button_start_pause)
        getmButtonReset = findViewById(R.id.button_reset)

        // クリックされた時のアクション
        mButtonStartPause.setOnClickListener {
            if (mTimerRunning) {
                pauseTimer()
            } else {
                startTimer()
            }
        }

        getmButtonReset.setOnClickListener() {
            resetTimer()
        }

        // 時刻が表示される
        updateCountDownText()
    }


    private fun startTimer() {
        mCountDownTimer = object: CountDownTimer(mTimeLeftInMillis.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                mTimeLeftInMillis = millisUntilFinished.toInt()
                updateCountDownText()
            }
            override fun onFinish() {
                // stop timer
                mTimerRunning = false
                mButtonStartPause.text = "start"

                // ボタンを表示する
                getmButtonReset.visibility = View.INVISIBLE

            }
        }.start()

        mTimerRunning = true
        mButtonStartPause.text = "stop"
        getmButtonReset.visibility = View.INVISIBLE
    }

    private fun pauseTimer() {
        mCountDownTimer.cancel()
        mTimerRunning = false
        mButtonStartPause.text = "reset"
        getmButtonReset.visibility = View.VISIBLE
    }

    private fun resetTimer() {
        mTimeLeftInMillis = START_TIME
        updateCountDownText()
        mButtonStartPause.visibility = View.VISIBLE
        getmButtonReset.visibility = View.INVISIBLE

    }

    // タイマーの表示を見やすくする
    private fun updateCountDownText() {
        var minutes = (mTimeLeftInMillis/1000) / 60
        var seconds = (mTimeLeftInMillis/1000) % 60

        var timerLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
        mTextViewCountDown.setText(timerLeftFormatted)
    }
}