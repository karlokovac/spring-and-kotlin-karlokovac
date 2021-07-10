package com.infinum.library

import java.time.LocalDate
import java.time.Period

enum class RentDuration {
    TWO_WEEKS, MONTH, TWO_MONTHS
}

fun dueDate( duration: RentDuration): LocalDate {
    val period = when (duration) {
        RentDuration.TWO_WEEKS -> Period.ofWeeks(2)
        RentDuration.MONTH -> Period.ofMonths(1)
        RentDuration.TWO_MONTHS -> Period.ofMonths(2)
    }
    return LocalDate.now().plus(period)
}