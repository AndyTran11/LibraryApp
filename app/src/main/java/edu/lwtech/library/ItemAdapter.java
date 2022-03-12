// Andy Tran
package edu.lwtech.library;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.BookViewHolder> {

    private ArrayList<BookInfo> bookInfoList;
    private Context mContext;

    public ItemAdapter(ArrayList<BookInfo> bookInfoList, Context mContext) {
        this.bookInfoList = bookInfoList;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public ItemAdapter.BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_row, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.BookViewHolder holder, int position) {

        BookInfo bookInfo = bookInfoList.get(position);
        holder.titleText.setText(bookInfo.getTitle());
        holder.authorText.setText(bookInfo.getAuthors().get(0));
        holder.dateText.setText(bookInfo.getDatePublished());
        holder.pageCountText.setText(new StringBuilder().append(bookInfo.getPageCount()).append(" pages").toString());
        Picasso.get().load("https://books.google.com/books/content?id=" + bookInfo.getBookID() +
                "&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api").into(holder.coverImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ModelDetailPage.class);
                intent.putExtra("id", bookInfo.getBookID());
                intent.putExtra("title", bookInfo.getTitle());
                intent.putExtra("subtitle", bookInfo.getSubtitle());
                intent.putExtra("authors", bookInfo.getAuthors());
                intent.putExtra("datePublished", bookInfo.getDatePublished());
                intent.putExtra("pageCount", bookInfo.getPageCount());
                intent.putExtra("description", bookInfo.getDescription());

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookInfoList.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        TextView titleText, authorText, pageCountText, dateText;
        ImageView coverImage;

        public BookViewHolder(View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.bookTitle);
            authorText = itemView.findViewById(R.id.bookAuthor);
            pageCountText = itemView.findViewById(R.id.pageCount);
            dateText = itemView.findViewById(R.id.datePublished);
            coverImage = itemView.findViewById(R.id.bookCover);
        }
    }
}
