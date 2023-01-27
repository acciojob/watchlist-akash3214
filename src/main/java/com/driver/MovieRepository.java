package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class MovieRepository {
    private HashMap<String, Movie> movieMap;
    private HashMap<String, Director> directorMap;
    private HashMap<String, List<String>> directorMovieMapping;

    public MovieRepository() {
        this.movieMap = new HashMap<String, Movie>();
        this.directorMap = new HashMap<String, Director>();
        this.directorMovieMapping = new HashMap<String, List<String>>();
    }

    public String addMovie(Movie movie) {
        movieMap.put(movie.getName(),movie);
        return "success";
    }

    public String addDirector(Director director) {
        directorMap.put(director.getName(), director);
        return "success";
    }

    public String addMovieDirectorPair(String movie, String director) {
        if(movieMap.containsKey(movie) && directorMap.containsKey(director)){
            movieMap.put(movie, movieMap.get(movie));
            directorMap.put(director, directorMap.get(director));
            List<String> currMoviesByDirector = new ArrayList<>();
            if(directorMovieMapping.containsKey(director)){
                currMoviesByDirector=directorMovieMapping.get(director);
            }
            currMoviesByDirector.add(movie);
            directorMovieMapping.put(director, currMoviesByDirector);
        }
        return "success";
    }

    public Movie getMovieByName(String name) {
        return movieMap.get(name);
    }

    public Director getDirectorByName(String name) {
        return directorMap.get(name);
    }

    public List<String> getMoviesByDirectorName(String director) {
        List<String> moviesList = new ArrayList<>();
        if(directorMovieMapping.containsKey(director)){
            moviesList = directorMovieMapping.get(director);
        }
        return moviesList;
    }

    public List<String> findAllMovies() {
        return new ArrayList<>(movieMap.keySet());
    }

    public String deleteDirectorByName(String director) {
        if(directorMap.containsKey(director)){
            if(directorMovieMapping.containsKey(director)){
                List<String> l= directorMovieMapping.get(director);
                for(String s: l){
                    movieMap.remove(s);
                }
                directorMovieMapping.remove(director);
            }
            directorMap.remove(director);
        }
        return "deleted successfully";
    }

    public String deleteAllDirectors() {
        for(String director : directorMap.keySet()){
           if(directorMovieMapping.containsKey(director)){
               List<String> list= directorMovieMapping.get(director);
               for(String m:list){
                   movieMap.remove(m);
               }
               directorMovieMapping.remove(director);
           }
           directorMap.remove(director);
        }
        return "deleted all directors successfully";
    }
}
