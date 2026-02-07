// [REQ-N009] 권한/보안 E2E 테스트 — TC-SEC-001~007 (2026-02-07)
package com.demo.blog.e2e;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("e2e")
class SecurityE2ETest extends BaseE2ETest {

    // TC-SEC-001: 비로그인 사용자의 포스트 목록 접근
    @Test
    void 포스트목록_비로그인_로그인페이지리다이렉트() {
        navigateTo("/posts");
        assertThat(page).hasURL(Pattern.compile("/auth/login"));
    }

    // TC-SEC-002: 비로그인 사용자의 포스트 상세 접근
    @Test
    void 포스트상세_비로그인_로그인페이지리다이렉트() {
        navigateTo("/posts/1");
        assertThat(page).hasURL(Pattern.compile("/auth/login"));
    }

    // TC-SEC-003: 타인 포스트 수정 시도
    @Test
    void 포스트수정_타인포스트_에러발생() {
        // user1으로 포스트 작성
        loginAsUser1();
        navigateTo("/posts/new");
        page.locator("input#title").fill("user1의 보안 테스트 포스트");
        page.locator("textarea#content").fill("내용");
        page.locator(".form-actions button[type=submit]").click();
        page.waitForURL(Pattern.compile("/posts/\\d+"));

        String postUrl = page.url();
        String postId = postUrl.replaceAll(".*/posts/(\\d+).*", "$1");

        // 로그아웃 후 user2로 로그인
        page.locator("button:has-text('로그아웃')").click();
        page.waitForURL(Pattern.compile("/auth/login"));
        login("user2", "password");

        // user1의 포스트 수정 페이지 접근 → 수정 시도
        navigateTo("/posts/" + postId + "/edit");
        page.locator("input#title").clear();
        page.locator("input#title").fill("변경 시도");
        page.locator(".form-actions button[type=submit]").click();

        // "본인의 포스트만 수정할 수 있습니다." 에러 또는 403
        String bodyText = page.locator("body").textContent();
        assertTrue(bodyText.contains("본인의 포스트만 수정할 수 있습니다")
                || bodyText.contains("403")
                || bodyText.contains("Forbidden"));
    }

    // TC-SEC-004: 타인 포스트 삭제 시도 (UI 버튼 미표시)
    @Test
    void 포스트삭제_타인포스트_삭제버튼미표시() {
        // user1으로 포스트 작성
        loginAsUser1();
        navigateTo("/posts/new");
        page.locator("input#title").fill("user1의 삭제방지 포스트");
        page.locator("textarea#content").fill("내용");
        page.locator(".form-actions button[type=submit]").click();
        page.waitForURL(Pattern.compile("/posts/\\d+"));

        String postUrl = page.url();

        // 로그아웃 후 user2로 로그인
        page.locator("button:has-text('로그아웃')").click();
        page.waitForURL(Pattern.compile("/auth/login"));
        login("user2", "password");

        // user1의 포스트 상세에서 수정/삭제 버튼이 없어야 함
        page.navigate(postUrl);
        assertThat(page.locator(".post-actions")).hasCount(0);
    }

    // TC-SEC-005: 타인 댓글 삭제 시도 (삭제 버튼 미표시)
    @Test
    void 댓글삭제_타인댓글_삭제버튼미표시() {
        // user1으로 포스트 + 댓글 작성
        loginAsUser1();
        navigateTo("/posts/new");
        page.locator("input#title").fill("댓글 보안 테스트 포스트");
        page.locator("textarea#content").fill("내용");
        page.locator(".form-actions button[type=submit]").click();
        page.waitForURL(Pattern.compile("/posts/\\d+"));

        page.locator(".comment-form textarea#content").fill("user1의 댓글");
        page.locator(".comment-form button[type=submit]").click();
        page.waitForURL(Pattern.compile("/posts/\\d+"));

        String postUrl = page.url();

        // 로그아웃 후 user2로 로그인
        page.locator("button:has-text('로그아웃')").click();
        page.waitForURL(Pattern.compile("/auth/login"));
        login("user2", "password");

        // user1의 댓글에 삭제 버튼이 표시되지 않아야 함
        page.navigate(postUrl);
        assertThat(page.locator(".comment-card")).isVisible();
        assertThat(page.locator(".comment-card button.btn-danger")).hasCount(0);
    }

    // TC-SEC-006: 인증 페이지 비로그인 접근 허용
    @Test
    void 인증페이지_비로그인_정상접근() {
        navigateTo("/auth/login");
        assertThat(page).hasURL(Pattern.compile("/auth/login"));
        assertThat(page.locator("h2")).containsText("로그인");

        navigateTo("/auth/register");
        assertThat(page).hasURL(Pattern.compile("/auth/register"));
        assertThat(page.locator("h2")).containsText("회원가입");
    }

    // TC-SEC-007: 로그인 후 보호 페이지 세션 유지
    @Test
    void 세션유지_로그인후여러페이지_재로그인불필요() {
        loginAsUser1();

        // 포스트 목록
        navigateTo("/posts");
        assertThat(page.locator(".nav-user")).containsText("user1");

        // 포스트 상세
        page.locator(".post-card h3 a").first().click();
        assertThat(page.locator(".nav-user")).containsText("user1");

        // 새 포스트 작성 페이지
        navigateTo("/posts/new");
        assertThat(page.locator(".nav-user")).containsText("user1");
        assertThat(page.locator("h2")).containsText("새 포스트 작성");
    }
}
