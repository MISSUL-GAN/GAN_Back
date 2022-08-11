package gan.missulgan.config.handler;

import gan.missulgan.common.ExceptionEnum;
import gan.missulgan.common.dto.ExceptionDto;
import gan.missulgan.common.exception.*;
import gan.missulgan.heart.exception.HeartNotFoundException;
import gan.missulgan.member.exception.MemberNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> exceptionHandler(HttpServletRequest request, final Exception e) {
        e.printStackTrace();
        return ResponseEntity
                .status(ExceptionEnum.INTERNAL_SERVER_ERROR.getStatus())
                .body(ExceptionDto.builder()
                        .errorCode(ExceptionEnum.INTERNAL_SERVER_ERROR.getCode())
                        .errorMessage(e.getMessage())
                        .build());
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ExceptionDto> exceptionHandler(HttpServletRequest request, final ApiException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(e.getError().getStatus())
                .body(ExceptionDto.builder()
                        .errorCode(e.getError().getCode())
                        .errorMessage(e.getError().getMessage())
                        .build());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionDto> exceptionHandler(HttpServletRequest request, final RuntimeException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(ExceptionEnum.RUNTIME_EXCEPTION.getStatus())
                .body(ExceptionDto.builder()
                        .errorCode(ExceptionEnum.RUNTIME_EXCEPTION.getCode())
                        .errorMessage(e.getMessage())
                        .build());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionDto> exceptionHandler(HttpServletRequest request, final AccessDeniedException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(ExceptionEnum.ACCESS_DENIED_EXCEPTION.getStatus())
                .body(ExceptionDto.builder()
                        .errorCode(ExceptionEnum.ACCESS_DENIED_EXCEPTION.getCode())
                        .errorMessage(e.getMessage())
                        .build());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionDto> exceptionHandler(HttpServletRequest request, final BadRequestException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(ExceptionEnum.BAD_REQUEST_EXCEPTION.getStatus())
                .body(ExceptionDto.builder()
                        .errorCode(ExceptionEnum.BAD_REQUEST_EXCEPTION.getCode())
                        .errorMessage(e.getMessage())
                        .build());
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ExceptionDto> exceptionHandler(HttpServletRequest request, final ConflictException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(ExceptionEnum.CONFLICT_EXCEPTION.getStatus())
                .body(ExceptionDto.builder()
                        .errorCode(ExceptionEnum.CONFLICT_EXCEPTION.getCode())
                        .errorMessage(e.getMessage())
                        .build());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ExceptionDto> exceptionHandler(HttpServletRequest request, final ForbiddenException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(ExceptionEnum.FORBIDDEN_EXCEPTION.getStatus())
                .body(ExceptionDto.builder()
                        .errorCode(ExceptionEnum.FORBIDDEN_EXCEPTION.getCode())
                        .errorMessage(e.getMessage())
                        .build());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDto> exceptionHandler(HttpServletRequest request, final NotFoundException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(ExceptionEnum.NOT_FOUND_EXCEPTION.getStatus())
                .body(ExceptionDto.builder()
                        .errorCode(ExceptionEnum.NOT_FOUND_EXCEPTION.getCode())
                        .errorMessage(e.getMessage())
                        .build());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionDto> exceptionHandler(HttpServletRequest request, final UnauthorizedException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(ExceptionEnum.UNAUTHORIZED_EXCEPTION.getStatus())
                .body(ExceptionDto.builder()
                        .errorCode(ExceptionEnum.UNAUTHORIZED_EXCEPTION.getCode())
                        .errorMessage(e.getMessage())
                        .build());
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ExceptionDto> exceptionHandler(HttpServletRequest request, final MemberNotFoundException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(ExceptionEnum.NO_SUCH_MEMBER.getStatus())
                .body(ExceptionDto.builder()
                        .errorCode(ExceptionEnum.NO_SUCH_MEMBER.getCode())
                        .errorMessage(e.getMessage())
                        .build());
    }

    @ExceptionHandler(HeartNotFoundException.class)
    public ResponseEntity<ExceptionDto> exceptionHandler(HttpServletRequest request, final HeartNotFoundException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(ExceptionEnum.NO_SUCH_HEART.getStatus())
                .body(ExceptionDto.builder()
                        .errorCode(ExceptionEnum.NO_SUCH_HEART.getCode())
                        .errorMessage(e.getMessage())
                        .build());
    }

}
