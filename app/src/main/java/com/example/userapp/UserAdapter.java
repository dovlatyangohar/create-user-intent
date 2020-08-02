package com.example.userapp;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<User> users;
    private OnItemClickListener itemClickListener;

    public void setListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    UserAdapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.user_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final User user = users.get(position);

        holder.firstName.setText(user.getFirstName() + " ");
        holder.lastName.setText(user.getLastName());
        holder.coverImageView.setImageBitmap(BitmapFactory.decodeFile(user.getImgPath()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClickListener(user);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (user.isSelected() && holder.getAdapterPosition() != RecyclerView.NO_POSITION) {
                    users.remove(user);
                    notifyItemRemoved(holder.getAdapterPosition());
                }
                itemClickListener.onItemLongClickListener(user);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView firstName;
        TextView lastName;
        ImageView coverImageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            firstName = itemView.findViewById(R.id.firstNameTextView);
            lastName = itemView.findViewById(R.id.lastNameTextView);
            coverImageView = itemView.findViewById(R.id.userImage);
        }
    }

    interface OnItemClickListener {

        void onItemClickListener(User user);

        void onItemLongClickListener(User user);

    }
}
