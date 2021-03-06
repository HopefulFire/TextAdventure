package Server.World.Tutorial;

import Server.CombatHandler.Weapons.Weapon;
import Server.GamePieces.Holdable;
import Server.NPCHandler.NPCRunListener;
import Server.PlayerHandler.Player;

/**
 * Dragon slayer first dialogue
 * <p>
 * Date Last Modified: 12/14/2019
 *
 * @author Daniel Masker, Ben Hodsdon, Emma Smith, Joseph Teahen
 * <p>
 * CS1131, Fall 2019
 * Lab Section 2
 */
public class DragonSlayerGhost extends InfoGiver {
    public DragonSlayerGhost() {
        super("Ghost Guardian of the Dragon Slayer Longsword");
        this.addLine("BEWARE! To the east lays a great terrible beast!");
        this.addLine("If you go to his lair and remain unprepared,");
        this.addLine("You will sadly become a morbid feast.");
        this.addLine("But hope! Be not sad, for this beasty so bad");
        this.addLine("Can be slain with this sword forged by a poor undergrad");
        this.addLine("Working overtime to save you, my lad.");
        this.addLine("If you don't think I'm right, just can't trust me tonight,");
        this.addLine("Know I didn't die in a terrible fight.");
        this.addLine("Grading's what got me, \nfor I am Michael the Rubix's Cube Knight.");

        //Gives the player a sword after it's done talking
        this.dataStorage.set(4, (NPCRunListener) event -> {
            if (event.getSource().getRoom().getPlayers().size() > 0) {
                Player player = event.getSource().getRoom().getPlayers().get(0);
                boolean hasSword = false;
                for (Holdable holdable : player.getInventory()) {
                    if (holdable instanceof Weapon) {
                        Weapon weapon = (Weapon) holdable;
                        if (weapon instanceof DragonSlayerLongsword) {
                            hasSword = true;
                        }
                    }
                }
                if (!hasSword) {
                    player.getInventory().add(new DragonSlayerLongsword());
                    player.sendMessage("[Notice]: You have received the Dragon Slayer Longsword of Dragon Slaying");
                }
            }
        });
    }
}
