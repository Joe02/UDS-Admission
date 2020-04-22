package com.example.uds.modules.scenes.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.uds.R
import com.example.uds.databinding.FragmentPasswordRecoveryBinding

class PasswordRecoveryFragment : Fragment() {

    private lateinit var recoveryBinding : FragmentPasswordRecoveryBinding

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

    fun setUpListeners() {
        recoveryBinding.navUp.setOnClickListener {
            view?.let { it ->
                Navigation.findNavController(it)
                    .navigate(R.id.action_passwordRecoveryFragment_to_loginFragment)
            }
        }
    }
}