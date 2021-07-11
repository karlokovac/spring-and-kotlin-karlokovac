package com.infinum.library

import java.time.LocalDate

object Library {
    private val rentedBooks: MutableMap<RentedBook,String> = hashMapOf()
    private val storedBooks: MutableList<Book> = mutableListOf(
        Book("The Hunger Games"," Suzanne Collins"),
        Book("Harry Potter and the Order of the Phoenix"," J.K. Rowling"),
        Book("To Kill a Mockingbird","Harper Lee"),
        Book("Pride and Prejudice"," Jane Austen"),
        Book("Twilight","Stephenie Meyer"),
        Book("The Book Thief","Markus Zusak"),
        Book("Animal Farm","George Orwell"),
        Book("The Chronicles of Narnia","C.S. Lewis"),
        Book("The Hobbit and The Lord of the Rings","J.R.R. Tolkien"),
        Book("The Fault in Our Stars","John Green")
    )

    fun isBookAvailable(title: String, authorName: String): Boolean{
        return storedBooks.any { book -> book.title==title && book.authorName==authorName }
    }

    fun rentBook(title: String, authorName: String, customerOIB: String, duration: RentDuration): Book?{
        return storedBooks
            .firstOrNull { book -> book.title==title && book.authorName==authorName }
            ?.also { book ->
                storedBooks.remove(book)
                rentedBooks[RentedBook(book, dueDate(duration))] = customerOIB
            }
    }
    fun returnBook(book:Book){
        rentedBooks.keys.firstOrNull { rentedBook -> rentedBook.book == book}
            ?.also { rentedBook ->
                rentedBooks.remove(rentedBook)
                storedBooks.add(rentedBook.book)
            } ?: throw BookNotRentedException(book)
    }
    fun isBookRented(book: Book): Boolean{
        return rentedBooks.keys.any{ rentedBook -> rentedBook.book == book }
    }
    fun getRentedBooks(customerOIB: String): List<RentedBook>{
        return rentedBooks.keys.filter { rentedBook ->
            rentedBooks[rentedBook] == customerOIB }
    }

    private fun dueDate(duration: RentDuration): LocalDate = LocalDate.now().plus(duration.period)
    data class RentedBook( val book: Book, val dueDate: LocalDate )
}

class BookNotRentedException(book: Book) : RuntimeException("$book was never rented")