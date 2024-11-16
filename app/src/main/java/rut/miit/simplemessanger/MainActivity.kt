package rut.miit.simplemessanger

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import rut.miit.simplemessanger.fragments.HomeFragment
import rut.miit.simplemessanger.fragments.SignInFragment
import rut.miit.simplemessanger.fragments.SignUpFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Log.d("MainActivity", "created")
    }

    fun navigateToSignIn(username: String?, password: String?) {
        val bundle: Bundle = Bundle().apply {
            putString("username", username)
            putString("password", password)
        }

        val signInFragment: Fragment = SignInFragment().apply {
            arguments = bundle
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.hostFragment, signInFragment)
            .addToBackStack(null)
            .commit()
    }

    fun navigateToSignUp() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.hostFragment, SignUpFragment())
            .addToBackStack(null)
            .commit()
    }

    fun navigateToHome() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.hostFragment, HomeFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivity", "resumed")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "destroyed")
    }
}