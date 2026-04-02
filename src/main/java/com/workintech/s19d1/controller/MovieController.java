package com.workintech.s19d1.controller;

import com.workintech.s19d1.entity.Movie;
import com.workintech.s19d1.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public List<Movie> findAll() {
        log.info("Tüm filmler getiriliyor.");
        return movieService.findAll();
    }

    @GetMapping("/{id}")
    public Movie findById(@PathVariable long id) {
        log.info("Film getiriliyor, id: {}", id);
        return movieService.findById(id);
    }

    @PostMapping
    public Movie save(@RequestBody Movie movie) {
        log.info("Yeni film kaydediliyor: {}", movie.getName());
        // Yönerge: Movie ve Actor beraber kaydedilmeli.
        // Movie nesnesi içindeki actors listesi JPA Cascade sayesinde beraber kaydedilecektir.
        return movieService.save(movie);
    }

    @PutMapping("/{id}")
    public Movie update(@PathVariable long id, @RequestBody Movie movie) {
        log.info("Film güncelleniyor, id: {}", id);
        movieService.findById(id); // Varlığını kontrol et, yoksa hata fırlatır
        movie.setId(id);
        return movieService.save(movie);
    }

    @DeleteMapping("/{id}")
    public Movie delete(@PathVariable long id) {
        log.info("Film siliniyor, id: {}", id);
        Movie movie = movieService.findById(id);
        movieService.delete(movie);
        return movie;
    }
}
