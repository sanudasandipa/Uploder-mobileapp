package com.clouduploader.app.data.api

import com.clouduploader.app.data.model.*
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface CloudApiService {
    
    @GET("files")
    suspend fun getFiles(): Response<FileListResponse>
    
    @GET("storage")
    suspend fun getStorageInfo(): Response<StorageResponse>
    
    @Multipart
    @POST("upload")
    suspend fun uploadFile(
        @Part file: MultipartBody.Part
    ): Response<ApiResponse<CloudFile>>
    
    @DELETE("delete/{filename}")
    suspend fun deleteFile(
        @Path("filename") filename: String
    ): Response<ApiResponse<String>>
    
    @GET("download/{filename}")
    suspend fun downloadFile(
        @Path("filename") filename: String
    ): Response<ResponseBody>
    
    @POST("share")
    suspend fun createShare(
        @Body shareRequest: ShareRequest
    ): Response<ShareResponse>
    
    @GET("shares/{filename}")
    suspend fun getFileShares(
        @Path("filename") filename: String
    ): Response<ShareListResponse>
    
    @DELETE("share/{shareId}")
    suspend fun deleteShare(
        @Path("shareId") shareId: String
    ): Response<ApiResponse<String>>
}
