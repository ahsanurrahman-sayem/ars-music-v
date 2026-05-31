package com.musicplayer.core.common

import java.util.UUID

/**
 * Generates unique identifiers for database entities.
 * Uses UUID v4 for guaranteed uniqueness across sessions.
 */
object IdGenerator {

    /**
     * Generate a new random UUID string.
     */
    fun generate(): String = UUID.randomUUID().toString()

    /**
     * Generate a deterministic ID from a seed string.
     * Useful for generating consistent IDs from URIs.
     */
    fun generateFromSeed(seed: String): String {
        return UUID.nameUUIDFromBytes(seed.toByteArray()).toString()
    }

    /**
     * Generate a session ID for queue sessions.
     */
    fun generateSessionId(): String = "session_${System.currentTimeMillis()}_${generate().take(8)}"
}
