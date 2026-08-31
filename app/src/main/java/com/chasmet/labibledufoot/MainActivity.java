package com.chasmet.labibledufoot;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    private static class Rubrique {
        final String nom;
        final int imageRes;

        Rubrique(String nom, int imageRes) {
            this.nom = nom;
            this.imageRes = imageRes;
        }
    }

    private final Rubrique[] rubriquesIllustrees = {
            new Rubrique("Technique", R.drawable.rubrique_technique),
            new Rubrique("Tactique", R.drawable.rubrique_tactique),
            new Rubrique("Défense", R.drawable.rubrique_defense),
            new Rubrique("Milieu", R.drawable.rubrique_milieu),
            new Rubrique("Attaque", R.drawable.rubrique_attaque),
            new Rubrique("Gardien", R.drawable.rubrique_gardien),
            new Rubrique("Vitesse", R.drawable.rubrique_vitesse)
    };

    private final String[] autresRubriques = {
            "Endurance", "Explosivité", "Préparation physique", "Récupération",
            "Prévention blessures", "Séances individuelles", "Séances collectives",
            "Échauffements", "Tests & progression", "Créateur de séance", "Favoris", "Historique"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout rubriquesContainer = findViewById(R.id.rubriquesContainer);
        LinearLayout autresContainer = findViewById(R.id.autresRubriquesContainer);
        TextView settingsButton = findViewById(R.id.settingsButton);

        for (Rubrique rubrique : rubriquesIllustrees) {
            rubriquesContainer.addView(createImageCard(rubrique));
        }

        for (String rubrique : autresRubriques) {
            autresContainer.addView(createSimpleCard(rubrique));
        }

        settingsButton.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));
    }

    private View createImageCard(Rubrique rubrique) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(0, 0, 0, dp(12));
        card.setBackground(roundedBackground("#0D2E24", 18));
        card.setClickable(true);
        card.setFocusable(true);

        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        cardParams.setMargins(0, 0, 0, dp(16));
        card.setLayoutParams(cardParams);

        ImageView image = new ImageView(this);
        image.setImageResource(rubrique.imageRes);
        image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        image.setContentDescription(rubrique.nom);
        image.setAdjustViewBounds(false);
        image.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dp(190)
        ));
        card.addView(image);

        TextView title = new TextView(this);
        title.setText(rubrique.nom);
        title.setTextColor(Color.WHITE);
        title.setTextSize(20);
        title.setTypeface(null, android.graphics.Typeface.BOLD);
        title.setPadding(dp(16), dp(12), dp(16), 0);
        card.addView(title);

        TextView subtitle = new TextView(this);
        subtitle.setText("Niveau enfant et adulte • Exercices guidés");
        subtitle.setTextColor(Color.parseColor("#B9D0C7"));
        subtitle.setTextSize(13);
        subtitle.setPadding(dp(16), dp(4), dp(16), 0);
        card.addView(subtitle);

        card.setOnClickListener(v -> openCategory(rubrique.nom));
        return card;
    }

    private View createSimpleCard(String rubrique) {
        TextView item = new TextView(this);
        item.setText(rubrique + "  ›");
        item.setTextColor(Color.WHITE);
        item.setTextSize(17);
        item.setGravity(Gravity.CENTER_VERTICAL);
        item.setPadding(dp(18), dp(16), dp(18), dp(16));
        item.setBackground(roundedBackground("#12392D", 14));
        item.setClickable(true);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, dp(10));
        item.setLayoutParams(params);
        item.setOnClickListener(v -> openCategory(rubrique));
        return item;
    }

    private void openCategory(String rubrique) {
        Intent intent = new Intent(this, CategoryActivity.class);
        intent.putExtra(CategoryActivity.EXTRA_CATEGORY, rubrique);
        startActivity(intent);
    }

    private GradientDrawable roundedBackground(String color, int radiusDp) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.parseColor(color));
        drawable.setCornerRadius(dp(radiusDp));
        drawable.setStroke(dp(1), Color.parseColor("#31594C"));
        return drawable;
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }
}
