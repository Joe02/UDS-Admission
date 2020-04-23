package com.example.uds.modules.scenes.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import com.example.uds.R
import com.example.uds.databinding.FragmentHomePageBinding
import com.example.uds.modules.scenes.home.components.closedSchedules.ClosedSchedulesFragment
import com.example.uds.modules.scenes.home.components.openSchedules.OpenSchedulesFragment
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth

class HomePageFragment : Fragment() {

    private lateinit var homeBinding : FragmentHomePageBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        homeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_page, container, false)

        setListeners()
        openSchedules()
        homeBinding.userName.text = auth.currentUser?.displayName

        return homeBinding.root
    }

    private fun setListeners() {

        homeBinding.addSchedule.setOnClickListener{
            createSchedule()
        }

        homeBinding.pageSelector.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) { }

            override fun onTabUnselected(tab: TabLayout.Tab?) { }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position) {
                    0 -> { openSchedules() }
                    1 -> { closedSchedules() }
                }
            }
        })
    }

    private fun openSchedules() {
        val transaction: FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.home_fragment,
            OpenSchedulesFragment()
        )
        transaction?.addToBackStack(null)
        transaction?.commit()
    }

    private fun closedSchedules() {
        val transaction: FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.home_fragment,
            ClosedSchedulesFragment()
        )
        transaction?.addToBackStack(null)
        transaction?.commit()
    }

    private fun createSchedule() {
        view?.let { it ->
            Navigation.findNavController(it)
                .navigate(R.id.action_homePageFragment_to_scheduleCreationForm)
        }
    }
}