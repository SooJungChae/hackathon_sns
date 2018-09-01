package com.planethackathon.worklifebalancelife.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.planethackathon.worklifebalancelife.R

class WorkFragment : Fragment() {
    var mContainer: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        if (mContainer == null) {
            mContainer = inflater.inflate(R.layout.fragment_work, container, false)
        }

        return mContainer
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WorkFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = WorkFragment()
    }
}
