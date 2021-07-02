package it.polimi.ingsw.client.view.ui.cli;

import java.util.*;
import java.util.stream.Collectors;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.*;
import it.polimi.ingsw.client.view.representation.RepresentationBuilder;
import it.polimi.ingsw.client.view.ui.*;
import it.polimi.ingsw.network.auth_data.*;
import it.polimi.ingsw.server.model.card.effect.ChangeEffect;
import it.polimi.ingsw.server.model.card.effect.DepotEffect;
import it.polimi.ingsw.server.model.card.effect.DiscountEffect;
import it.polimi.ingsw.server.model.card.effect.ProductionEffect;
import it.polimi.ingsw.server.model.marble.Marble;
import it.polimi.ingsw.server.model.marble.MarbleSelection;
import it.polimi.ingsw.server.model.marble.Orientation;
import it.polimi.ingsw.server.model.resource.ConsumeTarget;
import it.polimi.ingsw.server.model.resource.ResourcePosition;
import it.polimi.ingsw.server.model.resource.ResourceStock;
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


    private static final String TITLE;

    static {
        TITLE = """

                       \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m   \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m      \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m             \s
                       \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m\u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m    \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m             \s
                       \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m   \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m   \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m  \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m╝\u001B[0m    \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m   \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m               \s
                       \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[93m╚\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m╝\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m   \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m   \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m  \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m    \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m   \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m               \s
                       \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m \u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m  \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m   \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m   \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m  \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m    \u001B[93m╚\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m╝\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m                  \s
                       \u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m     \u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m\u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m  \u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m\u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m   \u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m   \u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m\u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m  \u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m     \u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m \u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m                  \s
                                                                                                       \s
                \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m   \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m   \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m
                \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m  \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m  \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m
                \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m╝\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m  \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m     \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m \s
                \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m  \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[93m╚\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[93m╚\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m     \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╔\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m \s
                \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m  \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m \u001B[93m╚\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m  \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m  \u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m \u001B[93m╚\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m║\u001B[0m\u001B[93m╚\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[94m█\u001B[0m\u001B[93m╗\u001B[0m
                \u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m  \u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m\u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m\u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m  \u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m\u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m  \u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m\u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m\u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m\u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m\u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m  \u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m\u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m  \u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m \u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m\u001B[93m╚\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m═\u001B[0m\u001B[93m╝\u001B[0m
                """;
    }

    private final Integer outSemaphore = 0;
    private boolean gameStarted = false;

    private ClientController controller;
    private final ArrayList<Command> commands = new ArrayList<>();
    Scanner in = new Scanner(System.in);


    public CLI() {
        initCommands();
    }

    /**
     * Loads commands available for CLI
     */
    private void initCommands(){
        this.commands.addAll(CLICommandsBuilder.getCommands());
    }

    public void setController(ClientController c){
        this.controller = c;
    }

    /**
     * Start management of lobby creation and joining
     * @return
     */
    @Override
    public AuthData requestAuth(){
        AuthData authData = null;
        synchronized (outSemaphore) {
            String playerUsername;
            String choice;
            System.out.println(TITLE);
            System.out.print("Enter an username:\n\t> ");
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

    /**
     * Display errors info when lobby joining is not successful
     * @param errType type of authentication error
     */
    @Override
    public void displayAuthFail(String errType) {
        synchronized (outSemaphore) {
            System.out.print("Failed to login! ");
            switch (errType) {
                case "full_lobby" -> System.out.println("Full lobby!");
                case "invalid_lobby" -> System.out.println("Invalid lobby!");
                default -> System.out.println("Generic error while connecting to server.");
            }
            System.out.println("Trying again...");
        }
    }

    /**
     * Displays successful lobby joining
     * @param lobbyId successfully joined lobby id
     */
    @Override
    public void displayLobbyJoined(String lobbyId){
        synchronized (outSemaphore) {
            System.out.println("User logged successfully in lobby with ID: " + ANSI_YELLOW + lobbyId + ANSI_RESET);
        }
    }

    /**
     * Displays successful lobby creation
     * @param lobbyId successfully created lobby id
     */
    @Override
    public void displayLobbyCreated(String lobbyId){
        synchronized (outSemaphore) {
            System.out.println("Lobby created succeffully in lobby with ID: " + ANSI_YELLOW + lobbyId + ANSI_RESET);
        }
    }

    /**
     * Display new turn message
     * @param player username of the player in turn
     * @param myTurn true if player corresponds to current playing turn
     */
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

    /**
     * Manage choice of starting resources
     * @param toChoose number of resources to choose
     */
    public void displayChooseInitialResourcesMenu(int toChoose) {
        ConsumeTarget resources = new ConsumeTarget();
        synchronized (outSemaphore) {
            int chosenResources = 0;
            boolean validSelection;
            ResourcePosition resourcePosition = null;
            ResourceType resourceType = null;

            displayInfo("You have to choose " + toChoose + " resource/s!");
            while (chosenResources < toChoose) {
                do {
                    try{
                        displayInfo("Select between: | COIN | SERVANT | SHIELD | STONE |");
                        resourceType = parseResourceType(in.nextLine());
                        if(resourceType.equals(ResourceType.GENERIC) ||
                                resourceType.equals(ResourceType.BLANK) ||
                                resourceType.equals(ResourceType.FAITH)){
                            throw new Exception("Cannot select this resource!");
                        }
                        validSelection = true;
                    } catch (Exception e){
                        validSelection = false;
                        displayError(e.getMessage());
                    }
                } while (!validSelection);
                do {
                    try{
                        displayInfo("Choose in which depot you want to put this resouce | FIRST_DEPOT | SECOND_DEPOT | THIRD_DEPOT |");
                        resourcePosition = parseResourcePosition(in.nextLine());
                        if(resourcePosition.equals(ResourcePosition.DROPPED) ||
                            resourcePosition.equals(ResourcePosition.STRONG_BOX)){
                            throw new Exception("Cannot insert resource in that position!");
                        }
                        validSelection = true;
                    } catch (Exception e){
                        validSelection = false;
                        displayError(e.getMessage());
                    }
                } while (!validSelection);
                try {
                    resources.put(resourcePosition, new ResourceStock(resourceType, 1));
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                chosenResources++;
            }
        }
        controller.chooseInitialResources(resources);
    }

    /**
     * Manage choice of starting leader cards
     * @param cardsIDs IDs of cards to choose from
     */
    public void displayInitialLeadersMenu(ArrayList<String> cardsIDs){
        ArrayList<String> chosenLeaderCards = new ArrayList<>();
        synchronized (outSemaphore) {
            int cardsChosen = 0;
            boolean validSelection = true;
            System.out.println("You have to choose 2 leader cards!");
            while (cardsChosen < 2) {
                String chosenCardId;
                do {
                    validSelection = true;
                    System.out.print("Choose leader cards #" + (cardsChosen + 1) + " [type card ID]: \n\t> ");
                    chosenCardId = in.nextLine();
                    if (chosenLeaderCards.contains(chosenCardId)) {
                        validSelection = false;
                        System.out.println(ANSI_BRIGHT_BG_RED + ANSI_BRIGHT_WHITE + " You cannot choose the same leader card! " + ANSI_RESET);
                    }
                    else if(!cardsIDs.contains(chosenCardId)){
                        validSelection = false;
                        System.out.println(ANSI_BRIGHT_BG_RED + ANSI_BRIGHT_WHITE + " You must choose a valid leader card from your deck! " + ANSI_RESET);
                    }
                } while (!validSelection);
                chosenLeaderCards.add(chosenCardId);
                cardsChosen++;
            }
        }
        controller.chooseInitialLeaders(chosenLeaderCards);
    }

    /**
     *
     * @param clientLeaderCardDeck
     * @param username name of the deck's owner
     */
    @Override
    public void displayLeaderCardDeck(ClientLeaderCardDeck clientLeaderCardDeck, String username) {
        synchronized (outSemaphore) {
            System.out.println(RepresentationBuilder.render(clientLeaderCardDeck));
        }
    }


    /**
     *
     * @param warehouse warehouse to display
     * @param username name of the warehouse's owner
     */
    @Override
    public void displayWarehouse(ClientWarehouse warehouse, String username) {
        synchronized (outSemaphore){
            System.out.println(RepresentationBuilder.render(warehouse));
        }
    }

    /**
     * Start listen to user commands and executing them
     */
    public void startListening(){
        if(!gameStarted){
            gameStarted = true;
            new Thread(() -> {
                synchronized (outSemaphore) {
                    System.out.println(ANSI_BRIGHT_BG_GREEN + ANSI_BRIGHT_WHITE + " GAME HAS STARTED! " + ANSI_RESET);
                    System.out.println(ANSI_BRIGHT_BG_GREEN + ANSI_BRIGHT_WHITE + " You can now type commands. Type 'help' for commands list. " + ANSI_RESET);
                }
                while (true) {
                    String requestedCommand;
                        requestedCommand = in.nextLine();
                        Pair<String, HashMap<String, String>> formattedCommand = this.getFormattedCommand(requestedCommand);
                        if (formattedCommand != null) {
                            this.executeCommand(formattedCommand);
                        }
                }
            }).start();
        }
    }

    /**
     *
     * @param error error to display
     */
    public void displayError(String error){
        synchronized (outSemaphore){
            System.out.println(ANSI_BG_RED + error + ANSI_RESET);
        }
    }

    /**
     *
     * @param info info to display
     */
    public void displayInfo(String info){
        synchronized (outSemaphore){
            System.out.println(ANSI_BLUE + info + ANSI_RESET);
        }
    }

    /**
     *
     * @param market marble market to display
     */
    @Override
    public void displayMarbleMarket(ClientMarbleMarket market) {
        synchronized (outSemaphore){
            System.out.println(RepresentationBuilder.render(market));
        }
    }

    /**
     *
     * @param strongbox strongbox to display
     * @param username name of the strongbox's owner
     */
    @Override
    public void displayStrongBox(ClientStrongbox strongbox, String username) {
        synchronized (outSemaphore){
            System.out.println(RepresentationBuilder.render(strongbox));
        }
    }

    /**
     *
     * @param path faith path to display
     * @param username name of the faith path's owner
     */
    @Override
    public void displayFaithPath(ClientFaithPath path, String username) {
        synchronized (outSemaphore){
            System.out.println(RepresentationBuilder.render(path));
        }
    }

    /**
     *
     * @param deck player development cards deck to display
     * @param username name of the decks' owner
     */
    @Override
    public void displayDevelopmentCardDecks(ClientDevelopmentCardDecks deck, String username){
        synchronized (outSemaphore){
            System.out.println(RepresentationBuilder.render(deck));
        }
    }

    /**
     *
     * @param names of other players
     */
    @Override
    public void displayOtherPlayersUsername(ArrayList<String> names) {
        synchronized (outSemaphore){
            StringBuilder playersList = new StringBuilder();
            names.forEach(player -> playersList.append(player).append(" | "));
            playersList.delete(playersList.length()-3, playersList.length());
            this.displayInfo("[" + playersList + "]");
        }
    }

    /**
     *
     * @param names list of usernames
     * @param current username of the player in turn
     */
    @Override
    public void displayTurnInfo(ArrayList<String> names, String current){
        synchronized (outSemaphore){
            StringBuilder playersList = new StringBuilder();
            names.forEach(player -> {
                if(names.get(names.size()-1).equals(player)){
                    playersList.append(player);
                } else {
                    playersList.append(player).append(" -> ");
                }
            });
            this.displayInfo("[" + playersList + "]");
            this.displayInfo("Current player is: " + current);
        }
    }

    /**
     *
     * @param market development cards market to display
     */
    @Override
    public void displayCardMarket(ClientCardsMarket market) {
            synchronized (outSemaphore){
                System.out.println(RepresentationBuilder.render(market));
            }
    }

    /**
     *
     * @param isMyTurn flag used to filter possible actions
     * @param isMainActionDone flag used to filter possible actions
     */
    @Override
    public void displayPossibleActions(boolean isMyTurn, boolean isMainActionDone){
        if(!isMyTurn){
            displayInfo("Wait for your turn to make actions.");
        } else {
            displayInfo("Possible actions:");
            String actions = isMainActionDone ? "activate | drop | remove" : "activate | drop | produce | buy | pick";
            displayInfo(actions);
        }
    }

    /**
     *
     * @param inputCommand
     * @return Command content split
     */
    public Pair<String, HashMap<String, String>> getFormattedCommand(String inputCommand){

        if(inputCommand.equalsIgnoreCase("") || inputCommand.equalsIgnoreCase("\n")){
            // The given command is empty
            return null;
        }

        // Split the command string and check if the given command exists
        String commandKey = inputCommand.split(" ")[0]; // First part of the string (command key)
        Command requestedCommand = null;
        if(commands.stream().map(Command::getKey).noneMatch(key -> key.equalsIgnoreCase(commandKey))){
            this.displayError("The given command doesn't exist");
            return null;
        } else{
            // Fetch the proper requested command based on the given input command key
            requestedCommand = commands.stream().filter(com -> com.getKey().equals(commandKey)).collect(Collectors.toCollection(ArrayList::new)).get(0);
        }

        // Split parameters and create hash map
        HashMap<String, String> inputCommandParameters = new HashMap<>();
        inputCommand = inputCommand.replace(inputCommand.split("-")[0] + "-", "");
        if(!inputCommand.equals(requestedCommand.getKey())) {
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
        }


        // Check if all parameters coincide with requested parameters
        if(!inputCommandParameters.keySet().equals(requestedCommand.getParameters().keySet())){
            this.displayError("Some parameters do not coincide with requested parameters for command " + requestedCommand.getKey());
            return null;
        }

        return new Pair<>(requestedCommand.getKey(), inputCommandParameters);
    }

    /**
     * Execute handler of command written
     * @param inputCommand
     */
    public void executeCommand(Pair<String, HashMap<String, String>> inputCommand){
        switch (inputCommand.getFirst()) {
            case "view" -> viewCommandHandler(inputCommand.getSecond());
            case "turn" -> turnCommandHandler();
            case "help" -> helpCommandHandler();
            case "actions" -> actionsCommandHandler();
            case "activate" -> activateCommandHandler(inputCommand.getSecond());
            case "drop" -> dropCommandHandler(inputCommand.getSecond());
            case "marblemarket" -> marbleMarketCommandHandler();
            case "cardmarket" -> cardMarketCommandHandler();
            case "endturn" -> endTurnCommandHandler();
            case "swap" -> swapCommandHandler(inputCommand.getSecond());
            case "buy" -> buyCommandHandler(inputCommand.getSecond());
            case "pick" -> pickCommandHandler(inputCommand.getSecond());
            case "produce" -> controller.produceCommandHandler();
            case "remove" -> removeResourceCommandHandler(inputCommand.getSecond());
        }
    }

    /**
     * Handler of remove resource command
     * @param params command params
     */
    private void removeResourceCommandHandler(HashMap<String, String> params) {
        try{

            ResourcePosition selected = parseResourcePosition(params.get("d"));
            ArrayList<ResourcePosition> validPositions = new ArrayList<>();
            validPositions.add(ResourcePosition.FIRST_DEPOT);
            validPositions.add(ResourcePosition.SECOND_DEPOT);
            validPositions.add(ResourcePosition.THIRD_DEPOT);
            if(!validPositions.contains(selected)) throw new Exception("Cannot drop resource from that position!");
            controller.removeResource(selected);
        } catch (Exception e){
            displayError(e.getMessage());
        }



    }

    /**
     * Handler of view command
     * @param params commands params
     */
    private void viewCommandHandler(HashMap<String, String> params) {
            String targetedPlayer = params.get("p");
            String targetedContent = params.get("t");
            try{
                switch (targetedContent) {
                    case "leadercards" -> controller.viewLeaderCards(targetedPlayer);
                    case "developmentcards" -> controller.viewDevelopmentCards(targetedPlayer);
                    case "warehouse" -> controller.viewWarehouse(targetedPlayer);
                    case "strongbox" -> controller.viewStrongbox(targetedPlayer);
                    case "faithpath" -> controller.viewFaithPath(targetedPlayer);
                    default -> {
                        this.displayError("Input parameters are not correct");
                        this.displayError("Choose between -v [leadercards | developmentcards | warehouse | strongbox | faithpath]");
                    }
                }
            }catch (Exception e){
                this.displayError("The targeted player doesn't exists, choose between: ");
                controller.viewOtherPlayersUsername();
            }
    }

    /**
     * Display help message
     */
    @Override
    public void displayHelpMessage(){
        commands.forEach(command->{
            displayInfo("Command Key: " + command.getKey());
            displayInfo("Description: " + command.getDescription());
            displayInfo("Only available for current turn player: " + command.isOnlyForCurrent());
            for (String param : command.getParameters().keySet()) {
                displayInfo("Param -"+param + ": " + command.getParameters().get(param));
            }
            displayInfo("#############");
        });
    }

    /**
     * Handler of help command
     */
    private void helpCommandHandler(){
        controller.viewHelpMessage();
    }

    /**
     * Handler of turn command
     */
    private void turnCommandHandler(){
        controller.viewTurnInfo();
    }

    /**
     * Handler of actions command
     */
    private void actionsCommandHandler(){
        controller.viewPossibleActions();
    }

    /**
     * Handler of activate leader card command
     * @param params command params
     */
    private void activateCommandHandler(HashMap<String, String> params){
        String cardId = params.get("c");
        controller.activateLeaderCard(cardId);
    }

    /**
     * Handler of drop leader card command
     * @param params command params
     */
    private void dropCommandHandler(HashMap<String, String> params){
        String cardId = params.get("c");
        controller.dropLeaderCard(cardId);
    }

    /**
     * Handler of marble market command
     */
    private void marbleMarketCommandHandler(){
        controller.viewMarbleMarket();
    }

    /**
     * Handler of card market command
     */
    private void cardMarketCommandHandler(){
        controller.viewCardMarket();
    }

    /**
     * Handler of end turn command
     */
    private void endTurnCommandHandler(){
        controller.endTurn();
    }

    /**
     * Handler of swap command handler
     * @param params
     */
    private void swapCommandHandler(HashMap<String, String> params){
        try{
            int first = ResourcePosition.valueOf(params.get("a")).ordinal();
            int second = ResourcePosition.valueOf(params.get("b")).ordinal();
            controller.swapDepots(first, second);
        } catch (Exception e){
            displayError("Cannot parse depot position.");
        }

    }

    /**
     * Handler of buy development card command
     * @param params command params
     */
    private void buyCommandHandler(HashMap<String, String> params){
        String cardId = params.get("c");
        controller.buyCommandHandler(cardId);
    }

    /**
     * Handler of pick resources command
     * @param params
     */
    private void pickCommandHandler(HashMap<String, String> params){
        MarbleSelection selection;
        try{
            Orientation orientation = Orientation.valueOf(params.get("o"));
            int index = Integer.parseInt(params.get("i"))-1;
            selection = new MarbleSelection(orientation, index);
            controller.pickCommandHandler(selection);
        } catch (Exception e){
            displayError("Error while parsing params, try again.");
        }
    }

    /**
     * Manage pick resource command data retrieval
     * @param selection selected marble market row / column orientation
     * @param selected selected marbles from the market
     * @param changeEffectsCards
     * @param depotEffectsCards
     */
    public void displayPickResourceMenu(MarbleSelection selection, ArrayList<Marble> selected, ArrayList<ClientLeaderCard> changeEffectsCards, ArrayList<ClientLeaderCard> depotEffectsCards){
        ArrayList<ChangeEffect> changeEffects = changeEffectsCards.stream().map(card -> (ChangeEffect) card.getEffect()).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<DepotEffect> depotEffects = depotEffectsCards.stream().map(card -> (DepotEffect) card.getEffect()).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<ResourcePosition> positions = new ArrayList<>();
            ArrayList<ResourceType> conversions = new ArrayList<>();
            try{
                for(Marble marble : selected){
                    if(marble.getResourceType().equals(ResourceType.FAITH)) continue;
                    if(marble.getResourceType().equals(ResourceType.BLANK) && !changeEffects.isEmpty() ){
                        conversions.add(askForConversions(changeEffects));
                    }
                    if(!marble.getResourceType().equals(ResourceType.BLANK) ||
                        marble.getResourceType().equals(ResourceType.BLANK) && !changeEffects.isEmpty()){
                        displayInfo("Insert position in which you want to add " + marble.getResourceType().toString());
                        displayPossibleResourcePositions(depotEffects, false);
                        String positionRaw = in.nextLine();
                        positions.add(parseResourcePosition(positionRaw));
                    }
                }
                controller.pickResources(selection, positions, conversions);
            } catch(Exception e){
                displayError(e.getMessage());
            }
    }

    /**
     * Display possible position for resources
     * @param additionalDepots Depots from leader cards effects
     * @param strongbox Flag to insert strongbox in possible positions
     */
    private void displayPossibleResourcePositions(ArrayList<DepotEffect> additionalDepots, boolean strongbox){
        String message = "Select between: | FIRST_DEPOT | SECOND_DEPOT | THIRD_DEPOT | DROPPED |";
        if(strongbox) message += " STRONGBOX |";
        if(additionalDepots.size()==1) message += " FIRST_LEADER_DEPOT";
        if(additionalDepots.size()==2) message += " FIRST_LEADER_DEPOT | SECOND_LEADER_DEPOT ";
        displayInfo(message);
    }

    /**
     * Manage choice of marble conversion in case of two active leader cards with change effect
     * @param changeEffects
     * @return the resource selected
     * @throws Exception
     */
    private ResourceType askForConversions(ArrayList<ChangeEffect> changeEffects) throws Exception {
        switch (changeEffects.size()){
            case 1:
                return changeEffects.get(0).getResourceType();
            case 2:
                displayInfo("Select type to convert blank marble into, available types: ");
                for(ChangeEffect effect : changeEffects) displayInfo(effect.getResourceType().toString());
                String choiceRaw = in.nextLine();
                return parseResourceType(choiceRaw);
        }
        throw new Exception("Invalid size of change effects.");
    }

    /**
     * Manages buy development cards command data retrieval
     * @param id Card id that player wants to buy
     * @param discountsCards Leader cards with discount effect
     * @param depotEffectsCards Leader cards with depot effect
     */
    public void displayBuyDevelopmentCardMenu(String id, ArrayList<ClientLeaderCard> discountsCards, ArrayList<ClientLeaderCard> depotEffectsCards){
        ArrayList<DepotEffect> depotEffects = depotEffectsCards.stream().map(card -> (DepotEffect) card.getEffect()).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<DiscountEffect> discounts = discountsCards.stream().map(card -> (DiscountEffect) card.getEffect()).collect(Collectors.toCollection(ArrayList::new));
        try{
            int index = askDevelopmentDeckIndex();
            displayUserDiscounts(discounts);
            Optional<Integer> specificQuantity = Optional.empty();
            ConsumeTarget resourcesSelected = askResourcesToUse(specificQuantity, depotEffects);
            controller.buyDevelopmentCard(id, index, resourcesSelected);
        } catch(Exception e){
            displayError(e.getMessage());
        }
    }

    /**
     * Display info's about player available discounts
     * @param discounts available discounts effects
     */
    private void displayUserDiscounts(ArrayList<DiscountEffect> discounts){
        if(!discounts.isEmpty()){
            displayInfo("When choosing resources, consider having the following discounts:");
            for(DiscountEffect discount : discounts){
                displayInfo(discount.toString());
            }
        }
    }

    /**
     * Manage the retrieval of development deck index
     * @return
     * @throws Exception
     */
    private int askDevelopmentDeckIndex() throws Exception {
        displayInfo("Insert deck in which to add card. | 0 | 1 | 2 | ");
        String deckIndex = in.nextLine();
        try{
            return Integer.parseInt(deckIndex);
        } catch (Exception e){
            throw new Exception("Error parsing deck index, try again.");
        }
    }

    /**
     * Manage the retrieval of information for produce command
     * @param productionEffectsCards Leader cards with production effect
     * @param depotEffectsCard Leader cards with depot effect
     */
    public void displayProduceMenu(ArrayList<ClientLeaderCard> productionEffectsCards, ArrayList<ClientLeaderCard> depotEffectsCard){
        ArrayList<ProductionEffect> productionEffects = productionEffectsCards.stream().map(card -> (ProductionEffect) card.getEffect()).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<DepotEffect> depotEffects = depotEffectsCard.stream().map(card -> (DepotEffect) card.getEffect()).collect(Collectors.toCollection(ArrayList::new));

        try{
            ConsumeTarget consumed = new ConsumeTarget();
            Optional<ProductionEffect> genericProduction;
            ArrayList<ProductionEffect> leaderProductions;
            ArrayList<String> cardIds;
            genericProduction = askForGenericProduction(consumed, depotEffects);
            leaderProductions = askForLeaderProduction(productionEffects);
            cardIds = askForListOfIds();
            if(cardIds.size()!=0){
                ConsumeTarget toConsume = askResourcesToUse(Optional.empty(), depotEffects);
                consumed.putAll(toConsume);
            }
            controller.produceResources(consumed, cardIds, genericProduction, leaderProductions);
        } catch (Exception e){
            displayError(e.getMessage());
        }

    }

    /**
     * Empty method for CLI
     * @param game the client game
     */
    @Override
    public void startUI(ClientGame game) {

    }

    /**
     * Empty method for CLI
     * @param playerBoard user's playerboard
     */
    @Override
    public void displayUserStats(ClientPlayerBoard playerBoard) {

    }

    /**
     * Manage the retrieve of string IDs
     * @return List of chosen IDs
     * @throws Exception
     */
    private ArrayList<String> askForListOfIds() throws Exception {
        try{
            displayInfo("Insert IDs of card to use, separated by space: (or ok to continue)");
            String productionsRaw = in.nextLine();
            if(productionsRaw.equals("ok")) return new ArrayList<>();
            return new ArrayList(Arrays.asList(productionsRaw.split(" ")));
        } catch (Exception e){
            e.printStackTrace();
            throw new Exception("Cannot parse ids, try again.");
        }
    }

    /**
     * Manage the retrieval of production effects selected from leader effects
     * @param leadersEffect Production effects available
     * @return List of production selected
     */
    private ArrayList<ProductionEffect> askForLeaderProduction(ArrayList<ProductionEffect> leadersEffect){
        ArrayList<ProductionEffect> productionsSelected = new ArrayList<>();
        for(ProductionEffect effect : leadersEffect){
            displayInfo("This leader production effect is available:");
            displayInfo(effect.toString());
            displayInfo("If you want to use it, type the resource type you want to produce: [press enter to continue]");
            try{
                ResourceType selectedType = ResourceType.valueOf(in.nextLine());
                productionsSelected.add(
                        new ProductionEffect(effect.getInStocks(), effect.getOutStockConverted(selectedType)));

            } catch (Exception ignored){
            }

        }
        return productionsSelected;
    }

    /**
     * Manages the retrieval of data to manage the generic production
     * @param consumed Resources to consume into which will be added selected resources
     * @param depotEffects Depot leader effects available
     * @return Generic production effect, Optional.empty if not selected
     * @throws Exception
     */
    private Optional<ProductionEffect> askForGenericProduction(ConsumeTarget consumed, ArrayList<DepotEffect> depotEffects) throws Exception {
        displayInfo("If you want to use generic production, type the resource type to produce, else 'next':" );
            String selectedRaw = in.nextLine();
            if(!selectedRaw.equals("next")){
                ResourceType resourceFromGenericProduction = parseResourceType(selectedRaw);
                ConsumeTarget toConsumeForGeneric = askResourcesToUse(Optional.of(2), depotEffects);
                consumed.putAll(toConsumeForGeneric);
                return Optional.of(new ProductionEffect(
                        new ArrayList<>(toConsumeForGeneric.getStocks()),
                        new ArrayList<>(Collections.singletonList(new ResourceStock(resourceFromGenericProduction, 1)))));
            }
            return Optional.empty();
    }

    /**
     * Manage possible errors from parsing String -> ResourcePosition
     * @param raw String representing position selected
     * @return Resource position parsed
     * @throws Exception
     */
    private ResourcePosition parseResourcePosition(String raw) throws Exception {
        try{
            return ResourcePosition.valueOf(raw);
        } catch (Exception e){
            throw new Exception("Cannot parse resource type.");
        }
    }

    /**
     * Manage possible errors from parsing String -> ResourceType
     * @param raw String representing resource type selected
     * @return Resource type parsed
     * @throws Exception
     */
    private ResourceType parseResourceType(String raw) throws Exception {
        try{
            return ResourceType.valueOf(raw);
        } catch (Exception e){
            throw new Exception("Cannot parse resource type.");
        }
    }

    /**
     * Manages the retrieval of information needed to consume resources for actions
     * @param needed Quantity of resources to choose
     * @param depotEffects Depot effects available
     * @return Object containing information needed to consume correct resources
     * @throws Exception
     */
    private ConsumeTarget askResourcesToUse(Optional<Integer> needed, ArrayList<DepotEffect> depotEffects) throws Exception {
        ConsumeTarget resourcesSelected = new ConsumeTarget();
        String confirmString = "add";
        if(needed.isPresent()) displayInfo("You have to choose " + needed.get() + " resources.");
        while(!confirmString.equals("ok") && (needed.isEmpty() || (needed.get() != resourcesSelected.countResources()))){
            displayInfo("Add resource stock: <position> <quantity> <type> ('quit' to choose another action)");
            displayPossibleResourcePositions(depotEffects, true);
            String rawInput = in.nextLine();
            if(rawInput.equals("quit")) throw new Exception("User stopped action.");
            String[] resourceSelectionRaw = rawInput.split(" ");
            try{
                ResourcePosition positionSelected = ResourcePosition.valueOf(resourceSelectionRaw[0]);
                int quantitySelected = Integer.parseInt(resourceSelectionRaw[1]);
                ResourceType typeSelected = ResourceType.valueOf(resourceSelectionRaw[2]);
                ResourceStock stockSelected = new ResourceStock(typeSelected, quantitySelected);
                resourcesSelected.put(positionSelected, stockSelected);
                if(needed.isPresent()){
                    if(resourcesSelected.countResources() == needed.get()) return resourcesSelected;
                    if(resourcesSelected.countResources() > needed.get()) throw new Exception("Resources selected exceed needed!");
                }
                displayInfo("Stock added correctly, press enter to add another stock, 'ok' to continue.");
            } catch(Exception e ){
                throw new Exception(e.getMessage());
            }
            confirmString = in.nextLine();
        }
        return resourcesSelected;
    }

    /**
     * Display game is ended message
     */
    public void showGameIsEnded(){
        displayInfo("Game is ended, press any key to close.");
        String wait = in.nextLine();
        System.exit(0);
    }

}

