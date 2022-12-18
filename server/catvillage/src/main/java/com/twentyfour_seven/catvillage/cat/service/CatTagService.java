package com.twentyfour_seven.catvillage.cat.service;

import com.twentyfour_seven.catvillage.cat.entity.Cat;
import com.twentyfour_seven.catvillage.cat.entity.CatTag;
import com.twentyfour_seven.catvillage.cat.entity.TagToCat;
import com.twentyfour_seven.catvillage.cat.repository.CatTagRepository;
import com.twentyfour_seven.catvillage.cat.repository.TagToCatRepository;
import com.twentyfour_seven.catvillage.utils.CustomBeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CatTagService {
    private final CatTagRepository catTagRepository;
    private final TagToCatRepository tagToCatRepository;

    public CatTagService(CatTagRepository catTagRepository, TagToCatRepository tagToCatRepository) {
        this.catTagRepository = catTagRepository;
        this.tagToCatRepository = tagToCatRepository;
    }

    public List<CatTag> saveTag(List<CatTag> catTags, Cat cat) {
        // catTag 가 null 일 경우 null 리턴
        if (catTags == null) {
            return null;
        }
        catTags.forEach(catTag -> {
            CatTag saveCatTag = findExistCatTag(catTag);
            TagToCat tagToCat = new TagToCat(saveCatTag, cat);
            saveTagToCat(tagToCat);
        });
        return catTags;
    }

    public CatTag findExistCatTag (CatTag catTag) {
        Optional<CatTag> optionalCatTag = catTagRepository.findByTagName(catTag.getTagName());
        return optionalCatTag.orElseGet(() -> catTagRepository.save(catTag));
    }

    public void saveTagToCat(TagToCat tagToCat) {
        tagToCatRepository.save(tagToCat);
    }

    public List<CatTag> updateTag (List<CatTag> catTags, Cat cat) {
        List<TagToCat> findTagToCats = tagToCatRepository.findByCat(cat);
        List<CatTag> updateCatTags = new ArrayList<>();

        // catTag에 값이 하나도 없을 경우 cat에 연결되있던 TagToCat 객체 모두 삭제
        if (catTags == null) {
            if(!findTagToCats.isEmpty()) {
                findTagToCats.forEach(this::removeTagToCat);
            }
            return null;
        }

        // 추가된 태그 저장
        catTags.forEach(
                catTag -> {
                    CatTag saveCatTag = findExistCatTag(catTag);
                    TagToCat tagToCat = new TagToCat(saveCatTag, cat);
                    findExistTagToCat(tagToCat);
                    updateCatTags.add(saveCatTag);
                }
        );

        // 사라진 테그 삭제
        List<CatTag> findCatTags = findTagToCats.stream().map(tagToCat -> tagToCat.getCatTag()).collect(Collectors.toList());
        List<String> catTagNames = catTags.stream().map(catTag -> catTag.getTagName()).collect(Collectors.toList());
        findCatTags.stream().forEach(
                catTag -> {
                    if (!catTagNames.contains(catTag.getTagName())) {
                        removeTagToCat(new TagToCat(catTag, cat));
                    }
                }
        );

        return updateCatTags;
    }

    public TagToCat findExistTagToCat (TagToCat tagToCat) {
        Optional<TagToCat> optionalTagToCat =
                tagToCatRepository.findByCatTagAndCat(tagToCat.getCatTag(), tagToCat.getCat());
        TagToCat createTagToCat = optionalTagToCat.orElse(tagToCatRepository.save(tagToCat));
        return createTagToCat;
    }

    public void removeTagToCat (TagToCat tagToCat) {
        tagToCatRepository.delete(tagToCat);
    }
}