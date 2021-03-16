package com.example.abir.source.sample.video_web_view;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.abir.source.R;


/**
 * Check Below Links
 *
 * @link - https://github.com/cprcrack/VideoEnabledWebView
 * @link - https://stackoverflow.com/questions/15768837/playing-html5-video-on-fullscreen-in-android-webview
 */
public class VideoWebViewActivity extends AppCompatActivity {

    private VideoEnabledWebView webView;
    private VideoEnabledWebChromeClient webChromeClient;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState); // Saving state with screen rotation
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        webView.restoreState(savedInstanceState); // Restoring state with screen rotation
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_web_view);
        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR); // Support both Portrait and Landscape

        // Save the web view
        webView = findViewById(R.id.webView);

        // Initialize the VideoEnabledWebChromeClient and set event handlers
        View nonVideoLayout = findViewById(R.id.nonVideoLayout); // Your own view, read class comments
        ViewGroup videoLayout = (ViewGroup) findViewById(R.id.videoLayout); // Your own view, read class comments
        //noinspection all
        //View loadingView = getLayoutInflater().inflate(R.layout.view_loading_video, null); // Your own view, read class comments
        View loadingView = null;
        webChromeClient = new VideoEnabledWebChromeClient(nonVideoLayout, videoLayout, loadingView, webView) // See all available constructors...
        {
            // Subscribe to standard events, such as onProgressChanged()...
            @Override
            public void onProgressChanged(WebView view, int progress) {
                // Your code...
            }
        };
        webChromeClient.setOnToggledFullscreen(new VideoEnabledWebChromeClient.ToggledFullscreenCallback() {
            @Override
            public void toggledFullscreen(boolean fullscreen) {
                // Your code to handle the full-screen change, for example showing and hiding the title bar. Example:
                if (fullscreen) {
                    WindowManager.LayoutParams attrs = getWindow().getAttributes();
                    attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
                    attrs.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                    getWindow().setAttributes(attrs);
                    if (android.os.Build.VERSION.SDK_INT >= 14) {
                        //noinspection all
                        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
                    }
                } else {
                    WindowManager.LayoutParams attrs = getWindow().getAttributes();
                    attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
                    attrs.flags &= ~WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                    getWindow().setAttributes(attrs);
                    if (android.os.Build.VERSION.SDK_INT >= 14) {
                        //noinspection all
                        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    }
                }

            }
        });
        webView.setWebChromeClient(webChromeClient);
        // Call private class InsideWebViewClient
        webView.setWebViewClient(new InsideWebViewClient());

        // Navigate anywhere you want, but consider that this classes have only been tested on YouTube's mobile site
        String testUrl = "https://www.youtube.com/watch?v=_CTUs_2hq6Y";
        //String testUrl = "http://m.youtube.com";
        if (savedInstanceState == null) { // Only load when Screen is not rotated
            webView.loadUrl(testUrl);
        }
    }

    private class InsideWebViewClient extends WebViewClient {
        @Override
        // Force links to be opened inside WebView and not in Default Browser
        // Thanks http://stackoverflow.com/a/33681975/1815624
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        // Notify the VideoEnabledWebChromeClient, and handle it ourselves if it doesn't handle it
        if (!webChromeClient.onBackPressed()) {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                // Standard back button implementation (for example this could close the app)
                super.onBackPressed();
            }
        }
    }

}
