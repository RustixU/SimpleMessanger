package rut.miit.simplemessanger.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import rut.miit.simplemessanger.R
import rut.miit.simplemessanger.databinding.FragmentSignUpBinding
import rut.miit.simplemessanger.models.User

private const val ARG_USER = "user"

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private var _binding: FragmentSignUpBinding? = null
    private val binding: FragmentSignUpBinding
        get() = _binding ?: throw RuntimeException("FragmentSignUpBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usernameEditText: EditText = binding.regUsernameEditText
        val passwordEditText: EditText = binding.regPasswordEditText

        binding.signUpBtn.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val user = User(username, password)
            val bundle = Bundle()
            bundle.putParcelable(ARG_USER, user)

            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d("SignUpFragment", "Fragment Destroyed")
    }
}