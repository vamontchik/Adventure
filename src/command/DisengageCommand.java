package command;

import console.Console;
import data.Player;

public class DisengageCommand implements Command {
    private Player player;

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
