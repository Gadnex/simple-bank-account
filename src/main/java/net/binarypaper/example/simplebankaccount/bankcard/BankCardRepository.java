package net.binarypaper.example.simplebankaccount.bankcard;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BankCardRepository extends JpaRepository<BankCard, Long> {

    List<BankCard> findByAccountIdOrderByNumber(Long accountId);

    Optional<BankCard> findByAccountIdAndId(Long accountId, Long bankCardId);
}