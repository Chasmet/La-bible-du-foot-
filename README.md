# La Bible du Foot

Application Android Java dédiée à l'entraînement football : technique, tactique, postes, préparation physique, récupération et progression, avec niveaux enfant et adulte.

## Version actuelle
- Base Android : 1.0.0
- minSdk 21
- targetSdk 34
- compileSdk 34
- Section Réglages / Mises à jour intégrée
- Dossier `interface/` pour tous les visuels de l'application

## Compilation
GitHub Actions compile l'APK avec :

```bash
./gradlew assembleDebug
```

L'APK est publié dans les Artifacts du workflow `Build APK`.

## Mises à jour sans perte de données
Les futures versions doivent conserver le même `applicationId` (`com.chasmet.labibledufoot`) et être signées avec la même clé de publication. Une APK signée différemment ne peut pas remplacer proprement l'application installée.
