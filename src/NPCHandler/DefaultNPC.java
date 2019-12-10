package NPCHandler;

import PlayerHandler.CombatHandler.CombatGroup;
import PlayerHandler.CombatHandler.Weapons.Weapon;

import java.util.ArrayList;

/**
 * A default NPC template for easy NPC creation
 * Doesn't do much other than be standard
 * <p>
 * Date Last Modified: 10/9/2019
 *
 * @author Daniel Masker, Joe Teahen, Emma Smith, Ben Hodsdon
 * <p>
 * CS 1131, Fall 2019
 * Lab Section 2
 */
public class DefaultNPC implements NPCTemplate {

    private String name;                                        //Name of the npc in this template
    private ArrayList<Weapon> weapons = new ArrayList<>();      //Weapons it hold. Empty
    private ArrayList<String> words = new ArrayList<>();        //String of words it uses in combat.
    private int count = 0;                                      //Times this template has been used
    private int maxHitpoints = 10;                              //Maximum hitpoints of this NPC

    /**
     * Creates the default with the name given
     *
     * @param name the name of the NPC
     */
    public DefaultNPC(String name) {
        this.name = name;
        for (int i = 0; i < 3; i++) {
            words.add("hit");
        }
    }

    /**
     * Creates the default with the name and maximum hitpoints given
     *
     * @param name         Name of the npc
     * @param maxHitpoints Hitpoints of the npc
     */
    public DefaultNPC(String name, int maxHitpoints) {
        this(name);
        this.maxHitpoints = maxHitpoints;
    }

    /**
     * Allows easy update-and-use of new name
     *
     * @param newName new name to use
     * @return this DefaultNPC for easy one-line usage
     */
    public DefaultNPC updateName(String newName) {
        this.name = newName;
        return this;
    }

    /**
     * Returns the maximum hitpoints of this template
     *
     * @return the maximum hitpoints
     */
    @Override
    public int getMaxHitpoints() {
        return maxHitpoints;
    }

    /**
     * Returns the Moxy of this template
     *
     * @return 10
     */
    @Override
    public int getMoxy() {
        return 10;
    }

    /**
     * Returns the brawn of this template
     *
     * @return 10
     */
    @Override
    public int getBrawn() {
        return 10;
    }

    /**
     * Returns the spiffness of this template
     *
     * @return 10
     */
    @Override
    public int getSpiffness() {
        return 10;
    }

    /**
     * Returns the smarts of this template
     *
     * @return 10
     */
    @Override
    public int getSmarts() {
        return 10;
    }

    /**
     * Returns the name given to this template
     *
     * @return the name given
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Gets this templates weapons
     *
     * @return an empty ArrayList
     */
    @Override
    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    /**
     * Gets this templates combat words
     *
     * @return ArrayList with hit, hit, hit
     */
    @Override
    public ArrayList<String> getCombatWords() {
        return words;
    }

    /**
     * Gets the rps choice of this template
     *
     * @return talk
     */
    @Override
    public CombatGroup.rpsChoice getRPSChoice() {
        return CombatGroup.rpsChoice.talk;
    }

    /**
     * Increments the count for this template
     */
    @Override
    public void increment() {
        count++;
    }

    /**
     * Gets how many times this template has been used (at least if reported)
     *
     * @return count of times used
     */
    public int getTimesUsed() {
        return count;
    }
}