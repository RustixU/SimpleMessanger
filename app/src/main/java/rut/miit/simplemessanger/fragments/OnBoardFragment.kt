package rut.miit.simplemessanger.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import rut.miit.simplemessanger.MainActivity
import rut.miit.simplemessanger.R

class OnBoardFragment : Fragment(R.layout.fragment_on_board) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_on_board, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.sign_in).setOnClickListener {
            (activity as? MainActivity)?.navigateToSignIn()
        }

        view.findViewById<Button>(R.id.sign_up_text).setOnClickListener {
            (activity as? MainActivity)?.navigateToSignUp()
        }
    }
}