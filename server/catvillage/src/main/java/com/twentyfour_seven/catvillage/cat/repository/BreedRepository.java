package com.twentyfour_seven.catvillage.cat.repository;

import com.twentyfour_seven.catvillage.cat.entity.Breed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BreedRepository extends JpaRepository<Breed, Long> {
    public Optional<Breed> findByKorName(String korName);
}
