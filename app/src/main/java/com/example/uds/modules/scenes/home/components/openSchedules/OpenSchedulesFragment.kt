package com.example.uds.modules.scenes.home.components.openSchedules

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
    private var openSchedulesList = MutableLiveData<MutableList<Schedule>>(mutableListOf())
    lateinit var viewAdapter : ScheduleAdapter
    val model: OpenSchedulesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        openSchedulesBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_open_schedules, container, false)

        openSchedulesList.value = mutableListOf()

        return openSchedulesBinding.root
    }

    fun getData() {
        GlobalScope.launch {

            val response = model.loadOpenSchedules()

            MainScope().launch {

                openSchedulesList.value = response
                viewAdapter =
                    context?.let {openSchedulesList.value?.let { it1 ->
                        ScheduleAdapter(it1, "Open", it )
                    }
                    }!!
                openSchedulesBinding.schedulesRecycler.layoutManager = LinearLayoutManager(context)
                openSchedulesBinding.schedulesRecycler.setHasFixedSize(true)
                openSchedulesBinding.schedulesRecycler.adapter = viewAdapter
            }
        }
    }
}