package it.polimi.ingsw.client.view.ui.cli;

import java.util.*;
import java.util.stream.Collectors;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.*;
import it.polimi.ingsw.client.view.representation.RepresentationBuilder;
import it.polimi.ingsw.client.view.ui.*;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.network.auth_data.*;
import it.polimi.ingsw.server.model.resource.ResourcePosition;
import it.polimi.ingsw.server.model.resource.ResourceType;
import it.polimi.ingsw.utils.Pair;

public class CLI implements UI {

    public static final String ANSI_RESET  = "\u001B[0m";

    public static final String ANSI_BLACK  = "\u001B[30m";
    public static final String ANSI_RED    = "\u001B[31m";
    public static final String ANSI_GREEN  = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE   = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN   = "\u001B[36m";
    public static final String ANSI_WHITE  = "\u001B[37m";

    public static final String ANSI_BRIGHT_BLACK  = "\u001B[90m";
    public static final String ANSI_BRIGHT_RED    = "\u001B[91m";
    public static final String ANSI_BRIGHT_GREEN  = "\u001B[92m";
    public static final String ANSI_BRIGHT_YELLOW = "\u001B[93m";
    public static final String ANSI_BRIGHT_BLUE   = "\u001B[94m";
    public static final String ANSI_BRIGHT_PURPLE = "\u001B[95m";
    public static final String ANSI_BRIGHT_CYAN   = "\u001B[96m";
    public static final String ANSI_BRIGHT_WHITE  = "\u001B[97m";

    public static final String ANSI_BG_BLACK  = "\u001B[40m";
    public static final String ANSI_BG_RED    = "\u001B[41m";
    public static final String ANSI_BG_GREEN  = "\u001B[42m";
    public static final String ANSI_BG_YELLOW = "\u001B[43m";
    public static final String ANSI_BG_BLUE   = "\u001B[44m";
    public static final String ANSI_BG_PURPLE = "\u001B[45m";
    public static final String ANSI_BG_CYAN   = "\u001B[46m";
    public static final String ANSI_BG_WHITE  = "\u001B[47m";

    public static final String ANSI_BRIGHT_BG_BLACK  = "\u001B[100m";
    public static final String ANSI_BRIGHT_BG_RED    = "\u001B[101m";
    public static final String ANSI_BRIGHT_BG_GREEN  = "\u001B[102m";
    public static final String ANSI_BRIGHT_BG_YELLOW = "\u001B[103m";
    public static final String ANSI_BRIGHT_BG_BLUE   = "\u001B[104m";
    public static final String ANSI_BRIGHT_BG_PURPLE = "\u001B[105m";
    public static final String ANSI_BRIGHT_BG_CYAN   = "\u001B[106m";
    public static final String ANSI_BRIGHT_BG_WHITE  = "\u001B[107m";

    public static final String ANSI_BOLD = "\u001B[1m";
    public static final String ANSI_UNDERLINE = "\u001B[4m";
    public static final String ANSI_REVERSED = "\u001B[7m";


//    private static final String TITLE = " ___  ___  ___   __  ______  ____ ____   __       ___    ____    ____   ____ __  __  ___  __  __   __   ___  __  __   ___  ____\n" +
//            " ||\\\\//|| // \\\\ (( \\ | || | ||    || \\\\ (( \\     // \\\\  ||       || \\\\ ||    ||\\ || // \\\\ || (( \\ (( \\ // \\\\ ||\\ ||  //   ||   \n" +
//            " || \\/ || ||=||  \\\\    ||   ||==  ||_//  \\\\     ((   )) ||==     ||_// ||==  ||\\\\|| ||=|| ||  \\\\   \\\\  ||=|| ||\\\\|| ((    ||== \n" +
//            " ||    || || || \\_))   ||   ||___ || \\\\ \\_))     \\\\_//  ||       || \\\\ ||___ || \\|| || || || \\_)) \\_)) || || || \\||  \\\\__ ||___\n" +
//            "                                                                                                                               \n";

    private static final String TITLE;

