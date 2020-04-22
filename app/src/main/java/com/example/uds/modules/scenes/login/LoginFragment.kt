package com.example.uds.modules.scenes.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.uds.R
import com.example.uds.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var loginBinding : FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        loginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        return loginBinding.root
    }
}