package es.adriiiprieto.nasaapi.ui.fragment.detail

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import es.adriiiprieto.nasaapi.R
import es.adriiiprieto.nasaapi.base.BaseExtraData
import es.adriiiprieto.nasaapi.base.BaseState
import es.adriiiprieto.nasaapi.base.NoInternetConnectivity
import es.adriiiprieto.nasaapi.base.toMyDateFormat
import es.adriiiprieto.nasaapi.data.model.Item
import es.adriiiprieto.nasaapi.databinding.FragmentDetailBinding
import retrofit2.HttpException
import java.net.UnknownHostException

class DetailFragment : Fragment() {

    lateinit var binding: FragmentDetailBinding

    val viewModel: DetailViewModel by viewModels()

    val args: DetailFragmentArgs by navArgs()

    private lateinit var keyWordsAdapter: DetailAdapter

    private var buttonUrl: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

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
                    updateToNormalState(state.data as DetailState)
                }
            }
        })

        viewModel.setupParams(args.item)

        return binding.root
    }

    /**
     * Setup view elements
     */
    private fun setupView() {
        // Setup recycler view
        keyWordsAdapter = DetailAdapter(listOf())
        binding.fragmentDetailRecyclerViewKeywords.apply {
            adapter = keyWordsAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            itemAnimator = DefaultItemAnimator()
        }

        // Setup button listeners
        binding.fragmentDetailButtonShowURL.setOnClickListener {
            buttonUrl?.let {
                searchWeb(it)
            } ?: run {
                Toast.makeText(requireActivity(), "No existe una URL", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun searchWeb(query: String) {
        val intent = Intent(Intent.ACTION_WEB_SEARCH).apply {
            putExtra(SearchManager.QUERY, query)
        }
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        }
    }


    /**
     * Normal state, everything works!
     */
    private fun updateToNormalState(data: DetailState) {
        data.item?.let { item ->
            binding.fragmentDetailTextViewTitle.text = item.data.firstOrNull()?.title ?: ""
            binding.fragmentDetailTextViewDate.text = item.data.firstOrNull()?.date_created?.toMyDateFormat() ?: "Fecha desconocida"

            Glide.with(requireActivity())
                .load(item.links.firstOrNull()?.href ?: "https://image.freepik.com/free-vector/page-found-error-404_23-2147508324.jpg")
                .centerCrop()
                .placeholder(R.drawable.ic_navegador)
                .into(binding.fragmentDetailImageViewNasa)

            val description = when {
                !item.data.firstOrNull()?.description.isNullOrEmpty() -> item.data.firstOrNull()?.description
                !item.data.firstOrNull()?.description_508.isNullOrEmpty() -> item.data.firstOrNull()?.description_508
                else -> "No hay descripción"
            }
            binding.fragmentDetailTextViewDescription.text = description

            keyWordsAdapter.updateList(item.data.firstOrNull()?.keywords ?: listOf())

            buttonUrl = item.href
        }
    }


    /**
     * Loading state, wait until the results...
     */
    private fun updateToLoadingState(dataLoading: BaseExtraData?) {
        buttonUrl = null
    }

    /**
     * Error state :(
     */
    private fun updateToErrorState(dataError: Throwable) {
        if(dataError is NoUrlException){
            MaterialAlertDialogBuilder(requireActivity())
                .setTitle("Error")
                .setMessage(dataError.msg)
                .setPositiveButton("Volver atrás") { dialog, which ->
                    findNavController().navigateUp()
                }
                .show()
        }
    }

}