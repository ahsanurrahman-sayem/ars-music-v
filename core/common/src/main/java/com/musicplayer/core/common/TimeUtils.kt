package com.musicplayer.core.common

/**
 * Utility functions for time formatting and conversion.
 */
object TimeUtils {

    /**
     * Format milliseconds to a human-readable duration string.
     * Examples: "3:42", "1:23:45", "0:05"
     */
    fun formatDuration(millis: Long): String {
        if (millis <= 0) return "0:00"

        val totalSeconds = millis / 1000
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60

        return if (hours > 0) {
            String.format("%d:%02d:%02d", hours, minutes, seconds)
        } else {
            String.format("%d:%02d", minutes, seconds)
        }
    }

    /**
     * Parse a duration string back to milliseconds.
     * Supports formats: "3:42", "1:23:45"
     */
    fun parseDuration(durationStr: String): Long {
        val parts = durationStr.split(":")
            .mapNotNull { it.toIntOrNull() }

        return when (parts.size) {
            2 -> (parts[0] * 60L + parts[1]) * 1000L
            3 -> (parts[0] * 3600L + parts[1] * 60L + parts[2]) * 1000L
            else -> 0L
        }
    }

    /**
     * Format a timestamp to relative time string.
     * Examples: "Just now", "5 minutes ago", "2 hours ago", "Yesterday", "3 days ago"
     */
    fun formatRelativeTime(timestamp: Long): String {
        val now = System.currentTimeMillis()
        val diff = now - timestamp

        return when {
            diff < 60_000 -> "Just now"
            diff < 3_600_000 -> "${diff / 60_000}m ago"
            diff < 86_400_000 -> "${diff / 3_600_000}h ago"
            diff < 172_800_000 -> "Yesterday"
            diff < 604_800_000 -> "${diff / 86_400_000}d ago"
            diff < 2_592_000_000 -> "${diff / 604_800_000}w ago"
            diff < 31_536_000_000 -> "${diff / 2_592_000_000}mo ago"
            else -> "${diff / 31_536_000_000}y ago"
        }
    }
}
