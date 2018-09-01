package com.planethackathon.worklifebalancelife

import org.junit.Test

import org.junit.Assert.*
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testCalendar() {
        val calendar = Calendar.getInstance()
        assertEquals(1, calendar.firstDayOfWeek)
    }

    @Test
    fun testCalendar2() {
        val calendar = Calendar.getInstance()
        val lastDate = calendar.getActualMaximum(Calendar.DATE)
        calendar.set(Calendar.DATE, lastDate)

        val lastDay = calendar.get(Calendar.DAY_OF_WEEK)



        println(lastDay)
    }
}
