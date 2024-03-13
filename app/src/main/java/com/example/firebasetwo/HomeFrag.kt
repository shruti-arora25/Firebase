package com.example.firebasetwo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firebasetwo.databinding.FragmentHomeBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class HomeFrag : Fragment() {

    private lateinit var bind: FragmentHomeBinding
    private lateinit var fbAuth:FirebaseAuth
    private lateinit var signInC:GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentHomeBinding.inflate(layoutInflater, container, false)
        fbAuth=FirebaseAuth.getInstance()
        val signinO=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.clientId))
            .requestEmail()
            .build()


        signInC= GoogleSignIn.getClient(requireContext(),signinO)

        bind.SIGNOUTT.setOnClickListener {

            fbAuth.signOut()


        }


        return bind.root

    }
}