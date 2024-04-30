package com.example.mealplanner.viewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.R;
import com.example.mealplanner.database.entities.User;

public class UsersViewHolder extends RecyclerView.ViewHolder {
    private final TextView userViewItem;
    private Button deleteButton;
    public UsersViewHolder(@NonNull View itemView, UserAdapter.OnItemClickListener listener) {
        super(itemView);
        userViewItem = itemView.findViewById(R.id.recyclerItemUsername);

        deleteButton = itemView.findViewById(R.id.deleteButton);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onDeleteClick(position);;
                }
            }
        });

    }

    public void bind (String text) {
        userViewItem.setText(text);
    }

    static UsersViewHolder create(ViewGroup parent, UserAdapter.OnItemClickListener listener) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_recycler_item, parent,false);
        return new UsersViewHolder(view, listener);
    }
}
