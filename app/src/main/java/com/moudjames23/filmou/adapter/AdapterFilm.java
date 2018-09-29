package com.moudjames23.filmou.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.moudjames23.filmou.R;
import com.moudjames23.filmou.app.Constant;
import com.moudjames23.filmou.model.Film;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Moud on 29-Sep-18.
 */
public class AdapterFilm extends RecyclerView.Adapter<AdapterFilm.VHFilm>{

    private List<Film> filmList;

    private OnFilmClickLister onFilmClickLister;

    public AdapterFilm(List<Film> filmList) {
        this.filmList = filmList;
    }

    @Override
    public VHFilm onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VHFilm(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film, parent, false));
    }

    @Override
    public void onBindViewHolder(VHFilm holder, int position) {
        holder.bind(filmList.get(position));
    }

    @Override
    public int getItemCount() {
        return filmList.size();
    }

    public void setOnFilmClickLister(OnFilmClickLister lister)
    {
        this.onFilmClickLister = lister;
    }

    public class VHFilm extends RecyclerView.ViewHolder
    {
        private ImageView poster;
        private TextView titre;

        public VHFilm(View itemView) {
            super(itemView);

            poster = itemView.findViewById(R.id.poster);
            titre = itemView.findViewById(R.id.titre);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onFilmClickLister != null)
                        onFilmClickLister.onFilmClick(filmList.get(getLayoutPosition()));
                }
            });
        }

        public void bind(Film film)
        {
            titre.setText(film.getTitre());

            Picasso.with(itemView.getContext())
                    .load(Constant.IMAGE_URL+ "" +film.getPoster())
                    .into(poster);
        }
    }


    public interface OnFilmClickLister
    {
        void onFilmClick(Film film);
    }
}
