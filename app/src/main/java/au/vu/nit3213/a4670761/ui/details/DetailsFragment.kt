package au.vu.nit3213.a4670761.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import au.vu.nit3213.a4670761.R
import au.vu.nit3213.a4670761.databinding.FragmentDetailsBinding
import au.vu.nit3213.a4670761.domain.model.Entity

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val e: Entity? = arguments?.getParcelable("entity")

        binding.tvP1.text = "property1: ${e?.property1 ?: "-"}"
        binding.tvP2.text = "property2: ${e?.property2 ?: "-"}"
        binding.tvDesc.text = e?.description ?: "(no description)"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}