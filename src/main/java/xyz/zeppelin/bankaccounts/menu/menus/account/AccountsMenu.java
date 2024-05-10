package xyz.zeppelin.bankaccounts.menu.menus.account;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.zeppelin.bankaccounts.BankPlugin;
import xyz.zeppelin.bankaccounts.menu.Menu;
import xyz.zeppelin.bankaccounts.models.BankAccount;
import xyz.zeppelin.bankaccounts.storage.MySqlStorage;
import xyz.zeppelin.bankaccounts.utils.ItemBuilder;
import xyz.zeppelin.bankaccounts.utils.Message;

import java.util.List;

public class AccountsMenu extends Menu {

    private Player player;
    private MySqlStorage storage;

    public AccountsMenu(Player player) {
        super("Bank - Your Accounts", 3);

        this.player = player;
        this.storage = BankPlugin.getInstance().getMySqlStorage();
    }

    @Override
    public void setupGui() {
        addBorder(Material.GRAY_STAINED_GLASS_PANE);

        List<BankAccount> bankAccountList = storage.getAllBankAccounts().stream()
                .filter(b -> b.getPlayer().equals(player))
                .toList();

        if (bankAccountList.isEmpty()) {
            addButton(4, 1, ItemBuilder.of(Material.BARRIER, "&cNo Associated Accounts").lore(new Message("&7Click to &acreate account&7.").getMessage()).build(), event -> {
                new AccountCreateMenu(event.getPlayer()).openGui(event.getPlayer());
            });
            return;
        }

        int x = 1;
        int y = 1;

        for (BankAccount bankAccount : bankAccountList) {
            addButton(x, y, createBankAccountButton(bankAccount), event -> {
                new SpecificAccountMenu(bankAccount).openGui(event.getPlayer());
            });

            // Increment coordinates
            x++;
            if (x > 7) {
                x = 1;
                y++;
                if (y > 2) {
                    break; // Only support 14 bank accounts for now (2 rows of 7)
                }
            }
        }

        if (bankAccountList.size() < 10) { // Less than 10 bank accounts, there's space for one more
            addButton(8, 2, ItemBuilder.of(Material.LIME_DYE, "&aCreate Account").build(), event -> {
                new AccountCreateMenu(event.getPlayer()).openGui(event.getPlayer());
            });
        } else {
            addButton(8, 2, ItemBuilder.of(Material.RED_DYE, "&cYou have no space for more accounts.").build(), event -> {});
        }
    }

    private ItemStack createBankAccountButton(BankAccount bankAccount) {
        // Customize this method to create a button representing a bank account
        // For example, you can use the bank account's name and balance to create the button
        return ItemBuilder.of(Material.CHEST)
                .displayName("&a" + bankAccount.getName())
                .lore("&7Balance: &e" + bankAccount.getBalance())
                .build();
    }


    @Override
    public void onClose(Player player) {}
}
