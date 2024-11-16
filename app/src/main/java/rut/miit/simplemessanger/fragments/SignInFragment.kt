package rut.miit.simplemessanger.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import rut.miit.simplemessanger.MainActivity
import rut.miit.simplemessanger.R

private const val ARG_USERNAME = "username"
private const val ARG_PASSWORD = "password"

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private var username: String? = null
    private var password: String? = null
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText

    private var enteredUsername: String = ""
    private var enteredPassword: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            username = it.getString(ARG_USERNAME)
            password = it.getString(ARG_PASSWORD)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usernameEditText = view.findViewById(R.id.signInUsernameEditText)
        passwordEditText = view.findViewById(R.id.signInPasswordEditText)

        username?.let {
            usernameEditText.setText(it)
        }
        password?.let {
            passwordEditText.setText(it)
        }

        enteredUsername = usernameEditText.text.toString()
        enteredPassword = passwordEditText.text.toString()

        usernameEditText.addTextChangedListener {
            enteredUsername = it.toString()
        }

        passwordEditText.addTextChangedListener {
            enteredPassword = it.toString()
        }

        val signInBtn: Button = view.findViewById(R.id.signInBtn)
        signInBtn.setOnClickListener {

            if (enteredUsername == "rstm_avzv" && enteredPassword == "1234") {
                (activity as? MainActivity)?.navigateToHome()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Неправильный логин или пароль",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}