package com.app.blogging.controller;

import com.app.blogging.model.Blog;
import com.app.blogging.service.BlogService;
import com.app.blogging.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userSerivce;



    @PostMapping("/create")
    public ResponseEntity<Blog> createBlog(@RequestHeader("Authorization") String jwt, @RequestBody Blog blog) throws Exception{
        
        if (jwt == null) {
            throw new Exception("token missing...");
        }

        Long userId = userSerivce.findUserProfileByJwt(jwt).getId();

        Blog createdBlog = blogService.createBlog(userId, blog);
        return ResponseEntity.ok(createdBlog);
    }

    @GetMapping
    public ResponseEntity<List<Blog>> getAllBlogs() {
        List<Blog> blogs = blogService.getAllBlogs();
        return ResponseEntity.ok(blogs);
    }

    // ✅ Get a blog by ID (Public)
    @GetMapping("/{blogId}")
    public ResponseEntity<Blog> getBlogById(@PathVariable Long blogId) {
        Blog blog = blogService.getBlogById(blogId);
        return ResponseEntity.ok(blog);
    }

}
