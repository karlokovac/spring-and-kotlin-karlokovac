package com.infinum.library

fun main(){
    var available = Library.isBookAvailable("The Hunger Games"," Suzanne Collins")
    println("\nThe Hunger Games - Suzanne Collins --- available:$available\n")

    val firstBook = Library.rentBook("The Hunger Games"," Suzanne Collins",
            "0123456789",RentDuration.MONTH)
    val secondBook = Library.rentBook("The Hunger Games"," Suzanne Collins",
        "0123456789",RentDuration.MONTH)
    Library.rentBook("Twilight","Stephenie Meyer",
        "0123456789",RentDuration.TWO_MONTHS)
    Library.rentBook("Harry Potter and the Order of the Phoenix"," J.K. Rowling",
        "0123456789",RentDuration.TWO_WEEKS)
    println("\nfirstBook: $firstBook\nsecondBook: $secondBook\n")

    val imaginary = Book( "Not", "in library")
    try {
        if (firstBook != null) {
            println("$firstBook ; rented: ${Library.isBookRented(firstBook)}")
            Library.returnBook(firstBook)
            println("Returned $firstBook")
            println("$firstBook ; rented: ${Library.isBookRented(firstBook)}")

        }
        println("$imaginary ; rented: ${Library.isBookRented(imaginary)}")
        Library.returnBook(imaginary)
        println("Returned $imaginary")
    }catch ( ex: BookNotRentedException ){
        println("EXCEPTION: ${ex.message}")
    }
    available = Library.isBookAvailable("The Hunger Games"," Suzanne Collins")
    println("\nThe Hunger Games - Suzanne Collins --- available:$available\n")

    val rentedList = Library.getRentedBooks("0123456789")
    println("Rented books for 0123456789")
    for( (book,dueDate) in rentedList )
        println("   $book ; dueDate: $dueDate")
}