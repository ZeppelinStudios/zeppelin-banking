package xyz.zeppelin.bankaccounts.menu.menus.confirm;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.zeppelin.bankaccounts.BankPlugin;
import xyz.zeppelin.bankaccounts.menu.Menu;
import xyz.zeppelin.bankaccounts.menu.menus.account.SpecificAccountMenu;
import xyz.zeppelin.bankaccounts.models.BankAccount;
import xyz.zeppelin.bankaccounts.utils.ItemBuilder;
import xyz.zeppelin.bankaccounts.utils.Message;

import java.math.BigDecimal;

public class ConfirmDeleteMenu extends Menu {
    private BankAccount bankAccount;

    public ConfirmDeleteMenu(BankAccount bankAccount) {
        super("Bank - Delete Confirm", 3);

        this.bankAccount = bankAccount;
    }


    @Override
    public void setupGui() {
        addBorder(Material.GRAY_STAINED_GLASS_PANE);

        ItemStack confirm = ItemBuilder.of(Material.BARRIER)
                .displayName("&c&cDelete account")
                .lore("§7There is no turning back. The money will be refunded directly to your account.")
                .build();

        ItemStack decline = ItemBuilder.of(Material.BARRIER)
                .displayName("&a&lDon't delete")
                .lore("§7Return back to the bank account.")
                .build();

        addButton(3, 1, confirm, event -> {
            event.getPlayer().closeInventory();

            new Message("").messageLine(32, ChatColor.DARK_RED, "=").send(event.getPlayer());
            new Message("&cYour account was successfully deleted. The money was refunded").color().send(event.getPlayer());
            new Message("&cto your primary account.").color().send(event.getPlayer());
            new Message("").messageLine(32, ChatColor.DARK_RED, "=").send(event.getPlayer());

            double returnBalance = bankAccount.getBalance().doubleValue();
            BankPlugin.getInstance().getVaultIntegration().addToPlayerAccount(event.getPlayer(), BigDecimal.valueOf(returnBalance));

            event.getPlayer().playSound(event.getPlayer(), Sound.BLOCK_BELL_USE, 1, 1);

            BankPlugin.getInstance().getMySqlStorage().deleteBankAccount(bankAccount.getUuid());
        });

        addButton(5, 1, decline, event -> {
            new SpecificAccountMenu(bankAccount).openGui(event.getPlayer());
        });

    }

    @Override
    public void onClose(Player player) {

    }
}
