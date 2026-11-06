package br.com.aweb.sistema_de_vendas.controller.exception;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.aweb.sistema_de_vendas.exception.EntidadeNaoEncontradaException;
import br.com.aweb.sistema_de_vendas.exception.RegraNegocioException;
import jakarta.servlet.http.HttpServletRequest;

// @RestController também é @Controller, então esta advice precisa vir antes da MvcExceptionHandler
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(annotations = RestController.class)
public class ResourceExceptionHandler {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<StandardError> entidadeNaoEncontrada(EntidadeNaoEncontradaException e,
            HttpServletRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, "Não encontrado", e.getMessage(), request);
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<StandardError> regraNegocio(RegraNegocioException e, HttpServletRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Erro de negócio", e.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validacao(MethodArgumentNotValidException e, HttpServletRequest request) {
        String mensagem = e.getBindingResult().getFieldErrors().stream()
                .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return buildResponse(HttpStatus.BAD_REQUEST, "Erro de validação", mensagem, request);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StandardError> jsonInvalido(HttpMessageNotReadableException e, HttpServletRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, "JSON inválido",
                "Corpo da requisição inválido ou com valor não reconhecido (ex.: especialidade)", request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> integridade(DataIntegrityViolationException e, HttpServletRequest request) {
        return buildResponse(HttpStatus.CONFLICT, "Conflito de dados",
                "Registro duplicado (email, CRM ou CPF já cadastrado) ou vinculado a outros registros", request);
    }

    private ResponseEntity<StandardError> buildResponse(HttpStatus status, String error, String message,
            HttpServletRequest request) {
        StandardError err = new StandardError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError(error);
        err.setMessage(message);
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}
