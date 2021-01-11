package dansplugins.factionsystem.commands;

import dansplugins.factionsystem.LocaleManager;
import dansplugins.factionsystem.data.EphemeralData;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CheckAccessCommand {

    public void checkAccess(CommandSender sender, String[] args) {
        // if sender is player and if player has permission
        if (sender instanceof Player && (((Player) sender).hasPermission("mf.checkaccess"))) {

            Player player = (Player) sender;

            if (args.length > 1) {
                if (args[1].equalsIgnoreCase("cancel")) {
                    player.sendMessage(ChatColor.RED + LocaleManager.getInstance().getText("Cancelled"));
                    if (EphemeralData.getInstance().getPlayersCheckingAccess().contains(player.getUniqueId())) {
                        EphemeralData.getInstance().getPlayersCheckingAccess().remove(player.getUniqueId());
                        return;
                    }
                }
            }

            if (!EphemeralData.getInstance().getPlayersCheckingAccess().contains(player.getUniqueId())) {
                EphemeralData.getInstance().getPlayersCheckingAccess().add(player.getUniqueId());
                player.sendMessage(ChatColor.GREEN + LocaleManager.getInstance().getText("RightClickCheckAccess"));
            }
            else {
                player.sendMessage(ChatColor.RED + LocaleManager.getInstance().getText("AlreadyEnteredCheckAccess"));
            }

        }
    }

}
