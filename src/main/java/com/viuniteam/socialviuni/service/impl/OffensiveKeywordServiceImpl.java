package com.viuniteam.socialviuni.service.impl;

import com.viuniteam.socialviuni.dto.response.offensivekeyword.OffensiveKeywordResponse;
import com.viuniteam.socialviuni.entity.OffensiveKeyword;
import com.viuniteam.socialviuni.exception.BadRequestException;
import com.viuniteam.socialviuni.exception.OKException;
import com.viuniteam.socialviuni.exception.ObjectNotFoundException;
import com.viuniteam.socialviuni.mapper.response.offensivekeyword.OffensiveKeywordResponseMapper;
import com.viuniteam.socialviuni.repository.OffensiveKeywordRepository;
import com.viuniteam.socialviuni.service.OffensiveKeywordService;
import com.viuniteam.socialviuni.utils.Keyword;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class OffensiveKeywordServiceImpl implements OffensiveKeywordService {

    private final OffensiveKeywordRepository offensiveKeywordRepository;
    private final OffensiveKeywordResponseMapper offensiveKeywordResponseMapper;

    @Override
    public void add(Keyword keyword) {
        String keywordValue = keyword.getKeyword().toLowerCase();
        OffensiveKeyword offensiveKeyword = offensiveKeywordRepository.findOneByKeyword(keywordValue);
        if(offensiveKeyword==null) {
            offensiveKeywordRepository.save(new OffensiveKeyword(keywordValue));
            throw new OKException("Thêm từ khóa thành công");
        }
        throw new BadRequestException("Từ khóa đã tồn tại");
    }

    @Override
    public void remove(Long keywordId) {
        OffensiveKeyword offensiveKeyword = offensiveKeywordRepository.findOneById(keywordId);
        if(offensiveKeyword == null) throw new ObjectNotFoundException("Từ khóa không tồn tại");
        offensiveKeywordRepository.delete(offensiveKeyword);
        throw new OKException("Xóa thành công");
    }

    @Override
    public void update(Long keywordId, Keyword keyword) {
        OffensiveKeyword offensiveKeyword = offensiveKeywordRepository.findOneById(keywordId);
        if(offensiveKeyword==null)
            throw new ObjectNotFoundException("Từ khóa không tồn tại");
        String keywordValue = keyword.getKeyword().toLowerCase();
        offensiveKeyword.setKeyword(keywordValue);
        offensiveKeywordRepository.save(offensiveKeyword);
        throw new OKException("Cập nhật từ khóa thành công");
    }

    @Override
    public List<OffensiveKeywordResponse> getAll() {
        return offensiveKeywordResponseMapper.from(offensiveKeywordRepository.findAll());
    }

    @Override
    public boolean isExist(String content) {
        List<OffensiveKeyword> offensiveKeywordList = offensiveKeywordRepository.findAll();
        for(OffensiveKeyword offensiveKeyword : offensiveKeywordList){
            if(content.contains(offensiveKeyword.getKeyword())) return true;
        }
        return false;
    }
}
