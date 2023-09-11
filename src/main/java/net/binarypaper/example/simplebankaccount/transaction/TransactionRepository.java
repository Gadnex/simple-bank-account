package net.binarypaper.example.simplebankaccount.transaction;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountIdOrderByTransactionDateTimeDesc(Long fromBankCardId);
}