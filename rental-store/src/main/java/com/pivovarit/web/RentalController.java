package com.pivovarit.web;

import com.pivovarit.domain.rental.RentalFacade;
import com.pivovarit.domain.rental.api.MovieAddRequest;
import com.pivovarit.domain.rental.api.MovieDto;
import com.pivovarit.domain.rental.api.MovieId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Optional;

@RestController
public class RentalController {

    private final RentalFacade rentalFacade;

    RentalController(RentalFacade rentalFacade) {
        this.rentalFacade = rentalFacade;
    }

    @GetMapping("/movies")
    public Collection<MovieDto> getMovies(@RequestParam(required = false) String type) {
        return type == null ? rentalFacade.findAllMovies() : rentalFacade.findMovieByType(type);
    }

    @GetMapping("/movies/{id}")
    public Optional<MovieDto> getMoviesById(@PathVariable int id) {
        return rentalFacade.findMovieById(new MovieId(id));
    }

    @PostMapping("/movies")
    public void addMovie(@RequestBody MovieAddRequest request) {
        rentalFacade.addMovie(request);
    }

    @PostMapping("/movies/{id}/rent")
    public void rentMovie(@RequestBody Renter renter, @PathVariable long id) {
        rentalFacade.rentMovie(renter.login(), new MovieId(id));
    }

    @PostMapping("/movies/{id}/return")
    public void returnMovie(@RequestBody Renter renter, @PathVariable long id) {
        rentalFacade.returnMovie(renter.login(), new MovieId(id));
    }

    private record Renter(String login) {
    }

    // curl --header "Content-Type: application/json" --request POST --data '{ "id":14, "title":"spiderman", "type":"NEW"}' http://localhost:8081/movies
}
