package com.freed_asd.fuel_calculator.presentation.price.dbItem

import com.freed_asd.fuel_calculator.domain.tripPrice.dbItem.PriceItemDbDomainMapper

class BasePriceDbItemDomainMapperUi: PriceItemDbDomainMapper<PriceDbItemUi> {

    override fun mapToUi(
        id: Long,
        name: String,
        distance: Float,
        needFuel: Float,
        generalPrice: Float,
        everyonePrice: Float
    ) = PriceDbItemUi.Base(id, name, distance, needFuel, generalPrice, everyonePrice)

    override fun mapToData(
        id: Long,
        name: String,
        distance: Float,
        needFuel: Float,
        generalPrice: Float,
        everyonePrice: Float
    ): PriceDbItemUi {
        TODO("Not yet implemented")
    }
}