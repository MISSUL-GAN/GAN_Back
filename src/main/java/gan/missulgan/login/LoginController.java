package gan.missulgan.login;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

public class LoginController {

    LoginService loginService = new LoginService();

    /**
     * 홈에서 로그인 화면으로 이동
     */
    @GetMapping("/home")
    public String createLoginForm() {
        return "login";
    }


    /**
     * 로그인
     */
    @RequestMapping(value = "/login", method = GET)
    public ModelAndView login(@RequestParam("code") String code, HttpSession session) throws IOException {
        ModelAndView mav = new ModelAndView();
        // 1번 인증코드 요청 전달
        String accessToken = loginService.getAccessToken(code);
        // 2번 인증코드로 토큰 전달
        HashMap<String, Object> userInfo = loginService.getUserInfo(accessToken);

        System.out.println("login info : " + userInfo.toString());

        if (userInfo.get("account_email") != null) {
            session.setAttribute("userId", userInfo.get("account_email"));
            session.setAttribute("accessToken", accessToken);
        }
        mav.addObject("userId", userInfo.get("account_email"));
        mav.setViewName("login");
        return mav;
    }


    /**
     * 로그아웃
     */
    @RequestMapping(value = "/logout", method = GET)
    public ModelAndView logout(HttpSession session) {
        ModelAndView mav = new ModelAndView();

        loginService.logout((String) session.getAttribute("accessToken"));
        session.removeAttribute("accessToken");
        session.removeAttribute("userId");
        mav.setViewName("login");
        return mav;
    }
}
