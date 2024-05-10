package xyz.zeppelin.bankaccounts.economy;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.math.BigDecimal;

/**
 * This is the integration for all economy adapters
 */
public interface EconomyIntegration {

    /**
     * When the economy integration is enabled
     * Defaults as a blank method
     */
    default void onEnable() {

    }

    /**
     * When the economy integration is disabled
     * Defaults as a blank method
     */
    default void onDisable() {

    }

    /**
     * This is to test if the economy integration is validated. You can use this method to
     * test the required dependencies. Returning true will end up in making the plugin run
     * as normal, but if it returns false - the plugin gets disabled as the Economy Integration
     * is not properly setup.
     * @return
     */
    default IntegrationTestCallback test() {
        return IntegrationTestCallback.SUCCESS;
    }

    /**
     * Add money to the players account, this is not related to the bank accounts.
     * @param player The player the account belongs to
     * @param toAdd The amount you are adding
     */
    void addToPlayerAccount(OfflinePlayer player, BigDecimal toAdd);

    /**
     * Set a amount to the players account, this is not related to the bank accounts.
     * @param player The player related to the account.
     * @param total The total amount you want the player to have in their account.
     */
    void setAmountInPlayerAccount(OfflinePlayer player, BigDecimal total);

    /**
     * Get the account balance
     * @param player Player
     * @return The amount in BigDecimal (double)
     */
    BigDecimal getFromPlayerAccount(OfflinePlayer player);

    /**
     * Remove amount from player account
     * @param player Player
     * @param toRemove amount to remove
     */
    void removeFromPlayerAccount(OfflinePlayer player, BigDecimal toRemove);

}
