package rut.miit.simplemessanger.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import rut.miit.simplemessanger.R

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usernameEditText: EditText = view.findViewById(R.id.regUsernameEditText)
        val passwordEditText: EditText = view.findViewById(R.id.regPasswordEditText)

        val signInBtn: Button = view.findViewById(R.id.signUpBtn)

        signInBtn.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            val signInFragment = SignInFragment.newInstance(username, password)
            parentFragmentManager.beginTransaction()
                .replace(R.id.mainFragment, signInFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}