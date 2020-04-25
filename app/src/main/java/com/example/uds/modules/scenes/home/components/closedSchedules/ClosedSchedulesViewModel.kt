package com.example.uds.modules.scenes.home.components.closedSchedules

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.uds.helpers.firebase_helper.FirebaseDatabaseHelper
import com.example.uds.models.Schedule
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.concurrent.CountDownLatch

class ClosedSchedulesViewModel : ViewModel() {

    private var closedSchedules = MutableLiveData<MutableList<Schedule>>(mutableListOf())

    fun loadClosedSchedules(): MutableList<Schedule>? {
        val done = CountDownLatch(1)
        GlobalScope.launch {
            val response = FirebaseDatabaseHelper().getClosedSchedules()
            MainScope().launch {
                closedSchedules.value = response
                done.countDown()
            }
        }
        try {
            done.await()
        } catch (e: Exception) {

        }
        return closedSchedules.value
    }
}