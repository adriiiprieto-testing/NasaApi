package es.adriiiprieto.nasaapi.ui.fragment.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import es.adriiiprieto.nasaapi.R
import es.adriiiprieto.nasaapi.data.model.Item
import es.adriiiprieto.nasaapi.databinding.ItemNasaListBinding

class ListAdapter(private var dataSet: List<Item>, private val context: Context, private val listener: (item: Item) -> Unit) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemNasaListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemNasaListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = dataSet[position]

        viewHolder.binding.apply {
            itemNasaListTextViewTitle.text = item.data.firstOrNull()?.title ?: "N/A"
            itemNasaListTextViewDescription.text = item.data.firstOrNull()?.description_508 ?: "N/A"

            Glide.with(context)
                .load(item.links.firstOrNull()?.href ?: "https://image.freepik.com/free-vector/page-found-error-404_23-2147508324.jpg")
                .centerCrop()
                .placeholder(R.drawable.ic_navegador)
                .into(itemNasaListImageView)
        }

        viewHolder.itemView.setOnClickListener {
            listener.invoke(item)
        }
    }

    override fun getItemCount() = dataSet.size

    fun updateList(newList: List<Item>){
        dataSet = newList
        notifyDataSetChanged()
    }
}