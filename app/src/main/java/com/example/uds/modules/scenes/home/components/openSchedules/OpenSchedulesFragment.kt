package com.example.uds.modules.scenes.home.components.openSchedules

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uds.R
import com.example.uds.databinding.FragmentOpenSchedulesBinding
import com.example.uds.models.Schedule
import com.example.uds.modules.scenes.home.components.ScheduleAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class OpenSchedulesFragment : Fragment() {

    private lateinit var openSchedulesBinding: FragmentOpenSchedulesBinding
    private var schedulesList = MutableLiveData<List<Schedule>>(listOf())
    lateinit var viewAdapter : ScheduleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlobalScope.launch {
            getSchedulesFromDatabase()
        }
    }

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

        viewAdapter = schedulesList.value?.let { context?.let { context ->
            ScheduleAdapter(it, "Open",
                context
            )
        } }!!

        val observer = Observer<Any> {
            viewAdapter.notifyDataSetChanged()
        }

        schedulesList.observe(viewLifecycleOwner, observer)

        openSchedulesBinding.schedulesRecycler.layoutManager = LinearLayoutManager(context)
        openSchedulesBinding.schedulesRecycler.setHasFixedSize(true)
        openSchedulesBinding.schedulesRecycler.adapter = viewAdapter

        return openSchedulesBinding.root
    }

    private fun getSchedulesFromDatabase() {
        GlobalScope.launch {
            val schedules = OpenSchedulesViewModel().readSchedules()
            MainScope().launch {
                schedulesList.value = schedules
            }
        }
    }
}