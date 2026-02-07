// [REQ-N009] 입력 유효성 검증 E2E 테스트 — TC-VAL-001~011 (2026-02-07)
package com.demo.blog.e2e;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Tag("e2e")
class ValidationE2ETest extends BaseE2ETest {

    // === 회원가입 유효성 검증 ===

    /** HTML5 required 속성 제거 (브라우저 기본 검증 우회) */
    private void removeRequired(String selector) {
        page.evaluate("document.querySelector('" + selector + "').removeAttribute('required')");
    }

    // TC-VAL-001: 회원가입 - 빈 사용자명
    @Test
    void 회원가입_빈사용자명_에러메시지() {
        navigateTo("/auth/register");
        page.locator("input#email").fill("val001@test.com");
        page.locator("input#password").fill("pass1234");
        removeRequired("input#username");
        page.locator("button[type=submit]").click();

        assertThat(page.locator(".field-error")).containsText("사용자명을 입력해주세요");
    }

    // TC-VAL-002: 회원가입 - 사용자명 길이 위반 (2자)
    @Test
    void 회원가입_짧은사용자명_에러메시지() {
        navigateTo("/auth/register");
        page.locator("input#username").fill("ab");
        page.locator("input#email").fill("val002@test.com");
        page.locator("input#password").fill("pass1234");
        page.locator("button[type=submit]").click();

        assertThat(page.locator(".field-error")).containsText("사용자명은 3~50자 이내로 입력해주세요");
    }

    // TC-VAL-003: 회원가입 - 비밀번호 길이 위반 (3자)
    @Test
    void 회원가입_짧은비밀번호_에러메시지() {
        navigateTo("/auth/register");
        page.locator("input#username").fill("val003user");
        page.locator("input#email").fill("val003@test.com");
        page.locator("input#password").fill("abc");
        page.locator("button[type=submit]").click();

        assertThat(page.locator(".field-error")).containsText("비밀번호는 4자 이상 입력해주세요");
    }

    // TC-VAL-004: 회원가입 - 잘못된 이메일 형식
    @Test
    void 회원가입_잘못된이메일_에러메시지() {
        navigateTo("/auth/register");
        page.locator("input#username").fill("val004user");
        page.locator("input#password").fill("pass1234");
        // type="email" 브라우저 검증 우회
        page.evaluate("document.querySelector('input#email').setAttribute('type', 'text')");
        page.locator("input#email").fill("not-an-email");
        page.locator("button[type=submit]").click();

        assertThat(page.locator(".field-error")).containsText("올바른 이메일 형식을 입력해주세요");
    }

    // TC-VAL-005: 회원가입 - 중복 사용자명
    @Test
    void 회원가입_중복사용자명_에러메시지() {
        navigateTo("/auth/register");
        page.locator("input#username").fill("user1"); // 이미 존재
        page.locator("input#email").fill("val005_unique@test.com");
        page.locator("input#password").fill("password");
        page.locator("button[type=submit]").click();

        // bindingResult.rejectValue → 필드 에러 또는 글로벌 에러로 표시
        String bodyText = page.locator("body").textContent();
        assertThat(page).hasURL(Pattern.compile("/auth/register"));
        org.junit.jupiter.api.Assertions.assertTrue(
                bodyText.contains("이미 사용 중인 사용자명입니다"));
    }

    // TC-VAL-006: 회원가입 - 중복 이메일
    @Test
    void 회원가입_중복이메일_에러메시지() {
        navigateTo("/auth/register");
        page.locator("input#username").fill("val006_unique");
        page.locator("input#email").fill("user1@example.com"); // 이미 존재
        page.locator("input#password").fill("password");
        page.locator("button[type=submit]").click();

        String bodyText = page.locator("body").textContent();
        assertThat(page).hasURL(Pattern.compile("/auth/register"));
        org.junit.jupiter.api.Assertions.assertTrue(
                bodyText.contains("이미 사용 중인 이메일입니다"));
    }

    // === 포스트 유효성 검증 ===

    // TC-VAL-007: 포스트 작성 - 빈 제목
    @Test
    void 포스트작성_빈제목_에러메시지() {
        loginAsUser1();
        navigateTo("/posts/new");
        page.locator("textarea#content").fill("내용은 있음");
        removeRequired("input#title");
        page.locator(".form-actions button[type=submit]").click();

        assertThat(page.locator(".field-error")).containsText("제목을 입력해주세요");
    }

    // TC-VAL-008: 포스트 작성 - 제목 200자 초과
    @Test
    void 포스트작성_긴제목_에러메시지() {
        loginAsUser1();
        navigateTo("/posts/new");
        String longTitle = "가".repeat(201);
        page.locator("input#title").fill(longTitle);
        page.locator("textarea#content").fill("내용");
        page.locator(".form-actions button[type=submit]").click();

        assertThat(page.locator(".field-error")).containsText("제목은 200자 이내로 입력해주세요");
    }

    // TC-VAL-009: 포스트 작성 - 빈 내용
    @Test
    void 포스트작성_빈내용_에러메시지() {
        loginAsUser1();
        navigateTo("/posts/new");
        page.locator("input#title").fill("제목은 있음");
        removeRequired("textarea#content");
        page.locator(".form-actions button[type=submit]").click();

        assertThat(page.locator(".field-error")).containsText("내용을 입력해주세요");
    }

    // TC-VAL-010: 포스트 수정 - 빈 제목
    @Test
    void 포스트수정_빈제목_수정폼유지() {
        loginAsUser1();
        // 포스트 생성
        navigateTo("/posts/new");
        page.locator("input#title").fill("수정 유효성 테스트");
        page.locator("textarea#content").fill("내용");
        page.locator(".form-actions button[type=submit]").click();
        page.waitForURL(Pattern.compile("/posts/\\d+"));

        // 수정 페이지 이동
        page.locator("a:has-text('수정')").click();
        page.waitForURL(Pattern.compile("/posts/\\d+/edit"));

        // 제목 비우고 제출
        page.locator("input#title").clear();
        removeRequired("input#title");
        page.locator(".form-actions button[type=submit]").click();

        // 수정 폼이 유지되어야 함
        assertThat(page.locator(".field-error")).containsText("제목을 입력해주세요");
        assertThat(page.locator("h2")).containsText("포스트 수정");
    }

    // TC-VAL-011: 댓글 작성 - 빈 내용
    @Test
    void 댓글작성_빈내용_저장되지않음() {
        loginAsUser1();
        // 포스트 생성
        navigateTo("/posts/new");
        page.locator("input#title").fill("댓글 유효성 테스트");
        page.locator("textarea#content").fill("내용");
        page.locator(".form-actions button[type=submit]").click();
        page.waitForURL(Pattern.compile("/posts/\\d+"));

        // 빈 댓글 제출 (required 우회)
        removeRequired(".comment-form textarea#content");
        page.locator(".comment-form button[type=submit]").click();

        // 댓글이 생성되지 않아야 함
        page.waitForURL(Pattern.compile("/posts/\\d+"));
        assertThat(page.locator(".comment-card")).hasCount(0);
    }
}
