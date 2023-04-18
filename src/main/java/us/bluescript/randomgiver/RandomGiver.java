package us.bluescript.randomgiver;

import org.bukkit.plugin.java.JavaPlugin;

public final class RandomGiver extends JavaPlugin {
    private static RandomGiver instance;

    @Override
    public void onEnable() {
        instance = this;

        CommandMatch commandMatch = new CommandMatch();
        getCommand("match").setExecutor(commandMatch);
        getCommand("match").setTabCompleter(commandMatch);
    }

    @Override
    public void onDisable() {}

    public static RandomGiver getInstance() {
        return instance;
    }
}
