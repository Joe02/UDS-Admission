package com.example.uds.modules.scenes.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.uds.R
import com.example.uds.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var registerBinding : FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        registerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)

        return registerBinding.root
    }
}