// [REQ-N009] 인증 E2E 테스트 — TC-AUTH-001~005 (2026-02-07)
package com.demo.blog.e2e;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Tag("e2e")
class AuthE2ETest extends BaseE2ETest {

    // TC-AUTH-001: 회원가입 - 정상 처리
    @Test
    void 회원가입_정상입력_로그인페이지리다이렉트후성공메시지() {
        navigateTo("/auth/register");
        page.locator("input#username").fill("e2e_newuser");
        page.locator("input#email").fill("e2e_newuser@test.com");
        page.locator("input#password").fill("pass1234");
        page.locator("button[type=submit]").click();

        assertThat(page).hasURL(Pattern.compile("/auth/login"));
        assertThat(page.locator(".alert-success").first()).containsText("회원가입이 완료되었습니다");
    }

    // TC-AUTH-002: 로그인 - 정상 처리
    @Test
    void 로그인_정상계정_포스트목록리다이렉트() {
        login("user1", "password");
        assertThat(page).hasURL(Pattern.compile("/posts"));
        assertThat(page.locator(".nav-user")).containsText("user1");
    }

    // TC-AUTH-003: 로그인 - 잘못된 비밀번호
    @Test
    void 로그인_잘못된비밀번호_에러메시지표시() {
        navigateTo("/auth/login");
        page.locator("input#username").fill("user1");
        page.locator("input#password").fill("wrongpassword");
        page.locator("button[type=submit]").click();

        assertThat(page.locator(".alert-error")).isVisible();
        assertThat(page.locator(".alert-error")).containsText("사용자명 또는 비밀번호가 올바르지 않습니다");
    }

    // TC-AUTH-004: 로그인 - 존재하지 않는 사용자
    @Test
    void 로그인_존재하지않는사용자_에러메시지표시() {
        navigateTo("/auth/login");
        page.locator("input#username").fill("nonexistent");
        page.locator("input#password").fill("password");
        page.locator("button[type=submit]").click();

        assertThat(page.locator(".alert-error")).isVisible();
    }

    // TC-AUTH-005: 로그아웃
    @Test
    void 로그아웃_로그인상태_로그인페이지리다이렉트() {
        loginAsUser1();
        page.locator("button:has-text('로그아웃')").click();

        assertThat(page).hasURL(Pattern.compile("/auth/login\\?logout"));
        assertThat(page.locator(".alert-success")).containsText("로그아웃되었습니다");
    }
}
