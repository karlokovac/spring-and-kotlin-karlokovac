package com.infinum.academy.restserver.models

import java.time.Period

private const val HALF_YEAR_MONTHS = 6

enum class UpcomingDuration(val period: Period) {
    WEEK(Period.ofWeeks(1)),
    MONTH(Period.ofMonths(1)),
    HALF_YEAR(Period.ofMonths(HALF_YEAR_MONTHS));
}
