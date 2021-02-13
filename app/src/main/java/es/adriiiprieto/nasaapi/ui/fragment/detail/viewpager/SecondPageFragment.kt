package es.adriiiprieto.nasaapi.ui.fragment.detail.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import es.adriiiprieto.nasaapi.data.model.Item
import es.adriiiprieto.nasaapi.databinding.FragmentSecondPageBinding
import es.adriiiprieto.nasaapi.ui.fragment.detail.DetailAdapter

class SecondPageFragment(val item: Item) : Fragment() {

    lateinit var binding: FragmentSecondPageBinding

    private lateinit var keyWordsAdapter: DetailAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSecondPageBinding.inflate(inflater, container, false)

        setupView()

        drawItem()

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
    }

    private fun drawItem() {
        val description = when {
            !item.data.firstOrNull()?.description.isNullOrEmpty() -> item.data.firstOrNull()?.description
            !item.data.firstOrNull()?.description_508.isNullOrEmpty() -> item.data.firstOrNull()?.description_508
            else -> "No hay descripción"
        }
        binding.fragmentDetailTextViewDescription.text = description

        keyWordsAdapter.updateList(item.data.firstOrNull()?.keywords ?: listOf())
    }
}