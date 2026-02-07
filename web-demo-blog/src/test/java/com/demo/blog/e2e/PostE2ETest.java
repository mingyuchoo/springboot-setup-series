// [REQ-N009] 포스트 관리 E2E 테스트 — TC-POST-001~007 (2026-02-07)
package com.demo.blog.e2e;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("e2e")
class PostE2ETest extends BaseE2ETest {

    // TC-POST-001: 포스트 목록 조회
    @Test
    void 포스트목록_로그인상태_목록페이지표시() {
        loginAsUser1();
        navigateTo("/posts");

        assertThat(page.locator("article.post-card").first()).isVisible();
        int postCount = page.locator("article.post-card").count();
        assertTrue(postCount > 0 && postCount <= 10);
        assertThat(page.locator("nav.pagination")).isVisible();
    }

    // TC-POST-002: 포스트 목록 페이징
    @Test
    void 포스트목록_다음페이지클릭_2페이지표시() {
        loginAsUser1();
        navigateTo("/posts");

        page.locator("nav.pagination a:has-text('다음')").click();

        assertThat(page).hasURL(Pattern.compile("[?&]page=1"));
        assertThat(page.locator("article.post-card").first()).isVisible();
    }

    // TC-POST-003: 포스트 상세 조회
    @Test
    void 포스트상세_목록에서클릭_상세페이지표시() {
        loginAsUser1();
        navigateTo("/posts");

        page.locator(".post-card h3 a").first().click();

        assertThat(page).hasURL(Pattern.compile("/posts/\\d+"));
        assertThat(page.locator(".post-detail h2")).isVisible();
        assertThat(page.locator(".post-content")).isVisible();
        assertThat(page.locator(".comments-section")).isVisible();
    }

    // TC-POST-004: 포스트 작성
    @Test
    void 포스트작성_정상입력_상세페이지리다이렉트() {
        loginAsUser1();
        navigateTo("/posts/new");

        page.locator("input#title").fill("E2E 테스트 포스트");
        page.locator("textarea#content").fill("E2E 테스트로 작성한 내용입니다.");
        page.locator(".form-actions button[type=submit]").click();

        assertThat(page).hasURL(Pattern.compile("/posts/\\d+"));
        assertThat(page.locator(".alert-success")).containsText("포스트가 작성되었습니다");
        assertThat(page.locator(".post-detail h2")).hasText("E2E 테스트 포스트");
    }

    // TC-POST-005: 포스트 수정 (본인)
    @Test
    void 포스트수정_본인포스트_수정성공() {
        loginAsUser1();
        // 포스트 생성
        navigateTo("/posts/new");
        page.locator("input#title").fill("수정 전 제목");
        page.locator("textarea#content").fill("수정 전 내용");
        page.locator(".form-actions button[type=submit]").click();
        page.waitForURL(Pattern.compile("/posts/\\d+"));

        // 수정 버튼 클릭
        page.locator("a:has-text('수정')").click();
        page.waitForURL(Pattern.compile("/posts/\\d+/edit"));

        // 제목 수정
        page.locator("input#title").clear();
        page.locator("input#title").fill("수정된 제목");
        page.locator(".form-actions button[type=submit]").click();

        assertThat(page.locator(".alert-success")).containsText("포스트가 수정되었습니다");
        assertThat(page.locator(".post-detail h2")).hasText("수정된 제목");
    }

    // TC-POST-006: 포스트 삭제 (본인)
    @Test
    void 포스트삭제_본인포스트_목록리다이렉트() {
        loginAsUser1();
        // 포스트 생성
        navigateTo("/posts/new");
        page.locator("input#title").fill("삭제할 포스트");
        page.locator("textarea#content").fill("삭제 테스트 내용");
        page.locator(".form-actions button[type=submit]").click();
        page.waitForURL(Pattern.compile("/posts/\\d+"));

        // confirm 다이얼로그 수락 등록 후 삭제
        acceptNextDialog();
        page.locator("button.btn-danger:has-text('삭제')").click();

        assertThat(page).hasURL(Pattern.compile("/posts(\\?|$)"));
        assertThat(page.locator(".alert-success")).containsText("포스트가 삭제되었습니다");
    }

    // TC-POST-007: 존재하지 않는 포스트 접근
    @Test
    void 포스트상세_존재하지않는ID_에러표시() {
        loginAsUser1();
        navigateTo("/posts/99999");

        // BusinessException → Whitelabel 에러 또는 에러 메시지 표시
        String bodyText = page.locator("body").textContent();
        assertTrue(bodyText.contains("포스트를 찾을 수 없습니다")
                || bodyText.contains("error")
                || bodyText.contains("Error")
                || bodyText.contains("404"));
    }
}
