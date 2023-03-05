package com.jimin.board.service;

import com.jimin.board.dto.ResponseDto;
import com.jimin.board.entity.BoardEntity;
import com.jimin.board.entity.PopularSearchEntity;
import com.jimin.board.repository.PopularSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PopularSearchService {
    @Autowired
    PopularSearchRepository popularSearchRepository;

    public ResponseDto<List<PopularSearchEntity>> getPopularSearchList() {
        List<PopularSearchEntity> popularSearchList = new ArrayList<PopularSearchEntity>();

        try {
            popularSearchList = popularSearchRepository.findTop10ByOrderByPopularSearchCountDesc();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("Database Error");
        }
        return ResponseDto.setSuccess("Success", popularSearchList);
    }

}
