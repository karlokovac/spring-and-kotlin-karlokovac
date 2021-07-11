package com.infinum.library

data class Book(
    val title: String,
    val authorName: String,
    val inventoryNumber: Long = generateUniqueCode()
) {
    companion object {
        private var counter: Long = 1
        fun generateUniqueCode(): Long = counter++
    }

    override fun toString() = "$title - $authorName - $inventoryNumber"
}