package com.example.springbootstudy.jpa;

import com.example.springbootstudy.domain.PostRepository;
import com.example.springbootstudy.domain.User;
import com.example.springbootstudy.domain.UserRepository;
import javax.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class JpqlEntityManagerTest {

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
  }

  @Test
  @DisplayName("엔티티 생명주기를 확인한다.")
  void entityLifeCycleTest() {
    // 비영속
    User user = new User(1L, "user1");

    //영속
    entityManager.persist(user);

    //준영속
    entityManager.detach(user);

    //삭제
    entityManager.remove(user);

  }

  @Test
  @DisplayName("영속화 상태를 확인한다")
  @Transactional
  void persistenceTest() {
    // given
    User user = User.builder()
        .name("user")
        .build();

    // when
    User savedUser = userRepository.save(user);

    // then
    System.out.println(entityManager.contains(savedUser) + " " + entityManager.contains(user));
    Assertions.assertThat(entityManager.contains(savedUser)).isTrue();
    Assertions.assertThat(entityManager.contains(user)).isTrue();

    //userRepository.findById(savedUser.getId());

  }

  @Test
  @DisplayName("영속화 컨텍스트 1차 캐시를 확인한다")
  void firstCacheTest() {
    // given
    User user = User.builder()
        .name("user")
        .build();

    // when
    User savedUser = userRepository.save(user);

    // when
    userRepository.findById(savedUser.getId());

  }

}
