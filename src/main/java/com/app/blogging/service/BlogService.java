package com.app.blogging.service;

import com.app.blogging.model.Blog;

import java.util.List;

public interface BlogService {

    Blog createBlog(Long authorId, Blog blog);
    Blog getBlogById(Long blogId);
    List<Blog> getAllBlogs();
}
