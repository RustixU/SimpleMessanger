package rut.miit.simplemessanger.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import rut.miit.simplemessanger.R
import rut.miit.simplemessanger.databinding.FragmentOnBoardBinding

class OnBoardFragment : Fragment(R.layout.fragment_on_board) {

    private var _binding: FragmentOnBoardBinding? = null
    private val binding: FragmentOnBoardBinding
        get() = _binding ?: throw RuntimeException("FragmentOnBoardBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signInBtn.setOnClickListener {
            findNavController().navigate(R.id.action_onBoardFragment_to_signInFragment)
        }

        binding.signUpBtn.setOnClickListener {
            findNavController().navigate(R.id.action_onBoardFragment_to_signUpFragment)
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d("OnBoardFragment", "Fragment Destroyed")
    }
}