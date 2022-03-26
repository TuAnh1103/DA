package com.viuniteam.socialviuni.service.impl;

import com.viuniteam.socialviuni.entity.Image;
import com.viuniteam.socialviuni.repository.ImageRepository;
import com.viuniteam.socialviuni.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    @Override
    public Image findOneById(Long id) {
        return imageRepository.findOneById(id);
    }

    @Override
    public Image save(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public void deleteById(Long id) {
        imageRepository.deleteById(id);
    }
}
