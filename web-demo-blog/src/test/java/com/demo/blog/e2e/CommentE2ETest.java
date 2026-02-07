// [REQ-N009] 댓글 관리 E2E 테스트 — TC-CMT-001~002 (2026-02-07)
package com.demo.blog.e2e;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Tag("e2e")
class CommentE2ETest extends BaseE2ETest {

    // TC-CMT-001: 댓글 작성
    @Test
    void 댓글작성_정상입력_댓글목록에추가() {
        loginAsUser1();
        // 포스트 생성
        navigateTo("/posts/new");
        page.locator("input#title").fill("댓글 테스트용 포스트");
        page.locator("textarea#content").fill("댓글 테스트 내용");
        page.locator(".form-actions button[type=submit]").click();
        page.waitForURL(Pattern.compile("/posts/\\d+"));

        // 댓글 작성
        page.locator(".comment-form textarea#content").fill("E2E 테스트 댓글입니다!");
        page.locator(".comment-form button[type=submit]").click();

        // 같은 상세 페이지에서 댓글 확인
        assertThat(page).hasURL(Pattern.compile("/posts/\\d+"));
        assertThat(page.locator(".comment-card").last()).isVisible();
        assertThat(page.locator(".comment-card p").last()).containsText("E2E 테스트 댓글입니다!");
    }

    // TC-CMT-002: 댓글 삭제 (본인)
    @Test
    void 댓글삭제_본인댓글_목록에서제거() {
        loginAsUser1();
        // 포스트 생성
        navigateTo("/posts/new");
        page.locator("input#title").fill("댓글삭제 테스트용 포스트");
        page.locator("textarea#content").fill("내용");
        page.locator(".form-actions button[type=submit]").click();
        page.waitForURL(Pattern.compile("/posts/\\d+"));

        // 댓글 작성
        page.locator(".comment-form textarea#content").fill("삭제할 댓글");
        page.locator(".comment-form button[type=submit]").click();
        page.waitForURL(Pattern.compile("/posts/\\d+"));

        // 댓글 삭제 (confirm 다이얼로그 수락)
        acceptNextDialog();
        page.locator(".comment-card button.btn-danger").click();

        // 댓글이 제거되었는지 확인
        assertThat(page.locator(".comment-card")).hasCount(0);
    }
}
