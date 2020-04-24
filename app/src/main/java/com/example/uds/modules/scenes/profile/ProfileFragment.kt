package com.example.uds.modules.scenes.profile

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.uds.R
import com.example.uds.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private lateinit var profileBinding: FragmentProfileBinding
    private var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlobalScope.launch {
            MainScope().launch {
                auth = FirebaseAuth.getInstance()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        profileBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        setUpListeners()
        profileBinding.userName.hint = auth.currentUser?.displayName.toString()
        profileBinding.userEmail.text = auth.currentUser?.email

        return profileBinding.root
    }

    private fun setUpListeners() {
        profileBinding.navUp.setOnClickListener {
            view?.let { it ->
                Navigation.findNavController(it)
                    .navigate(R.id.action_profileFragment_to_homePageFragment)
            }
        }

        profileBinding.logoff.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setMessage(getString(R.string.logoutConfirmationWarning))
                .setTitle(getString(R.string.logoutConfirmation)).setCancelable(true)

            builder.setPositiveButton("Sim") {
                    dialog, which -> auth.signOut()
                view?.let { it ->
                    Navigation.findNavController(it)
                        .navigate(R.id.action_profileFragment_to_loginFragment)
                }

            }

            builder.setNegativeButton("Não") {
                    dialog, which -> dialog.cancel()
            }

            val alertDialog = builder.create()
            alertDialog.show()
        }
    }

    private fun setUserName() {
        val user = FirebaseAuth.getInstance().currentUser
        val profileUpdate = UserProfileChangeRequest.Builder()
            .setDisplayName(profileBinding.userName.text.toString()).build()

        user?.updateProfile(profileUpdate)
    }
}