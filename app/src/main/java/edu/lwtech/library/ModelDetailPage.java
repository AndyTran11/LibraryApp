// Andy Tran
package edu.lwtech.library;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

// Activity Page for detailed view of book
public class ModelDetailPage extends AppCompatActivity {

    String id, title, subtitle, datePublished, description;
    int pageCount;

    TextView titleText, subtitleText, authorText, pageCountText, dateText, descText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        titleText = findViewById(R.id.detailBookTitle);
        subtitleText = findViewById(R.id.detailSubtitle);
        authorText = findViewById(R.id.detailBookAuthor);
        pageCountText = findViewById(R.id.detailPageCount);
        dateText = findViewById(R.id.detailDatePublished);
        descText = findViewById(R.id.detailDescription);
        ImageView coverImage = findViewById(R.id.detailBookCover);

        // Getting intents
        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        subtitle = getIntent().getStringExtra("subtitle");
        ArrayList<String> authors = getIntent().getStringArrayListExtra("authors");
        datePublished = getIntent().getStringExtra("datePublished");
        description = getIntent().getStringExtra("description");
        pageCount = getIntent().getIntExtra("pageCount", 0);

        titleText.setText(title);
        // Collapses empty space where subtitle would be if there isn't one
        if (subtitle.isEmpty()) {
            subtitleText.setVisibility(View.GONE);
        } else {
            subtitleText.setText(subtitle);
        }
        // Sets text to first author in the list
        authorText.setText(authors.get(0));
        dateText.setText(datePublished);
        descText.setText(description);
        pageCountText.setText(new StringBuilder().append(pageCount).append(" pages").toString());
        Picasso.get().load("https://books.google.com/books/content?id=" + id +
                "&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api").into(coverImage);
    }
}