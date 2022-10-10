package com.example.healthcareapp.ui.fragment.addMedicine

import android.app.*
import android.content.Context.ALARM_SERVICE
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.healthcareapp.R
import com.example.healthcareapp.adapter.AddMedicineAdapter
import com.example.healthcareapp.data.database.entity.MedicineEntity
import com.example.healthcareapp.databinding.FragmentAddMedicineBinding
import com.example.healthcareapp.ui.fragment.addMedicine.receiver.AddMedicineAlarmReceiver
import com.example.healthcareapp.util.Constants
import com.example.healthcareapp.util.Constants.Companion.CHANNEL_DESCRIPTION
import com.example.healthcareapp.util.Constants.Companion.CHANNEL_ID
import com.example.healthcareapp.util.Constants.Companion.CHANNEL_NAME
import com.example.healthcareapp.viewmodel.AddMedicineViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class AddMedicineFragment : Fragment() {

    private val addMedicineViewModel: AddMedicineViewModel by viewModels()
    private val mAdapter by lazy { AddMedicineAdapter(requireActivity(), addMedicineViewModel) }

    private var _binding: FragmentAddMedicineBinding? = null
    private val binding get() = _binding!!

    private lateinit var calendar: Calendar
    private var alarmManager: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent

    private var hour = 12
    private var min = 59

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddMedicineBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initMenu()
        createNotificationChannel()
        initListener()
        initViewModel()

    }

    private fun initMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.add_medicine, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.medicine_add_register -> {
                        moveNextPage()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun moveNextPage() {
        val action =
            AddMedicineFragmentDirections.actionAddMedicineFragmentToAddMedicineFirstFragment()
        findNavController().navigate(action)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName: CharSequence = CHANNEL_NAME
            val description = CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, channelName, importance)
            channel.description = description

            val notificationManager =
                context?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun initListener() {
        binding.floatingButtonAddMedicine.setOnClickListener {
            getTimePicker()
        }
    }

    private fun getTimePicker() {
        calendar = Calendar.getInstance()

        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            calendar[Calendar.HOUR_OF_DAY] = hour
            calendar[Calendar.MINUTE] = minute
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0

            getAlarmManager()
        }

        TimePickerDialog(
            requireContext(),
            timeSetListener,
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }

    private fun getAlarmManager() {
        alarmManager = context?.getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AddMedicineAlarmReceiver::class.java)

        alarmIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            0
        )

        alarmManager?.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            alarmIntent
        )

        Toast.makeText(requireContext(), "알람 설정 완료!", Toast.LENGTH_SHORT).show()
    }


    private fun initViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.RESUMED) {
                addMedicineViewModel.readMedicine.observe(viewLifecycleOwner) { entity ->
                    initRecyclerView(entity)
                }
            }
        }
    }

    private fun initRecyclerView(entity: List<MedicineEntity>) {
        mAdapter.setData(entity)
        binding.recyclerviewAddMedicine.adapter = mAdapter
        binding.recyclerviewAddMedicine.layoutManager =
            LinearLayoutManager(requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        mAdapter.clearContextualActionMode()
    }
}