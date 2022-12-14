package com.freed_asd.fuel_calculator.presentation.price.screens.dialog

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.freed_asd.fuel_calculator.FuelCalcApp
import com.freed_asd.fuel_calculator.R
import com.freed_asd.fuel_calculator.databinding.FragmentDialogPriceBinding
import com.freed_asd.fuel_calculator.presentation.price.PriceResultUi

class ResultDialogFragment : DialogFragment() {

    private var _binding: FragmentDialogPriceBinding? = null
    private val binding: FragmentDialogPriceBinding get() = _binding!!

    private lateinit var viewModel: ResultViewModel

    override fun onStart() {
        super.onStart()
        val width = resources.displayMetrics.widthPixels * DEFAULT_COEFF
        dialog?.window?.setLayout(width.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = (requireActivity().application as FuelCalcApp).provide()
        viewModel = ViewModelProvider(this, factory)[ResultViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogPriceBinding.inflate(inflater, container, false)

        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.provideResult(onDetails())
        isPassengerAlone()
        viewModel.observe(viewLifecycleOwner){
            binding.liters.text = getString(
                R.string.fuel_need_example,
                String.format("%.2f", it.needFuel())
            )
            binding.tvKilometers.text = getString(
                R.string.distance_example,
                it.distance().toInt()
            )
            binding.generalPriceEd.text = getString(
                R.string.general_price_example,
                String.format("%.2f", it.generalPrice())
            )
            binding.tvValuePriceForEveryone.text = getString(
                R.string.by_person_example,
                String.format("%.2f", it.everyonePrice())
            )
        }
        binding.saveBtnPrice.setOnClickListener {
            val defaultName = getString(R.string.trip_name_default, String.format("%.2f", onDetails().distance()))
            viewModel.insertIntoDb(defaultName)
            binding.isSavedPrice.isVisible = true
        }
    }

    private fun onDetails() = requireArguments().getParcelable<PriceResultUi.Base>(RESULT_DETAILS)
        ?: throw  IllegalStateException("calculation result cannot be null")

    private fun isPassengerAlone() {
        if (onDetails().passengers() == ONE_PASSENGER) {
            binding.tvValuePriceForEveryone.isVisible = false
            binding.tvForEveryoneTripPrice.isVisible = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        const val TAG = "RESULT_FRAGMENT_TAG"
        const val RESULT_DETAILS = "RESULT_DETAILS"
        const val ONE_PASSENGER = 1F
        const val DEFAULT_COEFF = 0.95

        fun newInstance(result: PriceResultUi) = ResultDialogFragment().apply {
            arguments = bundleOf(RESULT_DETAILS to result)
        }
    }
}