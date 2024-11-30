package rut.miit.simplemessanger

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import rut.miit.simplemessanger.databinding.ActivityMainBinding
import rut.miit.simplemessanger.fragments.OnBoardFragment
import rut.miit.simplemessanger.fragments.SettingsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportFragmentManager.registerFragmentLifecycleCallbacks(
            object : FragmentManager.FragmentLifecycleCallbacks() {
                override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
                    if (f.id == R.id.hostFragment) {
                        setVisibleSettingsBtn(f)
                        Log.d("FragmentTracker", "Active fragment: ${f::class.java.simpleName}")
                    }
                }
            },
            true
        )

        binding.settingsImageBtn.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.hostFragment, SettingsFragment())
                .addToBackStack(null)
                .commit()
        }

        Log.d("MainActivity", "created")
    }

    private fun setVisibleSettingsBtn(currentFragment: Fragment?) {
        if (currentFragment is NavHostFragment) {
            Log.d("FragmentTracker", "NavHostFragment detected, skipping check.")
            return
        }

        if (currentFragment is OnBoardFragment || currentFragment is SettingsFragment) {
            binding.settingsImageBtn.isVisible = false
            Log.d("FragmentTracker", "OnBoardFragment is active, settingsImageBtn disabled")
        } else {
            binding.settingsImageBtn.isVisible = true
            Log.d("FragmentTracker", "Other fragment is active, settingsImageBtn enabled")
        }
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
