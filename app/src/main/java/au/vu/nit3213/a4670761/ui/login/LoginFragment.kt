package au.vu.nit3213.a4670761.ui.login

import androidx.navigation.fragment.findNavController
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import au.vu.nit3213.a4670761.R
import au.vu.nit3213.a4670761.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val vm: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Campus dropdown
        val campuses = listOf("footscray", "sydney", "br")
        binding.etCampus.setAdapter(
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, campuses)
        )
        binding.etCampus.setText(campuses.first(), false)

        binding.btnLogin.setOnClickListener {
            val u = binding.etUsername.text?.toString()?.trim().orEmpty()
            val p = binding.etPassword.text?.toString()?.trim().orEmpty()
            val c = binding.etCampus.text?.toString()?.trim().orEmpty()
            vm.login(u, p, c)
        }

        vm.state.observe(viewLifecycleOwner) { s ->
            binding.progress.isVisible = s.loading
            binding.tvError.isVisible = s.error != null
            binding.tvError.text = s.error.orEmpty()
            if (s.navigateDashboard) {
                findNavController().navigate(R.id.dashboardFragment)
                vm.onNavigated()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}