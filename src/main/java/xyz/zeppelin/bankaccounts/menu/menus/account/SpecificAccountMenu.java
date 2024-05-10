package xyz.zeppelin.bankaccounts.menu.menus.account;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.zeppelin.bankaccounts.BankPlugin;
import xyz.zeppelin.bankaccounts.menu.Menu;
import xyz.zeppelin.bankaccounts.menu.menus.confirm.ConfirmDeleteMenu;
import xyz.zeppelin.bankaccounts.models.BankAccount;
import xyz.zeppelin.bankaccounts.storage.MySqlStorage;
import xyz.zeppelin.bankaccounts.utils.ItemBuilder;

public class SpecificAccountMenu extends Menu {

    private BankAccount bankAccount;
    private MySqlStorage storage;

    public SpecificAccountMenu(BankAccount bankAccount) {
        super("Bank - Account Info", 5);

        this.bankAccount = bankAccount;
        this.storage = BankPlugin.getInstance().getMySqlStorage();
    }

    @Override
    public void setupGui() {
        addBorder(Material.GRAY_STAINED_GLASS_PANE);

        ItemStack info = ItemBuilder.of(Material.HONEYCOMB)
                .displayName("&6Account Info")
                .lore("§7Name: §6" + bankAccount.getName(), "§7Owner: " + bankAccount.getPlayer().getName())
                .build();

        ItemStack balance = ItemBuilder.of(Material.BELL)
                .displayName("&6Balance")
                .lore("§7Current Available Balance:", "§6" + bankAccount.getBalance().toString())
                .build();

        ItemStack withdraw = ItemBuilder.of(Material.RED_DYE)
                .displayName("&cWithdraw")
                .lore("§7Withdraw from Account")
                .build();

        ItemStack deposit = ItemBuilder.of(Material.LIME_DYE)
                .displayName("&aDeposit")
                .lore("§7Deposit/bring money into the account.")
                .build();

        addButton(2, 1, info, event -> {

        });

        addButton(6, 1, balance, event -> {

        });

        addButton(3, 2, deposit, event -> {

        });

        addButton(5, 2, withdraw, event -> {

        });

        addButton(4, 0, ItemBuilder.of(Material.BARRIER, "&cDelete Account").build(), event -> {
            new ConfirmDeleteMenu(bankAccount).openGui(event.getPlayer());
        });

        addButton(4, 4, ItemBuilder.of(Material.RED_STAINED_GLASS_PANE).displayName("&cBack").build(), event -> {
            new AccountsMenu(event.getPlayer()).openGui(event.getPlayer());
        });

    }

    @Override
    public void onClose(Player player) {}
}
