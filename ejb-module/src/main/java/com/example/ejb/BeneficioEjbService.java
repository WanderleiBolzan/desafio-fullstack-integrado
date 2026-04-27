package com.example.ejb;

import com.example.ejb.model.Beneficio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Import do Spring

import java.math.BigDecimal;

@Service
public class BeneficioEjbService {

    @PersistenceContext // Removido o unitName para usar o padrão do Spring Boot
    private EntityManager em;

    @Transactional // O Spring cuidará do abrir/fechar transação e do Rollback em caso de RuntimeException
    public void transfer(Long fromId, Long toId, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da transferência deve ser positivo.");
        }

        // 1. Busca com PESSIMISTIC_WRITE (Excelente escolha para evitar Race Conditions!)
        Beneficio from = em.find(Beneficio.class, fromId, LockModeType.PESSIMISTIC_WRITE);
        Beneficio to   = em.find(Beneficio.class, toId, LockModeType.PESSIMISTIC_WRITE);

        if (from == null || to == null) {
            throw new RuntimeException("Uma ou ambas as contas não foram encontradas.");
        }

        // 2. Validação de Saldo
        if (from.getValor().compareTo(amount) < 0) {
            throw new RuntimeException("Saldo insuficiente.");
        }

        // 3. Execução da Lógica
        from.setValor(from.getValor().subtract(amount));
        to.setValor(to.getValor().add(amount));

        // No Spring/JPA com @Transactional, o merge é DESNECESSÁRIO aqui.
        // Como os objetos 'from' e 'to' foram buscados pelo 'em.find' dentro da transação,
        // eles estão no estado 'Managed'. Qualquer alteração neles será persistida
        // automaticamente pelo Hibernate no banco ao final do método (Dirty Checking).
    }
}