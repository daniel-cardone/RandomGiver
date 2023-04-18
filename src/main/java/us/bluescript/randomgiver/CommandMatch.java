package us.bluescript.randomgiver;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CommandMatch implements CommandExecutor, TabCompleter {
    private final static Random random = new Random();
    private final static Material[] materials = Material.values();
    private final static int totalMaterials = materials.length;
    private int task = -1;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args[0].equals("start")) {
            if (task != -1) {
                sender.sendMessage("A match is already ongoing.");
                return true;
            }

            task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(RandomGiver.getInstance(), () -> {
                Bukkit.getServer().getOnlinePlayers().forEach(player -> {
                    player.getInventory().addItem(new ItemStack(getRandomItem(), 1));
                });
            }, 20 * 10L, 20 * 5L);
            Bukkit.getServer().broadcastMessage("The match will begin in 10 seconds.");
        } else if (args[0].equals("stop")) {
            if (task != -1) {
                Bukkit.getServer().getScheduler().cancelTask(task);
                Bukkit.getServer().broadcastMessage("The match has ended.");
                task = -1;
            } else {
                sender.sendMessage("There is no ongoing match.");
            }
        } else {
            return false;
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> options = new ArrayList<>();
        if (args.length <= 1) {
            options.add("start");
            options.add("stop");
        }

        return options;
    }

    private Material getRandomItem() {
        return materials[random.nextInt(totalMaterials)];
    }
}
