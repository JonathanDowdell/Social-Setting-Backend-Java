package com.mettaworldj.socialsetting.server.controller;

import com.mettaworldj.socialsetting.server.dto.post.reponse.PostResponseDto;
import com.mettaworldj.socialsetting.server.service.post.IPostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final IPostService postService;

    @GetMapping("/feed")
    ResponseEntity<List<PostResponseDto>> getUserFeed(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "5") int amount) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getFeed(page, amount));
    }

}
