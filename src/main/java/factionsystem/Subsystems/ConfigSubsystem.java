package factionsystem.Subsystems;

import factionsystem.Main;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ConfigSubsystem {

    Main main = null;

    private final List<String> oldConfigOptions = Arrays.asList("officerLimit", "hourlyPowerIncreaseAmount", "maxPowerLevel");

    public ConfigSubsystem(Main plugin) {
        main = plugin;
    }

    public void handleVersionMismatch() {
        // set version
        if (!main.getConfig().isString("version")) {
            System.out.println("Version not set! Setting version to " + main.version);
            main.getConfig().addDefault("version", main.version);
        }
        else {
            System.out.println("Version set but mismatched! Setting version to " + main.version);
            main.getConfig().set("version", main.version);
        }

        // add defaults if they don't exist
        if (!main.getConfig().isInt("initialMaxPowerLevel")) {
            System.out.println("Max power level not set! Setting to default!");
            main.getConfig().addDefault("initialMaxPowerLevel", 20);
        }
        if (!main.getConfig().isInt("initialPowerLevel")) {
            System.out.println("Initial power level not set! Setting to default!");
            main.getConfig().addDefault("initialPowerLevel", 5);
        }
        if (!main.getConfig().isBoolean("mobsSpawnInFactionTerritory")) {
            System.out.println("Mobs spawn in faction territory not set! Setting to default!");
            main.getConfig().addDefault("mobsSpawnInFactionTerritory", false);
        }
        if (!main.getConfig().isInt("powerIncreaseAmount")) {
            System.out.println("Hourly power increase amount not set! Setting to default!");
            main.getConfig().addDefault("powerIncreaseAmount", 2);
        }
        if (!main.getConfig().isBoolean("laddersPlaceableInEnemyFactionTerritory")) {
            System.out.println("Ladders placeable in enemy faction territory not set! Setting to default!");
            main.getConfig().addDefault("laddersPlaceableInEnemyFactionTerritory", true);
        }
        if (!main.getConfig().isInt("minutesBeforeInitialPowerIncrease")) {
            System.out.println("minutesBeforeInitialPowerIncrease not set! Setting to default!");
            main.getConfig().addDefault("minutesBeforeInitialPowerIncrease", 30);
        }
        if (!main.getConfig().isInt("minutesBetweenPowerIncreases")) {
            System.out.println("minutesBetweenPowerIncreases not set! Setting to default!");
            main.getConfig().addDefault("minutesBetweenPowerIncreases", 60);
        }

        if (!main.getConfig().isBoolean("warsRequiredForPVP")) {
            System.out.println("warsRequiredForPVP not set! Setting to default!");
            main.getConfig().addDefault("warsRequiredForPVP", true);
        }

        if (!main.getConfig().isDouble("factionOwnerMultiplier")) {
            System.out.println("factionOwnerMultiplier not set! Setting to default");
            main.getConfig().addDefault("factionOwnerMultiplier", 2.0);
        }

        if (!main.getConfig().isDouble("officerPerMemberCount")){
            System.out.println("officerPerMemberCount is not set! Setting to default");
            main.getConfig().addDefault("officerPerMemberCount", 5);
        }

        if (!main.getConfig().isDouble("factionOfficerMultiplier")){
            System.out.println("factionOfficerMultiplier is not set! Setting to default");
            main.getConfig().addDefault("factionOfficerMultiplier", 2.0);
        }

        deleteOldConfigOptionsIfPresent();

        main.getConfig().options().copyDefaults(true);
        main.saveConfig();
    }

    private void deleteOldConfigOptionsIfPresent() {
        for (String option : oldConfigOptions) {
            deleteConfigOption(option);
        }
    }

    private void deleteConfigOption(String option) {
        System.out.println("Attempting to delete old config option: " + option);
        try {
            System.out.println("Creating temporary config file");
            File tempFile = new File("./plugins/MedievalFactions/temp-config.yml");
            FileWriter writer = new FileWriter(tempFile);

            File configFile = new File("./plugins/MedievalFactions/config.yml");
            Scanner loadReader = new Scanner(configFile);

            // actual loading
            while (loadReader.hasNextLine()) {
                String next = loadReader.nextLine();
                if (next.contains(option)) {
                    System.out.println("Line contains the option! Doing nothing!");
                    System.out.println("Line: '" + next + "'");
                    System.out.println("----------------------------------------");
                }
                else {
                    System.out.println("Line does not contain the option! Copying over to the temporary config file!");
                    System.out.println("----------------------------------------");
                    writer.write(next + "\n");
                }
            }
            System.out.println("No more lines found.");
            System.out.println("----------------------------------------");

            loadReader.close();
            writer.close();

            System.out.println("Deleting config.yml");
            if (!configFile.delete()) {
                System.out.println("Something went wrong deleting the config file!");
            }

            System.out.println("Renaming temporary config to config.yml");
            if (!tempFile.renameTo(new File("./plugins/MedievalFactions/config.yml"))) {
                System.out.println("Something went wrong renaming the temporary config!");
            }

        } catch (Exception e) {
            System.out.println("There was a problem deleting the config option.");
            e.printStackTrace();
        }
    }

    public void saveConfigDefaults() {
        main.getConfig().addDefault("version", main.version);
        main.getConfig().addDefault("initialMaxPowerLevel", 20);
        main.getConfig().addDefault("initialPowerLevel", 5);
        main.getConfig().addDefault("powerIncreaseAmount", 2);
        main.getConfig().addDefault("mobsSpawnInFactionTerritory", false);
        main.getConfig().addDefault("laddersPlaceableInEnemyFactionTerritory", true);
        main.getConfig().addDefault("minutesBeforeInitialPowerIncrease", 30);
        main.getConfig().addDefault("minutesBetweenPowerIncreases", 60);
        main.getConfig().addDefault("warsRequiredForPVP", true);
        main.getConfig().addDefault("factionOwnerMultiplier", 2.0);
        main.getConfig().addDefault("officerPerMemberCount", 5);
        main.getConfig().addDefault("factionOfficerMultiplier", 1.5);
        main.getConfig().options().copyDefaults(true);
        main.saveConfig();
    }

}
