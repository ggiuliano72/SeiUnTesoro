package com.ium.seiuntesoro.ui.photogallery;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.ium.seiuntesoro.databinding.FragmentItemListBinding;
import com.ium.seiuntesoro.databinding.ItemListContentBinding;
import com.ium.seiuntesoro.ui.adapter.ContentSearchAdapter;
import com.ium.seiuntesoro.ui.bean.PhotoGalleryItemBean;
import com.ium.seiuntesoro.ui.service.IPhotoGalleryService;
import com.ium.seiuntesoro.ui.service.PhotoGalleryService;
import com.ium.seiuntesoro.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class ItemListFragment extends Fragment {

    private IPhotoGalleryService photoGalleryService;

    ViewCompat.OnUnhandledKeyEventListenerCompat unhandledKeyEventListenerCompat = (v, event) -> {
        if (event.getKeyCode() == KeyEvent.KEYCODE_Z && event.isCtrlPressed()) {
            Toast.makeText(
                    v.getContext(),
                    "Undo (Ctrl + Z) shortcut triggered",
                    Toast.LENGTH_LONG
            ).show();
            return true;
        } else if (event.getKeyCode() == KeyEvent.KEYCODE_F && event.isCtrlPressed()) {
            Toast.makeText(
                    v.getContext(),
                    "Find (Ctrl + F) shortcut triggered",
                    Toast.LENGTH_LONG
            ).show();
            return true;
        }
        return false;
    };

    private FragmentItemListBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentItemListBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewCompat.addOnUnhandledKeyEventListener(view, unhandledKeyEventListenerCompat);

        RecyclerView recyclerView = binding.itemList;

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(mDividerItemDecoration);

        View.OnClickListener onClickListener = itemView -> {
            PhotoGalleryItemBean item = (PhotoGalleryItemBean) itemView.getTag();

            Bundle arguments = new Bundle();
            arguments.putString(ItemDetailFragment.ARG_ITEM_ID, item.getId().toString());
            arguments.putString(ItemDetailFragment.ARG_ITEM_TITOLO, item.getTitolo());
            arguments.putString(ItemDetailFragment.ARG_ITEM_DESCRIZIONE, item.getDescrizione());
            arguments.putString(ItemDetailFragment.ARG_ITEM_IMG, item.getPathImg());

            arguments.putString(ItemDetailFragment.ARG_ITEM_AUDIOSRC, item.getPathAudio());

            Navigation.findNavController(itemView).navigate(R.id.show_item_detail, arguments);

        };


        setupRecyclerView(recyclerView, onClickListener);
    }

    private void setupRecyclerView(
            RecyclerView recyclerView,
            View.OnClickListener onClickListener
    ) {

        photoGalleryService = new PhotoGalleryService();
        List<PhotoGalleryItemBean> listPg = photoGalleryService.getListaItemsFoto(getActivity().getApplicationContext()); 

        Context context = getActivity().getApplicationContext();

        recyclerView.setAdapter(new ContentSearchAdapter(
                listPg,
                onClickListener,
                context
        ));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



    private  List<PhotoGalleryItemBean> mockData(){
        List<PhotoGalleryItemBean> lista = new ArrayList<PhotoGalleryItemBean>();

        PhotoGalleryItemBean pg = new PhotoGalleryItemBean();
        pg.setId(1L);
        pg.setTitolo("TITOLONE 1");
        pg.setDescrizione("quel ramo del lago di Como, che volge a mezzogiorno, tra due catene non interrotte di monti, tutto a seni e a golfi, a seconda dello sporgere e del rientrare di quelli, vien, quasi a un tratto, a ristringersi, e a prender corso e figura di fiume, tra un promontorio a destra, e un’ampia costiera dall’altra parte; e il ponte, che ivi congiunge le due rive, par che renda ancor più sensibile all’occhio questa trasformazione, e segni il punto in cui il lago cessa, e l’Adda rincomincia, per ripigliar poi nome di lago dove le rive, allontanandosi di nuovo, lascian l’acqua distendersi e rallentarsi in nuovi golfi e in nuovi seni. La costiera, formata dal deposito di tre grossi torrenti, scende appoggiata a due monti contigui, l’uno detto di san [p. 10 modifica]Martino, l’altro, con voce lombarda, il Resegone, dai molti suoi cocuzzoli in fila, che in vero lo fanno somigliare a una sega: talchè non è chi, al primo vederlo, purchè sia di fronte, come per esempio di su le mura di Milano che guardano a settentrione, non lo discerna tosto, a un tal contrassegno, in quella lunga e vasta giogaia, dagli altri monti di nome più oscuro e di forma più comune. Per un buon pezzo, la costa sale con un pendìo lento e continuo; poi si rompe in poggi e in valloncelli, in erte e in ispianate, secondo l’ossatura de’ due monti, e il lavoro dell’acque. Il lembo estremo, tagliato dalle foci de’ torrenti, è quasi tutto");
        lista.add(pg);

        PhotoGalleryItemBean pg2 = new PhotoGalleryItemBean();
        pg2.setId(2L);
        pg2.setTitolo("TITOLONE 2");
        pg2.setDescrizione("ghiaia e ciottoloni; il resto, campi e vigne, sparse di terre, di ville, di casali; in qualche parte boschi, che si prolungano su per la montagna. Lecco, la principale di quelle terre, e che dà nome al territorio, giace poco discosto dal ponte, alla riva del lago, anzi viene in parte a trovarsi nel lago stesso, quando questo ingrossa: un gran borgo al giorno d’oggi, e che s’incammina a diventar città. Ai tempi in cui accaddero i fatti che prendiamo a raccontare, quel borgo, già considerabile, era anche un castello, e aveva perciò l’onore d’alloggiare un comandante, e il vantaggio di possedere una stabile guarnigione di soldati spagnoli, che insegnavan la modestia alle fanciulle e alle donne del paese, accarezzavan di tempo in tempo le spalle a qualche marito, a qualche padre; e, sul finir dell’estate, non mancavan mai di spandersi nelle vigne, per diradar l’uve, e alleggerire a’ contadini le fatiche della vendemmia. Dall’una all’altra di quelle terre, dall’alture alla riva, da un poggio all’altro, correvano, e corrono tuttavia, strade e stradette, più o men ripide, o piane; ogni tanto affondate, sepolte tra due muri, donde, alzando lo sguardo, non iscoprite che un pezzo di cielo e qualche vetta di monte; ogni tanto elevate su terrapieni aperti: e da qui la vista spazia per prospetti più o meno estesi");
        lista.add(pg2);

        return lista;

    }
}