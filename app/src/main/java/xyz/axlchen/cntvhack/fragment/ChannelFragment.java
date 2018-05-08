package xyz.axlchen.cntvhack.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.axlchen.cntvhack.R;

public class ChannelFragment extends BaseFragment {

    public ChannelFragment() {
        // Required empty public constructor
    }

    public static ChannelFragment newInstance() {
        ChannelFragment fragment = new ChannelFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_channel, container, false);
    }

}
