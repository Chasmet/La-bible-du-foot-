package com.chasmet.labibledufoot;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class TechniqueDetailActivity extends Activity {

    public static final String EXTRA_GUIDE_ID = "guide_id";
    private TechniqueGuide guide;
    private TextView memo;
    private Button enfantButton;
    private Button adulteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technique_detail);

        String id = getIntent().getStringExtra(EXTRA_GUIDE_ID);
        guide = TechniqueGuide.find(id == null ? "position_corps" : id);

        TextView title = findViewById(R.id.techniqueTitle);
        WebView webView = findViewById(R.id.techniqueWebView);
        memo = findViewById(R.id.techniqueMemo);
        enfantButton = findViewById(R.id.btnTechEnfant);
        adulteButton = findViewById(R.id.btnTechAdulte);

        title.setText(guide.title);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(false);
        settings.setAllowFileAccess(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        webView.loadUrl("file:///android_asset/" + guide.asset);

        enfantButton.setOnClickListener(v -> renderLevel(true));
        adulteButton.setOnClickListener(v -> renderLevel(false));
        renderLevel(true);
    }

    private void renderLevel(boolean enfant) {
        memo.setText(enfant ? guide.enfant : guide.adulte);
        enfantButton.setAlpha(enfant ? 1f : 0.55f);
        adulteButton.setAlpha(enfant ? 0.55f : 1f);
    }
}
