package com.jimin.board.controller;

import com.jimin.board.dto.ResponseDto;
import com.jimin.board.entity.BoardEntity;
import com.jimin.board.entity.PopularSearchEntity;
import com.jimin.board.service.BoardService;
import com.jimin.board.service.PopularSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/board")
public class BoardController {

    @Autowired
    BoardService boardService;
    @Autowired
    PopularSearchService popularSearchService;

    @GetMapping("/top3")
    public ResponseDto<List<BoardEntity>> getTop3() {
        return boardService.getTop3();
    }

    @GetMapping("/list")
    public ResponseDto<List<BoardEntity>> getList() {
        return boardService.getList();
    }

    @GetMapping("/popularsearchlist")
    public ResponseDto<List<PopularSearchEntity>> getPopularSearchList() {
        return popularSearchService.getPopularSearchList();
    }

    @GetMapping("/search/{title}")
    public ResponseDto<List<BoardEntity>> getSearchList(@PathVariable("title")String title) {
        return boardService.getSearchList(title);
    }
}
