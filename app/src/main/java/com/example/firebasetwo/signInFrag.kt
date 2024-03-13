package com.example.firebasetwo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.firebasetwo.databinding.FragmentSignInBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class signInFrag : Fragment() {
    private lateinit var bind: FragmentSignInBinding
    private lateinit var fbAuth: FirebaseAuth

    var firestore = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentSignInBinding.inflate(layoutInflater, container, false)
        fbAuth = FirebaseAuth.getInstance()


        bind.signIn.setOnClickListener {

            var email = bind.enailLogin.text.toString()
            var password = bind.passwordLogin.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                fbAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        save(email, password)
                    }
                }

            }

        }




        return bind.root

    }

    private fun save(userName: String?, userEmail: String?) {


        val map = hashMapOf(
            "UserEmail" to userName,
            "UserPass" to userEmail
        )
        firestore.collection("DetailsCustom").document("UserInfo").set(map).addOnSuccessListener {
            findNavController().navigate(
                R.id.action_signInFrag_to_homeFrag2,
                null,
                NavOptions.Builder().setPopUpTo(R.id.signInFrag, true).build()
            )

        }
            .addOnFailureListener {


            }
    }
}

