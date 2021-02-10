package es.adriiiprieto.nasaapi.ui.fragment.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.adriiiprieto.nasaapi.databinding.ItemKeywordListBinding

class DetailAdapter(private var dataSet: List<String>) : RecyclerView.Adapter<DetailAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemKeywordListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemKeywordListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = dataSet[position]

        viewHolder.binding.apply {
            itemKeywordListText.text = item
        }
    }

    override fun getItemCount() = dataSet.size
}