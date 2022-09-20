package com.twentyfour_seven.catvillage.cat.repository;

import com.twentyfour_seven.catvillage.cat.entity.TagToCat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagToCatRepository extends JpaRepository<TagToCat, Long> {
}
