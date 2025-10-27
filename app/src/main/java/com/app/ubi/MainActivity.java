package com.app.ubi;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText; // Importar EditText
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMapa;
    private EditText etLatitud, etLongitud, etTitulo;
    private Button btnCentrar, btnAgregarMarcador;
    // --- FIN DE VARIABLES AÑADIDAS ---

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etLatitud = findViewById(R.id.etLatitud);
        etLongitud = findViewById(R.id.etLongitud);
        etTitulo = findViewById(R.id.etTitulo);
        btnCentrar = findViewById(R.id.btnCentrar);
        btnAgregarMarcador = findViewById(R.id.btnAgregarMarcador);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa);
        if (mapFragment == null) {
            Toast.makeText(this, "No se cargo el Mapa", Toast.LENGTH_SHORT).show();
        } else {
            mapFragment.getMapAsync(this);
        }

        btnCentrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                centrarMapaManualmente();
            }
        });

        btnAgregarMarcador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarMarcadorManualmente();
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMapa = googleMap;
        mMapa.getUiSettings().setZoomControlsEnabled(true); // Habilitar controles de zoom

        LatLng plazaDeArmasCusco = new LatLng(-13.5167, -71.9788);
        mMapa.moveCamera(CameraUpdateFactory.newLatLngZoom(plazaDeArmasCusco, 15f));

        mMapa.addMarker(new MarkerOptions()
                .position(plazaDeArmasCusco)
                .title("Plaza de Armas de Cusco"));

        LatLng sacsayhuaman = new LatLng(-13.5085, -71.9816);
        mMapa.addMarker(new MarkerOptions()
                .position(sacsayhuaman)
                .title("Sacsayhuamán"));

        LatLng qorikancha = new LatLng(-13.5204, -71.9757);
        mMapa.addMarker(new MarkerOptions()
                .position(qorikancha)
                .title("Qorikancha"));

        LatLng sanPedro = new LatLng(-13.5195, -71.9840);
        mMapa.addMarker(new MarkerOptions()
                .position(sanPedro)
                .title("Mercado San Pedro"));

        LatLng cristoBlanco = new LatLng(-13.5106, -71.9780);
        mMapa.addMarker(new MarkerOptions()
                .position(cristoBlanco)
                .title("Cristo Blanco"));
    }

    private void centrarMapaManualmente() {
        String latitudStr = etLatitud.getText().toString();
        String longitudStr = etLongitud.getText().toString();

        if (TextUtils.isEmpty(latitudStr) || TextUtils.isEmpty(longitudStr)) {
            Toast.makeText(this, "Por favor, ingrese latitud y longitud", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double lat = Double.parseDouble(latitudStr);
            double lng = Double.parseDouble(longitudStr);

            LatLng nuevaUbicacion = new LatLng(lat, lng);
            mMapa.animateCamera(CameraUpdateFactory.newLatLngZoom(nuevaUbicacion, 15f));

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Coordenadas inválidas. Use números.", Toast.LENGTH_SHORT).show();
        }
    }

    private void agregarMarcadorManualmente() {
        String latitudStr = etLatitud.getText().toString();
        String longitudStr = etLongitud.getText().toString();
        String titulo = etTitulo.getText().toString();

        if (TextUtils.isEmpty(latitudStr) || TextUtils.isEmpty(longitudStr) || TextUtils.isEmpty(titulo)) {
            Toast.makeText(this, "Complete todos los campos (Lat, Lng, Título)", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double lat = Double.parseDouble(latitudStr);
            double lng = Double.parseDouble(longitudStr);

            LatLng nuevaUbicacion = new LatLng(lat, lng);

            mMapa.addMarker(new MarkerOptions()
                    .position(nuevaUbicacion)
                    .title(titulo));

            mMapa.animateCamera(CameraUpdateFactory.newLatLng(nuevaUbicacion));

            etLatitud.setText("");
            etLongitud.setText("");
            etTitulo.setText("");

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Coordenadas inválidas. Use números.", Toast.LENGTH_SHORT).show();
        }
    }
}
