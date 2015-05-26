package com.example.fabian.matchit;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dd.CircularProgressButton;

public class FragmentMainSlider extends Fragment {

    public static final String ARG_PAGE = "page";
    private int mPageNumber;
    private CircularProgressButton circularButton1;
    ViewGroup rootView;
    private ImageView ivThema;
    private TextView tvThemaTitel;

    public static FragmentMainSlider create(int pageNumber) {
        FragmentMainSlider fragment = new FragmentMainSlider();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentMainSlider() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("mppage", String.valueOf(mPageNumber));

        // Inflate the layout containing a title and body text.
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main_slide_themes, container, false);
        ivThema = (ImageView) rootView.findViewById(R.id.ivThema);
        tvThemaTitel = (TextView) rootView.findViewById(R.id.tvThemeTitel);
        switch(mPageNumber){
            case 0:
                ivThema.setImageResource(R.drawable.thema_1);
                tvThemaTitel.setText("Vroeg bloeiers");
                break;
            case 1:
                ivThema.setImageResource(R.drawable.thema_2);
                tvThemaTitel.setText("Verse Bloemen");
                break;
            case 2:
                ivThema.setImageResource(R.drawable.thema_3);
                tvThemaTitel.setText("Trouwerijen");
                break;
        }



        return rootView;
    }

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }


}
