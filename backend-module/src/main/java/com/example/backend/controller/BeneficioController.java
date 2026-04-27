package com.example.backend.controller;

import com.example.ejb.BeneficioEjbService;
import com.example.ejb.repository.BeneficioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

// Documentação da estrutura do Request no Swagger
record TransferRequest(Long idDe, Long idPara, BigDecimal valor) {}

@RestController
@RequestMapping("/api/beneficios")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Benefícios", description = "API para gestão de saldos e transferências de benefícios")
public class BeneficioController {

    @Autowired
    private BeneficioEjbService beneficioService;

    @Autowired
    private BeneficioRepository repository;

    @GetMapping
    @Operation(summary = "Lista todos os benefícios", description = "Retorna todos os registros de benefícios armazenados no banco H2")
    public ResponseEntity<?> listarTodos() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping("/transferir")
    @Operation(summary = "Realiza transferência entre contas", description = "Transfere um valor específico de um benefício para outro via EJB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transferência realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Regra de negócio violada (ex: saldo insuficiente)"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<?> transferir(@RequestBody TransferRequest request) {
        try {
            beneficioService.transfer(
                    request.idDe(),
                    request.idPara(),
                    request.valor()
            );

            return ResponseEntity.ok(Map.of("message", "Transferência realizada com sucesso!"));

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));

        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("message", "Erro interno ao processar transferência."));
        }
    }
}