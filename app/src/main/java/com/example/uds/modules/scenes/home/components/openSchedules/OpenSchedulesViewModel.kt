package com.example.uds.modules.scenes.home.components.openSchedules

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.uds.helpers.firebase_helper.FirebaseDatabaseHelper
import com.example.uds.models.Schedule
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.concurrent.CountDownLatch

class OpenSchedulesViewModel : ViewModel() {

    private var openSchedules = MutableLiveData<MutableList<Schedule>>(mutableListOf())

    fun loadOpenSchedules(): MutableList<Schedule>? {
        val done = CountDownLatch(1)
        GlobalScope.launch {
            val response = FirebaseDatabaseHelper().getOpenSchedules()
            MainScope().launch {
                openSchedules.value = response
                done.countDown()
            }
        }
        try {
            done.await()
        } catch (e: Exception) {

        }
        return openSchedules.value
    }
}