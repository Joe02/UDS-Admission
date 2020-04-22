package com.example.uds.modules.scenes.login

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.uds.R
import com.example.uds.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var loginBinding : FragmentLoginBinding
    var buttonAnimation : AlphaAnimation = AlphaAnimation(10F, 0.1F)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        loginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        setListeners()

        return loginBinding.root
    }

    private fun setListeners() {

        loginBinding.registerButton.setOnClickListener {
            view?.let { it1 ->
                Navigation.findNavController(it1)
                    .navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }

        loginBinding.submitButton.setOnClickListener {
            submit()
        }
    }

    fun submit() {
        var validation = true
        if (TextUtils.isEmpty(loginBinding.emailField.text) && !android.util.Patterns.EMAIL_ADDRESS.matcher(
                loginBinding.emailField.text.toString()
            ).matches()
        ) {
            validation = false
            Toast.makeText(context, "Endereço de Email ou senha inválidos.", Toast.LENGTH_SHORT).show()
        }

        if (loginBinding.passwordField.text.toString().length <= 5) {
            validation = false
            Toast.makeText(context, "Endereço de Email ou senha inválidos.", Toast.LENGTH_SHORT).show()
        }

        if (validation) {
            //TODO Login

        }
    }
}