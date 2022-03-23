package com.example.demo.domain.posts;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PostsRepositoryTest {

	@Autowired
	PostsRepository postsRepository;

	@AfterAll
	public void cleanup() {
		System.out.println("---cleanup---");
		postsRepository.deleteAll();
		System.out.println("---cleanup end---");
	}

	@Test
	public void list() {
		String title = "게시글";
		String content = "본문";

		postsRepository.save(Posts.builder()
				.title(title)
				.content(content)
				.author("snjune6@naver.com")
				.build());

		List<Posts> postsList = postsRepository.findAll();

		Posts posts  = postsList.get(0);
		assertThat(posts.getTitle()).isEqualTo(title);
		assertThat(posts.getContent()).isEqualTo(content);
		System.out.println(posts);
		System.out.println(posts.getTitle());
	}

	@Test
	public void BaseTimeEntity_insert() {
		LocalDateTime now = LocalDateTime.of(2019,6,4,0,0,0);
		postsRepository.save(Posts.builder()
				.title("title")
				.content("content")
				.author("author")
				.build());


		List<Posts> postsList = postsRepository.findAll();


		Posts posts = postsList.get(0);

		System.out.println(">>>>>>>>> createDate="+posts.getCreatedDate()+", modifiedDate="+posts.getModifiedDate());

		assertThat(posts.getCreatedDate()).isAfter(now);
		assertThat(posts.getModifiedDate()).isAfter(now);
	}

}
