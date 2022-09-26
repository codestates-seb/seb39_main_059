package com.twentyfour_seven.catvillage.cat.repository;

import com.twentyfour_seven.catvillage.cat.entity.TagToCat;
import com.twentyfour_seven.catvillage.cat.entity.TagToCatId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagToCatRepository extends JpaRepository<TagToCat, TagToCatId> {
}
