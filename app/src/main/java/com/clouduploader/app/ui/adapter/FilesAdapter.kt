package com.clouduploader.app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.clouduploader.app.R
import com.clouduploader.app.data.model.CloudFile
import com.clouduploader.app.databinding.ItemFileBinding
import java.text.SimpleDateFormat
import java.util.*

class FilesAdapter(
    private val onDownloadClick: (CloudFile) -> Unit,
    private val onDeleteClick: (CloudFile) -> Unit,
    private val onShareClick: (CloudFile) -> Unit
) : ListAdapter<CloudFile, FilesAdapter.FileViewHolder>(FileDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val binding = ItemFileBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FileViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    inner class FileViewHolder(
        private val binding: ItemFileBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(file: CloudFile) {
            binding.apply {
                textFileName.text = file.name
                textFileSize.text = file.sizeFormatted
                textFileType.text = file.type.uppercase()
                
                // Format the date
                try {
                    val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                        .parse(file.modified)
                    val displayFormat = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
                    textFileDate.text = date?.let { displayFormat.format(it) } ?: file.modified
                } catch (e: Exception) {
                    textFileDate.text = file.modified
                }
                
                // Set file icon based on type
                imageFileIcon.setImageResource(getFileIcon(file.type))
                
                // Set click listeners
                buttonDownload.setOnClickListener { onDownloadClick(file) }
                buttonDelete.setOnClickListener { onDeleteClick(file) }
                buttonShare.setOnClickListener { onShareClick(file) }
            }
        }
        
        private fun getFileIcon(type: String): Int {
            return when {
                type.contains("image", true) -> R.drawable.ic_image
                type.contains("video", true) -> R.drawable.ic_video
                type.contains("audio", true) -> R.drawable.ic_audio
                type.contains("pdf", true) -> R.drawable.ic_pdf
                type.contains("text", true) -> R.drawable.ic_text
                type.contains("zip", true) || type.contains("rar", true) -> R.drawable.ic_archive
                else -> R.drawable.ic_file
            }
        }
    }
}

class FileDiffCallback : DiffUtil.ItemCallback<CloudFile>() {
    override fun areItemsTheSame(oldItem: CloudFile, newItem: CloudFile): Boolean {
        return oldItem.name == newItem.name
    }
    
    override fun areContentsTheSame(oldItem: CloudFile, newItem: CloudFile): Boolean {
        return oldItem == newItem
    }
}
