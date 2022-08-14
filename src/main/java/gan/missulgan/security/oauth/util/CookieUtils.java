package gan.missulgan.security.oauth.util;

import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

@Component
public class CookieUtils {

    private static final int MAX_AGE = 180;
    private static final int EXPIRED_AGE = 0;

    public Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(name))
                .findFirst();
    }

    public void addCookie(HttpServletResponse response, String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true); // TODO: 필요한지 체크
        cookie.setMaxAge(MAX_AGE);
        response.addCookie(cookie);
    }

    public void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(name))
                .forEach(cookie -> setCookieExpired(response, cookie));
    }

    public String serialize(Object object) {
        byte[] serialized = SerializationUtils.serialize(object);
        return encodeToBase64(serialized);
    }

    public <T> T deserialize(Cookie cookie, Class<T> castClass) {
        byte[] decoded = decodeFromBase64(cookie.getValue());
        Object deserialized = SerializationUtils.deserialize(decoded);
        return castClass.cast(deserialized);
    }

    private void setCookieExpired(HttpServletResponse response, Cookie cookie) {
        cookie.setValue(null);
        cookie.setPath("/");
        cookie.setMaxAge(EXPIRED_AGE);
        response.addCookie(cookie);
    }

    private String encodeToBase64(byte[] bytes) {
        return Base64.getUrlEncoder()
                .encodeToString(bytes);
    }

    private byte[] decodeFromBase64(String encoded) {
        return Base64.getUrlDecoder()
                .decode(encoded);
    }
}
