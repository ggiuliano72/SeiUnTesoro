package com.ium.seiuntesoro.ui.search;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.ium.seiuntesoro.MainActivity;
import com.ium.seiuntesoro.R;
import com.ium.seiuntesoro.databinding.FragmentSearchBinding;
import com.ium.seiuntesoro.ui.adapter.ContentSearchAdapter;
import com.ium.seiuntesoro.ui.bean.PhotoGalleryItemBean;
import com.ium.seiuntesoro.ui.photogallery.ItemDetailFragment;
import com.ium.seiuntesoro.ui.service.IPhotoGalleryService;
import com.ium.seiuntesoro.ui.service.PhotoGalleryService;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment
{

    private IPhotoGalleryService photoGalleryService;

    FragmentSearchBinding binding;
    ContentSearchAdapter contentSearchAdapter;

    public SearchFragment() {
    }

    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSearchBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SearchView sv = binding.searchView;
        RecyclerView rv = binding.itemList;

        View.OnClickListener onClickListener = itemView -> {
            PhotoGalleryItemBean item = (PhotoGalleryItemBean) itemView.getTag();

            Bundle arguments = new Bundle();
            arguments.putString(ItemDetailFragment.ARG_ITEM_ID, item.getId().toString());
            arguments.putString(ItemDetailFragment.ARG_ITEM_TITOLO, item.getTitolo());
            arguments.putString(ItemDetailFragment.ARG_ITEM_DESCRIZIONE, item.getDescrizione());
            arguments.putString(ItemDetailFragment.ARG_ITEM_IMG, item.getPathImg());
            arguments.putString(ItemDetailFragment.ARG_ITEM_AUDIOSRC, item.getPathAudio());
            Navigation.findNavController(itemView).navigate(R.id.action_navigation_search_to_item_detail_fragment, arguments);
        };


        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                setupRecyclerView(rv, onClickListener, performSearch(query));
                sv.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty() && !"".equals(newText) && newText != null) {
                    setupRecyclerView(rv, onClickListener, performSearch(newText));
                } else {
                    setupRecyclerView(rv, onClickListener, new ArrayList<PhotoGalleryItemBean>());//ripulisce la lista dei risultati se il campo per la ricerca è vuoto
                }
                return true;
            }
        });

    }

    private List<PhotoGalleryItemBean> performSearch(String itemSearchable){
        photoGalleryService = new PhotoGalleryService();
        List<PhotoGalleryItemBean> listPg = photoGalleryService.getSingleItemFotoByDescrizione(getActivity().getApplicationContext(), itemSearchable);
        Log.println(Log.INFO, "Test", "Tot. trovati: " + listPg.size());
        return listPg;
    }

    private void setupRecyclerView(
            RecyclerView recyclerView,
            View.OnClickListener onClickListener,
            List<PhotoGalleryItemBean> listPg
    ) {

        Context context = getActivity().getApplicationContext();

        contentSearchAdapter = new ContentSearchAdapter(
                listPg,
                onClickListener,
                context
        );
        recyclerView.setAdapter(contentSearchAdapter);
    }
}