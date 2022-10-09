package com.twentyfour_seven.catvillage.common.picture.service;

import com.twentyfour_seven.catvillage.common.picture.entity.Picture;
import com.twentyfour_seven.catvillage.common.picture.repository.PictureRepository;
import com.twentyfour_seven.catvillage.exception.BusinessLogicException;
import com.twentyfour_seven.catvillage.exception.ExceptionCode;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PictureService {
    private PictureRepository pictureRepository;

    public PictureService(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    public Picture createPicture(Picture picture) {
        return pictureRepository.save(picture);
    }

    public void removePicture(Long pictureId) {
        Picture findPicture = findVerifiedPicture(pictureId);
        pictureRepository.delete(findPicture);
    }

    private Picture findVerifiedPicture(Long pictureId) {
        Optional<Picture> findPicture = pictureRepository.findById(pictureId);
        return findPicture.orElseThrow(() -> new BusinessLogicException(ExceptionCode.PICTURE_NOT_FOUND));
    }

    private Picture findVerifiedPath(String path) {
        Optional<Picture> findPicture = pictureRepository.findByPath(path);
        return findPicture.orElseThrow(() -> new BusinessLogicException(ExceptionCode.PICTURE_NOT_FOUND));
    }
}
