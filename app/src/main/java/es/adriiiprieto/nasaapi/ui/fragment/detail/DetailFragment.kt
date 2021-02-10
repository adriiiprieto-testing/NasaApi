package es.adriiiprieto.nasaapi.ui.fragment.detail

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SimpleAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import es.adriiiprieto.nasaapi.R
import es.adriiiprieto.nasaapi.data.model.Item
import es.adriiiprieto.nasaapi.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    lateinit var binding: FragmentDetailBinding

    val viewModel: DetailViewModel by viewModels()

    val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        setupView(args.item)

        return binding.root
    }

    private fun setupView(item: Item) {

        binding.fragmentDetailTextViewTitle.text = item.data.firstOrNull()?.title ?: ""
        binding.fragmentDetailTextViewDate.text = item.data.firstOrNull()?.date_created ?: "Fecha desconocida"

        Glide.with(requireActivity())
            .load(item.links.firstOrNull()?.href ?: "https://image.freepik.com/free-vector/page-found-error-404_23-2147508324.jpg")
            .centerCrop()
            .placeholder(R.drawable.ic_navegador)
            .into(binding.fragmentDetailImageViewNasa)

        binding.fragmentDetailButtonShowURL.setOnClickListener {
            searchWeb(item.href)
        }

        val description = when{
            !item.data.firstOrNull()?.description.isNullOrEmpty() -> item.data.firstOrNull()?.description
            !item.data.firstOrNull()?.description_508.isNullOrEmpty() -> item.data.firstOrNull()?.description_508
            else -> "No hay descripción"
        }
        binding.fragmentDetailTextViewDescription.text = description

        binding.fragmentDetailRecyclerViewKeywords.apply {
            adapter = DetailAdapter(item.data.firstOrNull()?.keywords ?: listOf())
            layoutManager = LinearLayoutManager(requireActivity())
            itemAnimator = DefaultItemAnimator()
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

}