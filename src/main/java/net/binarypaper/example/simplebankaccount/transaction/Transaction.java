package net.binarypaper.example.simplebankaccount.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Data;
import net.binarypaper.example.simplebankaccount.account.Account;

@Data
@Entity
@Schema(description = "A simple bank card transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = { Views.List.class, Views.View.class }, message = "{Transaction.id.NotNull}")
    @JsonView({ Views.List.class, Views.View.class })
    @Schema(description = "The unique id of the bank transaction for internal use", example = "1")
    private Long id;

    @ManyToOne
    @JsonView({})
    private Account account;

    @Transient
    @Null(groups = { Views.Deposit.class,
            Views.Withdrawal.class }, message = "{Transaction.destinationBankAccountNumber.Null}")
    @NotNull(groups = { Views.Transfer.class }, message = "{Transaction.destinationBankAccountNumber.NotNull}")
    @JsonView({ Views.List.class, Views.View.class, Views.Transfer.class })
    @Schema(description = "The destination bank account number for transfers only", example = "NL91ABNA3865890609")
    private String destinationBankAccountNumber;

    @ManyToOne
    @JsonView({})
    private Account destinationAccount;

    @Enumerated(EnumType.STRING)
    @NotNull(groups = { Views.List.class, Views.View.class }, message = "{Transaction.transactionType.NotNull}")
    @JsonView({ Views.List.class, Views.View.class })
    @Schema(description = "The type of the bank transaction")
    private TransactionType transactionType;

    @NotNull(groups = { Views.List.class, Views.View.class }, message = "{Transaction.transactionDateTime.NotNull}")
    @JsonView({ Views.List.class, Views.View.class })
    @Schema(description = "the date and time of the transaction")
    private LocalDateTime transactionDateTime;

    @NotNull(groups = { Views.List.class, Views.View.class, Views.Deposit.class, Views.Withdrawal.class,
            Views.Transfer.class }, message = "{Transaction.description.NotNull}")
    @Size(groups = { Views.List.class, Views.View.class, Views.Deposit.class, Views.Withdrawal.class,
            Views.Transfer.class }, min = 3, max = 160, message = "{Transaction.description.Size}")
    @JsonView({ Views.List.class, Views.View.class, Views.Deposit.class, Views.Withdrawal.class,
            Views.Transfer.class })
    @Schema(description = "A decription of the transaction", example = "Account opening deposit")
    private String description;

    @NotNull(groups = { Views.List.class, Views.View.class, Views.Deposit.class, Views.Withdrawal.class,
            Views.Transfer.class }, message = "{Transaction.amount.NotNull}")
    @DecimalMin(groups = { Views.List.class, Views.View.class, Views.Deposit.class, Views.Withdrawal.class,
            Views.Transfer.class }, value = "0.01", message = "{Transaction.amount.DecimalMin}")
    @JsonView({ Views.List.class, Views.View.class, Views.Deposit.class, Views.Withdrawal.class,
            Views.Transfer.class })
    @Schema(description = "The amount of the bank transaction", example = "100")
    private BigDecimal amount;

    public interface Views {

        public interface List {
        }

        public interface View {
        }

        public interface Deposit {
        }

        public interface Withdrawal {
        }

        public interface Transfer {
        }
    }
}