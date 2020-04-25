package com.example.uds.modules.scenes.login.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.uds.R
import com.example.uds.databinding.FragmentPasswordRecoveryBinding
import com.google.firebase.auth.FirebaseAuth

class PasswordRecoveryFragment : Fragment() {

    private lateinit var recoveryBinding : FragmentPasswordRecoveryBinding
    private var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Disable callback on PasswordRecoveryFragment
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) { }
        callback.isEnabled
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        recoveryBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_password_recovery, container, false)

        setUpListeners()

        return recoveryBinding.root
    }

    private fun setUpListeners() {
        recoveryBinding.navUp.setOnClickListener {
            view?.let { it ->
                Navigation.findNavController(it)
                    .navigate(R.id.action_passwordRecoveryFragment_to_loginFragment)
            }
        }

        recoveryBinding.submitButton.setOnClickListener {
            auth.sendPasswordResetEmail(recoveryBinding.emailField.editText?.text.toString())
            view?.let { it ->
                Navigation.findNavController(it)
                    .navigate(R.id.action_passwordRecoveryFragment_to_loginFragment)
            }
        }
    }
}