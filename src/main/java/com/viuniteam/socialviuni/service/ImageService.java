package com.viuniteam.socialviuni.service;

import com.viuniteam.socialviuni.entity.Image;

public interface ImageService {
    Image findOneById(Long id);
    Image save(Image image);
    void deleteById(Long id);
}
