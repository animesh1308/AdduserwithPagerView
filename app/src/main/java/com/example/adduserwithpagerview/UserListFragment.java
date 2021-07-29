package com.example.adduserwithpagerview;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserListFragment extends Fragment  {
    RecyclerView recyclerView;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
     view=inflater.inflate(R.layout.user_list_fragment,container,false);
     recyclerView=view.findViewById(R.id.recycler_view);
     userEvent();
     return view;
    }

    private void userEvent(){

        class DisplayUser extends AsyncTask<Void, Void,List<UserEntity>>{

            @Override
            protected List<UserEntity> doInBackground(Void... voids) {
                    List<UserEntity> userlist = UserRoomDatabaseClient.getInstance(getContext())
                            .getUserDatabase()
                            .userDao()
                            .getall();
                return userlist;
            }

            @Override
            protected void onPostExecute(List<UserEntity> userEntities) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            UserRecyclerAdapter userRecyclerAdapter=new UserRecyclerAdapter(getActivity(), userEntities, new UserListDeleteUpdate() {
                @Override
                public void popupMenue(String id, String flag) {
                    if(flag.equals("delete")){
                        DeleteUpdateUser del=new DeleteUpdateUser(id,"delete");
                        del.execute();
                    }
                    if(flag.equals("update")){
                        getFragmentManager().beginTransaction().replace(R.id.container,new AddUserFragment()).commit();
                    }
                }
            });
            recyclerView.setAdapter(userRecyclerAdapter);
            super.onPostExecute(userEntities);
            }
        }
        DisplayUser disp=new DisplayUser();
        disp.execute();
    }

    class DeleteUpdateUser extends AsyncTask<Void,Void,List<UserEntity>>{

        private final String id;
        private String flag;

        public DeleteUpdateUser(String id, String flag) {
            this.id = id;
            this.flag = flag;
        }

        @Override
        protected List<UserEntity> doInBackground(Void... voids) {
            if(flag.equals("delete")) {
                UserRoomDatabaseClient.getInstance(getContext())
                        .getUserDatabase()
                        .userDao()
                        .deleteuser(Integer.parseInt(id));
            }
            if(flag.equals("update")){

            }
            return null;
        }

    }
}
