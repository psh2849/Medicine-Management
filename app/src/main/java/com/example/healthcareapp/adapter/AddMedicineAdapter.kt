package com.example.healthcareapp.adapter

import android.app.DatePickerDialog
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.healthcareapp.R
import com.example.healthcareapp.data.database.entity.MedicineEntity
import com.example.healthcareapp.databinding.ItemAddMedicineBinding
import com.example.healthcareapp.ui.fragment.addMedicine.AddMedicineFragmentDirections
import com.example.healthcareapp.util.DiffUtils
import com.example.healthcareapp.viewmodel.AddMedicineViewModel
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar
import java.util.*

class AddMedicineAdapter(
    private val requireActivity: FragmentActivity,
    private val addMedicineViewModel: AddMedicineViewModel
) :
    RecyclerView.Adapter<AddMedicineAdapter.AddMedicineViewHolder>(),
    ActionMode.Callback {

    private var multiSelection = false
    private lateinit var rootView: View
    private lateinit var mActionMode: ActionMode

    private var selectedMedicines = arrayListOf<MedicineEntity>()
    private var mViewHolder = arrayListOf<AddMedicineViewHolder>()
    private var mMedicines = emptyList<MedicineEntity>()
    private var updateDate = ""

    class AddMedicineViewHolder(private val binding: ItemAddMedicineBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(medicineEntity: MedicineEntity) {
            binding.medicineEntity = medicineEntity
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddMedicineViewHolder {
        return AddMedicineViewHolder(
            ItemAddMedicineBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AddMedicineViewHolder, position: Int) {
        mViewHolder.add(holder)
        rootView = holder.itemView.rootView

        val currentMedicine = mMedicines[position]
        holder.bind(currentMedicine)

        holder.itemView.findViewById<ConstraintLayout>(R.id.cl_medicine).setOnClickListener {
            if (multiSelection) {
                applySelection(holder, currentMedicine)
            } else {
                val action =
                    AddMedicineFragmentDirections.actionAddMedicineFragmentToAddMedicineDetailFragment(
                        currentMedicine
                    )
                holder.itemView.findNavController().navigate(action)
            }
        }

        holder.itemView.findViewById<ConstraintLayout>(R.id.cl_medicine).setOnLongClickListener {
            if (multiSelection) {
                applySelection(holder, currentMedicine)
                multiSelection = false
                false
            } else {
                multiSelection = true
                requireActivity.startActionMode(this)
                applySelection(holder, currentMedicine)
                true
            }
        }

        holder.itemView.findViewById<Button>(R.id.button_add_medicine_update).setOnClickListener {
            getDatePicker(position)
        }
    }

    override fun getItemCount(): Int {
        return mMedicines.size
    }

    private fun applySelection(
        holder: AddMedicineViewHolder,
        currentMedicine: MedicineEntity
    ) {
        if (selectedMedicines.contains(currentMedicine)) {
            selectedMedicines.remove(currentMedicine)
            changeMedicineBackgroundColor(holder, R.color.cardBackgroundLightColor)
        } else {
            selectedMedicines.add(currentMedicine)
            changeMedicineBackgroundColor(
                holder,
                R.color.contextualActionBarColor
            )
        }

        changeActionModeTitleBar()
    }

    private fun changeMedicineBackgroundColor(
        holder: AddMedicineViewHolder,
        strokeColor: Int
    ) {

        holder.itemView.findViewById<MaterialCardView>(R.id.cardView_add_medicine).strokeColor =
            ContextCompat.getColor(requireActivity, strokeColor)

    }

    private fun changeActionModeTitleBar() {
        when (selectedMedicines.size) {
            0 -> {
                mActionMode.finish()
            }

            1 -> {
                mActionMode.title = "${selectedMedicines.size} item selected"
            }

            else -> {
                mActionMode.title = "${selectedMedicines.size} items selected!"
            }
        }
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.add_medicine_delete, menu)
        mActionMode = mode!!
        changeStatusBarColor(R.color.contextualStatusBarColor)

        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        mViewHolder.forEach { holder ->
            changeMedicineBackgroundColor(holder, R.color.cardBackgroundLightColor)
        }
        changeStatusBarColor(R.color.statusBarColor)
        multiSelection = false
        selectedMedicines.clear()
    }

    private fun changeStatusBarColor(color: Int) {
        requireActivity.window.statusBarColor = ContextCompat.getColor(requireActivity, color)
    }

    override fun onActionItemClicked(mode: ActionMode?, menu: MenuItem?): Boolean {
        if (menu?.itemId == R.id.medicine_add_delete) {
            selectedMedicines.forEach { entity ->
                addMedicineViewModel.deleteMedicine(entity)
            }
            showSnackBar("${selectedMedicines.size}개가 삭제 되었습니다.")
            multiSelection = false
            selectedMedicines.clear()
            mode?.finish()
        }

        return true
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            rootView,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("확인") {}
            .show()
    }


    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    fun clearContextualActionMode() {
        if (this::mActionMode.isInitialized) {
            mActionMode.finish()
        }
    }

    private fun getDatePicker(position: Int) {
        val calendar = Calendar.getInstance()

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, changeYear, changeMonth, changeDay ->
                updateDate = "$changeYear.${changeMonth + 1}.$changeDay"
                mMedicines[position].expire = updateDate
                updateNewExpireDate(position)
            }
        DatePickerDialog(requireActivity, dateSetListener, year, month, day).show()
    }

    private fun updateNewExpireDate(position: Int) {
        addMedicineViewModel.updateMedicine(mMedicines[position])
    }

    fun setData(newMedicineList: List<MedicineEntity>) {
        val addMedicineDiffUtil = DiffUtils(mMedicines, newMedicineList)
        val diffUtilResult = DiffUtil.calculateDiff(addMedicineDiffUtil)
        mMedicines = newMedicineList
        diffUtilResult.dispatchUpdatesTo(this)
    }
}