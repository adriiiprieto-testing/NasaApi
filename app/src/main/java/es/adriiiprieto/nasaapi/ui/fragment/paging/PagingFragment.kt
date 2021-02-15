package es.adriiiprieto.nasaapi.ui.fragment.paging

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import es.adriiiprieto.nasaapi.base.BaseExtraData
import es.adriiiprieto.nasaapi.base.BaseState
import es.adriiiprieto.nasaapi.base.NoInternetConnectivity
import es.adriiiprieto.nasaapi.databinding.FragmentListBinding
import es.adriiiprieto.nasaapi.databinding.FragmentPagingBinding
import retrofit2.HttpException
import java.net.UnknownHostException

class PagingFragment : Fragment() {

    lateinit var binding: FragmentPagingBinding

    private val viewModel: PagingViewModel by viewModels()

    lateinit var adapter: PagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPagingBinding.inflate(inflater, container, false)

        setupView()

        viewModel.getMyList().observe(viewLifecycleOwner, { items ->
            adapter.submitList(items)
        })

        return binding.root
    }


    /**
     * Setup view elements
     */
    private fun setupView() {
        // Set recycler view
        adapter = PagingAdapter(requireActivity()) { item ->
            findNavController().navigate(PagingFragmentDirections.actionPagingFragmentToDetailFragment(item))
        }
        binding.fragmentPagingRecyclerView.apply {
            adapter = this@PagingFragment.adapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
        }
    }
}