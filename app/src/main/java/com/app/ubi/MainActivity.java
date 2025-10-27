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

    // --- NUEVAS VARIABLES AÑADIDAS ---
    // Referencias a los componentes de la UI (layout)
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

        // --- CONECTAR UI ---
        // Enlazamos las variables con los IDs del activity_main.xml
        etLatitud = findViewById(R.id.etLatitud);
        etLongitud = findViewById(R.id.etLongitud);
        etTitulo = findViewById(R.id.etTitulo);
        btnCentrar = findViewById(R.id.btnCentrar);
        btnAgregarMarcador = findViewById(R.id.btnAgregarMarcador);
        // --- FIN DE CONEXIÓN UI ---

        // Cargar el fragmento del mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa);
        if (mapFragment == null) {
            Toast.makeText(this, "No se cargo el Mapa", Toast.LENGTH_SHORT).show();
        } else {
            mapFragment.getMapAsync(this);
        }

        // --- LISTENERS DE LOS BOTONES ---
        // REQUERIMIENTO: Implementar botón para centrar en coordenadas
        btnCentrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                centrarMapaManualmente();
            }
        });

        // REQUERIMIENTO: Implementar botón para agregar marcadores
        btnAgregarMarcador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarMarcadorManualmente();
            }
        });
        // --- FIN DE LISTENERS ---
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMapa = googleMap;
        mMapa.getUiSettings().setZoomControlsEnabled(true); // Habilitar controles de zoom

        // --- REQUERIMIENTO: Centrar el mapa en la Plaza de Armas de Cusco ---
        LatLng plazaDeArmasCusco = new LatLng(-13.5167, -71.9788);
        // Usamos newLatLngZoom para centrar Y establecer un nivel de zoom (15f es bueno para una ciudad)
        mMapa.moveCamera(CameraUpdateFactory.newLatLngZoom(plazaDeArmasCusco, 15f));

        // --- REQUERIMIENTO: Agregar al menos 5 marcadores en Cusco ---
        // 1. Plaza de Armas (¡El punto central!)
        mMapa.addMarker(new MarkerOptions()
                .position(plazaDeArmasCusco)
                .title("Plaza de Armas de Cusco"));

        // 2. Sacsayhuamán
        LatLng sacsayhuaman = new LatLng(-13.5085, -71.9816);
        mMapa.addMarker(new MarkerOptions()
                .position(sacsayhuaman)
                .title("Sacsayhuamán"));

        // 3. Qorikancha (Templo del Sol)
        LatLng qorikancha = new LatLng(-13.5204, -71.9757);
        mMapa.addMarker(new MarkerOptions()
                .position(qorikancha)
                .title("Qorikancha"));

        // 4. Mercado San Pedro
        LatLng sanPedro = new LatLng(-13.5195, -71.9840);
        mMapa.addMarker(new MarkerOptions()
                .position(sanPedro)
                .title("Mercado San Pedro"));

        // 5. Cristo Blanco (Otro lugar turístico)
        LatLng cristoBlanco = new LatLng(-13.5106, -71.9780);
        mMapa.addMarker(new MarkerOptions()
                .position(cristoBlanco)
                .title("Cristo Blanco"));
        // --- FIN DE 5 MARCADORES ---

        // (Tu código anterior de la Universidad Continental ha sido reemplazado por los 5 puntos turísticos)
    }

    /**
     * REQUERIMIENTO: Función para el botón "Centrar Mapa"
     */
    private void centrarMapaManualmente() {
        String latitudStr = etLatitud.getText().toString();
        String longitudStr = etLongitud.getText().toString();

        // Validar que los campos no estén vacíos
        if (TextUtils.isEmpty(latitudStr) || TextUtils.isEmpty(longitudStr)) {
            Toast.makeText(this, "Por favor, ingrese latitud y longitud", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double lat = Double.parseDouble(latitudStr);
            double lng = Double.parseDouble(longitudStr);

            LatLng nuevaUbicacion = new LatLng(lat, lng);
            // Mover la cámara (con animación) a la nueva ubicación
            mMapa.animateCamera(CameraUpdateFactory.newLatLngZoom(nuevaUbicacion, 15f));

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Coordenadas inválidas. Use números.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * REQUERIMIENTO: Función para el botón "Agregar Marcador"
     */
    private void agregarMarcadorManualmente() {
        String latitudStr = etLatitud.getText().toString();
        String longitudStr = etLongitud.getText().toString();
        String titulo = etTitulo.getText().toString();

        // Validar que los campos no estén vacíos
        if (TextUtils.isEmpty(latitudStr) || TextUtils.isEmpty(longitudStr) || TextUtils.isEmpty(titulo)) {
            Toast.makeText(this, "Complete todos los campos (Lat, Lng, Título)", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double lat = Double.parseDouble(latitudStr);
            double lng = Double.parseDouble(longitudStr);

            LatLng nuevaUbicacion = new LatLng(lat, lng);

            // Añadir el nuevo marcador al mapa
            mMapa.addMarker(new MarkerOptions()
                    .position(nuevaUbicacion)
                    .title(titulo));

            // Mover la cámara al marcador recién añadido
            mMapa.animateCamera(CameraUpdateFactory.newLatLng(nuevaUbicacion));

            // Limpiar campos después de agregar
            etLatitud.setText("");
            etLongitud.setText("");
            etTitulo.setText("");

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Coordenadas inválidas. Use números.", Toast.LENGTH_SHORT).show();
        }
    }
}
