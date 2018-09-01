package com.planethackathon.worklifebalancelife

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.planethackathon.worklifebalancelife.common.FiftyTwoHoursApplication
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.*

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        initText()

        val timerTask = object: TimerTask() {
            override fun run() {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                finish()
            }
        }

        Timer().schedule(timerTask, 2000)
    }

    private fun initText() {
        if (isLogin()) {
            txt_splash_top.setText(R.string.txt_splash_main)

            var time = "" // 2018월 9월 첫째주
            Calendar.getInstance().apply {
                time += get(Calendar.YEAR).toString() + "년 "
                time += (get(Calendar.MONTH) + 1).toString() + "월 "

                time += when (get(Calendar.WEEK_OF_MONTH)) {
                    1 -> "첫"
                    2 -> "둘"
                    3 -> "셋"
                    4 -> "넷"
                    5 -> "다섯"
                    else -> ""
                }

                time += "째주"
            }

            txt_splash_info_lower_time.setText(String.format(getString(R.string.txt_splash_main_info_lower_time, time)))

        } else {
            txt_splash_top.setText(R.string.txt_splash_main_without_login)
            txt_splash_bottom.text = "52”"
            txt_splash_info_lower_time.visibility = View.GONE
        }
    }

    private fun isLogin() = FiftyTwoHoursApplication.getSettingManager().userId != ""
}
