package com.example.springbootstudy.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

  @Query("SELECT p FROM Post p "
      + "JOIN FETCH p.user")
  List<Post> findAllByFetchJoin();

  @Query("SELECT new com.example.springbootstudy.domain.PostUserDTO(u.name, p.title) FROM Post p "
      + "LEFT JOIN User u "
      + "ON p.user.id = u.id")
  List<PostUserDTO> findAllPostUserByFetchJoin();

  @Query("SELECT p FROM Post p "
      + "LEFT JOIN User u "
      + "ON p.user.id = u.id")
  List<Post> findAllByJoin();
}
