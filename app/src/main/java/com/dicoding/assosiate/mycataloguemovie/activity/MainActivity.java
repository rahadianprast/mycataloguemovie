package com.dicoding.assosiate.mycataloguemovie.activity;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.dicoding.assosiate.mycataloguemovie.R;
import com.dicoding.assosiate.mycataloguemovie.adapter.MovieAdapter;
import com.dicoding.assosiate.mycataloguemovie.model.MovieItems;
import com.dicoding.assosiate.mycataloguemovie.util.MovieTaskLoader;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<MovieItems>> {
    ListView listView;
    MovieAdapter adapter;
    EditText editMovie;
    Button btnCari;

    static final String EXTRA_MOVIE = "EXTRAS_MOVIE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter= new MovieAdapter(this);
        adapter.notifyDataSetChanged();

        listView = (ListView)findViewById(R.id.list_movie);
        listView.setAdapter(adapter);


        editMovie = (EditText)findViewById(R.id.edit_movie);

        btnCari = (Button)findViewById(R.id.btn_movie);
        btnCari.setOnClickListener(myListener);

        String film = editMovie.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_MOVIE,film);

        getLoaderManager().initLoader(0, bundle, this);
    }


    @Override
    public Loader<ArrayList<MovieItems>> onCreateLoader(int id, Bundle args) {
        String judulFilm = "";
        if (args != null){
            judulFilm = args.getString(EXTRA_MOVIE);
        }
        return new MovieTaskLoader(this, judulFilm);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieItems>> loader, ArrayList<MovieItems> data) {
        adapter.setData(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieItems>> loader) {
        adapter.setData(null);
    }

    View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String film = editMovie.getText().toString();
            if (TextUtils.isEmpty(film))return;
            Bundle bundle = new Bundle();
            bundle.putString(EXTRA_MOVIE,film);
            getLoaderManager().restartLoader(0,bundle,MainActivity.this);
        }
    };
}
