package es.adriiiprieto.nasaapi.ui.fragment.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import es.adriiiprieto.nasaapi.R
import es.adriiiprieto.nasaapi.databinding.FragmentListBinding

class ListFragment : Fragment() {

    lateinit var binding: FragmentListBinding

    val viewModel: ListViewModel by viewModels()

    lateinit var adapter: ListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = FragmentListBinding.inflate(inflater, container, false)

        adapter = ListAdapter(listOf(), requireActivity())

        binding.fragmentListRecyclerView.apply {
            adapter = this@ListFragment.adapter
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
        }

        viewModel.response.observe(viewLifecycleOwner, { response ->
            adapter.updateList(response)
        })

        viewModel.requestInformation()

        return binding.root
    }

}