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
import java.util.regex.Pattern

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

        loginBinding.forgotPasswordOption.setOnClickListener {
            view?.let { it ->
                Navigation.findNavController(it)
                    .navigate(R.id.action_loginFragment_to_passwordRecoveryFragment)
            }
        }

        loginBinding.registerButton.setOnClickListener {
            view?.let { it ->
                Navigation.findNavController(it)
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
            ).matches() && loginBinding.emailField.text?.isNotEmpty()!!
        ) {
            validation = false
        } else {
            if (!validateEmail(loginBinding.emailField.text.toString())) {
                loginBinding.loginInputLayout.error = getString(R.string.emailWrong)
            } else {
                loginBinding.loginInputLayout.isErrorEnabled = false
            }
        }

        if (loginBinding.passwordField.text.toString().length <= 5) {
            validation = false
            loginBinding.passwordInputLayout.error = getString(R.string.passwordError)
        } else {
            loginBinding.passwordInputLayout.isErrorEnabled = false
        }

            if (validation) {
            //TODO Login
            view?.let { it ->
                Navigation.findNavController(it)
                    .navigate(R.id.action_loginFragment_to_homePageFragment)
            }
        }
    }

    fun validateEmail(email: String): Boolean {
        val validationString = ("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$")

        val emailCharSequence: CharSequence = email
        val emailPattern = Pattern.compile(validationString, Pattern.CASE_INSENSITIVE)
        val emailMatcher = emailPattern.matcher(emailCharSequence)

        return emailMatcher.matches()
    }
}