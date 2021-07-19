package com.infinum.library

import java.time.Period

enum class RentDuration(var period: Period) {
    TWO_WEEKS (Period.ofWeeks(2)),
    MONTH (Period.ofMonths(1)),
    TWO_MONTHS (Period.ofMonths(2));
}