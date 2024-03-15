package com.example.firebasetwo

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
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
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.firestore
import java.security.MessageDigest
import java.util.concurrent.TimeUnit

class RegisterFrag : Fragment() {

    private lateinit var bind: FragmentRegisterBinding
    private lateinit var fbAuth: FirebaseAuth
    private lateinit var signInC: GoogleSignInClient

    private lateinit var phone: EditText
    private lateinit var number: String
    private lateinit var progressBar: ProgressBar


    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {


            if (it.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)

                updateUI(task.result)

            }
        }
    val firestore = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bind = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        phone = bind.textPhone
        progressBar = bind.progressBar
        progressBar.visibility = View.INVISIBLE


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



        bind.register.setOnClickListener {
            val email = bind.emailSignUp.text.toString()
            val password = bind.passwordSignUp.text.toString()
            val confrmpw = bind.confrmpasswordSignUp.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty() && confrmpw.isNotEmpty()) {
                if (password == confrmpw) {
                    fbAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        if (it.isSuccessful) {

                            val HashPassword = hashPassword(password)
                            saveReg(email, HashPassword)
                        } else {
                            Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }


                }
            }
        }
        bind.AlreadyAccLogin.setOnClickListener {
            findNavController().navigate(
                R.id.action_registerFrag2_to_signInFrag,
                null,
                NavOptions.Builder().setPopUpTo(R.id.registerFrag2, true).build()
            )
        }




        bind.sendOtp.setOnClickListener {
            number = phone.text.trim().toString()
            if (number.isNotEmpty()) {
                if (number.length == 10) {
                    number = "+91${number}"
                    progressBar.visibility = View.VISIBLE
                    val options = PhoneAuthOptions.newBuilder(fbAuth)
                        .setPhoneNumber(number) // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(requireActivity()) // Activity (for callback binding)
                        .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
                        .build()
                    PhoneAuthProvider.verifyPhoneNumber(options)
                    Log.d("Tag-------","Clicked")

                } else {
                    Toast.makeText(context, "Enter 10 digits number", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Enter the number", Toast.LENGTH_SHORT).show()
            }
        }



        return bind.root
    }


    private fun updateUI(account: GoogleSignInAccount) {

        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        fbAuth.signInWithCredential(credential).addOnCompleteListener {

            if (it.isSuccessful) {
                val user = fbAuth.currentUser
                if (user != null) {
                    val name = user.displayName
                    val email = user.email


                    save(name, email)
                }

                Toast.makeText(context, "Successfully Registered", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun save(userName: String?, userEmail: String?) {


        val map = hashMapOf(
            "UserName" to userName,
            "Email" to userEmail
        )
        firestore.collection("Registration").document("Google").set(map).addOnSuccessListener {
            findNavController().navigate(
                R.id.action_registerFrag2_to_homeFrag2,
                null,
                NavOptions.Builder().setPopUpTo(R.id.registerFrag2, true).build()
            )

        }
            .addOnFailureListener {


            }
    }

    private fun saveReg(email: String, password: String) {

        val maps = hashMapOf(
            "EmailAddress" to email,
            "Password" to password
        )
        firestore.collection("Registration").document("CustomReg").set(maps).addOnSuccessListener {
            Toast.makeText(context, "Registered Successfullly", Toast.LENGTH_SHORT).show()
            findNavController().navigate(
                R.id.action_registerFrag2_to_signInFrag,
                null,
                NavOptions.Builder().setPopUpTo(R.id.registerFrag2, true).build()
            )

        }
    }

    fun hashPassword(password: String): String {
        val bytes = password.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("", { str, it -> str + "%02x".format(it) })
    }

    private var callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {

            Log.d("Tag------->", "vfComple")
            signInWithPhoneCredentials(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            if (e is FirebaseAuthInvalidCredentialsException) {
                Log.d("Tag------->", "failed")

                Toast.makeText(context, "Verification failed due to " + e.toString(), Toast.LENGTH_SHORT)
                    .show()
            } else if (e is FirebaseTooManyRequestsException) {
                Toast.makeText(context, "On Verification failed" + e.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {


            Log.d("Tag------->", "codesent")

            progressBar.visibility = View.INVISIBLE
            val directions=RegisterFragDirections.actionRegisterFrag2ToDisplayOtp(verificationId,number)
            findNavController().navigate(directions)

//            findNavController().navigate(
//                R.id.action_registerFrag2_to_displayOtp,
//                null,
//                NavOptions.Builder().setPopUpTo(R.id.registerFrag2, true).build()
//            )

        }

    }

    private fun signInWithPhoneCredentials(credential: PhoneAuthCredential) {

        fbAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {


                Toast.makeText(context, "Authenticate Successfully", Toast.LENGTH_SHORT).show()
                findNavController().navigate(
                    R.id.action_registerFrag2_to_homeFrag2, null,
                    NavOptions.Builder().setPopUpTo(R.id.registerFrag2, true).build()
                )
            } else {
                if (it.exception is FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(context, "Code is not valid", Toast.LENGTH_SHORT).show()
                }
            }


        }
    }
}
