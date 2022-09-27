package com.twentyfour_seven.catvillage.cat.service;

import com.twentyfour_seven.catvillage.cat.entity.Cat;
import com.twentyfour_seven.catvillage.cat.entity.CatTag;
import com.twentyfour_seven.catvillage.cat.entity.TagToCat;
import com.twentyfour_seven.catvillage.cat.repository.CatTagRepository;
import com.twentyfour_seven.catvillage.cat.repository.TagToCatRepository;
import com.twentyfour_seven.catvillage.utils.CustomBeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CatTagService {
    private final CatTagRepository catTagRepository;
    private final TagToCatRepository tagToCatRepository;

    private final CustomBeanUtils beanUtils;

    public CatTagService(CatTagRepository catTagRepository, TagToCatRepository tagToCatRepository,
                         CustomBeanUtils beanUtils) {
        this.catTagRepository = catTagRepository;
        this.tagToCatRepository = tagToCatRepository;
        this.beanUtils = beanUtils;
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

    public List<CatTag> updateTag (List<CatTag> catTags, Cat cat) {
        catTags.forEach(
                catTag -> {
                    catTag = findExistCatTag(catTag);
                    TagToCat tagToCat = new TagToCat(catTag, cat);
                    findExistTagToCat(tagToCat);
                }
        );
        return catTags;
    }

    public TagToCat findExistTagToCat (TagToCat tagToCat) {
        Optional<TagToCat> optionalTagToCat =
                tagToCatRepository.findByCatTagAndCat(tagToCat.getCatTag(), tagToCat.getCat());
        TagToCat createTagToCat = optionalTagToCat.orElse(tagToCatRepository.save(tagToCat));
        return createTagToCat;
    }
}
