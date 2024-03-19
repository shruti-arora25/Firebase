package com.example.firebasetwo

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.firebasetwo.databinding.FragmentSignInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.firestore
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class signInFrag : Fragment() {
    private lateinit var bind: FragmentSignInBinding
    private lateinit var fbAuth: FirebaseAuth
    private lateinit var signinC: GoogleSignInClient

    var firestore = Firebase.firestore


    private var launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

            if (it.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                updateUI(task.result)
            }

        }


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
            var hashPw=hashPassword(password)
            if (email.isNotEmpty() && password.isNotEmpty()) {
                fbAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        save(email, hashPw)
                    }
                }

            }

        }
        val signinO = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.clientId))
            .requestEmail()
            .build()

        bind.google.setOnClickListener {
            signinC = GoogleSignIn.getClient(requireContext(), signinO)
            val signinIntent=signinC.signInIntent
            launcher.launch(signinIntent)

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

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        fbAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                val currentUser = fbAuth.currentUser
                if (currentUser != null) {
                    val userName = currentUser.displayName
                    val email = currentUser.email
                    val time = Date().time

                    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    val formatDate = sdf.format(Date(time))

                    save(userName, email, formatDate)
                }
            }
        }
    }

    private fun save(userName: String?, email: String?, formatDate: String) {
        val map = hashMapOf(
            "name" to userName,
            "emailId" to email,
            "LogIn Time" to formatDate
        )
        firestore.collection("Login Details").document("Details").set(map)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    findNavController().navigate(R.id.action_signInFrag_to_homeFrag2,null,NavOptions.Builder().setPopUpTo(R.id.signInFrag,true).build())

                }
            }
    }

    fun hashPassword(password: String): String {
        val bytes = password.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("", { str, it -> str + "%02x".format(it) })
    }

}



