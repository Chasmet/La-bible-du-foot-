package com.chasmet.labibledufoot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TechniqueGuide {
    public final String id;
    public final String title;
    public final String asset;
    public final String enfant;
    public final String adulte;
    public final List<String> categories;

    TechniqueGuide(String id, String title, String asset, String enfant, String adulte, String... categories) {
        this.id = id;
        this.title = title;
        this.asset = asset;
        this.enfant = enfant;
        this.adulte = adulte;
        this.categories = Arrays.asList(categories);
    }

    public static final TechniqueGuide[] ALL = {
            new TechniqueGuide(
                    "position_corps", "Position du corps", "techniques/position_du_corps.svg",
                    "MÉMO ENFANT\n• Mets-toi légèrement de profil.\n• Plie un peu les genoux.\n• Reste léger sur l’avant des pieds.\n• Regarde le jeu avant que le ballon arrive.\n• Ouvre les bras pour rester équilibré.\n• Oriente ton premier toucher vers l’espace libre.\n\nERREUR À ÉVITER : recevoir complètement face au ballon et rester sur les talons.",
                    "MÉMO ADULTE\n• Orientation du bassin à 30–45° pour voir ballon + jeu.\n• Centre de gravité légèrement abaissé.\n• Appuis dynamiques, jamais figés.\n• Scanner avant, pendant et après la réception.\n• Premier contrôle préparant l’action suivante.\n• Utiliser bras et épaule pour stabiliser sans faire faute.\n\nPOINT EXPERT : le corps doit déjà annoncer l’action suivante avant le contact.",
                    "Technique", "Tactique", "Défense", "Milieu", "Gardien"),
            new TechniqueGuide(
                    "prise_information", "Prise d’information", "techniques/prise_information.svg",
                    "MÉMO ENFANT\n• Regarde autour de toi avant la passe.\n• Fais gauche → milieu → droite.\n• Repère un copain, un adversaire et l’espace libre.\n• Décide où jouer avant de recevoir.\n• Relève vite la tête après ton contrôle.\n\nJEU MÉMO : essaie de dire la couleur d’un plot placé derrière toi avant de recevoir.",
                    "MÉMO ADULTE\n• Scanner toutes les 1–2 secondes quand l’action approche.\n• Identifier pression, homme libre et profondeur.\n• Lire l’épaule du défenseur et la position du partenaire.\n• Pré-décider deux solutions avant réception.\n• Scanner encore immédiatement après la première touche.\n\nPOINT EXPERT : la qualité technique dépend souvent de l’information prise avant le ballon.",
                    "Technique", "Tactique", "Milieu", "Gardien"),
            new TechniqueGuide(
                    "controle_oriente", "Contrôle orienté", "techniques/controle_oriente.svg",
                    "MÉMO ENFANT\n• Prépare ton pied avant que le ballon arrive.\n• Amortis, ne tape pas le ballon.\n• Oriente-le vers l’espace libre.\n• Fais un contrôle qui te permet de repartir tout de suite.\n• Regarde devant toi après le toucher.\n\nOBJECTIF : contrôle + action suivante en deux touches maximum.",
                    "MÉMO ADULTE\n• Pied d’accueil relâché au contact puis ferme pour guider.\n• Première touche hors de la ligne de pression.\n• Contrôle avec intérieur, extérieur ou semelle selon l’angle.\n• Distance du contrôle adaptée à la vitesse suivante.\n• Enchaîner passe, conduite ou frappe sans temps mort.\n\nPOINT EXPERT : orienter le contrôle du côté opposé au pressing quand c’est possible.",
                    "Technique", "Milieu", "Attaque", "Gardien"),
            new TechniqueGuide(
                    "passe", "La passe", "techniques/passe.svg",
                    "MÉMO ENFANT\n• Mets ton pied d’appui à côté du ballon.\n• Pointe le pied d’appui vers la cible.\n• Bloque la cheville du pied qui frappe.\n• Utilise l’intérieur du pied pour la précision.\n• Dose la force selon la distance.\n• Termine ton geste vers ton partenaire.",
                    "MÉMO ADULTE\n• Ajuster angle d’approche et pied d’appui.\n• Cheville verrouillée et surface de contact propre.\n• Donner la bonne vitesse au ballon, pas seulement la direction.\n• Passe devant le partenaire quand il est en mouvement.\n• Masquer l’intention avec regard et posture si possible.\n• Varier intérieur, extérieur, cou-de-pied et passes verticales.\n\nPOINT EXPERT : une bonne passe améliore la prochaine action du receveur.",
                    "Technique", "Milieu", "Gardien", "Tactique"),
            new TechniqueGuide(
                    "conduite", "Conduite de balle", "techniques/conduite_balle.svg",
                    "MÉMO ENFANT\n• Fais de petits contacts quand il y a du monde.\n• Garde le ballon près du pied.\n• Regarde devant toi régulièrement.\n• Utilise pied droit et pied gauche.\n• Pousse plus loin le ballon quand l’espace est libre.\n• Accélère après un changement de direction.",
                    "MÉMO ADULTE\n• Adapter fréquence et longueur des touches à l’espace.\n• Alterner intérieur, extérieur et cou-de-pied.\n• Scanner entre deux contacts.\n• Protéger la balle avec le corps sur la trajectoire.\n• Changer de vitesse avant et après le duel.\n• Conduite longue uniquement si l’espace est réellement libre.\n\nPOINT EXPERT : la conduite sert à déplacer le bloc adverse, pas seulement à avancer.",
                    "Technique", "Attaque", "Vitesse"),
            new TechniqueGuide(
                    "dribble", "Dribble", "techniques/dribble.svg",
                    "MÉMO ENFANT\n• Approche l’adversaire sans aller trop vite.\n• Regarde ses pieds et son équilibre.\n• Fais une feinte claire avec ton corps.\n• Change vite de direction.\n• Accélère juste après.\n• Remets ton corps entre le défenseur et le ballon.",
                    "MÉMO ADULTE\n• Fixer pour obliger le défenseur à choisir.\n• Centre de gravité bas avant le geste.\n• Déclencher sur un appui lourd ou une jambe ouverte.\n• Variation de rythme indispensable après la feinte.\n• Sortie de dribble protégée par épaule et bassin.\n• Choisir dribble intérieur, extérieur ou ligne selon couverture.\n\nPOINT EXPERT : le dribble est gagné par le changement de rythme autant que par le geste.",
                    "Technique", "Attaque", "Vitesse"),
            new TechniqueGuide(
                    "frappe", "La frappe", "techniques/frappe.svg",
                    "MÉMO ENFANT\n• Regarde où tu veux tirer.\n• Mets le pied d’appui près du ballon.\n• Garde la cheville ferme.\n• Frappe avec le cou-de-pied pour la puissance.\n• Penche légèrement le corps au-dessus du ballon.\n• Termine ton geste vers la cible.",
                    "MÉMO ADULTE\n• Dernier pas d’approche stable et pied d’appui précis.\n• Cheville verrouillée au moment de l’impact.\n• Contact au centre ou légèrement sous le centre selon trajectoire.\n• Bras opposé utilisé pour équilibre et rotation.\n• Tête stable pendant l’impact.\n• Suivi du geste vers la zone visée.\n\nPOINT EXPERT : précision d’abord, puissance ensuite.",
                    "Technique", "Attaque"),
            new TechniqueGuide(
                    "protection", "Protection du ballon", "techniques/protection_ballon.svg",
                    "MÉMO ENFANT\n• Mets ton corps entre l’adversaire et le ballon.\n• Plie les jambes pour être solide.\n• Garde les bras ouverts sans pousser.\n• Mets le ballon du côté opposé au défenseur.\n• Sens où se trouve l’adversaire.\n• Dès qu’il y a un espace, repars vite.",
                    "MÉMO ADULTE\n• Utiliser bassin, épaule et appuis pour créer une barrière légale.\n• Ballon toujours hors de la ligne d’intervention du défenseur.\n• Bras actif pour sentir, jamais pour repousser.\n• Variations semelle/intérieur/extérieur pour conserver.\n• Attirer la pression puis sortir côté opposé.\n• Préparer la passe de soutien avant d’être enfermé.\n\nPOINT EXPERT : protéger n’est pas arrêter le jeu, c’est gagner le temps pour la prochaine solution.",
                    "Technique", "Défense", "Milieu", "Attaque"),
            new TechniqueGuide(
                    "jeu_tete", "Jeu de tête", "techniques/jeu_de_tete.svg",
                    "MÉMO ENFANT\n• Regarde le ballon jusqu’au contact.\n• Place-toi sous sa trajectoire.\n• Utilise tes bras pour l’équilibre.\n• Frappe avec le front, pas le sommet de la tête.\n• Dirige la tête vers la cible.\n\nENFANT : privilégier ballons légers, faible volume et apprentissage progressif avec éducateur.",
                    "MÉMO ADULTE\n• Lecture précoce de trajectoire et prise d’espace.\n• Impulsion synchronisée avec l’arrivée du ballon.\n• Gainer nuque et tronc au contact.\n• Front comme surface principale.\n• Mouvement du haut du corps vers la cible.\n• En duel : utiliser bras pour équilibre sans pousser.\n\nPOINT EXPERT : timing et positionnement comptent davantage que la force brute.",
                    "Technique", "Défense", "Attaque"),
            new TechniqueGuide(
                    "appuis", "Appuis & coordination", "techniques/appuis_coordination.svg",
                    "MÉMO ENFANT\n• Reste léger sur l’avant des pieds.\n• Fais des pas courts et précis.\n• Garde le buste droit et solide.\n• Utilise les bras naturellement.\n• Regarde devant toi, pas uniquement tes pieds.\n• Garde le même rythme jusqu’à la fin.",
                    "MÉMO ADULTE\n• Appuis réactifs sous le centre de gravité.\n• Fréquence élevée sans perdre la qualité.\n• Gainage dynamique du tronc.\n• Coordination bras-jambes naturelle.\n• Sortie de ladder immédiatement suivie d’un geste football.\n• Récupération suffisante pour garder de la vitesse.\n\nPOINT EXPERT : la coordination doit transférer vers accélération, freinage et changement de direction.",
                    "Technique", "Vitesse", "Préparation physique")
    };

    public static TechniqueGuide find(String id) {
        for (TechniqueGuide guide : ALL) if (guide.id.equals(id)) return guide;
        return ALL[0];
    }

    public static List<TechniqueGuide> forCategory(String category) {
        List<TechniqueGuide> result = new ArrayList<>();
        for (TechniqueGuide guide : ALL) {
            if (guide.categories.contains(category)) result.add(guide);
        }
        return result;
    }
}
