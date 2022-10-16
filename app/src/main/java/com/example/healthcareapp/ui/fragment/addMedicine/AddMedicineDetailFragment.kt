package com.example.healthcareapp.ui.fragment.addMedicine

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.healthcareapp.R
import com.example.healthcareapp.data.database.entity.EatEntity
import com.example.healthcareapp.databinding.FragmentAddMedicineDetailBinding
import com.example.healthcareapp.viewmodel.AddMedicineViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

@AndroidEntryPoint
class AddMedicineDetailFragment : Fragment() {

    private val addMedicineViewModel: AddMedicineViewModel by viewModels()
    private var _binding: FragmentAddMedicineDetailBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<AddMedicineDetailFragmentArgs>()

    private var count = 0
    private val valueList = ArrayList<Int>()
    private val entries: ArrayList<Entry> = ArrayList()
    private lateinit var eatList: List<EatEntity>
    private lateinit var job: Job

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddMedicineDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViewModel()
        initView()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initViewModel() {
        val dateTimeFormat = SimpleDateFormat("yyyy-MM", Locale.KOREA)
        val time = dateTimeFormat.format(System.currentTimeMillis())

        lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.RESUMED) {
                addMedicineViewModel.getEatMedicineMonth(time, args.medicineEntity.id)
                    .observe(viewLifecycleOwner) { eatEntity ->
                        eatList = eatEntity
                        initGraph()
                    }
            }
        }
    }

    private fun initView() {
        Glide.with(requireContext())
            .load(args.medicineEntity.image)
            .centerCrop()
            .error(R.drawable.ic_error_placeholder)
            .into(binding.imageViewAddDetailMainImage)

        binding.textViewAddDetailTitle.text = args.medicineEntity.name
        binding.textViewAddDetailType.text = args.medicineEntity.type
        binding.textViewAddDetailUse.text = args.medicineEntity.description

        calculateDate()
    }

    private fun calculateDate() {
        val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
        val expireDate = dateFormat.parse(args.medicineEntity.expire)

        val today = Calendar.getInstance()
        val getCalculateDate = (expireDate!!.time - today.time.time) / (60 * 60 * 24 * 1000)

        if (getCalculateDate < 0) {
            binding.textViewAddDetailExpire.text = args.medicineEntity.expire
            binding.textViewAddDetailExpire.setTextColor(Color.RED)
            binding.textViewAddDetailExpire.typeface = Typeface.DEFAULT_BOLD
        } else {
            binding.textViewAddDetailExpire.text = args.medicineEntity.expire
            binding.textViewAddDetailExpire.setTextColor(Color.BLACK)
        }
    }

    private fun initGraph() {

        if (eatList.isNotEmpty()) {
            setValueList()
            setEntry()

            val lineDataSet = LineDataSet(entries, "")
            lineDataSet.apply {
                color = ContextCompat.getColor(requireContext(), R.color.green)
                setDrawValues(false)
                setDrawCircles(false)
                valueTextSize = 12f
                valueFormatter = DefaultValueFormatter(0)
                lineWidth = 3f
                isHighlightEnabled = false
                circleRadius = 6f
                setDrawFilled(true)
                fillDrawable = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.add_detail_graph_background
                )
                mode = LineDataSet.Mode.CUBIC_BEZIER
            }

            val data = LineData(lineDataSet)
            binding.lineChartAddDetailGraph.data = data
            binding.lineChartAddDetailGraph.apply {
                invalidate()
                setScaleEnabled(false)
                setPinchZoom(false)
                setDrawBorders(false)
                isDragYEnabled = false
                description.isEnabled = false
                animateY(1000)
                animateX(1000)
                legend.isEnabled = false
                setVisibleXRangeMaximum(7f)
            }

            val xAxis = binding.lineChartAddDetailGraph.xAxis
            xAxis.apply {
                valueFormatter = XAxisValueFormat()
                position = XAxis.XAxisPosition.BOTTOM
                isGranularityEnabled = true
                setDrawAxisLine(false)
                setDrawGridLines(false)
            }

            val leftAxis = binding.lineChartAddDetailGraph.axisLeft
            leftAxis.apply {
                setDrawAxisLine(false)
                setDrawGridLines(false)
                valueFormatter = YAxisValueFormat()
                axisMinimum = 0f
                granularity = 1f
            }

            val rightAxis = binding.lineChartAddDetailGraph.axisRight
            rightAxis.apply {
                setDrawAxisLine(false)
                setDrawGridLines(false)
                valueFormatter = YAxisValueFormat()
                axisMinimum = 0f
                granularity = 1f
            }

            binding.lineChartAddDetailGraph.visibility = View.VISIBLE
            binding.textViewAddDetailNoEat.visibility = View.INVISIBLE
        } else {
            binding.lineChartAddDetailGraph.visibility = View.INVISIBLE
            binding.textViewAddDetailNoEat.visibility = View.VISIBLE
        }
    }

    private fun setValueList() {
        job = lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                for (i in 1..31) {
                    count = 0
                    for (element in eatList) {
                        val day = element.date.substring(8, 10).toInt()

                        if (day == i) {
                            count++
                        }
                    }

                    valueList.add(count)
                }
            }
        }
    }

    private fun setEntry() {
        for (i in 0 until valueList.size) {
            val lineEntry = Entry(i + 1.toFloat(), valueList[i].toFloat())
            entries.add(lineEntry)
        }
    }

    inner class XAxisValueFormat : IndexAxisValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            val stringFormat = "${value.toInt()} 일"
            return stringFormat.format(value)
        }
    }

    inner class YAxisValueFormat : IndexAxisValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            val stringFormat = "${value.roundToInt()}"
            return stringFormat.format(value)
        }
    }

    override fun onDestroy() {
        _binding = null
        job.cancel()
        super.onDestroy()
    }
}