package com.example.backend;

import com.example.ejb.BeneficioEjbService;
import com.example.ejb.model.Beneficio;
import com.example.ejb.repository.BeneficioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

// CORREÇÃO: Forçamos o teste a usar a BackendApplication que tem o scanBasePackages configurado
@SpringBootTest(classes = BackendApplication.class)
class BeneficioServiceIT {

    @Autowired
    private BeneficioEjbService beneficioService;

    @Autowired
    private BeneficioRepository repository;

    @Test
    void deveRealizarTransferenciaComSucesso() {
        // Cenário: id 1 tem 1000, id 2 tem 500
        Long idDe = 1L;
        Long idPara = 2L;

        // CORREÇÃO: Usar o valor exato para o assertEquals não falhar por escala decimal
        BigDecimal valorTransferencia = new BigDecimal("200.00");

        // Ação
        beneficioService.transfer(idDe, idPara, valorTransferencia);

        // Verificação
        Beneficio b1 = repository.findById(idDe).orElseThrow();
        Beneficio b2 = repository.findById(idPara).orElseThrow();

        // No DB, o valor costuma vir com duas casas decimais, por isso comparamos com "800.00"
        assertEquals(0, new BigDecimal("800.00").compareTo(b1.getValor()), "Saldo da conta de origem incorreto");
        assertEquals(0, new BigDecimal("700.00").compareTo(b2.getValor()), "Saldo da conta de destino incorreto");
    }

    @Test
    void naoDeveTransferirQuandoSaldoForInsuficiente() {
        // Cenário: id 2 tem 500. Vamos tentar tirar 600.
        Long idDe = 2L;
        Long idPara = 1L;
        BigDecimal valorAcimaDoLimite = new BigDecimal("600.00");

        // Ação & Verificação
        try {
            beneficioService.transfer(idDe, idPara, valorAcimaDoLimite);
        } catch (RuntimeException e) {
            assertEquals("Saldo insuficiente.", e.getMessage());
        }

        // O mais importante: Verificar se o saldo do id 2 CONTINUA 500 (Rollback)
        Beneficio b2 = repository.findById(idDe).get();
        assertEquals(0, new BigDecimal("500.00").compareTo(b2.getValor()), "O saldo não deveria ter mudado após erro");
    }
}