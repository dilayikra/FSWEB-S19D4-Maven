package com.workintech.s19d1.controller;

import com.workintech.s19d1.dto.ActorRequest;
import com.workintech.s19d1.entity.Actor;
import com.workintech.s19d1.entity.Movie;
import com.workintech.s19d1.service.ActorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/actor")
public class ActorController {

    private final ActorService actorService;

    @Autowired
    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping
    public List<Actor> findAll() {
        log.info("Tüm aktörler getiriliyor.");
        return actorService.findAll();
    }

    @GetMapping("/{id}")
    public Actor findById(@PathVariable long id) {
        log.info("Aktör getiriliyor, id: {}", id);
        return actorService.findById(id);
    }

    @PostMapping
    public Actor save(@RequestBody ActorRequest actorRequest) {
        log.info("Yeni aktör kaydediliyor: {} {}",
                actorRequest.getActor().getFirstName(), actorRequest.getActor().getLastName());

        Actor actor = actorRequest.getActor();
        List<Movie> movies = actorRequest.getMovies();

        if (movies != null) {
            for (Movie movie : movies) {
                actor.addMovie(movie);
            }
        }
        return actorService.save(actor);
    }

    @PutMapping("/{id}")
    public Actor update(@PathVariable long id, @RequestBody Actor actor) {
        log.info("Aktör güncelleniyor, id: {}", id);
        actorService.findById(id);
        actor.setId(id);
        return actorService.save(actor);
    }

    @DeleteMapping("/{id}")
    public Actor delete(@PathVariable long id) {
        log.info("Aktör siliniyor, id: {}", id);
        Actor actor = actorService.findById(id);
        actorService.delete(actor);
        return actor;
    }
}
