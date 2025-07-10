package com.clouduploader.app.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.clouduploader.app.databinding.FragmentStorageBinding
import com.clouduploader.app.ui.viewmodel.CloudViewModel

class StorageFragment : Fragment() {
    
    private var _binding: FragmentStorageBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: CloudViewModel by viewModels()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStorageBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupObservers()
        setupClickListeners()
        
        // Load storage info when fragment is created
        viewModel.loadStorageInfo()
    }
    
    private fun setupObservers() {
        viewModel.storageInfo.observe(viewLifecycleOwner) { storage ->
            storage?.let {
                binding.apply {
                    textUsedSpace.text = it.usedSpaceFormatted
                    textTotalSpace.text = it.totalSpaceFormatted
                    textFreeSpace.text = it.freeSpaceFormatted
                    textFileCount.text = it.fileCount.toString()
                    textMaxFileSize.text = it.maxFileSizeFormatted
                    
                    // Calculate usage percentage
                    val usagePercentage = if (it.totalSpace > 0) {
                        (it.usedSpace.toFloat() / it.totalSpace.toFloat() * 100).toInt()
                    } else {
                        0
                    }
                    
                    progressBarUsage.progress = usagePercentage
                    textUsagePercentage.text = "$usagePercentage%"
                }
            }
        }
        
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.swipeRefreshLayout.isRefreshing = false
        }
        
        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                viewModel.clearError()
            }
        }
    }
    
    private fun setupClickListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadStorageInfo()
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
