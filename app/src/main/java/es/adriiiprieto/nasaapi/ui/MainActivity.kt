package es.adriiiprieto.nasaapi.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import es.adriiiprieto.nasaapi.databinding.ActivityMainBinding
import es.adriiiprieto.nasaapi.ui.MainViewModel

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}