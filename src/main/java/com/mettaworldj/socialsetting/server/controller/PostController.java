package com.mettaworldj.socialsetting.server.controller;

import com.mettaworldj.socialsetting.server.dto.post.reponse.PostResponseDto;
import com.mettaworldj.socialsetting.server.dto.post.request.PostRequestDto;
import com.mettaworldj.socialsetting.server.service.post.IPostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subSetting/{subSettingName}/post")
@AllArgsConstructor
public class PostController {

    private final IPostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@PathVariable String subSettingName, @RequestBody PostRequestDto postRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(subSettingName, postRequestDto));
    }

}
