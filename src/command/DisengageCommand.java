package command;

import console.Console;
import data.Player;

/**
 * Command representing the Player disengaging from battle.
 */
public class DisengageCommand implements Command {
    private final Player player;

    public DisengageCommand(Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        Console.println("Leaving a duel with " + player.getOpponent().getName() + "!");

        player.setDueling(false);
        player.setOpponent(null);
    }
}
