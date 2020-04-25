package com.example.uds.modules.scenes.createSchedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.uds.R
import com.example.uds.databinding.FragmentScheduleCreationBinding
import com.example.uds.models.Schedule
import com.google.firebase.auth.FirebaseAuth

class ScheduleCreationFormFragment : Fragment() {

    private lateinit var scheduleFormBinding : FragmentScheduleCreationBinding
    private lateinit var auth: FirebaseAuth
    private val model : ScheduleCreationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        scheduleFormBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_schedule_creation, container, false)

        scheduleFormBinding.scheduleAuthorFormInput.setText(auth.currentUser?.displayName.toString())
        setUpListeners()

        return scheduleFormBinding.root
    }

    private fun setUpListeners() {

        scheduleFormBinding.createSchedule.setOnClickListener {
            var validation = true

            if (scheduleFormBinding.scheduleAuthorFormInput.text.isNullOrEmpty()) {
                scheduleFormBinding.scheduleAuthorFormLayout.error = context?.getString(R.string.nonNullField)
                validation = false
            } else {
                scheduleFormBinding.scheduleAuthorFormLayout.isErrorEnabled = false
            }

            if (scheduleFormBinding.scheduleDescriptionFormInput.text.isNullOrEmpty()) {
                scheduleFormBinding.scheduleDescriptionFormLayout.error = context?.getString(R.string.nonNullField)
                validation = false
            } else {
                scheduleFormBinding.scheduleDescriptionFormLayout.isErrorEnabled = false
            }

            if (scheduleFormBinding.scheduleLongDescriptionFormInput.text.isNullOrEmpty()) {
                scheduleFormBinding.scheduleLongDescriptionFormLayout.error = context?.getString(R.string.nonNullField)
                validation = false
            } else {
                scheduleFormBinding.scheduleLongDescriptionFormLayout.isErrorEnabled = false
            }

            if (scheduleFormBinding.scheduleTitleFormInput.text.isNullOrEmpty()) {
                scheduleFormBinding.scheduleTitleFormLayout.error = context?.getString(R.string.nonNullField)
                validation = false
            } else {
                scheduleFormBinding.scheduleTitleFormLayout.isErrorEnabled = false
            }

            if (validation) {
                val schedule = Schedule(
                    "",
                    scheduleFormBinding.scheduleTitleFormInput.text.toString(),
                    scheduleFormBinding.scheduleDescriptionFormInput.text.toString(),
                    scheduleFormBinding.scheduleLongDescriptionFormInput.text.toString(),
                    scheduleFormBinding.scheduleAuthorFormInput.text.toString(),
                    false
                )
                model.createNewSchedule(schedule)
                view?.let { it ->
                    Navigation.findNavController(it)
                        .navigate(R.id.action_scheduleCreationForm_to_homePageFragment)
                }
            }
        }

        scheduleFormBinding.navUp.setOnClickListener {
            view?.let { it ->
                Navigation.findNavController(it)
                    .navigate(R.id.action_scheduleCreationForm_to_homePageFragment)
            }
        }
    }
}