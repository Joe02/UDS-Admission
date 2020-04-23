package com.example.uds.modules.scenes.login

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import java.util.regex.Pattern


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class RegisterFragment : Fragment() {

    private lateinit var registerBinding : FragmentRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        auth = FirebaseAuth.getInstance()
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

        if (validation) {
            auth.createUserWithEmailAndPassword(
                registerBinding.emailField.text.toString(),
                registerBinding.passwordField.text.toString()
            ).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        context,
                        context?.getString(R.string.invalidEmailOrPassword),
                        Toast.LENGTH_SHORT
                    ).show()
                    auth.signInWithEmailAndPassword(
                        registerBinding.emailField.text.toString(),
                        registerBinding.passwordField.text.toString()
                    )
                } else {
                    try {
                        throw task.exception!!
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        registerBinding.passwordInputLayout.error = getString(R.string.weakPasswordException)
                        registerBinding.passwordInputLayout.requestFocus()
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        registerBinding.loginInputLayout.error = getString(R.string.invalidEmail)
                        registerBinding.loginInputLayout.requestFocus()
                    } catch (e: FirebaseAuthUserCollisionException) {
                        registerBinding.loginInputLayout.error = getString(R.string.userAlreadySignedUp)
                        registerBinding.loginInputLayout.requestFocus()
                    } catch (e: Exception) {
                        Log.e(TAG, e.message)
                    }
                }
            }
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

    private fun validateEmail(email: String): Boolean {
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