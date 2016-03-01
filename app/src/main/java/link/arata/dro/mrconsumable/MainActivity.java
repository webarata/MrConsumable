package link.arata.dro.mrconsumable;

import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;

import java.util.List;

import link.arata.dro.mrconsumable.ConsumableAdapter;
import link.arata.dro.mrconsumable.dao.ConsumableDao;
import link.arata.dro.mrconsumable.dao.impl.ConsumableDaoImpl;
import link.arata.dro.mrconsumable.entity.Consumable;
import link.arata.dro.mrconsumable.helper.AppOpenHelper;
import link.arata.dro.mrconsumable.model.ConsumableModel;
import link.arata.dro.mrconsumable.model.ModelEvent;
import link.arata.dro.mrconsumable.model.Observer;

public class MainActivity extends AppCompatActivity implements Observer {
    private ListView consumableListView;

    private ConsumableModel consumableModel;

    @Override
    public void notify(ModelEvent modelEvent) {
        switch (modelEvent) {
            case FINISH_FETCH_CONSUMABLE_LIST:
                ConsumableAdapter consumableAdapter = new ConsumableAdapter(this, 0, consumableModel.getConsumableList());
                consumableListView.setAdapter(consumableAdapter);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        consumableModel = ConsumableModel.getInstance();
        consumableModel.addObserver(this);

        FloatingActionButton newButton = (FloatingActionButton) findViewById(R.id.newButton);
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClassName("link.arata.dro.mrconsumable",
                    "link.arata.dro.mrconsumable.NewActivity");

                startActivity(intent);
            }
        });
        consumableListView = (ListView) findViewById(R.id.consumableListView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        consumableModel.fetchConsumableList(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_license) {
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.webview_license);
            dialog.setTitle("オープンソースライセンス");
            dialog.setCancelable(true);
            WebView webView = (WebView)dialog.findViewById(R.id.licenseWebView);

            webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    dialog.show();
                }
            });
            webView.loadUrl("file:///android_asset/license.html");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        consumableModel.removeObserver(this);
    }
}
