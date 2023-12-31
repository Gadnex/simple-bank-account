package net.binarypaper.example.simplebankaccount.account;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import net.binarypaper.example.simplebankaccount.utils.ClasspathProperties;
import net.binarypaper.example.simplebankaccount.utils.DataIntegrityViolationExceptionHandler;

@RestController
@RequestMapping(path = "accounts", produces = { MediaType.APPLICATION_JSON_VALUE })
@CrossOrigin(origins = { "${application.cors.origins}" })
@Tag(name = "Account API", description = "Manage bank accounts")
public class AccountController {

    private final AccountRepository accountRepository;

    private final Properties errorMessageProperties;

    private final DataIntegrityViolationExceptionHandler dataIntegrityViolationExceptionHandler;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        errorMessageProperties = ClasspathProperties.load("ErrorMessages.properties");
        dataIntegrityViolationExceptionHandler = new DataIntegrityViolationExceptionHandler(errorMessageProperties);
        dataIntegrityViolationExceptionHandler.addConstraintValidation("UC_ACCOUNT_NUMBER", "{ERR001}");
    }

    @GetMapping
    @JsonView(Account.Views.List.class)
    @Operation(summary = "Get a list of all bank accounts", description = "Get a list of all bank accounts sorted by account number")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "List of bank accounts returned") })
    public List<Account> getAllAccounts() {
        return accountRepository.findAll(Sort.by("number"));
    }

    @GetMapping(path = "{account-id}")
    @JsonView(Account.Views.List.class)
    @Operation(summary = "Get a bank account by account id", description = "Get a bank account by its unique id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Bank account returned"),
            @ApiResponse(responseCode = "404", description = "Invalid account id", content = @Content)
    })
    public Account getAccountById(
            @PathVariable(name = "account-id") @Parameter(description = "The account id to find", example = "1") Long accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid account id");
        }
        return account.get();
    }

    @GetMapping(path = "number")
    @JsonView(Account.Views.List.class)
    @Operation(summary = "Get a bank account by account number", description = "Get a bank account by its unique external account number")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Bank account returned"),
            @ApiResponse(responseCode = "404", description = "Invalid account number", content = @Content)
    })
    public Account getAccountByNumber(
            @RequestHeader(name = "account-number") @Parameter(description = "The account number to find", example = "NL91ABNA1312728792") String accountNumber) {
        Optional<Account> account = accountRepository.findByNumber(accountNumber);
        if (account.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid account number");
        }
        return account.get();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    @JsonView(Account.Views.View.class)
    @Operation(summary = "Create a new bank account", description = "Create a new bank account. The unique account id will be generated by the system.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "The account was created"),
            @ApiResponse(responseCode = "400", description = "Invalid account details", content = @Content)
    })
    public Account createAccount(
            @RequestBody @Validated(Account.Views.Create.class) @JsonView(Account.Views.Create.class) Account account) {
        // Generate new account number
        long accountNumber = (long) (Math.random() * 10000000000L);
        account.setNumber("NL91ABNA" + accountNumber);
        try {
            accountRepository.save(account);
        } catch (DataIntegrityViolationException ex) {
            throw dataIntegrityViolationExceptionHandler.handleException(ex);
        }
        return account;
    }
}