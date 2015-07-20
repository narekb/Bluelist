package am.narekb.bluelist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    EditText searchField;
    boolean hasSearchFocus = false;
    String searchQuery;
    Document doc;
    Elements els;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        searchField = (EditText)findViewById(R.id.search);
        searchField.setVisibility(View.GONE); //Hide search line at first


        searchField.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    searchField.setVisibility(View.GONE);
                    searchQuery = searchField.getText().toString().trim();
                    if (searchQuery != "") {
                        downloadResults(searchQuery);
                    }
                return true;
            }

            return false;
        }
    }); //Hide search field when pressing Enter


        searchField.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    searchField.setVisibility(View.GONE);
                }
            }
        }); //Hide search field when losing focus


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
        if (id == R.id.action_search) {
            if(hasSearchFocus) {
                searchField.setVisibility(View.VISIBLE);
                searchField.requestFocus();
            }
            else {
                searchField.setVisibility(View.GONE);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void downloadResults(String query) {
        query.replace(' ', '+');

        try {
                doc = Jsoup.connect("www.list.am/category")
                            .data("q", query) //Hopefully this IS ?q=...
                            .userAgent("Mozilla")
                            .timeout(3000)
                            .get();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        //START PARSING RESULTS
        els = doc.select("tr");
    }

}
