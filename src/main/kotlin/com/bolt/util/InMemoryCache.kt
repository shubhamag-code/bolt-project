package com.bolt.util

import java.util.concurrent.ConcurrentHashMap

data class CacheEntry<T>(val data: T, val expiresAt: Long)

object InMemoryCache {
    private const val TTL = 5 * 60 * 1000L // 5 minutes
    private val store = ConcurrentHashMap<String, CacheEntry<Any>>()

    fun <T : Any> get(key: String): T? {
        val entry = store[key] ?: return null
        return if (System.currentTimeMillis() < entry.expiresAt) {
            @Suppress("UNCHECKED_CAST")
            entry.data as T
        } else {
            store.remove(key)
            null
        }
    }

    fun <T : Any> put(key: String, value: T, ttl: Long = TTL) {
        store[key] = CacheEntry(value, System.currentTimeMillis() + ttl)
    }
}
