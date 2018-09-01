package com.planethackathon.worklifebalancelife.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.planethackathon.worklifebalancelife.R

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
        }
        return mContainer
    }

    companion object {
        @JvmStatic
        fun newInstance() = LifeFragment()
    }
}
