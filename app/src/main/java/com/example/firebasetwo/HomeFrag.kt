package com.example.firebasetwo

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.firebasetwo.databinding.FragmentHomeBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class HomeFrag : Fragment() {

    private lateinit var bind: FragmentHomeBinding
    private lateinit var fbAuth: FirebaseAuth
    private lateinit var signInC: GoogleSignInClient

    private val args: HomeFragArgs by navArgs()
    private var emailAddress=""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentHomeBinding.inflate(layoutInflater, container, false)
        fbAuth = FirebaseAuth.getInstance()


        val signinO = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.clientId))
            .requestEmail()
            .build()


        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)?:return bind.root


        Log.d("TAG------------>,",emailAddress)

        val isLogin = sharedPref.getString("emailAddress", "1")

         emailAddress = args.emailId

        if (isLogin == "1") {
            if (fbAuth.currentUser != null) {
                with(sharedPref.edit())
                {
                    putString("userEmail", emailAddress)
                    apply()
                }
            } else {
                findNavController().navigate(
                    R.id.action_homeFrag2_to_registerFrag2,
                    null,
                    NavOptions.Builder().setPopUpTo(R.id.homeFrag2, true).build()
                )

            }
        } else {

            findNavController().navigate(R.id.homeFrag2)

        }

        signInC = GoogleSignIn.getClient(requireContext(), signinO)

        bind.SIGNOUTT.setOnClickListener {

            sharedPref.edit().remove("userEmail").apply()

                findNavController().navigate(
                    R.id.action_homeFrag2_to_signInFrag,
                    null,
                    NavOptions.Builder().setPopUpTo(R.id.homeFrag2, true).build()
                )


            }

            signInC.signOut().addOnCompleteListener {

                if (it.isSuccessful) {

                    fbAuth.signOut()
                    findNavController().navigate(
                        R.id.action_homeFrag2_to_signInFrag,
                        null,
                        NavOptions.Builder().setPopUpTo(R.id.homeFrag2, true).build()
                    )
                }

            }




        return bind.root

    }
}