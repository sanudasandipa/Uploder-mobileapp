package com.clouduploader.app.data.repository

import android.content.Context
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import com.clouduploader.app.data.api.ApiClient
import com.clouduploader.app.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class CloudRepository {
    
    private val apiService = ApiClient.apiService
    
    suspend fun getFiles(): Result<FileListResponse> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getFiles()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to fetch files: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getStorageInfo(): Result<StorageResponse> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getStorageInfo()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to fetch storage info: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun uploadFile(
        context: Context,
        uri: Uri,
        onProgress: ((Float) -> Unit)? = null
    ): Result<ApiResponse<CloudFile>> = withContext(Dispatchers.IO) {
        try {
            val file = createTempFileFromUri(context, uri)
            val requestBody = file.asRequestBody("*/*".toMediaTypeOrNull())
            val multipartBody = MultipartBody.Part.createFormData("file", file.name, requestBody)
            
            val response = apiService.uploadFile(multipartBody)
            
            // Clean up temp file
            file.delete()
            
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Upload failed: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun deleteFile(filename: String): Result<ApiResponse<String>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.deleteFile(filename)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Delete failed: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun downloadFile(
        context: Context,
        filename: String,
        onProgress: ((Float) -> Unit)? = null
    ): Result<File> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.downloadFile(filename)
            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!
                val downloadsDir = File(context.getExternalFilesDir(null), "Downloads")
                if (!downloadsDir.exists()) {
                    downloadsDir.mkdirs()
                }
                
                val file = File(downloadsDir, filename)
                val inputStream = body.byteStream()
                val outputStream = FileOutputStream(file)
                
                val buffer = ByteArray(8192)
                var totalBytes = 0L
                val contentLength = body.contentLength()
                
                try {
                    var bytesRead: Int
                    while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                        outputStream.write(buffer, 0, bytesRead)
                        totalBytes += bytesRead
                        if (contentLength > 0) {
                            onProgress?.invoke(totalBytes.toFloat() / contentLength)
                        }
                    }
                } finally {
                    inputStream.close()
                    outputStream.close()
                }
                
                Result.success(file)
            } else {
                Result.failure(Exception("Download failed: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun createShare(shareRequest: ShareRequest): Result<ShareResponse> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.createShare(shareRequest)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to create share: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getFileShares(filename: String): Result<ShareListResponse> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getFileShares(filename)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to fetch shares: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun deleteShare(shareId: String): Result<ApiResponse<String>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.deleteShare(shareId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to delete share: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    private fun createTempFileFromUri(context: Context, uri: Uri): File {
        val inputStream: InputStream = context.contentResolver.openInputStream(uri)
            ?: throw Exception("Cannot open input stream")
        
        val documentFile = DocumentFile.fromSingleUri(context, uri)
        val fileName = documentFile?.name ?: "temp_file"
        
        val tempFile = File(context.cacheDir, fileName)
        val outputStream = FileOutputStream(tempFile)
        
        try {
            inputStream.copyTo(outputStream)
        } finally {
            inputStream.close()
            outputStream.close()
        }
        
        return tempFile
    }
}
