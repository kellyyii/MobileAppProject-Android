package com.project.hkwt;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
public class InnerActivity extends AppCompatActivity {

    private String TAG = "InnerActivity";
    private ListView listView; // ui component for displaying all fitness tracks
    private EditText searchBox;
    static String EXTRA_MESSAGE = "EXTRA_MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inner);

        // initialize the listView ui component by using findViewById method
        listView = (ListView) findViewById(R.id.listview);
        searchBox = (EditText) findViewById(R.id.searchBox);

        // Get fitness tracks list with thread class
        JsonHandlerThread jsonHandlerThread = new JsonHandlerThread();
        jsonHandlerThread.lang = Settings.getLang();
        jsonHandlerThread.start();

        try {
            jsonHandlerThread.join();

            // after retrieved the json contents and stored into the "fitnessList"
            // 1. setup ListView component to display the fitness tracks list
            // 2. implement the item click event handling

            // 1.
            // Create an adapter object that accommodates a data list of items to views that becomes children of an adapter view
            // i.e. the Adapter object acts as a bridge between an ListView and the tracks for that view
            SimpleAdapter adapter = new SimpleAdapter(
                    this,
                    FitnessTrack.fitnessList,  // "fitnessList" that stores all the fitness tracks' info
                    R.layout.list_view_layout, // layout resource represent item layout design
                    new String[]{FitnessTrack.TITLE, FitnessTrack.DISTRICT, FitnessTrack.ROUTE},  // represent the three data that display in an item
                    new int[]{R.id.title, R.id.district, R.id.route}  // represent where the item is displayed
            );
            // Associate the adapter with the ListView
            listView.setAdapter(adapter);

            // 2.
            listView.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            HashMap<String, String> fitness = FitnessTrack.fitnessList.get(position);
                            HashMap<String, Double> fitnessMap = FitnessTrack.fitnessListMap.get(position);
                            // AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);// dialog creation
                            //
                            // builder.setTitle(fitness.get(FitnessTrack.TITLE));
                            // builder.setMessage("Mobile: " + fitness.get(FitnessTrack.DISTRICT));
                            // AlertDialog alertDialog = builder.create();
                            // alertDialog.show();

                            // 在第一個Activity中創建一個Bundle對象並添加一些數據
                            Bundle bundle = new Bundle();
                            bundle.putString("title", fitness.get(FitnessTrack.TITLE));
                            bundle.putString("district", fitness.get(FitnessTrack.DISTRICT));
                            bundle.putString("map_image", fitness.get(FitnessTrack.MAP_URL));
                            bundle.putString("howtoaccess", fitness.get(FitnessTrack.HOW_TO_ACCESS));
                            bundle.putString("route", fitness.get(FitnessTrack.ROUTE));
                            bundle.putDouble("latitude", fitnessMap.get(FitnessTrack.LATITUDE));
                            bundle.putDouble("longitude", fitnessMap.get(FitnessTrack.LONGITUDE));
                            // 創建一個Intent對象，指定要啟動的Activity
                            Intent intent = new Intent(InnerActivity.this, DisplayActivity.class);

                            // 將Bundle對象添加到Intent對象中
                            intent.putExtras(bundle);
                            //intent.putExtra(EXTRA_MESSAGE, urlStr);
                            startActivity(intent);

                        }
                    }
            );

            searchBox.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    String searchText = s.toString().toLowerCase();
                    ArrayList<HashMap<String, String>> filteredList = new ArrayList<HashMap<String, String>>();

                    for (HashMap<String, String> fitness : FitnessTrack.fitnessList) {
                        if (fitness.get(FitnessTrack.TITLE).toLowerCase().contains(searchText)
                                || fitness.get(FitnessTrack.DISTRICT).toLowerCase().contains(searchText)
                                || fitness.get(FitnessTrack.ROUTE).toLowerCase().contains(searchText)) {
                            filteredList.add(fitness);
                        }
                    }

                    // Update the fitnessList with the filteredList
                    FitnessTrack.fitnessList.clear();
                    FitnessTrack.fitnessList.addAll(filteredList);

                    Comparator<HashMap<String, String>> titleComparator = new Comparator<HashMap<String, String>>() {
                        @Override
                        public int compare(HashMap<String, String> fitness1, HashMap<String, String> fitness2) {
                            String title1 = fitness1.get(FitnessTrack.TITLE);
                            String title2 = fitness2.get(FitnessTrack.TITLE);
                            return title1.compareTo(title2);
                        }
                    };

                    // Notify the adapter that the data has changed
                    SimpleAdapter adapter = (SimpleAdapter) listView.getAdapter();
                    adapter.notifyDataSetChanged();
                }
            });
        } catch (InterruptedException e) {
            Log.e(TAG, "InterruptedException: " + e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true; // return true so that the menu pop up is opened
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                finish();
                startActivity(getIntent());
                return true;
            case R.id.settings:
                Intent intent = new Intent(InnerActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}