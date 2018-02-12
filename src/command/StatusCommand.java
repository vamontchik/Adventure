package command;

import console.Console;
import data.Monster;
import data.Player;

/**
 * Class representing the player asking for the status of the two combatants.
 */
public class StatusCommand implements Command {
    private final Player player;
    private final Monster opponent;
    private final int totalLengthOfBar;
    private final double percentPerTick;

    public StatusCommand(Player player, Monster opponent) {
        this.player = player;
        this.opponent = opponent;

        //each tick would be 5% of their health
        this.totalLengthOfBar = 20;
        this.percentPerTick = (100 / totalLengthOfBar) / 100.0;
    }

    @Override
    public void execute() {
        //Prints the health for the player based on the percent and length of bar , as defined above
        double totalPlayerHealth = player.getTotalHealth();
        double currentPlayerHealth = player.getHealth();

        //prints the amount of '#' needed to represent the health left
        int amountPrinted = 0;
        Console.print("Player:  ");
        while (currentPlayerHealth > 0) {
            Console.print("#");
            amountPrinted++;
            currentPlayerHealth -= (percentPerTick * totalPlayerHealth);
        }

        //print underscores to represent lost health
        while (amountPrinted < totalLengthOfBar) {
            Console.print("_");
            amountPrinted++;
        }

        //separate onto the next line
        Console.println("");

        //Print the health for the monster based on the percent and length of the bar , as defined above
        double totalMonsterHealth = opponent.getInitialHealth();
        double currentMonsterHealth = opponent.getHealth();

        //prints the amount of '#' needed to represent the health left
        amountPrinted = 0;
        Console.print("Monster: ");
        while (currentMonsterHealth > 0) {
            Console.print("#");
            amountPrinted++;
            currentMonsterHealth -= (percentPerTick * totalMonsterHealth);
        }

        //print underscores to represent lost health
        while (amountPrinted < totalLengthOfBar) {
            Console.print("_");
            amountPrinted++;
        }

        //separate onto the next line
        Console.println("");
    }
}
