package es.adriiiprieto.nasaapi.ui.fragment.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.adriiiprieto.nasaapi.R
import es.adriiiprieto.nasaapi.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    lateinit var binding: FragmentDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)



        return binding.root
    }

}