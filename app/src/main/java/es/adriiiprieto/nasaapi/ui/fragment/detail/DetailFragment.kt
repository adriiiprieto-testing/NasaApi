package es.adriiiprieto.nasaapi.ui.fragment.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator
import es.adriiiprieto.nasaapi.base.BaseExtraData
import es.adriiiprieto.nasaapi.base.BaseState
import es.adriiiprieto.nasaapi.databinding.FragmentDetailBinding
import es.adriiiprieto.nasaapi.ui.fragment.detail.viewpager.CustomPageAdapter

class DetailFragment : Fragment() {

    lateinit var binding: FragmentDetailBinding

    val viewModel: DetailViewModel by viewModels()

    val args: DetailFragmentArgs by navArgs()

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
    }


    /**
     * Normal state, everything works!
     */
    private fun updateToNormalState(data: DetailState) {
        data.item?.let { item ->
            val pagerAdapter = CustomPageAdapter(this, item)
            binding.fragmentDetailViewPager.adapter = pagerAdapter

            TabLayoutMediator(binding.tabLayout, binding.fragmentDetailViewPager) { tab, position ->
                when (position){
                    0 -> tab.text = "Imagen"
                    1 -> tab.text = "Detalles"
                }

            }.attach()
        }
    }


    /**
     * Loading state, wait until the results...
     */
    private fun updateToLoadingState(dataLoading: BaseExtraData?) {
    }

    /**
     * Error state :(
     */
    private fun updateToErrorState(dataError: Throwable) {
        if (dataError is NoUrlException) {
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