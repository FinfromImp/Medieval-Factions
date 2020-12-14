package dansplugins.factionsystem.data;

import dansplugins.factionsystem.objects.*;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.UUID;

public class PersistentData {

    // instance
    private static PersistentData instance;

    public ArrayList<Faction> factions = new ArrayList<>();
    public ArrayList<ClaimedChunk> claimedChunks = new ArrayList<>();
    public ArrayList<PlayerPowerRecord> playerPowerRecords = new ArrayList<>();
    public ArrayList<PlayerActivityRecord> playerActivityRecords = new ArrayList<>();
    public ArrayList<LockedBlock> lockedBlocks = new ArrayList<>();

    private PersistentData() {

    }

    public static PersistentData getInstance() {
        if (instance == null) {
            instance = new PersistentData();
        }
        return instance;
    }

    // arraylist getters ---

    public ArrayList<Faction> getFactions() {
        return factions;
    }

    public ArrayList<ClaimedChunk> getClaimedChunks() {
        return claimedChunks;
    }

    public ArrayList<PlayerPowerRecord> getPlayerPowerRecords() {
        return playerPowerRecords;
    }

    public ArrayList<PlayerActivityRecord> getPlayerActivityRecords() {
        return playerActivityRecords;
    }

    public ArrayList<LockedBlock> getLockedBlocks() {
        return lockedBlocks;
    }

    // specific getters ---

    public Faction getFaction(String name) {
        for (Faction faction : getFactions()) {
            if (faction.getName().equalsIgnoreCase(name)) {
                return faction;
            }
        }
        return null;
    }

    public Faction getPlayersFaction(UUID playerUUID) {
        // membership check
        for (Faction faction : getFactions()) {
            if (faction.isMember(playerUUID)) {
                return faction;
            }
        }
        return null;
    }

    public PlayerPowerRecord getPlayersPowerRecord(UUID playerUUID) {
        for (PlayerPowerRecord record : getPlayerPowerRecords()) {
            if (record.getPlayerUUID().equals(playerUUID)) {
                return record;
            }
        }
        return null;
    }

    public PlayerActivityRecord getPlayerActivityRecord(UUID uuid)
    {
        for (PlayerActivityRecord record : getPlayerActivityRecords())
        {
            if (record.getPlayerUUID().equals(uuid))
            {
                return record;
            }
        }
        return null;
    }

    public LockedBlock getLockedBlock(Block block) {
        return getLockedBlock(block.getX(), block.getY(), block.getZ(), block.getWorld().getName());
    }

    private LockedBlock getLockedBlock(int x, int y, int z, String world) {
        for (LockedBlock block : getLockedBlocks()) {
            if (block.getX() == x && block.getY() == y && block.getZ() == z && block.getWorld().equalsIgnoreCase(world)) {
                return block;
            }
        }
        return null;
    }

    // checkers --

    public boolean isBlockLocked(Block block) {
        return isBlockLocked(block.getX(), block.getY(), block.getZ(), block.getWorld().getName());
    }

    private boolean isBlockLocked(int x, int y, int z, String world) {
        for (LockedBlock block : PersistentData.getInstance().getLockedBlocks()) {
            if (block.getX() == x && block.getY() == y && block.getZ() == z && block.getWorld().equalsIgnoreCase(world)) {
                return true;
            }
        }
        return false;
    }
}
