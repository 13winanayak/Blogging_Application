Blogging Platform Backend API
This is a backend API for a blogging platform built using Spring Boot, Spring Data JPA, and JWT authentication. It provides RESTful endpoints for managing blogs, including adding, fetching, updating, and deleting blog posts. The application uses PostgreSQL/MySQL as the database.



Features :-<br>
JWT-based authentication for secure access.
CRUD operations for blog management.
Pagination support for fetching all blogs.
PostgreSQL/MySQL integration using Spring Data JPA.


Tech Stack<br>
Backend: Spring Boot, Spring Data JPA
Database: MySQL
Security: Spring Security with JWT Authentication
Build Tool: Maven




API Endpoints :-<br>
Authentication

Register a new user<br>
POST /auth/signup


Login and get JWT token<br>
POST/auth/signin


Blog APIs (Require JWT Authentication)

 createBlog<br>
POST /api/blogs/create


getAllBlogs<br>
 GET /api/blogs/all


updateBlog<br>
Put /api/blogs/{blogId}


deleteBlog<br>
Delete  /api/blogs/{blogId}


pagging<br>
GET /api/blogs/paginated


open ai chat<br>
Post /api/chats/{blog_id}/bot
