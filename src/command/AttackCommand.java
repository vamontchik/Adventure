package command;

import console.Console;
import data.Item;
import data.Monster;
import data.Player;

public class AttackCommand implements Command {
    private Player player;
    private Monster opponent;
    private Item item;
    private final double TOLERANCE;

    public AttackCommand(Player player, Monster opponent, Item item) {
        this.player = player;
        this.opponent = opponent;
        this.item = item;
        TOLERANCE = 0.0001;
    }

    public AttackCommand(Player player, Monster opponent) {
        this.player = player;
        this.opponent = opponent;
        TOLERANCE = 0.0001;
    }

    @Override
    public void execute() {
        //calculate damage to be done
        double damage = player.getAttack() - opponent.getDefense();

        //add in item damage if one is used
        if (!(item == null)) {
            Console.println("Item " + item.getName() + " used!");
            player.removeItem(item);
            damage += item.getDamage();
        }

        //check to see if your attack did any damage. if so, don't update any value for health values past this point
        if (damage < TOLERANCE) {
            Console.println("Your attack did nothing...");
        } else {
            //calculate new health of damage opponent
            double newHealth = opponent.getHealth() - damage;

            //check to see if the monster is dead. If so, disengage duel and add in experience.
            if (newHealth < TOLERANCE) {
                Console.println("You have won the duel!");

                //Add experience if necessary. setExperience() also handles leveling up, if necessary.
                double experience = ((opponent.getAttack() + opponent.getDefense()) / 2 + opponent.getInitialHealth()) * 20;
                player.setExperience(player.getExperience() + experience);

                //remove the monster from the room
                player.getCurrentRoom().removeMonster(opponent.getName());

                //disengage the player from the duel gracefully
                new DisengageCommand(player).execute();

                //since the duel is over, leave the method
                return;
            }

            //apply damage
            opponent.setHealth(newHealth);
        }

        //monster's turn: calculate damage it'd do
        double monsterDamage = opponent.getAttack() - player.getDefense();

        //if damage is 0 or below, don't update any health values past this point
        if (monsterDamage < TOLERANCE) {
            Console.println("The monster's attack did nothing...");
        } else {
            double newPlayerHealth = player.getHealth() - monsterDamage;

            if (newPlayerHealth < TOLERANCE) {
                //oops :(
                Console.println("You have died! Game over.");

                //display player stats one last time
                new PlayerInfoCommand(player).execute();

                //quit the game
                new ExitCommand().execute();
            }

            player.setHealth(newPlayerHealth);
        }
    }
}
