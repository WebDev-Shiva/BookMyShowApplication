package Repositories;

import models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Override
    List<Movie> findAll();
}
