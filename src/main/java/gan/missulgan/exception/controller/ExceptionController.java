package gan.missulgan.exception.controller;

import gan.missulgan.exception.ApiException;
import gan.missulgan.exception.ExceptionEnum;
import gan.missulgan.exception.ForbiddenException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;

// 테스트용 컨트롤러
@RestController
public class ExceptionController {

    @PostMapping("/ex")
    public void exTest() throws Exception {
        throw new Exception();
    }

    @PostMapping("/ApiException")
    public String ApiExceptionTest() throws ApiException {
        throw new ApiException(ExceptionEnum.SECURITY_01);
    }

    @PostMapping("/AccessDeniedException")
    public String AccessDeniedExceptionTest() throws AccessDeniedException {
        throw new ApiException(ExceptionEnum.ACCESS_DENIED_EXCEPTION);
    }

    @PostMapping("/ForbiddenException")
    public String ForbiddenExceptionTest() throws ForbiddenException {
        throw new ApiException(ExceptionEnum.FORBIDDEN_EXCEPTION);
    }


}
