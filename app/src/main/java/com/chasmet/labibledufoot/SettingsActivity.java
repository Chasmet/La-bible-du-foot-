package com.chasmet.labibledufoot;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.FileProvider;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SettingsActivity extends Activity {

    private static final String RELEASES_API = "https://api.github.com/repos/Chasmet/La-bible-du-foot-/releases/latest";

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private TextView statusText;
    private Button updateButton;
    private String apkDownloadUrl;
    private String latestVersion;
    private File pendingApk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        TextView versionText = findViewById(R.id.versionText);
        statusText = findViewById(R.id.updateStatusText);
        Button checkButton = findViewById(R.id.checkUpdateButton);
        updateButton = findViewById(R.id.openUpdateButton);

        versionText.setText("Version installée : " + BuildConfig.VERSION_NAME);
        checkButton.setOnClickListener(v -> checkForUpdate());
        updateButton.setOnClickListener(v -> downloadAndInstall());

        checkForUpdate();
    }

    private void checkForUpdate() {
        statusText.setText("Recherche d'une mise à jour…");
        updateButton.setVisibility(View.GONE);
        apkDownloadUrl = null;
        latestVersion = null;

        executor.execute(() -> {
            HttpURLConnection connection = null;
            try {
                URL url = new URL(RELEASES_API);
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);
                connection.setRequestProperty("Accept", "application/vnd.github+json");
                connection.setRequestProperty("User-Agent", "LaBibleDuFoot-Android");

                int code = connection.getResponseCode();
                if (code != 200) {
                    throw new IllegalStateException("HTTP " + code);
                }

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder json = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    json.append(line);
                }
                reader.close();

                JSONObject release = new JSONObject(json.toString());
                latestVersion = release.optString("tag_name", "").replaceFirst("^[vV]", "");

                JSONArray assets = release.optJSONArray("assets");
                if (assets != null) {
                    for (int i = 0; i < assets.length(); i++) {
                        JSONObject asset = assets.optJSONObject(i);
                        if (asset == null) continue;
                        String name = asset.optString("name", "");
                        if (name.toLowerCase(Locale.US).endsWith(".apk")) {
                            apkDownloadUrl = asset.optString("browser_download_url", null);
                            break;
                        }
                    }
                }

                boolean newer = compareVersions(latestVersion, BuildConfig.VERSION_NAME) > 0;
                runOnUiThread(() -> {
                    if (newer && apkDownloadUrl != null) {
                        statusText.setText("Nouvelle version disponible : " + latestVersion);
                        updateButton.setText("Télécharger et installer la v" + latestVersion);
                        updateButton.setVisibility(View.VISIBLE);
                    } else if (newer) {
                        statusText.setText("La v" + latestVersion + " existe, mais son APK n'est pas encore disponible.");
                    } else {
                        statusText.setText("L'application est à jour.");
                    }
                });
            } catch (Exception e) {
                runOnUiThread(() -> statusText.setText(
                        "Impossible de vérifier les mises à jour pour le moment."
                ));
            } finally {
                if (connection != null) connection.disconnect();
            }
        });
    }

    private void downloadAndInstall() {
        if (apkDownloadUrl == null || latestVersion == null) {
            checkForUpdate();
            return;
        }

        updateButton.setEnabled(false);
        statusText.setText("Téléchargement de la v" + latestVersion + "…");

        executor.execute(() -> {
            HttpURLConnection connection = null;
            try {
                File baseDir = getExternalCacheDir();
                if (baseDir == null) baseDir = getCacheDir();
                File updateDir = new File(baseDir, "updates");
                if (!updateDir.exists() && !updateDir.mkdirs()) {
                    throw new IllegalStateException("Impossible de créer le dossier de mise à jour");
                }

                File apk = new File(updateDir, "La-Bible-du-Foot-v" + latestVersion + ".apk");
                URL url = new URL(apkDownloadUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(15000);
                connection.setReadTimeout(30000);
                connection.setInstanceFollowRedirects(true);
                connection.setRequestProperty("User-Agent", "LaBibleDuFoot-Android");

                int code = connection.getResponseCode();
                if (code < 200 || code >= 300) {
                    throw new IllegalStateException("Téléchargement HTTP " + code);
                }

                try (InputStream input = connection.getInputStream();
                     FileOutputStream output = new FileOutputStream(apk)) {
                    byte[] buffer = new byte[32768];
                    int read;
                    while ((read = input.read(buffer)) != -1) {
                        output.write(buffer, 0, read);
                    }
                    output.flush();
                }

                if (!apk.exists() || apk.length() < 1024) {
                    throw new IllegalStateException("APK téléchargé invalide");
                }

                pendingApk = apk;
                runOnUiThread(() -> {
                    statusText.setText("Téléchargement terminé. Préparation de l'installation…");
                    updateButton.setEnabled(true);
                    requestInstallPermissionOrInstall(apk);
                });
            } catch (Exception e) {
                runOnUiThread(() -> {
                    statusText.setText("Échec du téléchargement de la mise à jour.");
                    updateButton.setEnabled(true);
                });
            } finally {
                if (connection != null) connection.disconnect();
            }
        });
    }

    private void requestInstallPermissionOrInstall(File apk) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
                && !getPackageManager().canRequestPackageInstalls()) {
            statusText.setText("Android doit autoriser La Bible du Foot à installer sa mise à jour. Active l'autorisation puis reviens dans l'application.");
            Intent intent = new Intent(
                    Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
                    Uri.parse("package:" + getPackageName())
            );
            startActivity(intent);
            return;
        }

        launchInstaller(apk);
    }

    private void launchInstaller(File apk) {
        try {
            pendingApk = null;
            Uri apkUri = FileProvider.getUriForFile(
                    this,
                    getPackageName() + ".fileprovider",
                    apk
            );

            Intent installIntent = new Intent(Intent.ACTION_VIEW);
            installIntent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            installIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(installIntent);
            statusText.setText("Valide maintenant l'installation de la mise à jour dans Android.");
        } catch (Exception e) {
            statusText.setText("Impossible d'ouvrir l'installateur Android.");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (pendingApk != null && pendingApk.exists()) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O
                    || getPackageManager().canRequestPackageInstalls()) {
                File apk = pendingApk;
                launchInstaller(apk);
            }
        }
    }

    private int compareVersions(String a, String b) {
        String[] left = a.split("\\.");
        String[] right = b.split("\\.");
        int max = Math.max(left.length, right.length);
        for (int i = 0; i < max; i++) {
            int l = i < left.length ? parsePart(left[i]) : 0;
            int r = i < right.length ? parsePart(right[i]) : 0;
            if (l != r) return Integer.compare(l, r);
        }
        return 0;
    }

    private int parsePart(String value) {
        try {
            String numeric = value.replaceAll("[^0-9]", "");
            return numeric.isEmpty() ? 0 : Integer.parseInt(numeric);
        } catch (Exception ignored) {
            return 0;
        }
    }

    @Override
    protected void onDestroy() {
        executor.shutdownNow();
        super.onDestroy();
    }
}
