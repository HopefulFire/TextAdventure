package Server.PlayerHandler.UI;

import Server.CombatHandler.AttackCommands;
import Server.PlayerHandler.Player;

import java.util.ArrayList;

/**
 * The class creates a combat frame
 * <p>
 * Date Last Modified: 12/14/2019
 *
 * @author Daniel Masker, Ben Hodsdon, Emma Smith, Joseph Teahen
 * <p>
 * CS1131, fall 2019
 * Lab Section 2
 */
public class CombatFrame extends Frame {
    private ArrayList<AttackCommands> attackCommands = new ArrayList<>(); //the attack commands a player can use
    private Player player; //the player
    private SizableFrame console = new SizableFrame(11, 51); //sizable frame for the combat frame
    private ArrayList<String> combatLog = new ArrayList<>(); //the commands used by the player
    public final int height = 30; //the height of the frame
    String startTimer = "Start in: 5"; //start of the timer

    /**
     * The CombatFrame constructor
     *
     * @param player
     */
    public CombatFrame(Player player) {
        this.player = player;
    }

    /**
     * Changes the startTimer String
     *
     * @param startTimer
     */
    public void updateStartTimer(String startTimer) {
        this.startTimer = startTimer;
    }

    /**
     * Gets different lines of the stats of the player for combat
     *
     * @param line the line of the stats
     * @return a string for the info
     */
    private String getStatString(int line) {
        switch (line) {
            case 2:
                return "Stats   ";
            case 3:
                return "";
            case 4:
                return String.format("%6s:  %2d", "Max HP", player.getMaxHitpoints());
            case 5:
                return String.format("%6s:  %2d", "HP", player.getHitPoints());
            case 6:
                return String.format("%6s:  %2d", "Brawn", player.getBrawn());
            case 7:
                return String.format("%6s:  %2d", "Spiff", player.getSpiffness());
            case 8:
                return String.format("%6s:  %2d", "Smarts", player.getSmarts());
            case 9:
                return String.format("%6s:  %2d", "Moxy", player.getMoxy());
        }
        return null;
    }

    /**
     * Gets the combat long string necessary
     *
     * @param line the line getting from
     * @return the string for the combat long
     */
    private String getCombatLogString(int line) {
        if (line < 14) {
            throw new IllegalArgumentException();
        } else if (line < 15) {
            return String.format("Combat %-58s", combatLog.size() > 0 ? "> " + combatLog.get(0) : "");
        } else if (line < 16) {
            return String.format(" log:  %-58s", combatLog.size() > 1 ? "> " + combatLog.get(0) : "");
        } else {
            return String.format("       %-58s", combatLog.size() > line - 14 ? "> " + combatLog.get(line - 14) : "");
        }
    }

    /**
     * Gets the combat commands possible and returns them as a string
     *
     * @param line the line getting from
     * @return the string version of the commands possible
     */
    private String getCommandsPossibleString(int line) {
        line -= 2;
        if (player.getCombatGroup() == null) {
            return "";
        }
        switch (player.getCombatGroup().getCombatState()) {
            case firstround:
            case words:
                if (line < 13) {
                    return "            Commands";
                } else if (line < 14) {
                    return "";
                } else if (line < 15) {
                    return " inventory (number)";
                } else {
                    if (player.getPossibleAttackCommands().size() > line - 15) {
                        return " " + player.getPossibleAttackCommands().get(line - 15).toString();
                    } else {
                        return "";
                    }
                }
            case rps:
            case initialize:
            case calculate:
            case startCombat:
                if (line < 13) {
                    return "            Targets";
                } else if (line < 14) {
                    return "";
                } else if (player.getCombatGroup().getCombatants(player).size() > line - 14) {
                    return String.format("%2d. %-16.16s", line - 13,
                            player.getCombatGroup().getCombatants(player).get(line - 14).getName());
                } else {
                    return "";
                }
        }
        return "";
    }

    /**
     * Gets the string for the console
     *
     * @param line the line necessary
     * @return the string version of that line
     */
    private String getConsoleString(int line) {
        return console.getLine(line);
    }

    /**
     * Gets the startTimer string
     *
     * @return the startTimer String
     */
    private String getStartString() {
        return startTimer;
    }

    /**
     * Gets the inventory as a string
     *
     * @param line the line necessary
     * @return the inventory line
     */
    private String getInventoryString(int line) {
        if (line < 0) {
            throw new IllegalArgumentException();
        }
        if (player.getInventory().size() < 1) {
            if (line < 2 || line > 3) {
                return String.format("%-31.31s", new InventoryFrame(player).getLine(line));
            } else {
                if (line == 2) {
                    return String.format("%-31.31s", "Your inventory is empty!");
                } else {
                    return String.format("%-31.31s", "¯\\_(ツ)_/¯ Sowwy.");
                }
            }
        }
        return new InventoryFrame(player).getLine(line);
    }

