// [REQ-N005] 샘플 데이터 초기화 (2026-02-07)
package com.demo.blog.config;

import com.demo.blog.domain.Comment;
import com.demo.blog.domain.Post;
import com.demo.blog.domain.User;
import com.demo.blog.repository.CommentRepository;
import com.demo.blog.repository.PostRepository;
import com.demo.blog.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String[] POST_TITLES = {
            "Spring Boot 시작하기", "Java Stream API 활용법", "RESTful API 설계 원칙",
            "JPA N+1 문제 해결하기", "Docker 컨테이너 활용", "Git 브랜치 전략",
            "클린 코드 작성법", "디자인 패턴 이해하기", "테스트 주도 개발 실전",
            "마이크로서비스 아키텍처", "Redis 캐시 전략", "Spring Security 심화",
            "CI/CD 파이프라인 구축", "함수형 프로그래밍 입문", "데이터베이스 인덱스 최적화",
            "코드 리뷰 모범 사례", "웹 성능 최적화 기법", "API 버전 관리 전략",
            "로깅과 모니터링", "SOLID 원칙 실전 적용"
    };

    private static final String[] POST_CONTENTS = {
            "이 글에서는 %s에 대해 자세히 알아보겠습니다. 실무에서 자주 마주치는 상황을 중심으로 핵심 개념과 적용 방법을 단계별로 설명합니다. 초보자도 쉽게 따라 할 수 있도록 예제 코드와 함께 정리했습니다.",
            "%s은(는) 현대 소프트웨어 개발에서 매우 중요한 주제입니다. 이번 포스트에서는 기본 개념부터 고급 활용까지 폭넓게 다루며, 실제 프로젝트에 적용할 수 있는 팁을 공유합니다.",
            "개발자라면 반드시 알아야 할 %s! 이론만으로는 부족하고 실전에서 어떻게 적용하는지가 중요합니다. 다양한 사례와 함께 best practice를 소개합니다.",
            "오늘은 %s에 대해 포스팅합니다. 프로젝트를 진행하면서 겪었던 시행착오와 해결 과정을 솔직하게 공유합니다. 같은 고민을 가진 분들에게 도움이 되길 바랍니다.",
            "%s 관련 학습 내용을 정리했습니다. 공식 문서와 여러 레퍼런스를 참고하여 핵심만 추려 작성했습니다. 질문이나 피드백은 댓글로 남겨주세요.",
            "팀에서 %s을(를) 도입한 경험을 공유합니다. 도입 배경, 기술 선정 과정, 실제 적용 후 느낀 장단점을 정리했습니다.",
            "%s, 어렵게 느껴지시나요? 이 글을 통해 핵심 개념을 쉽게 이해할 수 있습니다. 단계별 튜토리얼과 함께 직접 실습해 보세요.",
            "최근 %s에 관한 흥미로운 사례를 발견해서 분석해 봤습니다. 기존 방식과의 차이점, 성능 비교, 그리고 적용 시 주의사항을 다룹니다.",
            "%s을(를) 효과적으로 적용하기 위한 가이드입니다. 자주 발생하는 실수와 이를 방지하는 방법, 그리고 실무에서 검증된 패턴을 소개합니다.",
            "이번 글에서는 %s의 내부 동작 원리를 파헤쳐 봅니다. 표면적인 사용법을 넘어 왜 이렇게 동작하는지 이해하면 더 효과적으로 활용할 수 있습니다."
    };

    private static final String[] COMMENT_CONTENTS = {
            "정말 유익한 글이네요! 감사합니다.",
            "이 부분은 저도 고민이 많았는데 덕분에 해결했습니다.",
            "좋은 정보 감사합니다. 다음 글도 기대됩니다!",
            "혹시 관련 레퍼런스 자료가 있을까요?",
            "실무에서 바로 적용해 봐야겠습니다.",
            "설명이 정말 깔끔하네요. 이해가 잘 됩니다.",
            "비슷한 경험을 했는데, 공감이 많이 됩니다.",
            "이 방법 말고 다른 접근법도 있을까요?",
            "초보 개발자에게 정말 도움이 되는 글입니다.",
            "북마크 해두고 나중에 다시 읽어야겠어요.",
            "코드 예제가 있어서 이해하기 쉬웠습니다.",
            "팀원들에게도 공유했습니다. 좋은 글 감사합니다!",
            "궁금한 점이 있는데 따로 질문드려도 될까요?",
            "이전 버전과의 호환성은 어떤가요?",
            "잘 읽었습니다. 많은 도움이 되었어요."
    };

    public DataInitializer(UserRepository userRepository,
                           PostRepository postRepository,
                           CommentRepository commentRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (userRepository.count() > 0) {
            log.info("샘플 데이터가 이미 존재합니다. 초기화를 건너뜁니다.");
            return;
        }

        log.info("샘플 데이터 초기화를 시작합니다...");

        List<User> users = createUsers();
        List<Post> posts = createPosts(users);
        createComments(users, posts);

        log.info("샘플 데이터 초기화 완료: 사용자 {}명, 포스트 {}개, 댓글 {}개",
                users.size(), posts.size(), 100);
    }

    private List<User> createUsers() {
        // [REQ-N005] BCrypt 인코딩은 1회만 수행하여 기동 시간 최적화
        String encodedPassword = passwordEncoder.encode("password");
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            users.add(new User("user" + i, encodedPassword, "user" + i + "@example.com"));
        }
        return userRepository.saveAll(users);
    }

    private List<Post> createPosts(List<User> users) {
        Random random = new Random(42);
        List<Post> posts = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            String title = POST_TITLES[i % POST_TITLES.length] + " #" + (i / POST_TITLES.length + 1);
            String content = String.format(POST_CONTENTS[i % POST_CONTENTS.length],
                    POST_TITLES[i % POST_TITLES.length]);
            User author = users.get(random.nextInt(users.size()));
            posts.add(new Post(title, content, author));
        }
        return postRepository.saveAll(posts);
    }

    private void createComments(List<User> users, List<Post> posts) {
        Random random = new Random(123);
        List<Comment> comments = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            User author = users.get(random.nextInt(users.size()));
            Post post = posts.get(random.nextInt(posts.size()));
            comments.add(new Comment(COMMENT_CONTENTS[i % COMMENT_CONTENTS.length], author, post));
        }
        commentRepository.saveAll(comments);
    }
}
