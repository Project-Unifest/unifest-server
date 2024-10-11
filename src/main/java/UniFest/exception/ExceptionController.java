package UniFest.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(UnifestCustomException.class)
    public ResponseEntity<ErrorResponse> handleUnifestException(UnifestCustomException e) {
        log.warn("Unifest Exception {} {} {}\n", e.getHttpStatus(), e.getMessage(), e.getClass().getSimpleName());
        //return ResponseEntity.status(e.getHttpStatus()).body(new ErrorResponse(e.getCode(),e.getMessage(),e.getData()));
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(e.getCode(),e.getMessage(),e.getData()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleInputFieldException(MethodArgumentNotValidException e) {
        FieldError mainError = e.getFieldErrors().get(0);
        String[] errorInfo = Objects.requireNonNull(mainError.getDefaultMessage()).split(":");

        int code = Integer.parseInt(errorInfo[0]);
        String message = errorInfo[1];

        return ResponseEntity.badRequest().body(new ErrorResponse(code, message, null));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleJsonException(HttpMessageNotReadableException e) {
        log.warn("Json Exception ErrMessage={}\n", e.getMessage());

        return ResponseEntity.badRequest()
                .body(new ErrorResponse(9000, "Json 형식이 올바르지 않습니다.", null));
    }

    @ExceptionHandler(HttpMediaTypeException.class)
    public ResponseEntity<ErrorResponse> handleContentTypeException(HttpMediaTypeException e) {
        log.warn("ContentType Exception ErrMessage={}\n", e.getMessage());

        return ResponseEntity.badRequest()
                .body(new ErrorResponse(9001, "ContentType 값이 올바르지 않습니다.", null));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleRequestMethodException(HttpRequestMethodNotSupportedException e) {
        log.warn("Http Method not supported Exception ErrMessage={}\n", e.getMessage());

        return ResponseEntity.badRequest()
                .body(new ErrorResponse(9002, "해당 Http Method에 맞는 API가 존재하지 않습니다.", null));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingRequestParamException(MissingServletRequestParameterException e) {
        log.warn("Request Param is Missing! ErrMessage={}\n", e.getMessage());

        return ResponseEntity.badRequest()
                .body(new ErrorResponse(9003, "요청 param 이름이 올바르지 않습니다.", null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> unhandledException(Exception e, HttpServletRequest request) {
        log.error("UnhandledException: {} {} errMessage={}\n",
                request.getMethod(),
                request.getRequestURI(),
                e.getMessage()
        );
        return ResponseEntity.internalServerError()
                .body(new ErrorResponse(9999,
                        "일시적으로 접속이 원활하지 않습니다. 서비스 팀에 문의 부탁드립니다.", null));
    }
}