    /**
     * Gets the commands used by the player as a string
     *
     * @param line the line necessary
     * @return the string version of the commands
     */
    private String getCommandsUsedString(int line) {
        if (line < 12) {
            return String.format("Commands 1.%-12.12s2.%-12.12s3.%-12.12s4.%-12.12s",
                    player.getWords().size() > 0 ? player.getWords().get(0) : "",
                    player.getWords().size() > 1 ? player.getWords().get(1) : "",
                    player.getWords().size() > 2 ? player.getWords().get(2) : "",
                    player.getWords().size() > 3 ? player.getWords().get(3) : "");
        } else {
            return String.format("  Used:  5.%-12.12s6.%-12.12s7.%-12.12s8.%-12.12s",
                    player.getWords().size() > 4 ? player.getWords().get(4) : "",
                    player.getWords().size() > 5 ? player.getWords().get(5) : "",
                    player.getWords().size() > 6 ? player.getWords().get(6) : "",
                    player.getWords().size() > 7 ? player.getWords().get(7) : "");
        }
    }

    /**
     * Grabbing all the windows to making one big combat frame
     *
     * @param line the line necessary
     * @return the string version of that line of the combat frame
     */
    private String findFormat(int line) {
        if (line < 0) {
            throw new IllegalArgumentException();
        } else if (line == 0) {
            return String.format("|%-51s|%13.13s|%31.31s|",
                    getConsoleString(line), getStartString(), getInventoryString(line));
        } else if (line == 1) {
            return String.format("|%-51s|-------------|%31.31s|", getConsoleString(line), getInventoryString(line));
        } else if (line < 10) {
            return String.format("|%-51s| %11s |%31.31s|", getConsoleString(line), getStatString(line), getInventoryString(line));
        } else if (line < 11) {
            StringBuilder dash = new StringBuilder();
            for (int i = 0; i < 65; i++) {
                dash.append("-");
            }
            return String.format("|%65s|%31.31s|", dash.toString(), getInventoryString(line));
        } else if (line < 13) {
            return String.format("|%65s|%31.31s|", getCommandsUsedString(line), getInventoryString(line));
        } else if (line < 14) {
            StringBuilder dash1 = new StringBuilder();
            StringBuilder dash2 = new StringBuilder();
            for (int i = 0; i < 65; i++) {
                dash1.append("-");
                if (i < 51) {
                    dash2.append("-");
                }
            }
            return String.format("|%65.65s|%31.31s|", dash1.toString(), dash2.toString());
        } else if (line < 23) {
            return String.format("|%65.65s|%-31.31s|", getCombatLogString(line), getCommandsPossibleString(line));
        } else if (line < 24) {
            StringBuilder dash1 = new StringBuilder();
            StringBuilder dash2 = new StringBuilder();
            for (int i = 0; i < 65; i++) {
                dash1.append("=");
                if (i < 31) {
                    dash2.append("=");
                }
            }
            return String.format("|%65s|%31s|", dash1.toString(), dash2.toString());
        } else {
            return "";
        }
    }

    /**
     * Gets the output of the combat frame
     *
     * @return the combat frame as a string
     */
    @Override
    public String getOutput() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < height; i++) {
            output.append(findFormat(i));
            if (i < height - 1) {
                output.append("\n");
            }
        }
        return output.toString();
    }

    /**
     * Adds a line to combat frame
     *
     * @param line the line necessary
     * @return if it adds
     */
    @Override
    public boolean addLine(String line) {
        return this.addLine(line, true);
    }

    /**
     * Adds a line to the combat frame
     *
     * @param line  the line necessary
     * @param force if it deletes a line at the top so it can fit at the bottom or not
     * @return if it adds
     */
    @Override
    public boolean addLine(String line, boolean force) {
        return console.addLine(line, force);
    }

    /**
     * Adds a string to the console
     *
     * @param text the string to add
     * @return if it adds or not
     */
    @Override
    public boolean add(String text) {
        return console.add(text, true);
    }

    /**
     * Adds a string to the console
     *
     * @param text  the string to add
     * @param force if it deletes a line at the top so it can fit at the bottom or not
     * @return if it adds or not
     */
    @Override
    public boolean add(String text, boolean force) {
        return console.add(text, force);
    }

    /**
     * Adds a paragraph to the console
     *
     * @param text  the string to add
     * @param force if it deletes a line at the top so it can fit at the bottom or not
     * @return if it adds or not
     */
    @Override
    public boolean addParagraph(String text, boolean force) {
        return console.addParagraph(text, force);
    }

    /**
     * Adds a paragraph to the console
     *
     * @param text the string to add
     * @return if it adds or not
     */
    @Override
    public boolean addParagraph(String text) {
        return this.addParagraph(text, true);
    }

    /**
     * Adds a new line to the frame
     *
     * @return if it adds or not
     */
    @Override
    public boolean newLine() {
        return console.newLine();
    }

    /**
     * Clears the frame
     */
    @Override
    public void clearFrame() {
        console.clearFrame();
    }

    /**
     * Checks to see if the frame is empty
     *
     * @return false
     */
    @Override
    public boolean isEmpty() {
        return false;
    }

    /**
     * Adds to the combat log
     *
     * @param input the command to add to log
     */
    public void addToCombatLog(String input) {
        combatLog.add(input);
        if (combatLog.size() > 9) {
            combatLog.remove(0);
        }
    }
}
