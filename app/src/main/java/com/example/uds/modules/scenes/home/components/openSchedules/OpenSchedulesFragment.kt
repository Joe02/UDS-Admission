package com.example.uds.modules.scenes.home.components.openSchedules

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uds.R
import com.example.uds.databinding.FragmentOpenSchedulesBinding
import com.example.uds.models.Schedule
import com.example.uds.modules.scenes.home.components.ScheduleAdapter

class OpenSchedulesFragment : Fragment() {

    private lateinit var openSchedulesBinding: FragmentOpenSchedulesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        openSchedulesBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_open_schedules, container, false)

        lateinit var schedule1: Schedule
        val schedules: MutableList<Schedule> = mutableListOf()

        for (number in 1..10) {
            schedule1 = Schedule(
                "Teste $number",
                "Short description",
                "Joe",
                "Long description",
                false
            )
            schedules.add(schedule1)
        }

        openSchedulesBinding.schedulesRecycler.layoutManager = LinearLayoutManager(context)
        openSchedulesBinding.schedulesRecycler.setHasFixedSize(true)
        openSchedulesBinding.schedulesRecycler.adapter =
            context?.let {
                ScheduleAdapter(
                    schedules,
                    "Open",
                    it
                )
            }

        return openSchedulesBinding.root
    }
}