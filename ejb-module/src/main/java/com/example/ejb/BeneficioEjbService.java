package com.example.ejb;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;

import java.math.BigDecimal;

@Stateless
public class BeneficioEjbService {

    @PersistenceContext(unitName = "beneficioPU")
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void transfer(Long fromId, Long toId, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da transferência deve ser positivo.");
        }

        // 1. Busca com PESSIMISTIC_WRITE (Bloqueia as linhas no DB para outros processos)
        Beneficio from = em.find(Beneficio.class, fromId, LockModeType.PESSIMISTIC_WRITE);
        Beneficio to   = em.find(Beneficio.class, toId, LockModeType.PESSIMISTIC_WRITE);

        if (from == null || to == null) {
            throw new RuntimeException("Uma ou ambas as contas não foram encontradas.");
        }

        // 2. Validação de Saldo
        if (from.getValor().compareTo(amount) < 0) {
            // Lançar RuntimeException dentro de um EJB causa Rollback automático
            throw new RuntimeException("Saldo insuficiente para realizar a transferência.");
        }

        // 3. Execução da Lógica
        from.setValor(from.getValor().subtract(amount));
        to.setValor(to.getValor().add(amount));

        // No EJB, o merge é muitas vezes implícito ao fim da transação se os objetos estão 'managed',
        // mas chamá-lo explicitamente reforça a intenção.
        em.merge(from);
        em.merge(to);
    }
}