package es.adriiiprieto.nasaapi.ui.fragment.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import es.adriiiprieto.nasaapi.R
import es.adriiiprieto.nasaapi.base.BaseExtraData
import es.adriiiprieto.nasaapi.base.BaseState
import es.adriiiprieto.nasaapi.base.NetworkManager
import es.adriiiprieto.nasaapi.base.NoInternetConnectivity
import es.adriiiprieto.nasaapi.data.model.Item
import es.adriiiprieto.nasaapi.databinding.FragmentListBinding
import retrofit2.HttpException
import java.net.UnknownHostException

class ListFragment : Fragment() {

    lateinit var binding: FragmentListBinding

    private val viewModel: ListViewModel by viewModels()

    lateinit var adapter: ListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)

        setupView()

        viewModel.getState().observe(viewLifecycleOwner, { state ->
            when (state) {
                is BaseState.Error -> {
                    updateToErrorState(state.dataError)
                }
                is BaseState.Loading -> {
                    updateToLoadingState(state.dataLoading)
                }
                is BaseState.Normal -> {
                    updateToNormalState(state.data as ListState)
                }
            }
        })

        viewModel.requestInformation()

        return binding.root
    }


    /**
     * Setup view elements
     */
    private fun setupView() {
        // Set recycler view
        adapter = ListAdapter(listOf(), requireActivity()) { item ->
            findNavController().navigate(ListFragmentDirections.actionListFragmentToDetailFragment(item))
        }
        binding.fragmentListRecyclerView.apply {
            adapter = this@ListFragment.adapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
        }

        // Set swipe refresh gesture
        binding.fragmentListSwipeRefreshLayout.setOnRefreshListener {
            adapter.updateList(listOf())
            viewModel.requestInformation()
        }
    }

    /**
     * Normal state, everything works!
     */
    private fun updateToNormalState(data: ListState) {
        binding.fragmentListProgressBar.visibility = View.GONE
        binding.fragmentListSwipeRefreshLayout.isRefreshing = false
        adapter.updateList(data.picturesList)
    }

    /**
     * Loading state, wait until the results...
     */
    private fun updateToLoadingState(dataLoading: BaseExtraData?) {
        binding.fragmentListProgressBar.visibility = View.VISIBLE
    }

    /**
     * Error state :(
     */
    private fun updateToErrorState(dataError: Throwable) {
        binding.fragmentListProgressBar.visibility = View.GONE
        adapter.updateList(listOf())

        val msg = when (dataError) {
            is HttpException -> "Fatal error: " + dataError.code().toString()
            is UnknownHostException -> "No tienes conexión a internet"
            is NoInternetConnectivity -> "Encienda los datos móviles o Wi-Fi e inténtelo de nuevo"
            else -> "Error genérico"
        }
        MaterialAlertDialogBuilder(requireActivity())
            .setTitle("Error")
            .setMessage(msg)
            .setPositiveButton("Reintentar") { dialog, which ->
                viewModel.requestInformation()
            }
            .show()
    }
}