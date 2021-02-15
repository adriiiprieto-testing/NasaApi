package es.adriiiprieto.nasaapi.ui.fragment.paging

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import es.adriiiprieto.nasaapi.R
import es.adriiiprieto.nasaapi.data.model.Item
import es.adriiiprieto.nasaapi.databinding.ItemNasaListBinding

class PagingAdapter(private val context: Context, private val listener: (item: Item) -> Unit) : PagedListAdapter<Item, PagingAdapter.ViewHolder>(object : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item) = oldItem.data.firstOrNull()?.nasa_id ?: 0 == newItem.data.firstOrNull()?.nasa_id ?: 0
    override fun areContentsTheSame(oldItem: Item, newItem: Item) = oldItem.data.firstOrNull()?.description ?: "" == newItem.data.firstOrNull()?.description ?: ""
}) {

    class ViewHolder(val binding: ItemNasaListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemNasaListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        getItem(position)?.let{ item ->
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
    }
}
