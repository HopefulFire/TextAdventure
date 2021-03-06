package Server.NPCHandler;

import Server.CombatHandler.CombatGroup;
import Server.CombatHandler.Combatant;
import Server.CombatHandler.Weapons.StatHandler;
import Server.CombatHandler.Weapons.Weapon;
import Server.GamePieces.Room;
import Server.PlayerHandler.Player;

import java.util.ArrayList;

/**
 * This class is the basic class for a non-player-character
 * <p>
 * It allows combat, but not much other than that
 * <p>
 * <p>
 * Date Last Modified: 12/9/2019
 *
 * @author Daniel Masker
 * <p>
 * CS 1131, Fall 2019
 * Lab Section 2
 */
public class NPC implements Combatant {

    public static ArrayList<NPC> npcs = new ArrayList<>();

    //For stats
    private String name;
    private int maxHitpoints;
    private int currentHitpoints;
    private int brawn;
    private int spiffness;
    private int smarts;
    private int moxy;
    private Room room;

    //For combat
    private ArrayList<Weapon> weapons;
    private int pendingHeal = 0;
    private int pendingBlock = 0;
    private int pendingDamage = 0;
    private Combatant combatTarget = null;
    private CombatGroup combatGroup = null;
    private ArrayList<String> combatWords;
    private CombatGroup.rpsChoice rpsChoice;

    //For AI
    private NPCMeetSomeoneListener npcMeetSomeoneListener;
    private NPCRunListener npcRunListener;
    private NPCFindTargetListener npcFindTargetListener;
    private NPCAttackListener npcAttackListener;
    private NPCDeathListener npcDeathListener;
    private ArrayList<Object> dataStorage;

    /**
     * Creates an NPC from the values specified
     *
     * @param name                   Name of the NPC
     * @param maxHitpoints           the maximum hitpoints
     * @param brawn                  the brawn stat
     * @param spiffness              the spiffness stat
     * @param smarts                 the smarts stat
     * @param moxy                   the moxy stat
     * @param weapons                ArrayList of weapons the NPC has equipped
     * @param combatWords            The combatWords the npc will use
     * @param combatChoice           The decision the npc will make in combat RPS
     * @param npcMeetSomeoneListener The listener to handle this npc meeting someone
     */
    //Todo update this constructor to make copies of the ArrayList objects
    NPC(String name, int maxHitpoints,
        int brawn, int spiffness, int smarts, int moxy,
        ArrayList<Weapon> weapons, ArrayList<String> combatWords,
        CombatGroup.rpsChoice combatChoice,
        NPCMeetSomeoneListener npcMeetSomeoneListener,
        NPCRunListener npcRunListener,
        NPCFindTargetListener npcFindTargetListener,
        ArrayList<Object> dataStorage,
        NPCAttackListener npcAttackListener,
        NPCDeathListener npcDeathListener) {

        this.rpsChoice = combatChoice;
        this.weapons = weapons;
        this.name = name;
        this.maxHitpoints = maxHitpoints;
        this.currentHitpoints = maxHitpoints;
        this.brawn = brawn;
        this.spiffness = spiffness;
        this.smarts = smarts;
        this.moxy = moxy;
        this.combatWords = combatWords;
        this.npcMeetSomeoneListener = npcMeetSomeoneListener;
        this.npcRunListener = npcRunListener;
        this.npcFindTargetListener = npcFindTargetListener;
        this.dataStorage = dataStorage;
        this.npcAttackListener = npcAttackListener;
        this.npcDeathListener = npcDeathListener;

        npcs.add(this);
    }

    /**
     *
     */

    /**
     * Creates an NPC from the specified template
     *
     * @param template template to use for NPC creation. Must implement the NPCTemplate interface
     */
    //Todo review this constructor and make deep copies
    public NPC(NPCTemplate template) {
        this.rpsChoice = template.getRPSChoice();
        this.weapons = template.getWeapons();
        this.name = template.getName();
        this.maxHitpoints = template.getMaxHitpoints();
        this.currentHitpoints = template.getMaxHitpoints();
        this.brawn = template.getBrawn();
        this.spiffness = template.getSpiffness();
        this.smarts = template.getSmarts();
        this.moxy = template.getMoxy();
        this.combatWords = template.getCombatWords();
        this.npcMeetSomeoneListener = template.getNPCMeetSomeoneListener();
        this.npcRunListener = template.getNPCRunListener();
        this.npcFindTargetListener = template.getNPCFindTargetListener();
        this.dataStorage = template.getDataStorage();
        this.npcAttackListener = template.getNPCAttackListener();
        this.npcDeathListener = template.getNPCDeathListener();
        template.increment();

        npcs.add(this);
    }

