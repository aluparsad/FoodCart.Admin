package com.icmi.foodcartadmin.Home.Fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.icmi.foodcartadmin.Home.Fragments.Orders.OrdersFragment;
import com.icmi.foodcartadmin.Model.Status;

public class ScreenSlidePagerAdapter extends FragmentStateAdapter {

    private final int NUM_FRAGS = 3;

    private OrdersFragment
            frag_processing,
            frag_onTheWay,
            frag_Delivered;

    private void initFrags() {
        frag_processing = new OrdersFragment(Status.PROCESSING);
        frag_onTheWay = new OrdersFragment(Status.ON_THE_WAY);
        frag_Delivered = new OrdersFragment(Status.DELIVERED);
    }


    public ScreenSlidePagerAdapter(@NonNull FragmentActivity fa) {
        super(fa);
        initFrags();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment temp;
        switch (position) {
            case 0:
                temp = frag_processing;
                break;
            case 1:
                temp = frag_onTheWay;
                break;
            default:
                temp = frag_Delivered;
        }
        return temp;
    }

    @Override
    public int getItemCount() {
        return NUM_FRAGS;
    }

}
