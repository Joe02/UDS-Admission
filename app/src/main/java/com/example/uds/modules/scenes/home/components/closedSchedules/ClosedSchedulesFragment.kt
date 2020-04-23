package com.example.uds.modules.scenes.home.components.closedSchedules

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uds.R
import com.example.uds.databinding.FragmentClosedSchedulesBinding
import com.example.uds.models.Schedule
import com.example.uds.modules.scenes.home.components.ScheduleAdapter

class ClosedSchedulesFragment : Fragment() {

    private lateinit var closedSchedulesBinding: FragmentClosedSchedulesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        closedSchedulesBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_closed_schedules, container, false)

        lateinit var schedule1: Schedule
        val schedules: MutableList<Schedule> = mutableListOf()

        for (number in 1..10) {
            schedule1 = Schedule(
                "Teste $number",
                "Lorem ipsum riling rawling rawlers chama no reskein do mectrab FOOOOOON",
                "Joe, o Grande",
                false
            )
            schedules.add(schedule1)
        }

        closedSchedulesBinding.schedulesRecycler.layoutManager = LinearLayoutManager(context)
        closedSchedulesBinding.schedulesRecycler.setHasFixedSize(true)
        closedSchedulesBinding.schedulesRecycler.adapter =
            context?.let {
                ScheduleAdapter(
                    schedules,
                    "Closed",
                    it
                )
            }

        return closedSchedulesBinding.root
    }
}