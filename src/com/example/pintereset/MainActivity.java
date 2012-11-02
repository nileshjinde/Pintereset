package com.example.pintereset;




import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.HttpAuthHandler;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class MainActivity extends Activity {

	Button btnPin,btnClose;
	WebView mWebView;
	public ProgressDialog mProgressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		btnClose=(Button)findViewById(R.id.btnClose);
		btnPin=(Button)findViewById(R.id.btnPin);
		mWebView=(WebView)findViewById(R.id.webView1);
		mWebView.getSettings().setJavaScriptEnabled(true); 
		mWebView.getSettings().setPluginState(PluginState.ON);
		mWebView.getSettings().setAllowFileAccess(true); 
		mWebView.setWebViewClient(new MyWebViewClient());

		btnClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mWebView.setVisibility(WebView.GONE);
			}
		});
		btnPin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openWebView();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private void openWebView() {
		// TODO Auto-generated method stub
		mWebView.setVisibility(WebView.VISIBLE);
		String htmlString = generatePinterestHTMLForSKU();
		Log.i("Generated HTML String= ", htmlString);
		mWebView.loadData(htmlString, "text/html", "UTF-8");
		mWebView.requestFocus(View.FOCUS_DOWN);
	}

	private String generatePinterestHTMLForSKU() {
		String strImg="http://www.alkalima.com/images/08-02/nature.jpg";
		String description = "Post your description here";	

		
		String imageUrl = strImg;
		String buttonUrl = "\"http://pinterest.com/pin/create/button/?url=www.flor.com&media="+imageUrl+"&description="+description+"\"";

		String htmlString ="<html> <body>";
		htmlString =htmlString+"<p align=\"center\"><a href="+buttonUrl +"class=\"pin-it-button\" count-layout=\"horizontal\"><img border=\"0\" src=\"http://assets.pinterest.com/images/PinExt.png\" title=\"Pin It\" /></a></p>";
		htmlString =htmlString+"<p align=\"center\"><img width=\"400px\" height = \"400px\" src="+imageUrl+"></img></p>";
		htmlString =htmlString+"<script type=\"text/javascript\" src=\"//assets.pinterest.com/js/pinit.js\"></script>";
		htmlString =htmlString+"</body> </html>";
		/*String htmlString="<html> <body><p align=\"center\"><a href=\"http://pinterest.com/pin/create/button/?url=www.flor.com&media=http%3A%2F%2Fwww.alkalima.com%2Fimages%2F08-02%2Fnature.jpg&description=Post your description here\" " +
				"class=\"pin-it-button\" count-layout=\"horizontal\"><img border=\"0\" src=\"http://assets.pinterest.com/images/PinExt.png\" title=\"Pin It\" /></a></p><p align=\"center\"><img width=\"400px\" height = \"400px\" " +
				"src=\"http://www.alkalima.com/images/08-02/nature.jpg\"></img></p><script type=\"text/javascript\" src=\"//assets.pinterest.com/js/pinit.js\"></script></body> </html>";
	*/	
		return htmlString;
	}


	private class MyWebViewClient extends WebViewClient { 
		@Override 
		//show the web page in webview but not in web browser
		public boolean shouldOverrideUrlLoading(WebView view, String url) { 
			view.loadUrl (url); 
			return true;
		}

		@Override
		public void onReceivedHttpAuthRequest(WebView view,
				HttpAuthHandler handler, String host, String realm) {
			handler.proceed("checkin", "4good");
		}
		
		@Override
        public void onLoadResource(WebView view, String url) {
            System.out.println("onLoadResource "+url);
            super.onLoadResource(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            System.out.println("onPageFinished "+url);
                                //TwitterDialog.this.dismiss();
            removeDialog(0);
            super.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            System.out.println("onPageStarted "+url);
            showDialog(0);
            super.onPageStarted(view, url, favicon);
        }
	}
	
	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		ProgressDialog dialog = new ProgressDialog(this);
		dialog = ProgressDialog.show(this,null,null);
		dialog.setContentView(R.layout.loader);
		dialog.setIndeterminate(true);
		dialog.setCancelable(false);
		dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				Log.i("CHECKINFORGOOD", "user cancelling authentication");

			}
		});
		mProgressDialog = dialog;
		return dialog;
	}
}
