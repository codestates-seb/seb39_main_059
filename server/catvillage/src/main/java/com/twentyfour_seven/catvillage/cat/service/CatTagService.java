package com.twentyfour_seven.catvillage.cat.service;

import com.twentyfour_seven.catvillage.cat.entity.Cat;
import com.twentyfour_seven.catvillage.cat.entity.CatTag;
import com.twentyfour_seven.catvillage.cat.entity.TagToCat;
import com.twentyfour_seven.catvillage.cat.repository.CatTagRepository;
import com.twentyfour_seven.catvillage.cat.repository.TagToCatRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CatTagService {
    private final CatTagRepository catTagRepository;
    private final TagToCatRepository tagToCatRepository;

    public CatTagService(CatTagRepository catTagRepository, TagToCatRepository tagToCatRepository) {
        this.catTagRepository = catTagRepository;
        this.tagToCatRepository = tagToCatRepository;
    }

    public List<CatTag> saveTag(List<CatTag> catTags, Cat cat) {
        catTags.forEach(catTag -> {
            catTag = findExistCatTag(catTag);
            TagToCat tagToCat = new TagToCat(catTag, cat);
            saveTagToCat(tagToCat);
        });
        return catTags;
    }

    public CatTag findExistCatTag (CatTag catTag) {
        Optional<CatTag> optionalCatTag = catTagRepository.findByTagName(catTag.getTagName());
        CatTag findCatTag = optionalCatTag.orElse(catTagRepository.save(catTag));
        return findCatTag;
    }

    public TagToCat saveTagToCat(TagToCat tagToCat) {
        TagToCat createdTagToCat = tagToCatRepository.save(tagToCat);
        return createdTagToCat;
    }

    public void removeTagToCats(List<TagToCat> tagToCats) {
        tagToCatRepository.deleteAll(tagToCats);
    }
}
