package com.clouduploader.app.ui.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.clouduploader.app.data.model.*
import com.clouduploader.app.data.repository.CloudRepository
import kotlinx.coroutines.launch
import java.io.File

class CloudViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository = CloudRepository()
    
    private val _files = MutableLiveData<List<CloudFile>>()
    val files: LiveData<List<CloudFile>> = _files
    
    private val _storageInfo = MutableLiveData<StorageInfo>()
    val storageInfo: LiveData<StorageInfo> = _storageInfo
    
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading
    
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    
    private val _uploadProgress = MutableLiveData<Float>()
    val uploadProgress: LiveData<Float> = _uploadProgress
    
    private val _downloadProgress = MutableLiveData<Float>()
    val downloadProgress: LiveData<Float> = _downloadProgress
    
    private val _shares = MutableLiveData<List<FileShare>>()
    val shares: LiveData<List<FileShare>> = _shares
    
    private val _shareCreated = MutableLiveData<String>()
    val shareCreated: LiveData<String> = _shareCreated
    
    fun loadFiles() {
        viewModelScope.launch {
            _loading.value = true
            repository.getFiles()
                .onSuccess { response ->
                    if (response.success) {
                        _files.value = response.files
                    } else {
                        _error.value = response.error ?: "Failed to load files"
                    }
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Unknown error occurred"
                }
            _loading.value = false
        }
    }
    
    fun loadStorageInfo() {
        viewModelScope.launch {
            repository.getStorageInfo()
                .onSuccess { response ->
                    if (response.success && response.storage != null) {
                        _storageInfo.value = response.storage
                    } else {
                        _error.value = response.error ?: "Failed to load storage info"
                    }
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Unknown error occurred"
                }
        }
    }
    
    fun uploadFile(uri: Uri) {
        viewModelScope.launch {
            _loading.value = true
            _uploadProgress.value = 0f
            
            repository.uploadFile(getApplication(), uri) { progress ->
                _uploadProgress.postValue(progress)
            }
                .onSuccess { response ->
                    if (response.success) {
                        loadFiles() // Refresh files list
                        loadStorageInfo() // Refresh storage info
                    } else {
                        _error.value = response.error ?: "Upload failed"
                    }
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Upload failed"
                }
            
            _loading.value = false
            _uploadProgress.value = 0f
        }
    }
    
    fun deleteFile(filename: String) {
        viewModelScope.launch {
            _loading.value = true
            repository.deleteFile(filename)
                .onSuccess { response ->
                    if (response.success) {
                        loadFiles() // Refresh files list
                        loadStorageInfo() // Refresh storage info
                    } else {
                        _error.value = response.error ?: "Delete failed"
                    }
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Delete failed"
                }
            _loading.value = false
        }
    }
    
    fun downloadFile(filename: String) {
        viewModelScope.launch {
            _loading.value = true
            _downloadProgress.value = 0f
            
            repository.downloadFile(getApplication(), filename) { progress ->
                _downloadProgress.postValue(progress)
            }
                .onSuccess { file ->
                    // File downloaded successfully
                    _error.value = "File downloaded to: ${file.absolutePath}"
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Download failed"
                }
            
            _loading.value = false
            _downloadProgress.value = 0f
        }
    }
    
    fun createShare(filename: String, expiresHours: Int? = null, maxDownloads: Int? = null, password: String? = null) {
        viewModelScope.launch {
            _loading.value = true
            val shareRequest = ShareRequest(filename, expiresHours, maxDownloads, password)
            
            repository.createShare(shareRequest)
                .onSuccess { response ->
                    if (response.success && response.shareUrl != null) {
                        _shareCreated.value = response.shareUrl
                    } else {
                        _error.value = response.error ?: "Failed to create share"
                    }
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Failed to create share"
                }
            _loading.value = false
        }
    }
    
    fun loadFileShares(filename: String) {
        viewModelScope.launch {
            repository.getFileShares(filename)
                .onSuccess { response ->
                    if (response.success) {
                        _shares.value = response.shares
                    } else {
                        _error.value = response.error ?: "Failed to load shares"
                    }
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Failed to load shares"
                }
        }
    }
    
    fun deleteShare(shareId: String) {
        viewModelScope.launch {
            repository.deleteShare(shareId)
                .onSuccess { response ->
                    if (response.success) {
                        // Refresh shares list if we have a current file
                        // This could be improved by keeping track of current file
                    } else {
                        _error.value = response.error ?: "Failed to delete share"
                    }
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Failed to delete share"
                }
        }
    }
    
    fun clearError() {
        _error.value = null
    }
}