    public void runDeath() {
        NPCDeathEvent event = new NPCDeathEvent(this);
        if (this.npcDeathListener != null) {
            this.npcDeathListener.handle(event);
        }
    }

    public void makeAttack() {
        NPCAttackEvent event = new NPCAttackEvent(this);
        if (this.npcAttackListener != null) {
            this.npcAttackListener.handle(event);
        }
    }

    public void findTarget() {
        NPCFindTargetEvent event = new NPCFindTargetEvent(this);
        if (this.npcFindTargetListener != null) {
            this.npcFindTargetListener.handle(event);
        }
    }

    public int getStatByReference(StatHandler.Stats stats) {
        if (stats == null) {
            return -1;
        }
        switch (stats) {
            case smerts:
                return this.smarts;
            case moxy:
                return this.moxy;
            case spiffness:
                return this.spiffness;
            case brawn:
                return this.brawn;
        }
        return -1;
    }

    //Todo make this method return a copy of the ArrayList
    public ArrayList<Object> getDataStorage() {
        return this.dataStorage;
    }

    /**
     * Runs a frame for the NPC
     */
    public void run() {
        NPCRunEvent event = new NPCRunEvent(this);
        if (npcRunListener != null) {
            npcRunListener.handle(event);
        }
    }

    /**
     * Gets the NPCRunListener for this NPC
     *
     * @return the NPCRunListener
     */
    public NPCRunListener getNPCRunListener() {
        return npcRunListener;
    }

    /**
     * Sets the NPCRunListener for this NPC
     *
     * @param listener the NPCRunListener
     */
    public void setNPCRunListener(NPCRunListener listener) {
        this.npcRunListener = listener;
    }

    /**
     * Has the npc say something to the room
     *
     * @param message
     */
    public void say(String message) {
        for (Player player : this.room.getPlayers()) {
            player.sendMessage(this.name + ": " + message);
        }
    }

    /**
     * Gets the room this npc is in
     *
     * @return this NPCs room
     */
    public Room getRoom() {
        return this.room;
    }

    /**
     * Sets the room this npc is in
     *
     * @param room The room this npc is now in
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * Prompts this NPC to handle someone entering the room
     */
    public void handleEnter() {
        NPCMeetSomeoneEvent event = new NPCMeetSomeoneEvent(this);
        System.out.println("Handle Enter Called");
        if (npcMeetSomeoneListener != null) {
            System.out.println("Enter handled");
            npcMeetSomeoneListener.handle(event);
        }
    }

    /**
     * Set this NPCs npcMeetSomeoneListener
     *
     * @param listener the new Listener to set
     */
    public void setNpcMeetSomeoneListener(NPCMeetSomeoneListener listener) {
        this.npcMeetSomeoneListener = listener;
    }

    /**
     * Gets this NPCs npcMeetSomeoneListener
     *
     * @return the NPCMeetSomeoneListener
     */
    public NPCMeetSomeoneListener getNpcMeetSomeoneListener() {
        return this.npcMeetSomeoneListener;
    }


    /**
     * Sets the creature's rps choice
     *
     * @param rpsChoice new rps choice of the creature
     */
    @Override
    public void setCombatDecision(CombatGroup.rpsChoice rpsChoice) {
        this.rpsChoice = rpsChoice;
    }

    /**
     * Gets the RPS choice of the creature
     *
     * @return the creature's RPS choice
     */
    @Override
    public CombatGroup.rpsChoice getCombatDecision() {
        return this.rpsChoice;
    }

    /**
     * Sets a new combat group for this creature;=
     *
     * @param group the new group to set
     */
    @Override
    public void setCombatGroup(CombatGroup group) {
        this.combatGroup = group;
    }

    /**
     * Gets this creature's combat group
     *
     * @return combat group of the creature
     */
    @Override
    public CombatGroup getCombatGroup() {
        return this.combatGroup;
    }

    /**
     * Gets this NPC's combat target
     *
     * @return the creature's target
     */
    @Override
    public Combatant getTarget() {
        return this.combatTarget;
    }

    /**
     * Sets the target for combat
     *
     * @param target the target for this NPC's attacks
     */
    @Override
    public void setTarget(Combatant target) {
        this.combatTarget = target;
    }

