package com.planethackathon.worklifebalancelife

import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    val mAdapter: PagerAdapter by lazy { MainPagerAdapter(this, supportFragmentManager) }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewpager_main.adapter = mAdapter

        viewpager_main.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {
            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        onSelectWork()
                    }

                    1 -> {
                        onSelectLife()
                    }

                    2 -> {
                        container_main_title.visibility = View.GONE
                    }
                }
            }
        })

        onSelectWork()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun onSelectWork() {
        container_main_title.visibility = View.VISIBLE
        txt_work_main.apply {
            setTypeface(null, Typeface.BOLD);
            setTextColor(getColor(R.color.black))
        }
        txt_life_main.apply {
            setTypeface(null, Typeface.NORMAL);
            setTextColor(getColor(R.color.gray))
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun onSelectLife() {
        container_main_title.visibility = View.VISIBLE
        txt_life_main.apply {
            setTypeface(null, Typeface.BOLD);
            setTextColor(getColor(R.color.black))
        }
        txt_work_main.apply {
            setTypeface(null, Typeface.NORMAL);
            setTextColor(getColor(R.color.gray))
        }
    }
}
