package com.chasmet.labibledufoot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

    private static final String[] RUBRIQUES = {
            "Technique", "Tactique", "Défense", "Milieu", "Attaque", "Gardien",
            "Vitesse", "Endurance", "Explosivité", "Préparation physique", "Récupération",
            "Prévention blessures", "Séances individuelles", "Séances collectives",
            "Échauffements", "Tests & progression", "Créateur de séance", "Favoris",
            "Historique", "Réglages & mises à jour"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout container = findViewById(R.id.rubriquesContainer);
        for (String rubrique : RUBRIQUES) {
            Button button = new Button(this);
            button.setText(rubrique);
            button.setAllCaps(false);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 8, 0, 8);
            button.setLayoutParams(params);

            if (rubrique.startsWith("Réglages")) {
                button.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));
            } else {
                button.setOnClickListener(v -> showComingSoon(rubrique));
            }
            container.addView(button);
        }
    }

    private void showComingSoon(String rubrique) {
        android.widget.Toast.makeText(this,
                rubrique + " : contenu en cours d'intégration",
                android.widget.Toast.LENGTH_SHORT).show();
    }
}
