package com.sst.ngisiyuk.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sst.ngisiyuk.R
import com.sst.ngisiyuk.databinding.FragmentAuthBinding

import java.text.SimpleDateFormat
import java.util.*


class AuthFragment : Fragment() {

    lateinit var binding : FragmentAuthBinding
    lateinit var myDate : Calendar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthBinding.inflate(inflater, container, false)
        myDate = Calendar.getInstance()

        binding.authSignInButton.setOnClickListener {
            findNavController().navigate(R.id.action_authFragment_to_signInFragment)
        }

        binding.authSignUpButton.setOnClickListener {
            findNavController().navigate(R.id.action_authFragment_to_signUpFragment)
        }

        

        setClock(binding.timeView)
        setDate(binding.authScreenDate)

        return binding.root
    }

    private fun setDate(view:TextView) {
        val date = myDate.time
        val dateFormatter = SimpleDateFormat("dd MMMM yyyy | EEEE", Locale.US)
        val dateNow = dateFormatter.format(date)

        view.text = dateNow
    }


    private fun setClock(view: TextView) {
        val date = myDate.time

        val timeFormatter = SimpleDateFormat("HH:mm a", Locale.US)
        val time = timeFormatter.format(date)

        view.text = time
    }
}