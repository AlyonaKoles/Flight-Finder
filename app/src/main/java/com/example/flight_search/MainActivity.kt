package com.example.flight_search

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val departureDate = findViewById<EditText>(R.id.editDepartureDate)
        val arrivalDate = findViewById<EditText>(R.id.editArrivalDate)
        val departureCity = findViewById<Spinner>(R.id.spinnerDeparture)
        val arrivalCity = findViewById<Spinner>(R.id.spinnerArrival)
        val adults = findViewById<EditText>(R.id.editAdults)
        val children = findViewById<EditText>(R.id.editChildren)
        val searchButton = findViewById<Button>(R.id.buttonSearch)
        val result = findViewById<TextView>(R.id.textSearchResult)

        departureDate.setOnClickListener {
            showDatePicker(departureDate, getString(R.string.choose_departure_date))
        }
        arrivalDate.setOnClickListener {
            showDatePicker(arrivalDate, getString(R.string.choose_arrival_date))
        }

        searchButton.setOnClickListener {
            val adultCount = adults.text.toString().toIntOrNull() ?: 0
            val childCount = children.text.toString().toIntOrNull() ?: 0

            if (adultCount < 1 || childCount < 0 ) {
                Toast.makeText(this, "Укажите корректное число пассажиров (минимум 1 взрослый)", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (departureCity.selectedItemPosition == arrivalCity.selectedItemPosition) {
                Toast.makeText(this, "Выберите разные города вылета и прилёта", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (departureDate.text.isBlank() || arrivalDate.text.isBlank()) {
                Toast.makeText(this, "Выберите даты вылета и прилёта", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            result.text = getString(
                R.string.search_summary,
                departureCity.selectedItem.toString(),
                arrivalCity.selectedItem.toString(),
                departureDate.text.toString(),
                arrivalDate.text.toString(),
                adultCount,
                childCount,
            )
        }
    }

    private fun showDatePicker(target: EditText, title: String) {
        val calendar = Calendar.getInstance()
        val picker = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val selected = Calendar.getInstance().apply {
                    set(year, month, dayOfMonth)
                }
                target.setText(SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(selected.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        picker.setTitle(title)
        picker.datePicker.minDate = System.currentTimeMillis() - 1000
        picker.show()
    }
}
