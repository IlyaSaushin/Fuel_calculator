package com.freedasd.fuel_calculator.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.freedasd.fuel_calculator.domain.distance.interactor.DistanceInteractor
import com.freedasd.fuel_calculator.domain.distance.mappers.BaseInputUiToDomainMapper
import com.freedasd.fuel_calculator.domain.tripPrice.usecase.CalcTripPriceUseCase
import com.freedasd.fuel_calculator.presentation.distance.mappers.BaseResultDomainToUiMapper
import com.freedasd.fuel_calculator.presentation.distance.screens.DistanceViewModel
import com.freedasd.fuel_calculator.presentation.distance.screens.dialog.DialogFragmentViewModel
import com.freedasd.fuel_calculator.presentation.tripPrice.uiPriceMappers.PriceInputUiMapper
import com.freedasd.fuel_calculator.presentation.tripPrice.uiPriceMappers.PriceResultUiMapper
import com.freedasd.fuel_calculator.presentation.tripPrice.screens.PriceFragmentViewModel
import com.freedasd.fuel_calculator.presentation.tripPrice.screens.dialog.ResultViewModel
import com.freedasd.fuel_calculator.domain.consumption.interactor.ConsInteractor
import com.freedasd.fuel_calculator.domain.consumption.mappers.BaseConsInputUiToDomainMapper
import com.freedasd.fuel_calculator.presentation.consumption.mappers.BaseConsResultDomainToUiMapper
import com.freedasd.fuel_calculator.presentation.consumption.screens.ConsFragViewModel
import com.freedasd.fuel_calculator.presentation.consumption.screens.dialog.ConsDialogViewModel

class ViewModelsFactory(
    private val calcPriceUseCase: CalcTripPriceUseCase,
    private val inputTripMapper: PriceInputUiMapper,
    private val resultTripMapper: PriceResultUiMapper,
    private val distanceInteractor: DistanceInteractor,
    private val inputDistanceMapper: BaseInputUiToDomainMapper,
    private val resultDistanceMapper: BaseResultDomainToUiMapper,
    private val consInteractor: ConsInteractor,
    private val inputConsMapper: BaseConsInputUiToDomainMapper,
    private val resultConsMapper: BaseConsResultDomainToUiMapper
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(PriceFragmentViewModel::class.java) -> PriceFragmentViewModel(
                calcPriceUseCase,
                inputTripMapper,
                resultTripMapper
            )
            modelClass.isAssignableFrom(ResultViewModel::class.java) -> ResultViewModel()
            modelClass.isAssignableFrom(DistanceViewModel::class.java) -> DistanceViewModel(
                distanceInteractor,
                inputDistanceMapper,
                resultDistanceMapper
            )
            modelClass.isAssignableFrom(DialogFragmentViewModel::class.java) -> DialogFragmentViewModel()
            modelClass.isAssignableFrom(ConsFragViewModel::class.java) -> ConsFragViewModel(
                consInteractor,
                inputConsMapper,
                resultConsMapper
            )
            modelClass.isAssignableFrom(ConsDialogViewModel::class.java) -> ConsDialogViewModel()
            else -> throw IllegalStateException("model class $modelClass not found")
        } as T
    }
}