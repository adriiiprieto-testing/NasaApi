package es.adriiiprieto.nasaapi.ui.fragment.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import es.adriiiprieto.nasaapi.base.BaseState
import es.adriiiprieto.nasaapi.base.NetworkManager
import es.adriiiprieto.nasaapi.base.NoInternetConnectivity
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

        // Observer
        viewModel.getState().observe(viewLifecycleOwner, { state ->
            when (state) {
                is BaseState.Error -> {
                    binding.fragmentListProgressBar.visibility = View.GONE
                    adapter.updateList(listOf())

                    val msg = when (state.dataError) {
                        is HttpException -> "Fatal error: " + state.dataError.code().toString()
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
                is BaseState.Loading -> {
                    binding.fragmentListProgressBar.visibility = View.VISIBLE
                }
                is BaseState.Normal -> {
                    binding.fragmentListProgressBar.visibility = View.GONE
                    binding.fragmentListSwipeRefreshLayout.isRefreshing = false
                    adapter.updateList((state.data as ListState).picturesList)
                }
            }
        })

        // Set recycler view
        adapter = ListAdapter(listOf(), requireActivity())
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

        // Request information
        viewModel.requestInformation()

        return binding.root
    }

}