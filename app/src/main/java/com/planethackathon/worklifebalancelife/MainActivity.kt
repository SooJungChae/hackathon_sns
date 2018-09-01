package com.planethackathon.worklifebalancelife

import android.os.Bundle
import android.support.v4.view.PagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    val mAdapter: PagerAdapter by lazy { MainPagerAdapter(this, supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewpager_main.adapter = mAdapter
        tablayout_main.setupWithViewPager(viewpager_main)
    }
}
