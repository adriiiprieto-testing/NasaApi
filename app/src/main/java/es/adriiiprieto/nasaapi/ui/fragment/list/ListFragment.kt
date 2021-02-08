package es.adriiiprieto.nasaapi.ui.fragment.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import es.adriiiprieto.nasaapi.R
import es.adriiiprieto.nasaapi.databinding.FragmentListBinding

class ListFragment : Fragment() {

    lateinit var binding: FragmentListBinding

    val viewModel: ListViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = FragmentListBinding.inflate(inflater, container, false)

        viewModel.response.observe(viewLifecycleOwner, { response ->
            
        })

        return binding.root
    }

}