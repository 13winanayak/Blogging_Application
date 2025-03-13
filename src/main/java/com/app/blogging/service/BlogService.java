package com.app.blogging.service;

import com.app.blogging.model.Blog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface BlogService {

    Blog createBlog(Long authorId, Blog blog);
    Blog getBlogById(Long blogId);
    List<Blog> getAllBlogs();
    Blog updateBlog(Long blogId, Blog blogDetails);
    void deleteBlog(Long blogId);
    public Page<Blog> getAllBlogs(Pageable pageable);
}
