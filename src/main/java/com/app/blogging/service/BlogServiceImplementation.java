package com.app.blogging.service;

import com.app.blogging.model.Blog;
import com.app.blogging.model.User;
import com.app.blogging.repository.BlogRepository;
import com.app.blogging.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@Transactional
public class BlogServiceImplementation implements BlogService{

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserRepository userRepository;  // Assuming you have a UserRepository

    @Override
    public Blog createBlog(Long authorId, Blog blog) {
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        blog.setAuthor(author);
        return blogRepository.save(blog);
    }

    @Override
    public Blog getBlogById(Long blogId) {
        return blogRepository.findById(blogId)
                .orElseThrow(() -> new RuntimeException("Blog not found"));
    }


    @Override
    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    @Override
    public Blog updateBlog(Long blogId, Blog blogDetails) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new RuntimeException("Blog not found"));

        blog.setTitle(blogDetails.getTitle());
        blog.setContent(blogDetails.getContent());

        return blogRepository.save(blog);
    }

    @Override
    public void deleteBlog(Long blogId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new RuntimeException("Blog not found"));
        blogRepository.delete(blog);
    }

    public Page<Blog> getAllBlogs(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

}
