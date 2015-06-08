//------------------------------------------------------------------------
// <copyright file="MyActivity.java" company="Advisor ICT Solutions">
// Advisor ICT Solutions, Mijdrecht, The Netherlands. All rights reserved.
// </copyright>
//------------------------------------------------------------------------

package com.example.fabian.matchit;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class FragmentDrawer extends Fragment{

    SwipeRefreshLayout srlListRefresh;
    DrawerLayout dlDrawerMenu;
    // ListView lvNews;
    ProgressDialog pdListLoading;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    private TextView tvShoppingCartName;
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.drawer_navigation, container, false);
        mDrawerList = (ListView) v.findViewById(R.id.lvNavigationDrawer);
        mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);

        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

        navDrawerItems = new ArrayList<NavDrawerItem>();
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));

        adapter = new NavDrawerListAdapter(getActivity().getApplicationContext(), navDrawerItems);
        mDrawerList.setAdapter(adapter);


        return v;
    }


}