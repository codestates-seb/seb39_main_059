package com.twentyfour_seven.catvillage.common.picture.repository;

import com.twentyfour_seven.catvillage.common.picture.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PictureRepository extends JpaRepository<Picture, Long> {
    Optional<Picture> findByPath(String path);
}
