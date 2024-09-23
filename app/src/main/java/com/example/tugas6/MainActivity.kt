package com.example.tugas6

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.tugas6.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val tujuan = arrayOf(
        "Jakarta", "Bogor", "Bekasi", "Depok", "Bandung",
        "Cimahi", "Cirebon", "Purwakarta", "Garut", "Tasikmalaya",
        "Sukabumi", "Subang", "Yogyakarta", "Surabaya", "Mojokerto",
        "Jombang", "Madiun", "Banyuwangi", "Semarang", "Purwokerto",
        "Kutoarjo", "Gombong"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            setupDropdown(editTextTujuan)
            setupTimePicker(time)
            setupDatePicker(date)


            btnRegister.setOnClickListener {
                showConfirmationDialog()
            }
        }
    }

    private fun setupDropdown(tujuanEditText: EditText) {

        tujuanEditText.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Pilih Tujuan")


            builder.setItems(tujuan) { _, which ->
                tujuanEditText.setText(tujuan[which]) // Set selected item to EditText
            }


            builder.show()
        }
    }

    private fun setupTimePicker(timeEditText: EditText) {

        timeEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)


            val timePickerDialog = TimePickerDialog(
                this,
                { _, selectedHour, selectedMinute ->
                    timeEditText.setText(String.format("%02d:%02d", selectedHour, selectedMinute))
                },
                hour, minute, true
            )
            timePickerDialog.show()
        }
    }

    private fun setupDatePicker(dateEditText: EditText) {

        dateEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val formattedDate = String.format("%02d/%02d/%d", selectedDay, selectedMonth + 1, selectedYear)
                    dateEditText.setText(formattedDate)
                },
                year, month, day
            )
            datePickerDialog.show()
        }
    }

    private fun showConfirmationDialog() {
        with(binding) {

            val nama = username.text.toString()
            val jam = time.text.toString()
            val tanggal = date.text.toString()
            val selectedDestination = editTextTujuan.text.toString()


            if (nama.isEmpty() || jam.isEmpty() || tanggal.isEmpty() || selectedDestination.isEmpty()) {
                Toast.makeText(this@MainActivity, "Silakan lengkapi semua data!", Toast.LENGTH_SHORT).show()
                return
            }


            val ticketPrice = calculateTicketPrice(selectedDestination)


            val dialogView = layoutInflater.inflate(R.layout.dialog_konfirmasi, null)


            val dialogPrice = dialogView.findViewById<TextView>(R.id.price)
            dialogPrice.text = "Rp $ticketPrice"


            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setView(dialogView)

            val dialog = builder.create()


            val buttonCancel = dialogView.findViewById<Button>(R.id.buttonCancel)
            buttonCancel.setOnClickListener {
                Toast.makeText(this@MainActivity, "Pemesanan dibatalkan", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }


            val buttonOk = dialogView.findViewById<Button>(R.id.buttonOk)
            buttonOk.setOnClickListener {
                dialog.dismiss()


                val intent = Intent(this@MainActivity, Result::class.java).apply {
                    putExtra("nama", nama)
                    putExtra("jam", jam)
                    putExtra("tanggal", tanggal)
                    putExtra("tujuan", selectedDestination)
                }
                startActivity(intent)
            }

            dialog.show()
        }
    }


    private fun calculateTicketPrice(destination: String): Int {
        return when (destination) {
            "Jakarta" -> 150000
            "Bogor" -> 120000
            "Bandung" -> 200000
            "Yogyakarta" -> 300000
            "Surabaya" -> 350000
            else -> 100000 // Default rate
        }
    }
}
