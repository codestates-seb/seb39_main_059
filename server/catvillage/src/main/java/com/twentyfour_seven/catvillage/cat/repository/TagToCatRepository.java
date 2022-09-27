package com.twentyfour_seven.catvillage.cat.repository;

import com.twentyfour_seven.catvillage.cat.entity.Cat;
import com.twentyfour_seven.catvillage.cat.entity.CatTag;
import com.twentyfour_seven.catvillage.cat.entity.TagToCat;
import com.twentyfour_seven.catvillage.cat.entity.TagToCatId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagToCatRepository extends JpaRepository<TagToCat, TagToCatId> {
    public Optional<TagToCat> findByCatTagAndCat (CatTag catTag, Cat cat);
}
