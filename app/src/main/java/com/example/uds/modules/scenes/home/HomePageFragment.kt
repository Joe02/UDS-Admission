package com.example.uds.modules.scenes.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uds.R
import com.example.uds.databinding.FragmentHomePageBinding
import com.example.uds.models.Schedule

class HomePageFragment : Fragment() {

    private lateinit var homeBinding : FragmentHomePageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        homeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_page, container, false)

        lateinit var schedule1 : Schedule
        var schedules : MutableList<Schedule> = mutableListOf()

        for (number in 1..10) {
            schedule1 = Schedule("Teste $number")
            schedules.add(schedule1)
        }

        homeBinding.schedulesRecycler.layoutManager = LinearLayoutManager(context)
        homeBinding.schedulesRecycler.setHasFixedSize(true)
        homeBinding.schedulesRecycler.adapter = ScheduleAdapter(schedules)

        return homeBinding.root
    }
}