package com.fetchrewards.codingexercise.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.fetchrewards.codingexercise.R
import com.fetchrewards.codingexercise.databinding.FragmentListBinding
import com.fetchrewards.codingexercise.viewmodel.ListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {
    private val viewModel by viewModels<ListViewModel>()
    private lateinit var binding: FragmentListBinding

    private val customListAdapter by lazy {
        CustomListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initRecyclerView()
        initObservers()
    }

    override fun onStart() {
        super.onStart()
        viewModel.getList()
    }

    private fun initRecyclerView() {
        with(binding.countryRecyclerView) {
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            adapter = customListAdapter
        }
    }

    private fun initObservers() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ListViewModel.UiState.PopulateList -> {
                    customListAdapter.submitList(state.list)
                }
                is ListViewModel.UiState.Error -> {
                    Toast.makeText(
                        requireContext(),
                        state.errorMessage ?: getString(R.string.generic_error_message),
                        Toast.LENGTH_LONG,
                    ).show()
                }
            }
        }

        viewModel.progressBarFlag.observe(viewLifecycleOwner) { show ->
            binding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
        }
    }
}
