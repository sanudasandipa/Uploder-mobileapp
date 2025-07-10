package com.clouduploader.app.ui.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.clouduploader.app.R
import com.clouduploader.app.databinding.FragmentFilesBinding
import com.clouduploader.app.ui.adapter.FilesAdapter
import com.clouduploader.app.ui.viewmodel.CloudViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class FilesFragment : Fragment() {
    
    private var _binding: FragmentFilesBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: CloudViewModel by viewModels()
    private lateinit var filesAdapter: FilesAdapter
    
    private val filePickerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                viewModel.uploadFile(uri)
            }
        }
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilesBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView()
        setupObservers()
        setupClickListeners()
        
        // Load files when fragment is created
        viewModel.loadFiles()
    }
    
    private fun setupRecyclerView() {
        filesAdapter = FilesAdapter(
            onDownloadClick = { file ->
                viewModel.downloadFile(file.name)
            },
            onDeleteClick = { file ->
                showDeleteConfirmation(file.name)
            },
            onShareClick = { file ->
                showShareDialog(file.name)
            }
        )
        
        binding.recyclerViewFiles.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = filesAdapter
        }
    }
    
    private fun setupObservers() {
        viewModel.files.observe(viewLifecycleOwner) { files ->
            filesAdapter.submitList(files)
            binding.emptyState.visibility = if (files.isEmpty()) View.VISIBLE else View.GONE
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
        
        viewModel.uploadProgress.observe(viewLifecycleOwner) { progress ->
            if (progress > 0f) {
                binding.uploadProgress.visibility = View.VISIBLE
                binding.uploadProgress.progress = (progress * 100).toInt()
            } else {
                binding.uploadProgress.visibility = View.GONE
            }
        }
        
        viewModel.downloadProgress.observe(viewLifecycleOwner) { progress ->
            if (progress > 0f) {
                binding.downloadProgress.visibility = View.VISIBLE
                binding.downloadProgress.progress = (progress * 100).toInt()
            } else {
                binding.downloadProgress.visibility = View.GONE
            }
        }
        
        viewModel.shareCreated.observe(viewLifecycleOwner) { shareUrl ->
            shareUrl?.let {
                showShareUrlDialog(it)
            }
        }
    }
    
    private fun setupClickListeners() {
        binding.fabUpload.setOnClickListener {
            openFilePicker()
        }
        
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadFiles()
        }
    }
    
    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*"
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        filePickerLauncher.launch(intent)
    }
    
    private fun showDeleteConfirmation(filename: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete File")
            .setMessage("Are you sure you want to delete \"$filename\"?")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.deleteFile(filename)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun showShareDialog(filename: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Share File")
            .setMessage("Create a share link for \"$filename\"?")
            .setPositiveButton("Create Link") { _, _ ->
                viewModel.createShare(filename)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun showShareUrlDialog(shareUrl: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Share Link Created")
            .setMessage("Share URL: $shareUrl")
            .setPositiveButton("Copy") { _, _ ->
                copyToClipboard(shareUrl)
            }
            .setNegativeButton("Close", null)
            .show()
    }
    
    private fun copyToClipboard(text: String) {
        val clipboard = requireContext().getSystemService(android.content.Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
        val clip = android.content.ClipData.newPlainText("Share URL", text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "Share URL copied to clipboard", Toast.LENGTH_SHORT).show()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
