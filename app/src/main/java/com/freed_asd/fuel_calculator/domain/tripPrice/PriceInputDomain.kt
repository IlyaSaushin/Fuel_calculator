package com.freed_asd.fuel_calculator.domain.tripPrice

<<<<<<< HEAD
data class PriceInputDomain (
    val averageConsumption: Float,
    val distance: Float,
    var fuelPrice: Float = 1f,
    var passengersCount: Float = 1f
)
=======
import com.freed_asd.fuel_calculator.domain.tripPrice.mappers.PriceInputDomainToDataMapper

interface PriceInputDomain {

    fun <T> map(mapper: PriceInputDomainToDataMapper<T>): T

    class Base(
        private val averageConsumption: Float,
        private val distance: Float,
        private var fuelPrice: Float = 1f,
        private var passengersCount: Float = 1f
    ) : PriceInputDomain {
        override fun <T> map(mapper: PriceInputDomainToDataMapper<T>) =
            mapper.map(averageConsumption, distance, fuelPrice, passengersCount)
    }
}
>>>>>>> master
