package net.binarypaper.example.simplebankaccount.account;

import java.math.BigDecimal;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class AccountTest {

    private static Validator validator;
    private Account account;

    @BeforeAll
    public static void setUpClass() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        account = new Account();
        account.setId(1L);
        account.setAccountType(AccountType.CURRENT);
        account.setNumber("NL91ABNA1312728792");
        account.setHolderName("Mr Charles Stone");
        account.setBalance(BigDecimal.ZERO);
    }

    @Test
    public void accountValid() {
        Set<ConstraintViolation<Account>> violations = validator.validate(account, Account.Views.View.class);
        Assertions.assertEquals(0, violations.size());
    }

    @Test
    public void accountIdNull() {
        account.setId(null);
        Set<ConstraintViolation<Account>> violations = validator.validate(account, Account.Views.View.class);
        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals("The account id must be specified", violations.iterator().next().getMessage());
    }

    @Test
    public void accountTypeNull() {
        account.setAccountType(null);
        Set<ConstraintViolation<Account>> violations = validator.validate(account, Account.Views.View.class);
        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals("The account type must be specified", violations.iterator().next().getMessage());
    }

    @Test
    public void accountNumberNull() {
        account.setNumber(null);
        Set<ConstraintViolation<Account>> violations = validator.validate(account, Account.Views.View.class);
        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals("The account number must be specified", violations.iterator().next().getMessage());
    }

    @Test
    public void accountNumberSize() {
        account.setNumber("NL91ABNA13127287920");
        Set<ConstraintViolation<Account>> violations = validator.validate(account, Account.Views.View.class);
        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals("The account number must be 18 characters long", violations.iterator().next().getMessage());
    }

    @Test
    public void accountHolderNameNull() {
        account.setHolderName(null);
        Set<ConstraintViolation<Account>> violations = validator.validate(account, Account.Views.View.class);
        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals("The account holder name must be specified", violations.iterator().next().getMessage());
    }

    @Test
    public void accountHolderNameSize() {
        account.setHolderName("");
        Set<ConstraintViolation<Account>> violations = validator.validate(account, Account.Views.View.class);
        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals("The account holder name must be between 3 and 100 characters long", violations.iterator().next().getMessage());
    }

    @Test
    public void accountBalanceNull() {
        account.setBalance(null);
        Set<ConstraintViolation<Account>> violations = validator.validate(account, Account.Views.View.class);
        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals("The account balance must be specified", violations.iterator().next().getMessage());
    }

    @Test
    public void accountBalanceDecimalMin() {
        account.setBalance(BigDecimal.valueOf(-1L));
        Set<ConstraintViolation<Account>> violations = validator.validate(account, Account.Views.View.class);
        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals("The account balance must be greater than or equal to 0.00", violations.iterator().next().getMessage());
    }
}