package com.example.uds.helpers.firebase_helper

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.uds.models.Schedule
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception
import java.util.*
import java.util.concurrent.CountDownLatch

class FirebaseDatabaseHelper : Observable() {
    private var database = FirebaseDatabase.getInstance()
    private var reference = database.getReference("schedules")
    private var schedulesList = MutableLiveData<MutableList<Schedule>>(mutableListOf())

    fun getOpenSchedules(): MutableList<Schedule>? {
        val done = CountDownLatch(1)
        reference.child("open").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                schedulesList.value = mutableListOf()
                if (p0.exists()) {
                    for (item in p0.children) {
                        val schedule = item.getValue(Schedule::class.java)
                        schedule?.let { schedulesList.value!!.add(it) }
                        done.countDown()
                    }
                    schedulesList.value
                }
            }
        })
        try {
            done.await()
        }catch (e: Exception){

        }
        return schedulesList.value
    }

    fun getClosedSchedules(): MutableList<Schedule>? {
        val done = CountDownLatch(1)
        reference.child("closed").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                schedulesList.value = mutableListOf()
                if (p0.exists()) {
                    for (item in p0.children) {
                        val schedule = item.getValue(Schedule::class.java)
                        schedule?.let { schedulesList.value!!.add(it) }
                        done.countDown()
                    }
                    schedulesList.value
                }
            }
        })
        try {
            done.await()
        }catch (e: Exception){

        }
        return schedulesList.value
    }


    fun addSchedule(schedule: Schedule) {
        val key: String
        if (schedule.id.isNotEmpty()) {
            key = schedule.id
            reference.child("closed").child(schedule.id).removeValue()
        } else {
            key = reference.push().key.toString()
            schedule.id = key
        }
        reference.child("open").child(key).setValue(schedule)
    }

    fun closeSchedule(schedule: Schedule) {
        reference.child("open").child(schedule.id).removeValue()
        reference.child("closed").child(schedule.id).setValue(schedule)
    }

    fun removeSchedule(schedule: Schedule) {
        reference.child("open").child(schedule.id).removeValue()
        reference.child("closed").child(schedule.id).removeValue()
    }
}