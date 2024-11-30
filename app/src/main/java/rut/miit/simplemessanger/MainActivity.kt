package rut.miit.simplemessanger

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import rut.miit.simplemessanger.databinding.ActivityMainBinding

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

//        binding.hostFragment.setOnApplyWindowInsetsListener { _, insets ->
//            checkCurrentFragment()
//            insets
//        }

        Log.d("MainActivity", "created")
    }

//    private fun checkCurrentFragment() {
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.hostFragment) as? NavHostFragment
//        val currentFragment = navHostFragment?.childFragmentManager?.fragments?.firstOrNull()
//
//        Log.d("FragmentTracker", "Current fragment: ${currentFragment?.javaClass?.simpleName ?: "None"}")
//
//        if (currentFragment is OnBoardFragment) {
//            binding.settingsImageBtn.isEnabled = false
//            binding.settingsImageBtn.isVisible = false
//            Log.d("FragmentTracker", "OnBoardFragment is active, settingsImageBtn disabled")
//        } else {
//            binding.settingsImageBtn.isEnabled = true
//            binding.settingsImageBtn.isVisible = true
//            Log.d("FragmentTracker", "Other fragment is active, settingsImageBtn enabled")
//        }
//    }

    override fun onResume() {
        super.onResume()
//        checkCurrentFragment()
        Log.d("MainActivity", "resumed")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "destroyed")
    }
}
