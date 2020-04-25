package com.example.uds.modules.scenes.home.components.closedSchedules

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.ViewSkeletonScreen
import com.example.uds.R
import com.example.uds.databinding.FragmentClosedSchedulesBinding
import com.example.uds.models.Schedule
import com.example.uds.modules.scenes.home.components.ScheduleAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ClosedSchedulesFragment : Fragment() {

    private lateinit var closedSchedulesBinding: FragmentClosedSchedulesBinding
    private var closedSchedulesList = MutableLiveData<MutableList<Schedule>>(mutableListOf())
    private lateinit var viewAdapter : ScheduleAdapter
    private val model: ClosedSchedulesViewModel by viewModels()
    private lateinit var skeleton: ViewSkeletonScreen

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
        closedSchedulesBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_closed_schedules, container, false)

        skeleton = Skeleton
            .bind(closedSchedulesBinding.skeleton)
            .load(R.layout.item_skeleton_card)
            .shimmer(true)
            .show()

        return closedSchedulesBinding.root
    }

    private fun getData() {
        GlobalScope.launch {

            val response = model.loadClosedSchedules()

            MainScope().launch {
                skeleton.hide()
                closedSchedulesList.value = response
                if (response?.size == 0) {
                    closedSchedulesBinding.noSchedulesClosed.visibility = View.VISIBLE
                }
                viewAdapter =
                    context?.let {closedSchedulesList.value?.let { it1 ->
                        ScheduleAdapter(it1, "Closed", it )
                    }
                    }!!
                closedSchedulesBinding.schedulesRecycler.layoutManager = LinearLayoutManager(context)
                closedSchedulesBinding.schedulesRecycler.setHasFixedSize(true)
                closedSchedulesBinding.schedulesRecycler.adapter = viewAdapter
            }
        }
    }
}