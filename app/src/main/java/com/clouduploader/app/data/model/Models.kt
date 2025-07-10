package com.clouduploader.app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CloudFile(
    val name: String,
    val size: Long,
    val size_formatted: String,
    val modified: String,
    val type: String
) : Parcelable {
    val sizeFormatted: String get() = size_formatted
}

data class ApiResponse<T>(
    val success: Boolean,
    val message: String? = null,
    val error: String? = null,
    val data: T? = null
)

data class FileListResponse(
    val success: Boolean,
    val files: List<CloudFile>,
    val totalFiles: Int,
    val error: String? = null
)

data class StorageInfo(
    val used_space: Long,
    val used_space_formatted: String,
    val total_space: Long,
    val total_space_formatted: String,
    val free_space: Long,
    val free_space_formatted: String,
    val file_count: Int,
    val max_file_size: Long,
    val max_file_size_formatted: String
) {
    val usedSpace: Long get() = used_space
    val usedSpaceFormatted: String get() = used_space_formatted
    val totalSpace: Long get() = total_space
    val totalSpaceFormatted: String get() = total_space_formatted
    val freeSpace: Long get() = free_space
    val freeSpaceFormatted: String get() = free_space_formatted
    val fileCount: Int get() = file_count
    val maxFileSize: Long get() = max_file_size
    val maxFileSizeFormatted: String get() = max_file_size_formatted
}

data class StorageResponse(
    val success: Boolean,
    val storage: StorageInfo? = null,
    val error: String? = null
)

data class ShareRequest(
    val filename: String,
    val expires_hours: Int? = null,
    val max_downloads: Int? = null,
    val password: String? = null
)

data class ShareResponse(
    val success: Boolean,
    val share_id: String? = null,
    val share_url: String? = null,
    val message: String? = null,
    val error: String? = null
) {
    val shareId: String? get() = share_id
    val shareUrl: String? get() = share_url
}

data class FileShare(
    val share_id: String,
    val share_url: String,
    val created_at: String,
    val expires_at: String?,
    val download_count: Int,
    val max_downloads: Int?,
    val has_password: Boolean
) {
    val shareId: String get() = share_id
    val shareUrl: String get() = share_url
    val createdAt: String get() = created_at
    val expiresAt: String? get() = expires_at
    val downloadCount: Int get() = download_count
    val maxDownloads: Int? get() = max_downloads
    val hasPassword: Boolean get() = has_password
}

data class ShareListResponse(
    val success: Boolean,
    val shares: List<FileShare>,
    val error: String? = null
)
