package com.example.uds.helpers.firebase_helper

import com.example.uds.models.Schedule
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FirebaseDatabaseHelper {
    private var database = FirebaseDatabase.getInstance()
    private var reference = database.reference
    private var schedules = mutableListOf<Schedule>()

    constructor() {
        fun readSchedules(): MutableList<Schedule> {
            reference.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (item in dataSnapshot.children) {
                        val schedule = item.getValue(Schedule::class.java)
                        schedule?.let { schedules.add(it) }
                    }
                }
            })
            return schedules
        }
    }
}