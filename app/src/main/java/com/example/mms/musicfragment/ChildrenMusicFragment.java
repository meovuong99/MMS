package com.example.mms.musicfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mms.R;
import com.example.mms.adapter.MusicDealAdapter;
import com.example.mms.dao.ProductDAO;
import com.example.mms.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ChildrenMusicFragment extends Fragment {
    private GridLayoutManager gridLayoutManager;
    private ProductDAO productDAO;
    private MusicDealAdapter musicDealAdapter;
    private List<Product> productList;
    private RecyclerView rvMovieDeals;
    private Spinner spnSortMovie;
    private List<String> spList;
    private ArrayAdapter<String> spAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_horror, container, false);
        initView(view);
        spList.add(getResources().getString(R.string.frg_movie_spinner_arrange));
        spList.add(getResources().getString(R.string.frg_movie_spinner_lowtohigh));
        spList.add(getResources().getString(R.string.frg_movie_spinner_hightolow));
        spAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, spList);
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSortMovie.setAdapter(spAdapter);
        spnSortMovie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String ten = spList.get(position);
                if (ten.equals(getResources().getString(R.string.frg_movie_spinner_arrange))) {
                    showProduct(productDAO.getAllProductbyType(getResources().getString(R.string.frg_childrenmusic_title)));
                } else if (ten.equals(getResources().getString(R.string.frg_movie_spinner_lowtohigh))) {
                    showProduct(productDAO.getAllProductASC(getResources().getString(R.string.frg_childrenmusic_title)));
                } else {
                    showProduct(productDAO.getAllProductDESC(getResources().getString(R.string.frg_childrenmusic_title)));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    public void initView(View view) {
        spList = new ArrayList<>();
        spnSortMovie = view.findViewById(R.id.spnSortMovie);
        rvMovieDeals = view.findViewById(R.id.rvMovieDeals);
        productList = new ArrayList<>();
        productList.clear();
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        productDAO = new ProductDAO(getContext());

    }

    private void showProduct(List productArrayLisst) {
        productList = productArrayLisst;
        musicDealAdapter = new MusicDealAdapter(getContext(), productList);
        rvMovieDeals.setAdapter(musicDealAdapter);
        rvMovieDeals.setLayoutManager(gridLayoutManager);
        rvMovieDeals.setHasFixedSize(true);
        rvMovieDeals.scheduleLayoutAnimation();
        rvMovieDeals.setNestedScrollingEnabled(false);
        musicDealAdapter.notifyDataSetChanged();
    }
}