    /**
     * Gets the words the npc will use in combat
     *
     * @return array list of the attack words
     */
    //Todo have method return a copy of the ArrayList
    @Override
    public ArrayList<String> getWords() {
        return this.combatWords;
    }

    /**
     * Sets the amount of damage the creature is waiting to block
     *
     * @param amount the new amount pending
     */
    @Override
    public void setPendingBlock(int amount) {
        this.pendingBlock = amount;
    }

    /**
     * Gets the amount of damage the creature is waiting to block
     *
     * @return the pending blockage
     */
    @Override
    public int getPendingBlock() {
        return this.pendingBlock;
    }

    /**
     * Sets the amount of damage the creature is waiting to take
     *
     * @param amount amount of damage pending
     */
    @Override
    public void setPendingDamage(int amount) {
        this.pendingDamage = amount;
    }

    /**
     * Gets the amount of damage the creature is waiting to take
     *
     * @return amount of damage pending
     */
    @Override
    public int getPendingDamage() {
        return this.pendingDamage;
    }

    /**
     * Sets the amount the creature is waiting to be healed
     *
     * @param amount new amount to be healed
     */
    @Override
    public void setPendingHeal(int amount) {
        this.pendingHeal = amount;
    }

    /**
     * Gets the amount the creature is waiting to be healed
     *
     * @return The pending heal damage of the creature
     */
    @Override
    public int getPendingHeal() {
        return this.pendingHeal;
    }

    /**
     * Get the weapons this creature has
     *
     * @return Weapon array list
     */
    //Todo update method to return a copy of the ArrayList
    @Override
    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    /**
     * Modifies the creature's hitpoints
     *
     * @param amount value to add to the current hitpoints
     * @return new current hitpoint value
     */
    @Override
    public int modifyHitpoints(int amount) {
        currentHitpoints += amount;
        if (currentHitpoints > maxHitpoints) {
            currentHitpoints = maxHitpoints;
        } else if (currentHitpoints < 0) {
            currentHitpoints = 0;
        }
        if (currentHitpoints < 1) {
            runDeath();
            if (this.getRoom() != null) {
                this.getRoom().removeNPC(this);
            }
            npcs.remove(this);
        }


        return currentHitpoints;
    }

    /**
     * Get the creature's current hitpoints
     *
     * @return current hitpoints of the creature
     */
    @Override
    public int getHitPoints() {
        return this.currentHitpoints;
    }

    /**
     * Get the creature's maximum hitpoints
     *
     * @return maximum hitpoints of the creature
     */
    @Override
    public int getMaxHitpoints() {
        return this.maxHitpoints;
    }

    /**
     * Set the maximum hitpoints of the creature
     *
     * @param amount value to set it to
     */
    @Override
    public void setMaxHitpoints(int amount) {
        this.maxHitpoints = amount;
    }

    /**
     * Get the name of the creature
     *
     * @return the creature's name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Get whether creature is unconscious
     *
     * @return whether unconscious or not
     */
    @Override
    public boolean isUnconscious() {
        return currentHitpoints < 1;
    }

    /**
     * Set moxy of creature
     *
     * @param moxy Value to set
     */
    @Override
    public void setMoxy(int moxy) {
        this.moxy = moxy;
    }

    /**
     * Get moxy of creature
     *
     * @return creature's moxy
     */
    @Override
    public int getMoxy() {
        return this.moxy;
    }

    /**
     * Set spiffness of creature
     *
     * @param spiffness spiffness to set
     */
    @Override
    public void setSpiffness(int spiffness) {
        this.spiffness = spiffness;
    }

    /**
     * Get spiffness of creature
     *
     * @return creature's spiffness
     */
    @Override
    public int getSpiffness() {
        return this.spiffness;
    }

    /**
     * Set smarts for creature
     *
     * @param smarts value to set
     */
    @Override
    public void setSmarts(int smarts) {
        this.smarts = smarts;
    }

    /**
     * Get smarts for creature
     *
     * @return creature's smarts
     */
    @Override
    public int getSmarts() {
        return this.smarts;
    }

    /**
     * Get brawn for creature
     *
     * @return creature's brawn
     */
    @Override
    public int getBrawn() {
        return brawn;
    }

    /**
     * Set brawn to desired value
     *
     * @param brawn value to set brawn
     */
    @Override
    public void setBrawn(int brawn) {
        this.brawn = brawn;
    }
}
