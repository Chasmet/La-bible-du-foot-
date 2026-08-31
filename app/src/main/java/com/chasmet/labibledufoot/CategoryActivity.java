package com.chasmet.labibledufoot;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class CategoryActivity extends Activity {

    public static final String EXTRA_CATEGORY = "category";

    private String category;
    private LinearLayout contentContainer;
    private Button enfantButton;
    private Button adulteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        category = getIntent().getStringExtra(EXTRA_CATEGORY);
        if (category == null) category = "Football";

        TextView title = findViewById(R.id.categoryTitle);
        ImageView header = findViewById(R.id.categoryHeader);
        enfantButton = findViewById(R.id.btnEnfant);
        adulteButton = findViewById(R.id.btnAdulte);
        contentContainer = findViewById(R.id.categoryContent);

        title.setText(category);
        int image = imageForCategory(category);
        if (image != 0) {
            header.setImageResource(image);
            header.setVisibility(View.VISIBLE);
        } else {
            header.setVisibility(View.GONE);
        }

        enfantButton.setOnClickListener(v -> render(true));
        adulteButton.setOnClickListener(v -> render(false));
        render(true);
    }

    private void render(boolean enfant) {
        contentContainer.removeAllViews();
        styleLevelButtons(enfant);

        String niveau = enfant ? "Enfant" : "Adulte";
        addInfoCard("Niveau", niveau + " • progression adaptée • priorité à la qualité d'exécution");
        addInfoCard("Objectif", objectiveForCategory(category, enfant));
        addTechniqueGuides();

        if (category.equals("Technique")) {
            addExercise("Conduite + changement de direction", enfant ? "12 min" : "15 min", "Ballon • 6 plots",
                    enfant ? "Slalom lent puis accélération sur 5 m. 4 passages de chaque pied. Repos libre entre passages."
                            : "Slalom à haute fréquence, sortie explosive sur 8 m. 6 passages, 30 s de récupération.");
            addExercise("Contrôle orienté + passe", enfant ? "10 min" : "14 min", "Ballons • mur ou partenaire",
                    enfant ? "Contrôle intérieur, orienter le ballon puis passe précise. 3 séries de 8 répétitions."
                            : "Contrôle orienté sous contrainte de temps, passe en 1 ou 2 touches. 4 séries de 10 répétitions.");
        } else if (category.equals("Tactique")) {
            addExercise("Se démarquer entre les lignes", enfant ? "12 min" : "16 min", "Plots • ballon • 3 à 5 joueurs",
                    enfant ? "Créer une zone centrale et apprendre à regarder avant de recevoir."
                            : "Travail de prise d'information, orientation du corps et troisième homme sous pression.");
            addExercise("Transition après perte", enfant ? "10 min" : "15 min", "Chasubles • ballon",
                    enfant ? "À la perte, revenir rapidement derrière le ballon pendant 5 secondes."
                            : "Contre-pressing immédiat 5 secondes, fermer l'axe et protéger la profondeur.");
        } else if (category.equals("Défense")) {
            addExercise("1 contre 1 face à l'attaquant", enfant ? "10 min" : "15 min", "Ballon • plots",
                    enfant ? "Rester de profil, ralentir l'attaquant et attendre le bon moment pour intervenir."
                            : "Temporiser, orienter vers l'extérieur, intervenir sur la mauvaise touche de balle.");
            addExercise("Couverture du partenaire", enfant ? "10 min" : "15 min", "3 joueurs • ballon",
                    enfant ? "Un défenseur sort, le deuxième protège l'espace derrière lui."
                            : "Coordonner sortie, couverture et profondeur avec communication permanente.");
        } else if (category.equals("Milieu")) {
            addExercise("Scanner avant de recevoir", enfant ? "10 min" : "15 min", "Ballon • 4 plots",
                    enfant ? "Regarder derrière soi avant chaque réception, puis jouer vers un côté libre."
                            : "Prise d'information continue, contrôle orienté et passe verticale sous pression.");
            addExercise("Jeu entre les lignes", enfant ? "12 min" : "16 min", "Ballons • partenaires",
                    enfant ? "Se placer dans une zone libre pour recevoir face au jeu."
                            : "Recevoir entre deux lignes, fixer puis trouver un troisième joueur lancé.");
        } else if (category.equals("Attaque")) {
            addExercise("Appel contre-appel", enfant ? "10 min" : "14 min", "Ballon • plots",
                    enfant ? "Faire un petit mouvement vers le ballon puis partir dans la profondeur."
                            : "Synchroniser contre-appel, changement de rythme et timing avec le passeur.");
            addExercise("Finition après contrôle", enfant ? "12 min" : "16 min", "Ballons • but",
                    enfant ? "Contrôle vers le but puis frappe placée. 8 tentatives pied droit, 8 pied gauche."
                            : "Contrôle orienté sous pression puis finition en 2 touches maximum.");
        } else if (category.equals("Gardien")) {
            addExercise("Prises de balle", enfant ? "10 min" : "14 min", "Ballons",
                    enfant ? "Ballons à mi-hauteur et au sol, mains devant le corps, replacer rapidement les appuis."
                            : "Enchaîner prises au sol, mi-hauteur et aériennes avec déplacements latéraux.");
            addExercise("Jeu au pied", enfant ? "10 min" : "15 min", "Ballons • cibles",
                    enfant ? "Passes courtes au sol vers des cibles à 8-12 m."
                            : "Relances courtes puis longues, pied faible inclus, sous contrainte de temps.");
        } else if (category.equals("Vitesse")) {
            addExercise("Accélérations courtes", enfant ? "8 min" : "12 min", "6 plots",
                    enfant ? "6 x 10 m avec départs variés. Récupération complète en marchant."
                            : "2 séries de 5 x 15 m à 95 %, 45 s entre répétitions, 3 min entre séries.");
            addExercise("Réactivité", enfant ? "8 min" : "12 min", "Plots colorés",
                    enfant ? "Partir vers la couleur annoncée, format ludique et court."
                            : "Départs aléatoires visuels ou sonores, changement de direction explosif.");
        } else {
            addExercise("Séance guidée", enfant ? "20 min" : "30 min", "Selon la rubrique",
                    enfant ? "Contenu progressif avec charge réduite et priorité à la coordination."
                            : "Contenu structuré avec volume, intensité et récupération adaptés à l'adulte.");
            addExercise("Progression", enfant ? "10 min" : "15 min", "Carnet ou suivi dans l'application",
                    "Noter la difficulté, la qualité d'exécution et les progrès après chaque séance.");
        }

        addInfoCard("Points clés", enfant
                ? "Expliquer simplement • corriger sans surcharger • garder du plaisir • arrêter si douleur."
                : "Qualité avant volume • récupération suffisante • progression graduelle • arrêter si douleur inhabituelle.");
    }

    private void addTechniqueGuides() {
        List<TechniqueGuide> guides = TechniqueGuide.forCategory(category);
        if (guides.isEmpty()) return;

        TextView heading = textView("Fiches techniques illustrées", 21, Color.parseColor("#102A22"), true);
        heading.setPadding(0, dp(8), 0, dp(10));
        contentContainer.addView(heading);

        for (TechniqueGuide guide : guides) {
            LinearLayout card = baseCard();
            TextView title = textView(guide.title + "  ›", 18, Color.parseColor("#E0BE65"), true);
            TextView desc = textView("Schéma SVG • position du corps • mémos enfant/adulte", 13, Color.parseColor("#C8D9D2"), false);
            desc.setPadding(0, dp(5), 0, 0);
            card.addView(title);
            card.addView(desc);
            card.setClickable(true);
            card.setFocusable(true);
            card.setOnClickListener(v -> {
                Intent intent = new Intent(this, TechniqueDetailActivity.class);
                intent.putExtra(TechniqueDetailActivity.EXTRA_GUIDE_ID, guide.id);
                startActivity(intent);
            });
            contentContainer.addView(card);
        }
    }

    private void addInfoCard(String title, String text) {
        LinearLayout card = baseCard();
        TextView t = textView(title, 18, Color.WHITE, true);
        TextView d = textView(text, 14, Color.parseColor("#C8D9D2"), false);
        d.setPadding(0, dp(6), 0, 0);
        card.addView(t);
        card.addView(d);
        contentContainer.addView(card);
    }

    private void addExercise(String title, String duration, String material, String instructions) {
        LinearLayout card = baseCard();
        TextView t = textView(title, 19, Color.parseColor("#E0BE65"), true);
        TextView meta = textView("Durée : " + duration + "\nMatériel : " + material, 14, Color.WHITE, false);
        TextView desc = textView(instructions, 14, Color.parseColor("#C8D9D2"), false);
        meta.setPadding(0, dp(8), 0, 0);
        desc.setPadding(0, dp(8), 0, 0);
        card.addView(t);
        card.addView(meta);
        card.addView(desc);
        contentContainer.addView(card);
    }

    private LinearLayout baseCard() {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(16), dp(16), dp(16), dp(16));
        GradientDrawable bg = new GradientDrawable();
        bg.setColor(Color.parseColor("#0F3026"));
        bg.setCornerRadius(dp(16));
        bg.setStroke(dp(1), Color.parseColor("#31594C"));
        card.setBackground(bg);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        p.setMargins(0, 0, 0, dp(12));
        card.setLayoutParams(p);
        return card;
    }

    private TextView textView(String text, int size, int color, boolean bold) {
        TextView v = new TextView(this);
        v.setText(text);
        v.setTextSize(size);
        v.setTextColor(color);
        if (bold) v.setTypeface(null, Typeface.BOLD);
        return v;
    }

    private void styleLevelButtons(boolean enfant) {
        enfantButton.setAlpha(enfant ? 1f : 0.55f);
        adulteButton.setAlpha(enfant ? 0.55f : 1f);
    }

    private String objectiveForCategory(String category, boolean enfant) {
        String base;
        switch (category) {
            case "Technique": base = "Améliorer la maîtrise du ballon, le contrôle et la précision."; break;
            case "Tactique": base = "Comprendre le placement, les espaces, les transitions et les décisions."; break;
            case "Défense": base = "Mieux défendre en duel, couvrir et protéger la profondeur."; break;
            case "Milieu": base = "Prendre l'information, orienter le jeu et jouer entre les lignes."; break;
            case "Attaque": base = "Créer des appels, se démarquer et finir les actions."; break;
            case "Gardien": base = "Développer prises de balle, réflexes, placement et jeu au pied."; break;
            case "Vitesse": base = "Développer accélération, réactivité et changements de direction."; break;
            default: base = "Progresser de façon structurée et mesurable dans cette rubrique.";
        }
        return base + (enfant ? " Charge légère et ludique." : " Intensité et contraintes supérieures.");
    }

    private int imageForCategory(String category) {
        switch (category) {
            case "Technique": return R.drawable.rubrique_technique;
            case "Tactique": return R.drawable.rubrique_tactique;
            case "Défense": return R.drawable.rubrique_defense;
            case "Milieu": return R.drawable.rubrique_milieu;
            case "Attaque": return R.drawable.rubrique_attaque;
            case "Gardien": return R.drawable.rubrique_gardien;
            case "Vitesse": return R.drawable.rubrique_vitesse;
            default: return 0;
        }
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }
}
