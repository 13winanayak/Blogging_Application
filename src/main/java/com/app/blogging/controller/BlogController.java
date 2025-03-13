package com.app.blogging.controller;

import com.app.blogging.model.Blog;
import com.app.blogging.service.BlogService;

import com.app.blogging.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/blogs")
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

    @GetMapping("/all")
    public ResponseEntity<List<Blog>> getAllBlogs() {
        List<Blog> blogs = blogService.getAllBlogs();
        return ResponseEntity.ok(blogs);
    }

    
    @GetMapping("/{blogId}")
    public ResponseEntity<Blog> getBlogById(@PathVariable Long blogId) {
        Blog blog = blogService.getBlogById(blogId);
        return ResponseEntity.ok(blog);
    }

    @PutMapping("/{blogId}")
    public ResponseEntity<Blog> updateBlog(@PathVariable Long blogId, @RequestBody Blog blogDetails) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); 

        Blog updatedBlog = blogService.updateBlog(blogId, blogDetails);
        return ResponseEntity.ok(updatedBlog);
    }

    // ✅ Delete Blog
    @DeleteMapping("/{blogId}")
    public ResponseEntity<String> deleteBlog(@PathVariable Long blogId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Get the logged-in user

        blogService.deleteBlog(blogId);
        return ResponseEntity.ok("Blog deleted successfully");
    }

    @GetMapping("/paginated")
    public Page<Blog> getBlogs(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "5") int size,
                               @RequestParam(defaultValue = "title") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return blogService.getAllBlogs(pageable);
    }






}
