package com.example.uds.modules.scenes.createSchedule

import androidx.lifecycle.ViewModel
import com.example.uds.helpers.firebase_helper.FirebaseDatabaseHelper
import com.example.uds.models.Schedule

class ScheduleCreationViewModel : ViewModel() {

    fun createNewSchedule(schedule: Schedule) {
        FirebaseDatabaseHelper().addSchedule(schedule)
    }
}