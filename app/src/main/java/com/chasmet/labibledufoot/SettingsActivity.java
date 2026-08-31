package com.chasmet.labibledufoot;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SettingsActivity extends Activity {

    private static final String RELEASES_API = "https://api.github.com/repos/Chasmet/La-bible-du-foot-/releases/latest";

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private TextView statusText;
    private Button updateButton;
    private String releaseUrl;

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
        updateButton.setOnClickListener(v -> {
            if (releaseUrl != null) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(releaseUrl)));
            }
        });
    }

    private void checkForUpdate() {
        statusText.setText("Recherche d'une mise à jour…");
        updateButton.setVisibility(View.GONE);

        executor.execute(() -> {
            try {
                URL url = new URL(RELEASES_API);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(8000);
                connection.setReadTimeout(8000);
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
                connection.disconnect();

                JSONObject release = new JSONObject(json.toString());
                String latestTag = release.optString("tag_name", "").replaceFirst("^[vV]", "");
                releaseUrl = release.optString("html_url", null);

                boolean newer = compareVersions(latestTag, BuildConfig.VERSION_NAME) > 0;
                runOnUiThread(() -> {
                    if (newer) {
                        statusText.setText("Nouvelle version disponible : " + latestTag);
                        updateButton.setVisibility(View.VISIBLE);
                    } else {
                        statusText.setText("L'application est à jour.");
                    }
                });
            } catch (Exception e) {
                runOnUiThread(() -> statusText.setText(
                        "Impossible de vérifier les mises à jour pour le moment."
                ));
            }
        });
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
