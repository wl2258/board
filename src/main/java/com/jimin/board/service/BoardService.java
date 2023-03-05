package com.jimin.board.service;

import com.jimin.board.dto.ResponseDto;
import com.jimin.board.entity.BoardEntity;
import com.jimin.board.repository.BoardRepository;
import com.jimin.board.repository.PopularSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BoardService {
    @Autowired
    BoardRepository boardRepository;

    public ResponseDto<List<BoardEntity>> getTop3() {
        List<BoardEntity> boardList =  new ArrayList<>();
        Date date = Date.from(Instant.now().minus(7, ChronoUnit.DAYS));
        try {
            boardList = boardRepository.findTop3ByBoardWriteDateAfterOrderByBoardLikeCountDesc(date);
        }
        catch (Exception e) {
            return ResponseDto.setFailed("Database Error");
        }
        return ResponseDto.setSuccess("Success", boardList);
    }

    public ResponseDto<List<BoardEntity>> getList() {
        List<BoardEntity> boardList = new ArrayList<>();

        try {
            boardList = boardRepository.findByOrderByBoardWriteDateDesc();
        } catch (Exception e) {
            return  ResponseDto.setFailed("Database Error");
        }

        return ResponseDto.setSuccess("Success", boardList);
    }

    public ResponseDto<List<BoardEntity>> getSearchList(String boardTitle) {
        List<BoardEntity> boardList = new ArrayList<>();

        try {
            boardList = boardRepository.findByBoardTitleContains(boardTitle);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("Database Error");
        }
        return ResponseDto.setSuccess("Success", boardList);
    }
}