    static {
        TITLE = "\n" +
                "       \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m   \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m      \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m              \n" +
                "       \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m\u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m    \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m              \n" +
                "       \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m   \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m   \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m  \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m╝\u001B[0m    \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m   \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m                \n" +
                "       \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[93m╚\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m╝\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m   \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m   \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m  \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m    \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m   \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m                \n" +
                "       \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m \u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m  \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m   \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m   \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m  \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m    \u001B[93m╚\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m╝\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m                   \n" +
                "       \u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m     \u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m\u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m  \u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m\u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m   \u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m   \u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m\u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m  \u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m     \u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m \u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m                   \n" +
                "                                                                                        \n" +
                "\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m   \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m   \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m\n" +
                "\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m  \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m  \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m\n" +
                "\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m╝\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m  \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m     \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m  \n" +
                "\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m  \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[93m╚\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[93m╚\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m     \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m  \n" +
                "\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m  \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m \u001B[93m╚\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m  \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m  \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m \u001B[93m╚\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[93m╚\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m\n" +
                "\u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m  \u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m\u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m\u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m  \u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m\u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m  \u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m\u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m\u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m\u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m\u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m  \u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m\u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m  \u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m \u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m\u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m\n";
    }

    private final Integer outSemaphore = 0;
    private boolean gameStarted = false;

    private ArrayList<Command> commands = new ArrayList<>();
    Scanner in = new Scanner(System.in);

    public CLI(ArrayList<Command> commands) {
        this.commands.addAll(commands);
    }

    public void execute(String key) throws UnknownCommandException {
        Command target = commands.stream().filter(command -> command.identifiedBy(key)).findAny().orElse(null);
        if (target == null) {
            throw new UnknownCommandException(String.format("No command identified by '%s' exists", key));
        }
        // target.execute();
    }

    private void parseCommand(String command) {

    }

    @Override
    public AuthData requestAuth(){
        AuthData authData = null;
        synchronized (outSemaphore) {
            String playerUsername;
            String choice;
            System.out.println(TITLE);
            System.out.printf("Enter an username:\n\t> ");
            playerUsername = in.nextLine();
            do {
                System.out.print("Welcome, " + playerUsername + "! Would you like to join [J] or create [C] a lobby?\n\t> ");
                choice = in.nextLine();
                if (choice.equalsIgnoreCase("J")) {
                    String lobbyToJoin;
                    System.out.print("Enter a valid lobby ID: \n\t> ");
                    lobbyToJoin = in.nextLine();
                    authData = AuthData.joinLobby(playerUsername, lobbyToJoin);
                } else if (choice.equalsIgnoreCase("C")) {
                    int playerNumber = 0;
                    boolean validNumber;
                    do {
                        validNumber = true;
                        System.out.print("Enter the maximum number of players for this lobby [1-4]: \n\t> ");
                        try {
                            playerNumber = Integer.parseInt(in.nextLine());
                        }catch (NumberFormatException e){
                            validNumber = false;
                        }
                        if(validNumber){
                            if(playerNumber < 1 || playerNumber > 4){
                                validNumber = false;
                            }
                        }
                    } while (!validNumber);
                    authData = AuthData.createLobby(playerUsername, playerNumber);
                }
            } while (!choice.equalsIgnoreCase("J") && !choice.equalsIgnoreCase("C"));
        }
        return authData;
    }

    @Override
    public void displayAuthFail(String errType) {
        synchronized (outSemaphore) {
            System.out.print("Failed to login! ");
            switch (errType) {
                case "full_lobby":
                    System.out.println("Full lobby!");
                    break;
                case "invalid_lobby":
                    System.out.println("Invalid lobby!");
                    break;
                default:
                    System.out.println("Generic error while connecting to server.");
                    break;
            }
            System.out.println("Trying again...");
        }
    }

    @Override
    public void displayLobbyJoined(String lobbyId){
        synchronized (outSemaphore) {
            System.out.println("User logged successfully in lobby with ID: " + ANSI_YELLOW + lobbyId + ANSI_RESET);
        }
    }

    @Override
    public void displayLobbyCreated(String lobbyId){
        synchronized (outSemaphore) {
            System.out.println("Lobby created succeffully in lobby with ID: " + ANSI_YELLOW + lobbyId + ANSI_RESET);
        }
    }

    @Override
    public void displayNewTurn(String player, Boolean myTurn){
        synchronized (outSemaphore) {
            if (myTurn) {
                System.out.println(player + ", IT'S YOUR TURN!");
            } else {
                System.out.println("It's " + player + "'s turn.");
            }
        }
    }

    @Override
    public void displayGameStarted(){
        synchronized (outSemaphore) {
            System.out.println("Game has started!");
        }
    }

    @Override
    public void displayLeaderCard(ClientLeaderCard clientLeaderCard) {
        synchronized (outSemaphore) {
            String outputFormat = "%s Lv%d - Victory Points: %d - [";
        }
    }

