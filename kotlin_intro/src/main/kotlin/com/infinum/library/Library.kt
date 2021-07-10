package com.infinum.library

import java.time.LocalDate
import java.time.Period

object Library {
    private val storedBooks: MutableList<Book> = mutableListOf()
    private val rentedBooks: MutableMap<String,MutableList<RentedBook>> = hashMapOf()

    fun addBooks(vararg books: Book){
        for( book in books )
            storedBooks.add(book)
    }

    fun isBookAvailable(title: String, authorName: String): Boolean{
        return storedBooks.stream().anyMatch({ book -> book.match(title,authorName) })
    }

    fun rentBook(title: String, authorName: String, customerOIB: String, duration: RentDuration): Book?{
        val book = storedBooks.firstOrNull { book -> book.match(title, authorName) } ?: return null
        storedBooks.remove(book)
        if(customerOIB !in rentedBooks.keys) rentedBooks.put(customerOIB, mutableListOf())
        rentedBooks.get(customerOIB)!!.add( RentedBook(book,duration))
        return book;
    }
    fun returnBook(book:Book){
        var returned = false
        searchRented(
            { true },
            { bookList ->
                for( rentedBook in bookList) {
                    if( book.inventoryNumber == rentedBook.book.inventoryNumber) {
                        bookList.remove(rentedBook)
                        storedBooks.add(rentedBook.book)
                        returned = true
                        break
                    }
                }
            })
        if(!returned) throw BookNotRentedException(book)
    }
    fun isBookRented(book: Book): Boolean{
        var rented: Boolean = false
        searchRented(
            { true },
            { bookList ->
                for (rentedBook in bookList) {
                    if (book == rentedBook.book) {
                        rented = true
                        break
                    }
                }
            })
        return rented
    }
    fun getRentedBooks(customerOIB: String): List<RentedBook>?{
        var rentedBooks: MutableList<RentedBook>? = null
        searchRented(
            { mapCustomerOIB -> mapCustomerOIB == customerOIB },
            { bookList -> rentedBooks = bookList })
        return rentedBooks?.toList()
    }

    private fun searchRented(predicate:(String)->Boolean, action:(MutableList<RentedBook>)-> Unit){
        for( (customer,bookList) in rentedBooks.entries) {
            if( predicate.invoke(customer)) {
                action.invoke(bookList)
            }
        }
    }

    data class RentedBook( val book: Book, val dueDate: LocalDate  ){
        constructor( book: Book, duration: RentDuration ):
                this(book, dueDate(duration))
    }

    fun printStoredBooks(){
        for(book in storedBooks)
            println(book)
    }
    fun printRentedBooks(){
        for( (customer,bookList) in rentedBooks.entries) {
            println("$customer:")
            for ( (book,due) in bookList)
                println("   - $book - Due: $due")
        }
    }
}

class BookNotRentedException(book: Book) : RuntimeException("$book was never rented")