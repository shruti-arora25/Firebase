package com.example.firebasetwo

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.firebasetwo.databinding.FragmentDisplayOtpBinding
import com.google.firebase.auth.FirebaseAuth

class DisplayOtp : Fragment() {

    private lateinit var bind:FragmentDisplayOtpBinding
    private lateinit var fbAuth:FirebaseAuth
    private lateinit var Inputnum1:EditText
     private lateinit var Inputnum2:EditText
     private lateinit var Inputnum3:EditText
     private lateinit var Inputnum4:EditText
     private lateinit var Inputnum5:EditText
     private lateinit var Inputnum6:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind= FragmentDisplayOtpBinding.inflate(layoutInflater,container,false)


        init()


        return bind.root
    }

    private fun init(){
        fbAuth=FirebaseAuth.getInstance()
        Inputnum1=bind.num1
        Inputnum2=bind.num2
        Inputnum3=bind.num3
        Inputnum4=bind.num4
        Inputnum5=bind.num5
        Inputnum6=bind.num6
    }

    inner class EditText(private val view:View):TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            val text=s.toString()
            when(view.id){
                Inputnum1-> if (text.length==1)
            }


        }

    }



}