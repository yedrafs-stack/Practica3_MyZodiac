package es.studium.myzodiac;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class SecondaryActivity extends AppCompatActivity {

    private static final int[] ICONOS = {
            R.drawable.zodiac_capricornio, R.drawable.zodiac_acuario, R.drawable.zodiac_piscis,
            R.drawable.zodiac_aries, R.drawable.zodiac_tauro, R.drawable.zodiac_geminis,
            R.drawable.zodiac_cancer, R.drawable.zodiac_leo, R.drawable.zodiac_virgo,
            R.drawable.zodiac_libra, R.drawable.zodiac_escorpio, R.drawable.zodiac_sagitario
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, windowInsets) -> {
            Insets systemBars = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return windowInsets;
        });

        TextView tvEdad = findViewById(R.id.tvEdad);
        TextView tvSigno = findViewById(R.id.tvSigno);
        ImageView ivSigno = findViewById(R.id.ivSigno);
        Button btnVolver = findViewById(R.id.btnVolver);

        int edad = getIntent().getIntExtra(MainActivity.EXTRA_EDAD, 0);
        int signo = getIntent().getIntExtra(MainActivity.EXTRA_SIGNO, 0);

        String[] nombres = getResources().getStringArray(R.array.signos);

        tvEdad.setText(getString(R.string.tienes_anios, edad));
        tvSigno.setText(getString(R.string.eres_signo, nombres[signo].toUpperCase(Locale.getDefault())));
        ivSigno.setImageResource(ICONOS[signo]);
        ivSigno.setContentDescription(nombres[signo]);

        btnVolver.setOnClickListener(v -> finish());
    }
}