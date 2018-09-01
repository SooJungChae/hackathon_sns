package com.planethackathon.worklifebalancelife.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.planethackathon.worklifebalancelife.R
import com.planethackathon.worklifebalancelife.R.id.btn_test
import com.planethackathon.worklifebalancelife.TestActivity
import kotlinx.android.synthetic.main.fragment_work.*

class WorkFragment : Fragment() {
    var mContainer: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        if (mContainer == null) {
            mContainer = inflater.inflate(R.layout.fragment_work, container, false)

            btn_test.setOnClickListener { val intent = Intent(activity, TestActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent) }
        }

        return mContainer
    }


    companion object {
        @JvmStatic
        fun newInstance() = WorkFragment()
    }
}
