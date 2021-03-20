package br.com.tryyourfood.fragments.recipes.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import br.com.tryyourfood.R
import br.com.tryyourfood.utils.Constants.Companion.DEFAULT_DIET_TYPE
import br.com.tryyourfood.utils.Constants.Companion.DEFAULT_MEAL_TYPE
import br.com.tryyourfood.viewmodel.RecipesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.fragment_bottom_sheet.view.*
import java.lang.Exception
import java.util.*


class RecipeBottomSheet : BottomSheetDialogFragment() {

    private lateinit var mView: View
    private lateinit var recipesViewModel: RecipesViewModel
    private val TAG = RecipeBottomSheet::class.java.canonicalName

    private var mealTypeChip = DEFAULT_MEAL_TYPE
    private var mealTypeChipId = 0
    private var dietTypeChip = DEFAULT_DIET_TYPE
    private var dietTypeChipId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_bottom_sheet, container, false)

        recipesViewModel.readMealAndDietType.asLiveData().observe(viewLifecycleOwner) { value ->
            mealTypeChip = value.selectedMealType
            dietTypeChip = value.selectedDietType

            updateMealChip(value.selectedMealTypeId,mView.mealType_chipGroup_id)
            updateDietChip(value.selectedDietTypeId,mView.dietType_chipGroup_id)
        }

        mView.mealType_chipGroup_id.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedMealType = chip.text.toString().toLowerCase(Locale.ROOT)
            mealTypeChip = selectedMealType
            mealTypeChipId = selectedChipId
        }

        mView.dietType_chipGroup_id.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedDietType = chip.text.toString().toLowerCase(Locale.ROOT)
            dietTypeChip = selectedDietType
            dietTypeChipId = selectedChipId
        }

        mView.applyButton_sheet_id.setOnClickListener {
            recipesViewModel.saveMealAndDietType(
                mealTypeChip,
                mealTypeChipId,
                dietTypeChip,
                dietTypeChipId
            )

            val action = RecipeBottomSheetDirections.actionRecipeBottomSheetToRecipesFragmentNavId(true)
            findNavController().navigate(action)

        }


        return mView
    }

    private fun updateDietChip(chipId: Int, dietChipGroup: ChipGroup?) {
        if(chipId != 0){
            try {
                dietChipGroup?.findViewById<Chip>(chipId)?.isChecked = true
            }catch (e :Exception){
                Log.e(TAG, "updateDietChip: "+e.message )
            }
        }
    }

    private fun updateMealChip(chipId: Int, mealChipGroup: ChipGroup?) {
        if(chipId != 0){
            try {
                mealChipGroup?.findViewById<Chip>(chipId)?.isChecked = true
            }catch (e:Exception){
                Log.e(TAG, "updateDietChip: "+e.message )
            }
        }
    }
}