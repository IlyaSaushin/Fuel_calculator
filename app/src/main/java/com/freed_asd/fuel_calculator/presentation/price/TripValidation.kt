package com.freed_asd.fuel_calculator.presentation.price

import android.widget.EditText
import com.freed_asd.fuel_calculator.R

interface TripValidation {

    fun validate(): Boolean

    class Base(
        private val fuelConsumption: EditText,
        private val distance: EditText,
        private val fuelPrice: EditText,
    ): TripValidation {

        private val context = fuelConsumption.context

        override fun validate(): Boolean {
            val emptyErrorMsg = context.getString(R.string.empty_error_msg)
            return when {
                fuelConsumption.text.isEmpty() -> {
                    fuelConsumption.error = emptyErrorMsg
                    false
                }
                distance.text.isEmpty() -> {
                    distance.error = emptyErrorMsg
                    false
                }
                fuelPrice.text.isEmpty() -> {
                    fuelPrice.error = emptyErrorMsg
                    false
                }
                fuelConsumption.text.toString() == context.getString(R.string.dot) -> {
                    fuelConsumption.error = context.getString(R.string.incorrect_value)
                    false
                }
                fuelPrice.text.toString() == context.getString(R.string.dot) -> {
                    fuelPrice.error = context.getString(R.string.incorrect_value)
                    false
                }
                else -> true
            }
        }
    }
}