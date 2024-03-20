package com.example.firebasetwo.Log

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.firebasetwo.R
import com.example.firebasetwo.databinding.FragmentDisplayOtpBinding
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class DisplayOtp : Fragment() {

    private lateinit var bind: FragmentDisplayOtpBinding
    private lateinit var fbAuth: FirebaseAuth
    private lateinit var Inputnum1: android.widget.EditText
    private lateinit var Inputnum2: android.widget.EditText
    private lateinit var Inputnum3: android.widget.EditText
    private lateinit var Inputnum4: android.widget.EditText
    private lateinit var Inputnum5: android.widget.EditText
    private lateinit var Inputnum6: android.widget.EditText

    private val args: com.example.firebasetwo.DisplayOtpArgs by navArgs()



    private var idOtp=""
    private lateinit var token: PhoneAuthProvider.ForceResendingToken
    private var number = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentDisplayOtpBinding.inflate(layoutInflater, container, false)

        init()
        addTextChangeListener()
        textVisibility()


        bind.verify.setOnClickListener {

            val typedOtp =
                (Inputnum1.text.toString() + Inputnum2.text.toString() + Inputnum3.text.toString() + Inputnum4.text.toString() + Inputnum5.text.toString() + Inputnum6.text.toString())

            if (typedOtp.isNotEmpty()) {
                if (typedOtp.length == 6) {
                    val credential: PhoneAuthCredential =
                        PhoneAuthProvider.getCredential(idOtp, typedOtp)
                    signInWithPhoneCredentials(credential)


                } else {
                    Toast.makeText(context, "Enter Full OTP", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(context, "Enter OTP", Toast.LENGTH_SHORT).show()
            }

        }

        bind.resendCode.setOnClickListener {
            resendCodeF()
            textVisibility()
        }

        return bind.root
    }


    private fun init() {
        fbAuth = FirebaseAuth.getInstance()
        Inputnum1 = bind.num1
        Inputnum2 = bind.num2
        Inputnum3 = bind.num3
        Inputnum4 = bind.num4
        Inputnum5 = bind.num5
        Inputnum6 = bind.num6

        idOtp = args.otp
        number = args.number
        token = args.token
    }

    inner class EditTextWatcher(private val view: View) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            val text = s.toString()
            when (view.id) {
                R.id.num1 -> if (text.length == 1) Inputnum2.requestFocus()
                R.id.num2 -> if (text.length == 1) Inputnum3.requestFocus() else if (text.isEmpty()) Inputnum1.requestFocus()
                R.id.num3 -> if (text.length == 1) Inputnum4.requestFocus() else if (text.isEmpty()) Inputnum1.requestFocus()
                R.id.num4 -> if (text.length == 1) Inputnum5.requestFocus() else if (text.isEmpty()) Inputnum1.requestFocus()
                R.id.num5 -> if (text.length == 1) Inputnum6.requestFocus() else if (text.isEmpty()) Inputnum1.requestFocus()
                R.id.num6 -> if (text.isEmpty()) Inputnum5.requestFocus()
            }
        }
    }

    private fun addTextChangeListener() {
        Inputnum1.addTextChangedListener(EditTextWatcher(Inputnum1))
        Inputnum2.addTextChangedListener(EditTextWatcher(Inputnum2))
        Inputnum3.addTextChangedListener(EditTextWatcher(Inputnum3))
        Inputnum4.addTextChangedListener(EditTextWatcher(Inputnum4))
        Inputnum5.addTextChangedListener(EditTextWatcher(Inputnum5))
        Inputnum6.addTextChangedListener(EditTextWatcher(Inputnum6))
    }


    private fun signInWithPhoneCredentials(credential: PhoneAuthCredential) {
        Log.e("Tag--------------->", "Authentication Complete")

        fbAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {

                Toast.makeText(context, "Authenticate Successfully", Toast.LENGTH_SHORT).show()
                findNavController().navigate(
                    R.id.action_displayOtp_to_homeFrag2, null,
                    NavOptions.Builder().setPopUpTo(R.id.displayOtp, true).build()
                )
            } else {
                if (it.exception is FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(context, "Code is not valid", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun resendCodeF() {
        val options = PhoneAuthOptions.newBuilder(fbAuth)
            .setPhoneNumber(number)
            .setForceResendingToken(token)
            .setActivity(requireActivity())
            .setTimeout(60L, TimeUnit.SECONDS)
            .setCallbacks(callbacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)

    }


    private var callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            signInWithPhoneCredentials(credential)
        }


        override fun onVerificationFailed(e: FirebaseException) {
            if (e is FirebaseAuthInvalidCredentialsException) {
                Log.d("Tag------->", "failed")

                Toast.makeText(
                    context,
                    "Verification failed due to " + e.toString(),
                    Toast.LENGTH_SHORT
                )
                    .show()

            }
            else if (e is FirebaseTooManyRequestsException) {
                Toast.makeText(context, "On Verification failed" + e.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        }

        override fun onCodeSent(OTP: String, Token: PhoneAuthProvider.ForceResendingToken) {

            idOtp = OTP
            token = Token
        }
    }

    private fun textVisibility() {

        Inputnum1.setText("")
        Inputnum2.setText("")
        Inputnum3.setText("")
        Inputnum4.setText("")
        Inputnum5.setText("")
        Inputnum6.setText("")
        bind.resendCode.visibility = View.INVISIBLE
        bind.resendCode.isEnabled = false


        Handler(Looper.myLooper()!!).postDelayed(
            {
                bind.resendCode.visibility = View.VISIBLE
                bind.resendCode.isEnabled=true
            }, 60000
        )
    }
}