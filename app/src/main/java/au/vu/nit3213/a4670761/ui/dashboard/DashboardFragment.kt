package au.vu.nit3213.a4670761.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import au.vu.nit3213.a4670761.R
import au.vu.nit3213.a4670761.databinding.FragmentDashboardBinding
import au.vu.nit3213.a4670761.databinding.ItemEntityBinding
import au.vu.nit3213.a4670761.domain.model.Entity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val vm: DashboardViewModel by viewModels()
    private lateinit var adapter: EntityAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = EntityAdapter { entity ->
            val b = Bundle().apply { putParcelable("entity", entity) }
            findNavController().navigate(R.id.detailsFragment, b)
        }

        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        binding.rv.adapter = adapter

        vm.state.observe(viewLifecycleOwner) { s ->
            binding.progress.isVisible = s.loading
            binding.tvState.isVisible = s.error != null || (s.items.isEmpty() && !s.loading)
            binding.tvState.text = s.error ?: if (!s.loading) "No items" else ""
            if (s.items.isNotEmpty()) adapter.submit(s.items)
        }

        vm.load()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // ---------- Adapter (inner class) ----------
    private class EntityAdapter(
        val onClick: (Entity) -> Unit
    ) : RecyclerView.Adapter<EntityAdapter.VH>() {

        private val items = mutableListOf<Entity>()

        fun submit(list: List<Entity>) {
            items.clear()
            items.addAll(list)
            notifyDataSetChanged()
        }

        class VH(val binding: ItemEntityBinding) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val binding = ItemEntityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return VH(binding)
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            val e = items[position]

            // Title with smart fallback
            holder.binding.title.text = when {
                !e.property1.isNullOrBlank() -> e.property1!!
                !e.property2.isNullOrBlank() -> e.property2!!
                !e.description.isNullOrBlank() ->
                    e.description!!.lineSequence().first().take(60) + "…"
                else -> "Untitled"
            }

            // Show subtitle only; hide description on dashboard
            holder.binding.subtitle.text = e.property2.orEmpty()
            holder.binding.description.visibility = View.GONE

            holder.binding.root.setOnClickListener { onClick(e) }
        }

        override fun getItemCount(): Int = items.size
    }
}