    public HashMap<ResourcePosition, ResourceType> chooseInitialResources(int toChoose){
        HashMap<ResourcePosition, ResourceType> resources = new HashMap<>();
        synchronized (outSemaphore) {
            int chosenResources = 0;
            boolean validSelection = true;
            String tempResource, tempDepot;
            ResourcePosition resourcePosition = null;
            ResourceType resourceType = null;

            System.out.println("You have to choose " + toChoose + " resource/s!");
            while (chosenResources < toChoose) {
                do {
                    validSelection = true;
                    System.out.print("Choose resources #" + (chosenResources + 1) +
                            " [STONE | SERVANT | SHIELD | COIN]: \n\t> ");
                    tempResource = in.nextLine();
                    switch (tempResource) {
                        case "STONE":
                            resourceType = ResourceType.STONE;
                            break;
                        case "SERVANT":
                            resourceType = ResourceType.SERVANT;
                            break;
                        case "SHIELD":
                            resourceType = ResourceType.SHIELD;
                            break;
                        case "COIN":
                            resourceType = ResourceType.COIN;
                            break;
                        default:
                            validSelection = false;
                            System.out.println("Please choose a valid resource type!");
                    }
                } while (!validSelection);

                do {
                    validSelection = true;
                    System.out.print("Choose in which depot you want to put this resource [FIRST | SECOND | THIRD]: \n\t> ");
                    tempDepot = in.nextLine();
                    switch (tempDepot) {
                        case "FIRST":
                            resourcePosition = ResourcePosition.FIRST_DEPOT;
                            break;
                        case "SECOND":
                            resourcePosition = ResourcePosition.SECOND_DEPOT;
                            break;
                        case "THIRD":
                            resourcePosition = ResourcePosition.THIRD_DEPOT;
                            break;
                        default:
                            validSelection = false;
                            System.out.println("Please choose a valid resource position!");
                    }
                } while (!validSelection);
                resources.put(resourcePosition, resourceType);
                chosenResources++;
            }
        }
        return resources;
    }

    public ArrayList<String> chooseInitialLeaders(ArrayList<String> cardsIDs){
        ArrayList<String> chosenLeaderCards = new ArrayList<>();
        synchronized (outSemaphore) {
            int cardsChosen = 0;
            boolean validSelection = true;
            System.out.println("You have to choose 2 leader cards!");
            while (cardsChosen < 2) {
                String chosenCardId;
                validSelection = true;
                do {
                    validSelection = true;
                    System.out.print("Choose leader cards #" + (cardsChosen + 1) + " [type card ID]: \n\t> ");
                    chosenCardId = in.nextLine();
                    if (chosenLeaderCards.contains(chosenCardId)) {
                        validSelection = false;
                        System.out.println( CLI.ANSI_BG_RED + "You cannot choose the same leader card!" + CLI.ANSI_RESET);
                    }
                    else if(!cardsIDs.contains(chosenCardId)){
                        validSelection = false;
                        System.out.println(CLI.ANSI_BG_RED + "You must choose a valid leader card from your deck!" + CLI.ANSI_RESET);
                    }
                } while (!validSelection);
                chosenLeaderCards.add(chosenCardId);
                cardsChosen++;
            }
        }
        return chosenLeaderCards;
    }

    @Override
    public void displayLeaderCardDeck(ClientLeaderCardDeck clientLeaderCardDeck) {
        synchronized (outSemaphore) {
            System.out.println(RepresentationBuilder.render(clientLeaderCardDeck));
        }
    }



    @Override
    public void displayWarehouse(ClientWarehouse warehouse) {
        synchronized (outSemaphore){
            System.out.println(RepresentationBuilder.render(warehouse));
        }
    }

    public void startListening(ClientController controller){
        if(gameStarted == false){
            gameStarted = true;
            new Thread(() -> {
                synchronized (outSemaphore) {
                    System.out.println(ANSI_BG_GREEN + "GAME HAS STARTED!" + ANSI_RESET);
                    System.out.println(ANSI_BG_GREEN + "You can now type commands. Type 'help' for commands list." + ANSI_RESET);
                }
                while (true) {
                    Scanner input = new Scanner(System.in);
                    String requestedCommand = input.nextLine();
                    Pair<String, HashMap<String, String>> formattedCommand = this.getFormatedCommand(requestedCommand);
                    if(formattedCommand != null){
                        this.executeCommand(formattedCommand, controller);
                    }
                }
            }).start();
        }
    }

