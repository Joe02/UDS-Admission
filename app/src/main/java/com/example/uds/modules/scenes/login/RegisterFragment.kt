package com.example.uds.modules.scenes.login

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.uds.R
import com.example.uds.databinding.FragmentRegisterBinding
import java.util.regex.Pattern

class RegisterFragment : Fragment() {

    private lateinit var registerBinding : FragmentRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        registerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)

        val activity = (requireActivity() as AppCompatActivity)
        activity.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)

        setUpListeners()

        return registerBinding.root
    }

    private fun setUpListeners() {
        registerBinding.backToLogin.setOnClickListener {
            view?.let { it ->
                Navigation.findNavController(it)
                    .navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }

        registerBinding.submitButton.setOnClickListener { submit() }
    }

    private fun submit() {
        var validation = true
        if (registerBinding.usernameField.text.isNullOrEmpty()) {
            registerBinding.usernameInputLayout.error = getString(R.string.nonNullField)
        }

        if (TextUtils.isEmpty(registerBinding.emailField.text) && !android.util.Patterns.EMAIL_ADDRESS.matcher(
                registerBinding.emailField.text.toString()
            ).matches() && registerBinding.emailField.text?.isNotEmpty()!!
        ) {
            validation = false
        } else {
            if (!validateEmail(registerBinding.emailField.text.toString())) {
                registerBinding.loginInputLayout.error = getString(R.string.emailWrong)
            } else {
                registerBinding.loginInputLayout.isErrorEnabled = false
            }
        }

        if (registerBinding.passwordField.text.toString().length <= 5) {
            validation = false
            registerBinding.passwordInputLayout.error = getString(R.string.passwordError)
        } else {
            registerBinding.passwordInputLayout.isErrorEnabled = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigateUp()
                true
            }

            else -> super.onOptionsItemSelected(item)
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