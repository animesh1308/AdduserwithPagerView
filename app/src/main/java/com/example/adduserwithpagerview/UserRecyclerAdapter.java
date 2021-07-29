package com.example.adduserwithpagerview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.UserViewHolder> {

    private Context context;
    private List<UserEntity> userlist;
    View view;
    UserListDeleteUpdate popupInterface;



    public UserRecyclerAdapter(Context context, List<UserEntity> userlist,UserListDeleteUpdate popupInterface) {
        this.context = context;
        this.userlist = userlist;
        this.popupInterface=popupInterface;
    }


    @Override
    public UserViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.user_rcycleview_layout,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder( UserRecyclerAdapter.UserViewHolder holder, int position) {
        UserEntity list=userlist.get(position);
        holder.tvUsername.setText(list.getUser_name());
        holder.tvMobilenumber.setText(list.getUser_mobileno());
        if(list.getImage()!=null) {
            holder.cirImage.setImageBitmap(ImageConverter.byteArrayToImage(list.getImage()));
        }
        else{
            holder.cirImage.setImageResource(R.drawable.ic_baseline_person_24);
        }
        holder.tvUsername.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu popupMenu=new PopupMenu(view.getContext(),view);
                popupMenu.inflate(R.menu.delete_update);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()){
                            case R.id.item_del:
                                popupInterface.popupMenue(String.valueOf(list.getId()),"delete");
                            case R.id.item_updt:
                                popupInterface.popupMenue(String.valueOf(list.getId()),"update");
                            default:
                                return false;
                        }
                    }
                });
                popupMenu.show();
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }


    public class UserViewHolder extends RecyclerView.ViewHolder  {

        TextView tvUsername,tvMobilenumber;
        CircleImageView cirImage;

        public UserViewHolder(View itemView) {
            super(itemView);
            tvUsername=itemView.findViewById(R.id.rcv_usrnm);
            tvMobilenumber=itemView.findViewById(R.id.rcv_mobno);
            cirImage=itemView.findViewById(R.id.rcview_user_image);

        }

    }
}
