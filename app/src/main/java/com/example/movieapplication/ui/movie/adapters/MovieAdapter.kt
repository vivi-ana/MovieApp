package com.example.movieapplication.ui.movie.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapplication.core.BaseViewHolder
import com.example.movieapplication.data.model.movie.Movie
import com.example.movieapplication.databinding.MovieItemBinding


class MovieAdapter(
    private val moviesList: List<Movie>,
    private val itemClickListener: OnMovieClickListener
): RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnMovieClickListener{
        fun onMovieClick(movie: Movie)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = MoviesViewHolder(itemBinding, parent.context)
        itemBinding.root.setOnClickListener{
            val position = holder.bindingAdapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                ?: return@setOnClickListener //obtiene la posición o solo devuelve el onclick
            itemClickListener.onMovieClick(moviesList[position])
        }
        return holder

    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) { //cada uno de los elementos se dibuja en pantalla
        when(holder){
            is MoviesViewHolder -> holder.bind(moviesList[position])
        }
    }
    override fun getItemCount(): Int = moviesList.size

    private inner class MoviesViewHolder( //imagen de cada elemento
        val binding: MovieItemBinding,
        val context: Context
    ): BaseViewHolder<Movie>(binding.root){ //el root hace referencia a toda la layout
        override fun bind(item: Movie) {
            Glide.with(context).load("https://image.tmdb.org/t/p/w500/${item.backdrop_path}").centerCrop().into(binding.imgMovie)
        }
    }
}