    public void displayError(String error){
        synchronized (outSemaphore){
            System.out.println(ANSI_BG_RED + error + ANSI_RESET);
        }
    }

    public void displayInfo(String info){
        synchronized (outSemaphore){
            System.out.println(ANSI_BLUE + info + ANSI_RESET);
        }
    }

    @Override
    public void displayMarbleMarket(ClientMarbleMarket market) {
        synchronized (outSemaphore){
            System.out.println(RepresentationBuilder.render(market));
        }
    }

    @Override
    public void displayStrongBox(ClientStrongbox strongbox) {
        synchronized (outSemaphore){
            System.out.println(RepresentationBuilder.render(strongbox));
        }
    }

    @Override
    public void displayFaithPath(ClientFaithPath path) {
        synchronized (outSemaphore){
            System.out.println(RepresentationBuilder.render(path));
        }
    }

    @Override
    public void displayDevelopmentCardDecks(ClientDevelopmentCardDecks deck){
        synchronized (outSemaphore){
            System.out.println(RepresentationBuilder.render(deck));
        }
    }

    @Override
    public void displayOtherPlayersUsername(ArrayList<String> names) {
        synchronized (outSemaphore){
            StringBuilder playersList = new StringBuilder();
            names.stream().forEach(player -> playersList.append(player).append(" | "));
            playersList.delete(playersList.length()-3, playersList.length());
            this.displayInfo("[" + playersList + "]");
        }
    }

    public Pair<String, HashMap<String, String>> getFormatedCommand(String inputCommand){

        if(inputCommand.equalsIgnoreCase("") || inputCommand.equalsIgnoreCase("\n")){
            // The given command is empty
            return null;
        }

        // Split the command string and check if the given command exists
        String commandKey = inputCommand.split(" ")[0]; // First part of the string (command key)
        Command requestedCommand = null;
        if(commands.stream().map(com -> com.getKey()).noneMatch(key -> key.equalsIgnoreCase(commandKey))){
            this.displayError("The given command doesn't exist");
            return null;
        } else{
            // Fetch the proper requested command based on the given input command key
            requestedCommand = commands.stream().filter(com -> com.getKey().equals(commandKey)).collect(Collectors.toCollection(ArrayList::new)).get(0);
        }

        // Split parameters and create hash map
        HashMap<String, String> inputCommandParameters = new HashMap<>();
        int inputParametersNumber = 0;
        inputCommand = inputCommand.replace(inputCommand.split("-")[0] + "-", "");
        String[] stringParameters = inputCommand.split("-");
        try {
            for (String par : stringParameters) {
                String parKey = par.split(" " )[0];
                String parValue = par.split(" " )[1];
                inputCommandParameters.put(parKey, parValue);
            }
        }catch (Exception e){
            displayError("Command parsing error. Check its format again");
        }

        // Check if all parameters coincide with requested parameters
        if(!inputCommandParameters.keySet().equals(requestedCommand.getParameters().keySet())){
            this.displayError("Some parameters do not coincide with requested parameters for command " + requestedCommand.getKey());
            return null;
        }

        return new Pair<String, HashMap<String, String>>(requestedCommand.getKey(), inputCommandParameters);
    }

    public void executeCommand(Pair<String, HashMap<String, String>> inputCommand, ClientController targetController){
        // TODO fill this method with other commands
        switch (inputCommand.getFirst()){
            case "view":
                viewCommandHandler(inputCommand.getSecond(), targetController);
                break;
        }
    }

    private void viewCommandHandler(HashMap<String, String> params, ClientController controller) {
            String targetedPlayer = params.get("p");
            String targetedContent = params.get("v");
            try{
                switch(targetedContent){
                    case "leadercards":
                        controller.viewLeaderCards(targetedPlayer);
                        break;
                    case "developmentcards":
                        controller.viewDevelopmentCards(targetedPlayer);
                        break;
                    case "warehouse":
                        controller.viewWarehouse(targetedPlayer);
                        break;
                    case "strongbox":
                        controller.viewStrongbox(targetedPlayer);
                        break;
                    case "faithpath":
                        controller.viewFaithPath(targetedPlayer);
                        break;
                    default:
                        this.displayError("Input parameters are not correct");
                        this.displayError("Choose between -v [leadercards | developmentcards | warehouse | strongbox | faithpath]");
                }
            }catch (Exception e){
                this.displayError("The targeted player doesn't exists, choose between: ");
                controller.viewOtherPlayersUsername();
            }
    }

}

