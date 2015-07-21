package am.narekb.bluelist;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText searchField;
    boolean hasSearchFocus = false;
    String searchQuery;
    Document doc;
    Elements els;

    MultiViewAdapter mva;
    ArrayList<Ad> adList;
    ListView adListView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adList = new ArrayList<Ad>();
        mva = new MultiViewAdapter(this, adList, R.layout.item_layout);

        adListView = (ListView)findViewById(R.id.adListView);
        adListView.setAdapter(mva);

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
            if(!hasSearchFocus) {
                searchField.setVisibility(View.VISIBLE);
                searchField.requestFocus();
                hasSearchFocus = true;
            }
            else {
                searchField.setVisibility(View.GONE);
                hasSearchFocus = false;
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void downloadResults(String query) {
        query = query.trim().replaceAll(" ", "+");

        new AsyncTask<String, Void, Void> () {

            @Override
            protected Void doInBackground(String... params) {
                try {
                    doc = Jsoup.connect("http://www.list.am/category?q=" + params[0] + "&gl=1").get();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                //START PARSING RESULTS
                els = doc.select("div.i"); //divs of class i contain the ads in grid view (&gl=1)

                for(Element ad : els) {
                    String url = "http://www.list.am" + ad.select("a[href]").attr("href");
                    String imgUrl = ad.select("a > img").attr("src");
                    String title = ad.select("div.l > a").text();
                    String price = ad.select("div.l2 > div.l").text();

                    adList.add(new Ad(title, price, url, imgUrl));
                    mva.notifyDataSetChanged();
                }
            }
        }.execute(query);
    }

}
