package com.example.testapp

import org.junit.Assert
import org.junit.Before
import org.junit.Test


class CounterStateTest {
    private lateinit var counterState: CounterState

    @Before
    fun setup() {
        counterState = CounterState(initialValue = 0)
    }

    @Test
    fun testCounterStateInitialState() {
        Assert.assertEquals(counterState.count, 0)
    }

    @Test
    fun testCounterStateIncrement() {
        val currentValue = counterState.count
        counterState.increment()
        Assert.assertEquals(counterState.count, currentValue + 1)
    }

    @Test
    fun testCounterStateDecrement() {
        val currentValue = counterState.count
        counterState.decrement()
        Assert.assertEquals(counterState.count, currentValue - 1)
    }
}