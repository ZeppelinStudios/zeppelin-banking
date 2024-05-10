package xyz.zeppelin.bankaccounts;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.zeppelin.bankaccounts.command.BaseCommand;
import xyz.zeppelin.bankaccounts.command.commands.BankCommand;
import xyz.zeppelin.bankaccounts.economy.IntegrationTestCallback;
import xyz.zeppelin.bankaccounts.economy.VaultIntegration;
import xyz.zeppelin.bankaccounts.loader.BankAccountLoader;
import xyz.zeppelin.bankaccounts.loader.ZeppelinLoader;
import xyz.zeppelin.bankaccounts.menu.MenuListener;
import xyz.zeppelin.bankaccounts.storage.MySqlStorage;

import java.util.List;
import java.util.logging.Logger;

/**
 * Bank Plugin
 * Bank accounts in Minecraft
 */
@Getter
public final class BankPlugin extends JavaPlugin {

    private static BankPlugin instance;

    private final List<BaseCommand> commands = List.of(
            new BankCommand()
    );

    private BankAccountLoader bankAccountLoader;

    private MySqlStorage mySqlStorage;

    private VaultIntegration vaultIntegration;

    @Override
    public void onLoad() {
        instance = this;
        CommandAPI.onLoad(new CommandAPIBukkitConfig(this).silentLogs(true));
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        CommandAPI.onEnable();
        Bukkit.getLogger().info("Plugin successfully enabled!");

        this.mySqlStorage = new MySqlStorage(
                "127.0.0.1",
                3306,
                "root",
                "",
                "bank"
        );

        mySqlStorage.enable();

        if (mySqlStorage.get().isEmpty()) {
            getLogger().warning("A MySQL connection is required to be running Bank Accounts.");
            getLogger().warning("We attempted to create a instance of MySQL based on your connection config.");
            getLogger().warning("The driver failed to connect to the database, therefore plugin is disabling...");

            Bukkit.getPluginManager().disablePlugin(this);

            return;
        }

        this.vaultIntegration = new VaultIntegration();

        if (vaultIntegration.test() != IntegrationTestCallback.SUCCESS) {
            getLogger().warning("Could not start plugin as economy integration returned this error:");
            getLogger().warning("Error code: " + vaultIntegration.test().name());
            getServer().getPluginManager().disablePlugin(this);
        }

        vaultIntegration.onEnable();

        this.bankAccountLoader = new BankAccountLoader();
        bankAccountLoader.enable();

        getLogger().info("Starting command registration.");
        commands.forEach(command -> {
            getLogger().info("Loading command: " + command.getClass().getName());
            command.register();
        });

        getServer().getPluginManager().registerEvents(new MenuListener(), this);
    }

    /**
     * The plugin is being disabled
     */
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        instance = null;
        Bukkit.getLogger().warning("Shutdown plugin received by Server.");
        Bukkit.getLogger().warning("Cleaning up tasks & cache, then closing instance.");
        CommandAPI.onDisable();
    }

    /**
     * Get the instance of BankPlugin
     * The instance needs to be initialized to be accessible
     * @return The initialized instance of BankPlugin
     */
    public static BankPlugin getInstance() {
        return instance;
    }
}
