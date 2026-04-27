package com.example.backend.controller;

import com.example.ejb.BeneficioEjbService;
import com.example.ejb.repository.BeneficioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

// 1. O RECORD: Define a estrutura do JSON que virá do Angular
// Ele pode ficar aqui mesmo, fora da classe principal do arquivo.
record TransferRequest(Long idDe, Long idPara, BigDecimal valor) {}

@RestController
@RequestMapping("/api/beneficios")
@CrossOrigin(origins = "http://localhost:4200")
public class BeneficioController {

    @Autowired
    private BeneficioEjbService beneficioService;

    @Autowired
    private BeneficioRepository repository;

    @GetMapping
    public ResponseEntity<?> listarTodos() {
        return ResponseEntity.ok(repository.findAll());
    }

    // 2. O MÉTODO: Agora ele usa o @RequestBody para capturar o JSON
    @PostMapping("/transferir")
    public ResponseEntity<?> transferir(@RequestBody TransferRequest request) {
        // O Spring preenche o 'request' automaticamente com os dados do Angular
        beneficioService.transfer(
                request.idDe(),
                request.idPara(),
                request.valor()
        );

        // Retorna um JSON de sucesso para o Angular
        return ResponseEntity.ok(Map.of("message", "Transferência realizada com sucesso!"));
    }
}