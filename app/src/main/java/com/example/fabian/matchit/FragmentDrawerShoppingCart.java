//------------------------------------------------------------------------
// <copyright file="MyActivity.java" company="Advisor ICT Solutions">
// Advisor ICT Solutions, Mijdrecht, The Netherlands. All rights reserved.
// </copyright>
//------------------------------------------------------------------------

package com.example.fabian.matchit;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fabian.matchit.Adapters.lvAdapterShoppingCart;

public class FragmentDrawerShoppingCart extends Fragment{

    View v;

    // Order
    private lvAdapterShoppingCart                   aListShoppingCart;
    Orders                                          FOrders;
    Orderline                                       FOrderline;

    private ListView                                lvShoppingCartItems;
    private TextView                                tvAmount;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.drawer_shopping_cart, container, false);
        tvAmount = (TextView) v.findViewById(R.id.tvTotalShoppingCardAmount);
        lvShoppingCartItems = (ListView) v.findViewById(R.id.lvShoppingCartItems);
        // Haal winkelwagentje op
        FOrders = new Orders(this);
        FOrders.addReadyEventListener(new OrderReadyListener());
        FOrders.Get();
        // Maak orderline vast klaar
        FOrderline = new Orderline(this);
        FOrderline.addReadyEventListener(new OrderLineReadyListener());

        return v;
    }

    public class OrderReadyListener implements ReadyListener
    {
        @Override
        public void ready(ReadyEvent e) {
            // Haal orderlines op
            FOrderline.Get();
        }
    }
    public class OrderLineReadyListener implements ReadyListener
    {
        @Override
        public void ready(ReadyEvent e) {

            tvAmount.setText(" " + FOrderline.stCurrency + " " + FOrderline.stTotal);
            aListShoppingCart = new lvAdapterShoppingCart(getActivity(), FOrderline.getItems());
            lvShoppingCartItems.setAdapter(aListShoppingCart);
        }
    }
}