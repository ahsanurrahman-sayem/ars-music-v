package com.musicplayer.core.common

import android.content.Intent
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Utility functions for working with file URIs and content URIs.
 * Handles the complexities of Storage Access Framework (SAF) URIs.
 */
object FileUriUtils {

    /**
     * Extract file metadata from a content URI.
     * Returns a map with display name, size, and MIME type.
     */
    suspend fun getFileMetadata(context: Context, uri: Uri): FileMetadata = withContext(Dispatchers.IO) {
        val contentResolver = context.contentResolver
        var displayName = "Unknown"
        var size = 0L
        var mimeType = contentResolver.getType(uri) ?: "audio/*"

        contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex >= 0) {
                    displayName = cursor.getString(nameIndex) ?: displayName
                }
                val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                if (sizeIndex >= 0) {
                    size = cursor.getLong(sizeIndex)
                }
            }
        }

        FileMetadata(
            displayName = displayName,
            size = size,
            mimeType = mimeType,
            extension = MimeTypeMap.getSingleton()
                .getExtensionFromMimeType(mimeType) ?: ""
        )
    }

    /**
     * Check if we have persistable URI permission for the given URI.
     */
fun hasPersistablePermission(context: Context, uri: Uri): Boolean {
	return context.contentResolver.persistedUriPermissions.any {
		it.uri == uri && it.isReadPermission
	}
}
	/**
     * Take persistable URI permission for the given URI.
     */
    fun takePersistablePermission(context: Context, uri: Uri) {
        try {
            context.contentResolver.takePersistableUriPermission(
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
        } catch (e: SecurityException) {
            // Permission not grantable - will need to handle gracefully
        }
    }

    /**
     * Check if a URI is still valid/accessible.
     */
    suspend fun isUriValid(context: Context, uri: Uri): Boolean = withContext(Dispatchers.IO) {
        try {
            context.contentResolver.openFileDescriptor(uri, "r")?.close()
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Get a file name without extension.
     */
    fun getBaseName(fileName: String): String {
        val lastDot = fileName.lastIndexOf('.')
        return if (lastDot > 0) fileName.substring(0, lastDot) else fileName
    }
}

/**
 * File metadata extracted from a content URI.
 */
data class FileMetadata(
    val displayName: String,
    val size: Long,
    val mimeType: String,
    val extension: String
)
