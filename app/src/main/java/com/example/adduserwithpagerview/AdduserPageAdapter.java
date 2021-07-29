package com.example.adduserwithpagerview;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AdduserPageAdapter extends FragmentStateAdapter {

    private String[] titles = new String[]{"User List", "Add User"};

    public AdduserPageAdapter( FragmentManager fragmentManager,  Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new UserListFragment();
            case 1: return new AddUserFragment();
            default:return null;
        }
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
