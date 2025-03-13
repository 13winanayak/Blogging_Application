Blogging Platform Backend API
This is a backend API for a blogging platform built using Spring Boot, Spring Data JPA, and JWT authentication. It provides RESTful endpoints for managing blogs, including adding, fetching, updating, and deleting blog posts. The application uses PostgreSQL/MySQL as the database.



Features :-
JWT-based authentication for secure access.
CRUD operations for blog management.
Pagination support for fetching all blogs.
PostgreSQL/MySQL integration using Spring Data JPA.


Tech Stack
Backend: Spring Boot, Spring Data JPA
Database: MySQL
Security: Spring Security with JWT Authentication
Build Tool: Maven




API Endpoints :-
Authentication

Register a new user
POST /auth/signup


Login and get JWT token
POST/auth/signin


Blog APIs (Require JWT Authentication)

 createBlog
POST /api/blogs/create


getAllBlogs
 GET /api/blogs/all


updateBlog
Put /api/blogs/{blogId}


deleteBlog
Delete  /api/blogs/{blogId}


pagging
GET /api/blogs/paginated


open ai chat
Post /api/chats/{blog_id}/bot
