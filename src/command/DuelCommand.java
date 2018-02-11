package command;

import console.Console;
import data.Monster;
import data.Player;

/**
 * Command representing the player entering a duel with a Monster.
 */
public class DuelCommand implements Command {
    private Player player;
    private Monster opponent;

    public DuelCommand(Player player, Monster opponent) {
        this.player = player;
        this.opponent = opponent;
    }

    @Override
    public void execute() {
        Console.println("Entering a duel with " + opponent.getName() + "!");

        player.setDueling(true);
        player.setOpponent(opponent);
    }
}
