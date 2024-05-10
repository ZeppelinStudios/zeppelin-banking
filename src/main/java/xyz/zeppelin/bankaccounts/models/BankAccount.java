package xyz.zeppelin.bankaccounts.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class BankAccount {

    private UUID uuid;

    private Player player;

    private String name;

    private List<Player> shares;

    private BigDecimal balance;

    private boolean active;

    private List<Transaction> transactions;

}
