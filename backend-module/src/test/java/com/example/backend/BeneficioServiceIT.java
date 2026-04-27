package com.example.backend;

import com.example.ejb.BeneficioEjbService;
import com.example.ejb.model.Beneficio;
import com.example.ejb.repository.BeneficioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = BackendApplication.class)
class BeneficioServiceIT {

    @Autowired
    private BeneficioEjbService beneficioService;

    @Autowired
    private BeneficioRepository repository;

    @Test
    void deveRealizarTransferenciaComSucesso() {

        Long idDe = 1L;
        Long idPara = 2L;

        BigDecimal valorTransferencia = new BigDecimal("200.00");

        beneficioService.transfer(idDe, idPara, valorTransferencia);

        Beneficio b1 = repository.findById(idDe).orElseThrow();
        Beneficio b2 = repository.findById(idPara).orElseThrow();

        assertEquals(0, new BigDecimal("800.00").compareTo(b1.getValor()), "Saldo da conta de origem incorreto");
        assertEquals(0, new BigDecimal("700.00").compareTo(b2.getValor()), "Saldo da conta de destino incorreto");
    }

    @Test
    void naoDeveTransferirQuandoSaldoForInsuficiente() {

        Long idDe = 2L;
        Long idPara = 1L;
        BigDecimal valorAcimaDoLimite = new BigDecimal("600.00");

        try {
            beneficioService.transfer(idDe, idPara, valorAcimaDoLimite);
        } catch (RuntimeException e) {
            assertEquals("Saldo insuficiente.", e.getMessage());
        }

        Beneficio b2 = repository.findById(idDe).get();
        assertEquals(0, new BigDecimal("500.00").compareTo(b2.getValor()), "O saldo não deveria ter mudado após erro");
    }
}