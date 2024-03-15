package com.example.firebasetwo

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.firebasetwo.databinding.FragmentDisplayOtpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class DisplayOtp : Fragment() {

    private lateinit var bind: FragmentDisplayOtpBinding
    private lateinit var fbAuth: FirebaseAuth
    private lateinit var Inputnum1: android.widget.EditText
    private lateinit var Inputnum2: android.widget.EditText
    private lateinit var Inputnum3: android.widget.EditText
    private lateinit var Inputnum4: android.widget.EditText
    private lateinit var Inputnum5: android.widget.EditText
    private lateinit var Inputnum6: android.widget.EditText

    private val args: DisplayOtpArgs by navArgs()

    private var idOtp = ""
    private var token = 0
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



        bind.verify.setOnClickListener {

            val typedOtp =
                Inputnum1.text.toString() + Inputnum2.text.toString() + Inputnum3.text.toString() + Inputnum4.text.toString() + Inputnum5.text.toString() + Inputnum6.text.toString()

            if (typedOtp.isNotEmpty()) {
                if (typedOtp.length == 6) {
                    val credential:PhoneAuthCredential=PhoneAuthProvider.getCredential(idOtp,typedOtp)
                    signInWithPhoneCredentials(credential)


                } else {
                    Toast.makeText(context, "Enter Full OTP", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(context, "Enter OTP", Toast.LENGTH_SHORT).show()
            }

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

        idOtp=args.otp
        number=args.number
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