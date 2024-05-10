package xyz.zeppelin.bankaccounts.economy;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.math.BigDecimal;

public class VaultIntegration implements EconomyIntegration {

    private Economy economy;

    @Override
    public void onEnable() {
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);

        if (rsp == null) {
            return;
        }

        economy = rsp.getProvider();
    }

    @Override
    public void onDisable() {
        economy = null;
    }

    @Override
    public IntegrationTestCallback test() {
        if (Bukkit.getServer().getPluginManager().isPluginEnabled("Vault")) {
            return IntegrationTestCallback.SUCCESS;
        } else {
            return IntegrationTestCallback.ERROR_MISSING_DEPENDENCIES;
        }
    }

    @Override
    public void addToPlayerAccount(OfflinePlayer player, BigDecimal toAdd) {
        economy.depositPlayer(player, toAdd.doubleValue());
    }

    @Override
    public void setAmountInPlayerAccount(OfflinePlayer player, BigDecimal total) {
        economy.withdrawPlayer(player, economy.getBalance(player));
        economy.depositPlayer(player, total.doubleValue());
    }

    @Override
    public BigDecimal getFromPlayerAccount(OfflinePlayer player) {
        return BigDecimal.valueOf(economy.getBalance(player));
    }

    @Override
    public void removeFromPlayerAccount(OfflinePlayer player, BigDecimal toRemove) {
        economy.withdrawPlayer(player, toRemove.doubleValue());
    }

}
