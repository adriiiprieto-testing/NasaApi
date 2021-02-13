package es.adriiiprieto.nasaapi.ui.fragment.detail.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import es.adriiiprieto.nasaapi.data.model.Item

class CustomPageAdapter(fa: Fragment, val item: Item) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FirstPageFragment(item)
            1 -> SecondPageFragment(item)
            else -> FirstPageFragment(item)
        }
    }
}