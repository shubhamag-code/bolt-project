package com.bolt.domain.service

import com.bolt.domain.model.ClassDetail

interface DnDService {
    suspend fun getSummary(): Map<String, Int>
    suspend fun getClassDetails(className: String): ClassDetail?
}
