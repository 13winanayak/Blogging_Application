package com.app.blogging.repository;

import com.app.blogging.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BlogRepository extends JpaRepository<Blog, Long> {

    List<Blog> findByAuthorId(Long authorId);

}
