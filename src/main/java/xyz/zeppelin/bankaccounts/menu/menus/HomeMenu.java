package xyz.zeppelin.bankaccounts.menu.menus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.zeppelin.bankaccounts.BankPlugin;
import xyz.zeppelin.bankaccounts.menu.Menu;
import xyz.zeppelin.bankaccounts.menu.menus.account.AccountsMenu;
import xyz.zeppelin.bankaccounts.utils.ItemBuilder;

public class HomeMenu extends Menu {
    private final Player player;
    public HomeMenu(Player player) {
        super("Bank - Home", 3);
        this.player = player;
    }

    @Override
    public void setupGui() {
        addBorder(Material.GRAY_STAINED_GLASS_PANE);

        addButton(1, 1, ItemBuilder.of(Material.HONEYCOMB).displayName("&6View Bank Accounts").build(), event -> {
            new AccountsMenu(event.getPlayer()).openGui(event.getPlayer());
        });

        addButton(4, 1, ItemBuilder.of(Material.PAPER).displayName("&6View Transactions").build(), event -> {

        });

        addButton(7, 1, ItemBuilder.of(Material.COMPASS).displayName("&6Create Payment").build(), event -> {

        });

        ItemStack balance = ItemBuilder.of(Material.BELL)
                .displayName("&6Personal Balance")
                .lore("§e> " + BankPlugin.getInstance().getVaultIntegration().getFromPlayerAccount(player).doubleValue())
                .build();

        addButton(4, 2, balance, event -> {});
    }

    @Override
    public void onClose(Player player) {}
}
