    package org.moveitcapital.simcap.domain.repository

interface SimcapRepository {
    suspend fun fetchItems(): List<String>
}