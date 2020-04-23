package com.example.uds.modules.scenes.createSchedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.uds.R
import com.example.uds.databinding.FragmentScheduleCreationBinding

class ScheduleCreationForm : Fragment() {

    private lateinit var scheduleFormBinding : FragmentScheduleCreationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        scheduleFormBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_schedule_creation, container, false)

        return scheduleFormBinding.root
    }
}