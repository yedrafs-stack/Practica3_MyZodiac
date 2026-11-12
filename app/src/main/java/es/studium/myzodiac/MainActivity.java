package es.studium.myzodiac;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_EDAD = "edad";
    public static final String EXTRA_SIGNO = "signo";

    private static final DateTimeFormatter FORMATO =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

    private static final int[] CORTE = {20, 19, 21, 20, 21, 21, 23, 23, 23, 23, 22, 22};
    private EditText etFecha;
    private CalendarView calendario;
    private Button btnCalcular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, windowInsets) -> {
            Insets systemBars = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return windowInsets;
        });

        etFecha = findViewById(R.id.etFecha);
        calendario = findViewById(R.id.calendario);
        btnCalcular = findViewById(R.id.btnCalcular);

        calendario.setOnDateChangeListener((view, year, month, dayOfMonth) ->
                etFecha.setText(String.format(Locale.getDefault(), "%02d/%02d/%04d",
                        dayOfMonth, month + 1, year)));

        btnCalcular.setOnClickListener(v -> calcular());
    }

    private void calcular() {
        String texto = etFecha.getText().toString().trim();

        if (texto.isEmpty()) {
            mostrarError(R.string.error_vacio);
            return;
        }

        try {
            LocalDate nacimiento = LocalDate.parse(texto, FORMATO);
            LocalDate hoy = LocalDate.now();

            if (nacimiento.isAfter(hoy)) {
                mostrarError(R.string.error_futura);
                return;
            }

            int edad = Period.between(nacimiento, hoy).getYears();
            int signo = obtenerSigno(nacimiento.getMonthValue(), nacimiento.getDayOfMonth());

            Intent intent = new Intent(this, SecondaryActivity.class);
            intent.putExtra(EXTRA_EDAD, edad);
            intent.putExtra(EXTRA_SIGNO, signo);
            startActivity(intent);

        } catch (DateTimeParseException e) {
            mostrarError(R.string.error_formato);
        }
    }

    private int obtenerSigno(int mes, int dia) {
        return (dia < CORTE[mes - 1]) ? (mes - 1) : (mes % 12);
    }

    private void mostrarError(int mensaje) {
        Snackbar.make(findViewById(R.id.main), mensaje, Snackbar.LENGTH_LONG)
                .setBackgroundTint(getColor(R.color.error))
                .setTextColor(getColor(R.color.blanco))
                .show();
    }
}