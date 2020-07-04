package com.maks.durov.testapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import lombok.RequiredArgsConstructor;
import com.bumptech.glide.Glide;
import com.maks.durov.testapplication.R;
import com.maks.durov.testapplication.model.Contact;
import java.util.List;


@RequiredArgsConstructor
public class RecyclerViewContactAdapter extends
        RecyclerView.Adapter<RecyclerViewContactAdapter.ContactViewHolder> {
    private final List<Contact> contacts;
    private final Context context;
    private OnItemClicked onClick;

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item, parent, false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.textViewFirstName.setText(contacts.get(position).getFirstName());
        holder.textViewLastName.setText(contacts.get(position).getLastName());
        Glide.with(context).load(contacts.get(position).getImageUrl()).circleCrop()
                .into(holder.imageView);
        holder.itemView.setOnClickListener(v -> onClick.onItemClick(contacts.get(position)));
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewFirstName;
        private final TextView textViewLastName;
        private ImageView imageView;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewFirstName = itemView.findViewById(R.id.contact_item_first_name);
            textViewLastName = itemView.findViewById(R.id.contact_item_last_name);
            imageView = itemView.findViewById(R.id.contact_item_photo);
        }
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick = onClick;
    }

    public interface OnItemClicked {
        void onItemClick(Contact contact);
    }
}
