// Andy Tran
package edu.lwtech.library;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RequestQueue mRequestQueue;
    private ArrayList<BookInfo> bookInfoArrayList;
    private ProgressBar progressBar;
    private EditText searchText;
    private ImageButton searchBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.loader);
        searchText = findViewById(R.id.searchText);
        searchBtn = findViewById(R.id.searchBtn);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // To close keyboard
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                // Displays error if nothing was inputted into the search bar
                if (searchText.getText().toString().isEmpty()) {
                    searchText.setError("No input detected.");
                } else {
                    // Displays progress bar
                    progressBar.setVisibility(View.VISIBLE);
                    // Hides keyboard
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    // Performs search
                    searchBooks(searchText.getText().toString());
                }
            }
        });
    }

    private void searchBooks(String query) {
        // Creates arrayList for volumes
        bookInfoArrayList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(MainActivity.this);
        mRequestQueue.getCache().clear();

        String url = "https://www.googleapis.com/books/v1/volumes?q=" + query;

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        // Request data from endpoint using the url
        JsonObjectRequest booksRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Removes progress bar when search is complete
                        progressBar.setVisibility(View.GONE);
                        int i = 0;

                        // Populates variables with data from endpoint
                        try {
                            JSONArray itemsArray = response.getJSONArray("items");
                            while (i < itemsArray.length()) {
                                JSONObject itemsObj = itemsArray.getJSONObject(i);
                                JSONObject volumeObj = itemsObj.getJSONObject("volumeInfo");
                                String bookID = itemsObj.optString("id");
                                String title = volumeObj.optString("title");
                                String subtitle = volumeObj.optString("subtitle");
                                JSONArray authorsArray = volumeObj.getJSONArray("authors");
                                String publishedDate = volumeObj.optString("publishedDate");
                                String description = volumeObj.optString("description");
                                int pageCount = volumeObj.optInt("pageCount");
                                JSONObject imageLinks = volumeObj.optJSONObject("imageLinks");
                                ArrayList<String> authorsArrayList = new ArrayList<>();
                                if (authorsArray.length() != 0) {
                                    for (int j = 0; j < authorsArray.length(); j++) {
                                        authorsArrayList.add(authorsArray.optString(0));
                                    }
                                }

                                // Create new BookInfo object with the data
                                BookInfo bookInfo = new BookInfo(bookID, title, subtitle, authorsArrayList,
                                        publishedDate, description, pageCount);

                                //Add BookInfo object to the arrayList
                                bookInfoArrayList.add(bookInfo);

                                //Initialize adapter
                                ItemAdapter adapter = new ItemAdapter(bookInfoArrayList, MainActivity.this);

                                // Attach information to the Recyler and Linear views w/ adapter
                                LinearLayoutManager llManager = new LinearLayoutManager(MainActivity.this,
                                        RecyclerView.VERTICAL, false);
                                RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.searchResults);

                                mRecyclerView.setLayoutManager(llManager);
                                mRecyclerView.setAdapter(adapter);

                                // Increment i by 1
                                i++;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "No Data Found",
                                    Toast.LENGTH_LONG).show();
                        }
                        ;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error",
                        Toast.LENGTH_LONG).show();
            }
        });
        // Add search request to the queue
        queue.add(booksRequest);
    }
}