package com.example.firebasetwo

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.firebasetwo.databinding.FragmentRegisterBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.signin.SignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.firestore

class RegisterFrag : Fragment() {

    private lateinit var bind:FragmentRegisterBinding
    private lateinit var fbAuth:FirebaseAuth
    private lateinit var signInC:GoogleSignInClient

    private val launcher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){



        if (it.resultCode==Activity.RESULT_OK){
            val task=GoogleSignIn.getSignedInAccountFromIntent(it.data)

            updateUI(task.result)

        }
    }
    val firestore=Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bind = FragmentRegisterBinding.inflate(layoutInflater, container, false)


        fbAuth = FirebaseAuth.getInstance()
        val signInO = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.clientId))
            .requestEmail()
            .build()

        bind.GoogleClick.setOnClickListener {
            signInC = GoogleSignIn.getClient(requireActivity(), signInO)
            val signinintent = signInC.signInIntent

            launcher.launch(signinintent)



        }

        return bind.root
    }

    private fun updateUI(account:GoogleSignInAccount){

        val credential=GoogleAuthProvider.getCredential(account.idToken,null)
        fbAuth.signInWithCredential(credential).addOnCompleteListener {

            if (it.isSuccessful){
                val user=fbAuth.currentUser
                if (user!=null){
                    val name=user.displayName
                    val email=user.email


                    save(name,email)
                }

                Toast.makeText(context,"Successfully Registered",Toast.LENGTH_SHORT).show()
        }
        }
        }



    private fun save(userName:String?,userEmail:String?)
    {


        val map= hashMapOf(
            "UserName" to userName,
            "Email" to userEmail
        )
        firestore.collection("Details").document("User").set(map).addOnSuccessListener {
            findNavController().navigate(R.id.action_registerFrag2_to_homeFrag2,null,NavOptions.Builder().setPopUpTo(R.id.registerFrag2,true).build())

        }
            .addOnFailureListener {


            }
    }
    }
