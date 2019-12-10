package PlayerHandler;

import NPCHandler.DefaultNPC;
import NPCHandler.NPC;
import PlayerHandler.GamePieces.Room;

import java.io.File;

public class Tutorial {
    private Room start, startWest, startNorth, startEast, startSouth, monsterRoom, dragonSlayer, dragonRoom, moneyAndGlory;

    public Tutorial() {
        start = new Room("Tutorial Level",
                "Welcome to Generic Dungeon Crawler 2019!\n" +
                        "You are in a square blue room with smooth walls.\nIt smells vaguely of adventure.\n" +
                        "This tutorial will help you get familiar with the controls.\n" +
                        "If you know what you're doing, type 'restore' to retrieve your data.\n\n" +
                        "Let's start by learning the controls. GDC is not case sensitive.\n" +
                        "To move, simply type 'go (direction)' or just '(direction)'\n" +
                        "Try 'go north' now.",
                "This should never be seen");

        startWest = new Room("West Room",
                "You are in a triangle orange room with hairy walls.\nIt smells vaguely of the ocean.\n",
                "A room to the West. It looks like it's orange?");
        startNorth = new Room("North Room",
                "You are in a rectangle yellow room with bricked walls.\nIt smells vaguely of rotting fish.\n" +
                        "Good, now try 'go east' now!",
                "A room to the North. It looks like it's yellow?");
        startEast = new Room("East Room",
                "You are in a circle red room with sharp walls.\nIt smells vaguely of burnt toast.\n" +
                        "Awesome! Now try 'go south'",
                "A room to the East. It looks like it's red?");
        startSouth = new Room("South Room",
                "You are in a kite green room with sticky walls.\nIt smells vaguely of wet pain on a hot day.\n" +
                        "Next! Now try 'go west'",
                "A room to the South. It looks like it's green?");
        monsterRoom = new Room("Monster Room",
                "You are in a regular old square purple room with silky walls.\nIt smells vaguely of perfume of the ones you love.",
                "A room to the West. It looks like it's purple? You can hear growling.");
        dragonSlayer = new Room("Dragon Slayer Room",
                "You are in a asteroid white room with icy walls.\nIt smells vaguely of sulfur.",
                "A room to the South. It looks like its's white?");
        dragonRoom = new Room("Dragon Room",
                "You are in a pentagram black room with pointy walls.\nIt smells vaguely of rotting onions.",
                "A room to the East. It looks like it's black? You can hear a roar.");
        moneyAndGlory = new Room("Money and Glory Room",
                "You are in a octagon gold room with shiny walls\nIt smells vaguely of glory.",
                "A room to the West. It looks like its gold?");

        start.setNorth(startNorth);
        start.setWest(moneyAndGlory);
        startNorth.setEast(startEast);
        startEast.setSouth(startSouth);
        startSouth.setWest(startWest);
        startWest.setWest(monsterRoom);
        monsterRoom.setSouth(dragonSlayer);
        dragonSlayer.setEast(dragonRoom);
    }

    public Room getStart() {
        return this.start;
    }
}
