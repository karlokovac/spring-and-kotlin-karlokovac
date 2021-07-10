package com.infinum.library

data class Book(
    val title: String,
    val authorName: String,
    val inventoryNumber: Long
) {
    constructor(title: String,authorName: String):
            this(title,authorName,generateUniqueCode())

    companion object {
        private var counter: Long = 1
        fun generateUniqueCode(): Long = counter++
    }

    fun match(title: String,authorName: String): Boolean{
        return this.title==title && this.authorName==authorName
    }

    override fun toString() = "$title - $authorName - $inventoryNumber"
}