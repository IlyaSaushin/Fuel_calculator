package com.freed_asd.fuel_calculator.data.tripPrice

import com.freed_asd.fuel_calculator.data.local.AppDataBase
import com.freed_asd.fuel_calculator.data.local.price.BasePriceItemDbMapper
import com.freed_asd.fuel_calculator.data.tripPrice.dbItem.BasePriceDbToDataItemMapper
import com.freed_asd.fuel_calculator.data.tripPrice.dbItem.PriceDbItemData
import com.freed_asd.fuel_calculator.domain.tripPrice.CalcTripPriceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PriceRepositoryImpl(
    private val appDb: AppDataBase,
    private val priceDbDataToDbMapper: BasePriceItemDbMapper,
    private val dbToDataMapper: BasePriceDbToDataItemMapper
): CalcTripPriceRepository {

    override fun calcTripPrice(input: PriceInputData): PriceResultData {
        return PriceResultData.Base(
            distance = input.distance(),
            needFuel = amountOfFuel(input),
            generalTripPrice = generalPrice(input),
            everyoneTripPrice = priceForOnePerson(input),
            passengers = input.passengers()
        )
    }

    private fun priceForOnePerson(input: PriceInputData) = input.onePersonPrice()

    private fun generalPrice(input: PriceInputData) = input.generalPrice()

    private fun amountOfFuel(input: PriceInputData) = input.needFuel()

    override suspend fun insertIntoDb(value: PriceDbItemData) {
        appDb.priceDao().insertValue(value.mapToDb(priceDbDataToDbMapper))
    }

    override fun allDbValues(): Flow<List<PriceDbItemData>> {
        return appDb.priceDao().allValues().map { list ->
            list.map { dbToDataMapper.mapToData(it) }
        }
    }

    override suspend fun itemById(itemId: Long): PriceDbItemData {
        val itemDb = appDb.priceDao().itemById(itemId)
        return dbToDataMapper.mapToData(itemDb)
    }

    override suspend fun deleteItem(itemId: Long) {
        appDb.priceDao().deleteValue(itemId)
    }

    override suspend fun updateItem(item: Long, newName: String) {
        appDb.priceDao().updateItem(item, newName)
    }
}