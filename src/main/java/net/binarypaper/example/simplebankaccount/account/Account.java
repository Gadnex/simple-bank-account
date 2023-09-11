package net.binarypaper.example.simplebankaccount.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "UC_ACCOUNT_NUMBER", columnNames = { "NUMBER" }) })
@Schema(description = "A simple bank account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = { Views.List.class, Views.View.class }, message = "{Account.id.NotNull}")
    @JsonView({ Views.List.class, Views.View.class })
    @Schema(description = "The unique id of the bank account for internal use", example = "1")
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull(groups = { Views.Create.class, Views.List.class,
            Views.View.class }, message = "{Account.accountType.NotNull}")
    @JsonView({ Views.List.class, Views.View.class, Views.Create.class })
    @Schema(description = "The type of the bank account")
    private AccountType accountType;

    @NotNull(groups = { Views.List.class, Views.View.class }, message = "{Account.number.NotNull}")
    @Size(groups = { Views.List.class, Views.View.class }, min = 18, max = 18, message = "{Account.number.Size}")
    @JsonView({ Views.List.class, Views.View.class })
    @Schema(description = "The unique bank account number for external use", example = "NL91ABNA0417164300")
    private String number;

    @NotNull(groups = { Views.Create.class, Views.List.class,
            Views.View.class }, message = "{Account.holderName.NotNull}")
    @Size(groups = { Views.Create.class, Views.List.class,
            Views.View.class }, min = 3, max = 100, message = "{Account.holderName.Size}")
    @JsonView({ Views.List.class, Views.View.class, Views.Create.class })
    @Schema(description = "The name of the bank account holder", example = "Mr John Smith")
    private String holderName;

    @NotNull(groups = { Views.List.class, Views.View.class }, message = "{Account.balance.NotNull}")
    @DecimalMin(groups = { Views.List.class,
            Views.View.class }, value = "0.00", message = "{Account.balance.DecimalMin}")
    @JsonView({ Views.List.class, Views.View.class })
    @Schema(description = "The account balance of the bank account", example = "9.99")
    private BigDecimal balance = BigDecimal.ZERO;

    public interface Views {

        public interface List {
        }

        public interface View {
        }

        public interface Create {
        }
    }
}