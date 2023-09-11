package net.binarypaper.example.simplebankaccount.bankcard;

import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import net.binarypaper.example.simplebankaccount.account.Account;

@Data
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "UC_BANK_CARD_NUMBER", columnNames = { "NUMBER" }) })
@Schema(description = "A simple bank card")
public class BankCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = { Views.List.class, Views.View.class }, message = "{BankCard.id.NotNull}")
    @JsonView({ Views.List.class, Views.View.class })
    @Schema(description = "The unique id of the bank card for internal use", example = "1")
    private Long id;

    @ManyToOne
    @JsonView({})
    private Account account;

    @NotNull(groups = { Views.List.class, Views.View.class }, message = "{BankCard.number.NotNull}")
    @Size(groups = { Views.List.class, Views.View.class }, min = 16, max = 16, message = "{BankCard.number.Size}")
    @JsonView({ Views.List.class, Views.View.class })
    @Schema(description = "The unique bank card number for external use", example = "5255511914292238")
    private String number;

    @NotNull(groups = { Views.Create.class, Views.List.class,
            Views.View.class }, message = "{BankCard.cardName.NotNull}")
    @Size(groups = { Views.Create.class, Views.List.class,
            Views.View.class }, min = 3, max = 100, message = "{BankCard.cardName.Size}")
    @JsonView({ Views.List.class, Views.View.class, Views.Create.class })
    @Schema(description = "The name of the bank card", example = "Debit Card 1")
    private String cardName;

    public interface Views {

        public interface List {
        }

        public interface View {
        }

        public interface Create {
        }
    }
}