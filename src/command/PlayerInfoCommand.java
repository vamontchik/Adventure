package command;

import console.Console;
import data.Player;

/**
 * Command representing the printing out of the Player's stats.
 */
public class PlayerInfoCommand implements Command {
    private Player player;

    public PlayerInfoCommand(Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        Console.println("Your stats: ");
        Console.println("\tLevel: " + player.getLevel());
        Console.println("\tAttack: " + player.getAttack());
        Console.println("\tDefense: " + player.getDefense());
        Console.println("\tHealth: " + player.getHealth());
    }
}
