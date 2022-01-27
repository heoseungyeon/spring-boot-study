package com.example.springbootstudy.jpa;


import com.example.springbootstudy.domain.*;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JoinQueryTest {

  @Autowired
  UserRepository userRepository;

  @Autowired
  PostRepository postRepository;

  @Autowired
  EntityManager entityManager;

  @BeforeEach
  void setUp() {
    postRepository.deleteAll();
    userRepository.deleteAll();

    System.out.println("==== setUp start ====");

    for (int i = 0; i < 5; i++) {
      User user = User.builder()
          .name("user" + i)
          .build();

      userRepository.save(user);

      Post post = Post.builder()
          .title("title" + i)
          .description("description" + i)
          .user(user)
          .build();

      postRepository.save(post);
    }
    System.out.println("==== setUp end ====");

  }

  @Test
  @DisplayName("N+1문제를 확인한다.")
  void nPlusOneTest() {
    List<Post> posts = postRepository.findAll();

    posts.stream()
        .map(post -> post.getUser().getName())
        .forEach(System.out::println);
  }

  @Test
  @DisplayName("Fetch Join 쿼리 사용하여 N+1 문제를 해결한다.")
  void fetchJoinTest() {
    List<Post> posts = postRepository.findAllByFetchJoin();

    posts.stream()
        .map(post -> post.getUser().getName())
        .forEach(System.out::println);
  }

  @Test
  @DisplayName("일반 Join 쿼리 사용하여 N+1 문제를 해결한다.")
  void joinTest() {
    List<Post> posts = postRepository.findAllByJoin();

    posts.stream()
        .map(post -> post.getUser().getName())
        .forEach(System.out::println);
  }

  @Test
  @DisplayName("일반 Join 쿼리 와 DTO 를 사용하여 N+1 문제를 해결한다.")
  void joinWithDTOTest() {
    List<PostUserDTO> postUserDTOS = postRepository.findAllPostUserByFetchJoin();

    postUserDTOS.stream()
        .map(postUser -> postUser.getUserName())
        .forEach(System.out::println);
  }


}
