package xyz.zeppelin.bankaccounts.menu.menus.account;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import xyz.zeppelin.bankaccounts.BankPlugin;
import xyz.zeppelin.bankaccounts.menu.Menu;
import xyz.zeppelin.bankaccounts.models.BankAccount;
import xyz.zeppelin.bankaccounts.utils.ItemBuilder;
import xyz.zeppelin.bankaccounts.utils.Message;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class AccountCreateMenu extends Menu {
    private Player player;

    public AccountCreateMenu(Player player) {
        super("Bank - Create Account", 5);

        this.player = player;
    }


    private UUID uuid = UUID.randomUUID();
    private String name = "Your Bank Account";

    private BankAccount bankAccount = null;

    @Override
    public void setupGui() {
        addBorder(Material.GRAY_STAINED_GLASS_PANE);

        addButton(2, 1, ItemBuilder.of(Material.PAPER, "&aAccount Nickname").lore(ChatColor.ITALIC + name).build(), event -> {

        });

        addButton(4, 1, ItemBuilder.of(Material.PAPER, "&aAccount Shares").lore(new Message("&7People you want to share the account with&7.").getMessage()).build(), event -> {

        });

        addButton(6, 1, ItemBuilder.of(Material.PAPER, "&aAccount Owner").lore(new Message("&7" + player.getName()).color().getMessage()).build(), event -> {

        });

        addButton(4, 3, ItemBuilder.of(Material.LIME_DYE, "&aConfirm Creation").lore(new Message("&7Your account will be available right away.").color().getMessage()).build(), event -> {
            bankAccount = new BankAccount(uuid, event.getPlayer(), name, List.of(), BigDecimal.ZERO, true, List.of());
            BankPlugin.getInstance().getMySqlStorage().save(bankAccount);

            new Message("").messageLine(32, ChatColor.GRAY, "=").send(player);
            new Message("&7Your account has been created.").color().send(player);
            new Message("").messageLine(32, ChatColor.GRAY, "=").send(player);

            player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
            player.closeInventory();
        });
    }

    @Override
    public void onClose(Player player) {}
}
