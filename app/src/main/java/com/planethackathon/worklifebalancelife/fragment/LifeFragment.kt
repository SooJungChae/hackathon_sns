package com.planethackathon.worklifebalancelife.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.planethackathon.worklifebalancelife.R
import com.planethackathon.worklifebalancelife.common.FiftyTwoHoursApplication
import com.planethackathon.worklifebalancelife.common.GlobalUtils

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [LifeFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [LifeFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class LifeFragment : Fragment() {
    var mContainer: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if (mContainer == null) {
            mContainer = inflater.inflate(R.layout.fragment_life, container, false)

            bindElement()
        }
        return mContainer
    }

    private fun bindElement() {
        val setting = FiftyTwoHoursApplication.getSettingManager()
        val txtLifeInfoDetail = mContainer?.findViewById<TextView>(R.id.txt_life_info_detail)

        txtLifeInfoDetail?.text = String.format(getString(R.string.txt_work_info), setting.userName, GlobalUtils.SecToNatureString(setting.userElapsedTime))
    }

    companion object {
        @JvmStatic
        fun newInstance() = LifeFragment()
    }
}
