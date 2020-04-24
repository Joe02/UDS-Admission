package com.example.uds.helpers.firebase_helper

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.uds.models.Schedule
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FirebaseDatabaseHelper {
    private var database = FirebaseDatabase.getInstance()
    var schedules = MutableLiveData<List<Schedule>>(mutableListOf())
    private var reference = database.reference
    private var schedulesList = mutableListOf<Schedule>()

    fun readSchedules(): List<Schedule>? {
        reference = FirebaseDatabase.getInstance().getReference("schedules")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
               Log.d("error", "Falha ao ler arquivos do banco")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (item in dataSnapshot.children) {
                    val schedule = item.getValue(Schedule::class.java)
                    schedule?.let { schedulesList.add(it) }
                    schedules.value = schedulesList
                    }
                }
            })
            return schedules.value
        }

    fun addSchedule(schedule: Schedule) {
        val key: String = reference.push().key.toString()
        reference.child("schedules").child(key).setValue(schedule).addOnSuccessListener { }
    }
}