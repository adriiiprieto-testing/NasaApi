package es.adriiiprieto.nasaapi.ui.fragment.detail.viewpager

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import es.adriiiprieto.nasaapi.R
import es.adriiiprieto.nasaapi.base.toMyDateFormat
import es.adriiiprieto.nasaapi.data.model.Item
import es.adriiiprieto.nasaapi.databinding.FragmentFirstPageBinding

class FirstPageFragment(val item: Item) : Fragment() {

    lateinit var binding: FragmentFirstPageBinding

    private var buttonUrl: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFirstPageBinding.inflate(inflater, container, false)

        setupView()

        drawElements()

        return binding.root
    }

    /**
     * Setup view elements
     */
    private fun setupView() {
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


    private fun drawElements() {
        binding.fragmentDetailTextViewTitle.text = item.data.firstOrNull()?.title ?: ""
        binding.fragmentDetailTextViewDate.text = item.data.firstOrNull()?.date_created?.toMyDateFormat() ?: "Fecha desconocida"

        Glide.with(requireActivity())
            .load(item.links.firstOrNull()?.href ?: "https://image.freepik.com/free-vector/page-found-error-404_23-2147508324.jpg")
            .centerCrop()
            .placeholder(R.drawable.ic_navegador)
            .into(binding.fragmentDetailImageViewNasa)

        buttonUrl = item.href
    }
}