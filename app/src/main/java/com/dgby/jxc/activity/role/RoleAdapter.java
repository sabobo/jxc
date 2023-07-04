package com.dgby.jxc.activity.role;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dgby.jxc.R;

import java.util.ArrayList;
import java.util.List;
public class RoleAdapter extends RecyclerView.Adapter<RoleAdapter.ViewHolder> {

    private List<Role> roleList;
    private ItemClickListener clickListener;

    public RoleAdapter(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_role, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Role role = roleList.get(position);
        holder.nameTextView.setText(role.getName());
    }

    @Override
    public int getItemCount() {
        return roleList == null ? 0 : roleList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView nameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.role_name_text_view);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.onItemClick(v, getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (clickListener != null) {
                clickListener.onItemLongClick(v, getAdapterPosition());
            }
            return true;
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }
}
