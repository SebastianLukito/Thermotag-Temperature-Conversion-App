package com.sebastian.thermotag;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class menuKalkulator extends AppCompatActivity {

    EditText inputSuhu;
    TextView textViewResult;
    Spinner spinnerSuhuAsal, spinnerSuhuTujuan;
    Button btnEnter;
    String selectedUnitAsal = "Celsius";
    String selectedUnitTujuan = "Celsius";

/*    TextView textViewTitle3;
    TextView textViewTitle4;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.kalkulatorsuhu);

        inputSuhu = findViewById(R.id.inputSuhu);
        textViewResult = findViewById(R.id.textViewResult);
        spinnerSuhuAsal = findViewById(R.id.spinnerSuhuAsal);
        spinnerSuhuTujuan = findViewById(R.id.spinnerSuhuTujuan);
        btnEnter = findViewById(R.id.btnEnter);
/*      textViewTitle3 = findViewById(R.id.textViewTitle3);
        textViewTitle3.setSelected(true);
        textViewTitle3.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        textViewTitle3.setSingleLine(true);
        textViewTitle3.setMarqueeRepeatLimit(-1);

        textViewTitle4 = findViewById(R.id.textViewTitle4);
        textViewTitle4.setSelected(true);
        textViewTitle4.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        textViewTitle4.setSingleLine(true);
        textViewTitle4.setMarqueeRepeatLimit(-1);*/

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.temperature_units, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSuhuAsal.setAdapter(adapter);
        spinnerSuhuTujuan.setAdapter(adapter);

        spinnerSuhuAsal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedUnitAsal = parent.getItemAtPosition(position).toString();
                updateInputHint(selectedUnitAsal);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Default to Celsius if nothing is selected
                updateInputHint("Celsius");
            }
        });

        spinnerSuhuTujuan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedUnitTujuan = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                konversiSuhu(selectedUnitAsal, selectedUnitTujuan);
            }
        });

        // Set the default hint
        updateInputHint(selectedUnitAsal);
    }

    private void updateInputHint(String unitAsal) {
        String hint = "";
        switch (unitAsal) {
            case "Celsius":
                hint = "°C";
                break;
            case "Fahrenheit":
                hint = "°F";
                break;
            case "Kelvin":
                hint = "K";
                break;
            case "Reamur":
                hint = "°R";
                break;
            default:
                hint = "°C"; // Default to Celsius
                break;
        }
        inputSuhu.setHint("Masukkan Suhu (" + hint + ")");
    }

    @SuppressLint("SetTextI18n")
    private void konversiSuhu(String satuanAsal, String satuanTujuan) {
        try {
            double suhuAsal = Double.parseDouble(inputSuhu.getText().toString());
            double hasilKonversi = 0;
            String symbol = ""; // Initialize symbol

            if (satuanAsal.equals(satuanTujuan)) {
                // Tidak perlu konversi jika satuan sama
                hasilKonversi = suhuAsal;
                switch (satuanTujuan) {
                    case "Celsius":
                        symbol = "°C";
                        break;
                    case "Fahrenheit":
                        symbol = "°F";
                        break;
                    case "Reamur":
                        symbol = "°R";
                        break;
                    case "Kelvin":
                        symbol = "K";
                        break;
                }
            } else {
                // Konversi dari satuan asal ke Celsius terlebih dahulu
                double suhuCelsius = suhuAsal;
                switch (satuanAsal) {
                    case "Fahrenheit":
                        suhuCelsius = (suhuAsal - 32) * 5 / 9;
                        break;
                    case "Reamur":
                        suhuCelsius = suhuAsal * 5 / 4;
                        break;
                    case "Kelvin":
                        suhuCelsius = suhuAsal - 273.15;
                        break;
                }

                // Konversi dari Celsius ke satuan tujuan
                switch (satuanTujuan) {
                    case "Celsius":
                        hasilKonversi = suhuCelsius;
                        symbol = "°C";
                        break;
                    case "Fahrenheit":
                        hasilKonversi = suhuCelsius * 9 / 5 + 32;
                        symbol = "°F";
                        break;
                    case "Reamur":
                        hasilKonversi = suhuCelsius * 4 / 5;
                        symbol = "°R";
                        break;
                    case "Kelvin":
                        hasilKonversi = suhuCelsius + 273.15;
                        symbol = "K";
                        break;
                }
            }

            double thirdDecimal = (hasilKonversi * 1000) % 10;
            if (thirdDecimal > 5) {
                hasilKonversi = Math.ceil(hasilKonversi * 100) / 100;
            } else if (thirdDecimal < 5) {
                hasilKonversi = Math.floor(hasilKonversi * 100) / 100;
            } // if exactly 5, leave it as is

            // Tampilkan hasil konversi dengan simbol yang sesuai dan pembulatan yang ditentukan
            textViewResult.setText(satuanAsal + " ke " + satuanTujuan + ": " + String.format("%.2f", hasilKonversi) + symbol);
        } catch (NumberFormatException e) {
            inputSuhu.setError("Invalid input");
        }
    }
}
