package it.polimi.ingsw.client.view.representation;

import it.polimi.ingsw.client.model.*;
import it.polimi.ingsw.client.view.ui.cli.CLI;
import it.polimi.ingsw.server.model.resource.ResourceDepot;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class RepresentationBuilder {

    private static final String LEADER_CARD_FORMAT = "ID: %1s | %2s VP | %3s | " + whiteText(boldText("Requirement:")) + " %4s | " + whiteText(boldText("Effect:")) + " %5s";
    private static final String DEVELOPMENT_CARD_FORMAT = "ID: %s | Color: %s | Lv %d | %d VP | " + whiteText(boldText("Cost:")) + " %s | " + whiteText(boldText("Production:")) + " %s";
    private static final String LEADER_CARD_DECK_FORMAT = "Leader cards:\n%s";
    private static final String DEVELOPMENT_CARD_DECK_FORMAT = "Development cards:\nSlot #1\n%s\nSlot #2\n%s\nSlot #3\n%s";
    private static final String CARD_MARKET_FORMAT = "> GREEN:\nLv1) %s\nLv2) %s\nLv3) %s\n> YELLOW:\nLv1) %s\nLv2) %s\nLv3) %s\n> PURPLE:\nLv1) %s\nLv2) %s\nLv3) %s\n> BLUE:\nLv1) %s\nLv2) %s\nLv3) %s";
    private static final String MARBLE_MARKET_FORMAT = "  ||     1     |     2     |     3     |     4     |\n==||===========|===========|===========|===========|\n1 || %1$9s | %2$9s | %3$9s | %4$9S |\n2 || %5$9s | %6$9s | %7$9s | %8$9S |\n3 || %9$9s | %10$9s | %11$9s | %12$9S |\n\nNOT FOR SALE > %13$s";
    private static final String FAITH_PATH_FORMAT =  "Faith Points: %d\n[%c][%c][%c][%c]<[%c][%c][%c]{%c}>[%c][%c][%c]<[%c][%c][%c][%c]{%c}>[%c][%c]<[%c][%c][%c][%c][%c]{%c}>\nPope favors: [%c][%c][%c]";
    private static final String WAREHOUSE_FORMAT = "Warehouse: \n[%1$9s]\n[%2$9s][%3$9s]\n[%4$9s][%5$9s][%6$9s]\n";
    private static final String STRONGBOX_FORMAT = "Strongbox: \n| COIN      | SERVANT   | SHIELD    | STONE     |\n| %1$9d | %2$9d | %3$9d | %4$9d |";

    public static String render(ClientDevelopmentCard card) {
        String requirementString = card.getRequirement().toString();
        String effectString = card.getEffect().toString();
        return String.format(DEVELOPMENT_CARD_FORMAT,
                card.getId(),
                card.getColor().toString(),
                card.getLevel(),
                card.getVictoryPoints(),
                requirementString,
                effectString);
    }

    public static String render(ClientLeaderCard card, boolean isMine) {
        String requirementString = card.getRequirement().toString();
        String effectString = card.getEffect().toString();
        return String.format(LEADER_CARD_FORMAT,
                !card.isActive() && !isMine ? hiddenText(" XXX ") : cyanText(underlineText(card.getId())),
                !card.isActive() && !isMine ?  hiddenText(" XXX ") : Integer.toString(card.getVictoryPoints()),
                card.isActive() ? positiveText("  ACTIVE  ") : negativeText(" INACTIVE "),
                !card.isActive() && !isMine ? hiddenText(" XXX ") : requirementString,
                !card.isActive() && !isMine ? hiddenText(" XXX ") : effectString
        );
    }

    public static String render(ClientLeaderCardDeck deck) {
        StringBuilder tempString = new StringBuilder();
        ArrayList<String> renderCards = deck.getClientLeaderCards()
                .stream()
                .map(clientLeaderCard -> RepresentationBuilder.render(clientLeaderCard, deck.isMine()))
                .collect(Collectors.toCollection(ArrayList::new));
        for (String renderCard : renderCards) {
            tempString.append(renderCard).append("\n");
        }
        tempString.delete(tempString.length()-1, tempString.length());
        return String.format(LEADER_CARD_DECK_FORMAT, tempString.toString());
    }

    public static String render(ClientDevelopmentCardDecks decks) {

        if(!decks.isInitialized()){
            return "The player's development cards decks are empty";
        }

        ArrayList<String> tempStrings = new ArrayList<>();
        for (ArrayList<ClientDevelopmentCard> deck: decks.getDevelopmentCards()) {
            StringBuilder tempString = new StringBuilder();
            ArrayList<String> renderCards = deck
                    .stream()
                    .map(RepresentationBuilder::render)
                    .collect(Collectors.toCollection(ArrayList::new));
            for (String renderCard : renderCards) {
                tempString.append(renderCard).append("\n");
            }
            tempStrings.add(tempString.toString());
        }
        return String.format(DEVELOPMENT_CARD_DECK_FORMAT, tempStrings.get(0), tempStrings.get(1), tempStrings.get(2));
    }

    public static String render(ClientMarbleMarket market) {
        return String.format(MARBLE_MARKET_FORMAT,
                market.getOnSale()[0][0].getResourceType().toString(),
                market.getOnSale()[0][1].getResourceType().toString(),
                market.getOnSale()[0][2].getResourceType().toString(),
                market.getOnSale()[0][3].getResourceType().toString(),
                market.getOnSale()[1][0].getResourceType().toString(),
                market.getOnSale()[1][1].getResourceType().toString(),
                market.getOnSale()[1][2].getResourceType().toString(),
                market.getOnSale()[1][3].getResourceType().toString(),
                market.getOnSale()[2][0].getResourceType().toString(),
                market.getOnSale()[2][1].getResourceType().toString(),
                market.getOnSale()[2][2].getResourceType().toString(),
                market.getOnSale()[2][3].getResourceType().toString(),
                market.getNotForSale().getResourceType().toString());
    }

    public static String render(ClientCardsMarket market) {
        return String.format(CARD_MARKET_FORMAT,
                RepresentationBuilder.render(market.getDecks().get(0).get(0).get(0)),
                RepresentationBuilder.render(market.getDecks().get(0).get(1).get(0)),
                RepresentationBuilder.render(market.getDecks().get(0).get(2).get(0)),
                RepresentationBuilder.render(market.getDecks().get(1).get(0).get(0)),
                RepresentationBuilder.render(market.getDecks().get(1).get(1).get(0)),
                RepresentationBuilder.render(market.getDecks().get(1).get(2).get(0)),
                RepresentationBuilder.render(market.getDecks().get(2).get(0).get(0)),
                RepresentationBuilder.render(market.getDecks().get(2).get(1).get(0)),
                RepresentationBuilder.render(market.getDecks().get(2).get(2).get(0)),
                RepresentationBuilder.render(market.getDecks().get(3).get(0).get(0)),
                RepresentationBuilder.render(market.getDecks().get(3).get(1).get(0)),
                RepresentationBuilder.render(market.getDecks().get(3).get(2).get(0)));
    }

    public static String render(ClientFaithPath faithPath) {
        return String.format(FAITH_PATH_FORMAT,
                faithPath.getFaithPoints(),
                faithPath.getFaithPoints() >= 1 ? 'X' : ' ',
                faithPath.getFaithPoints() >= 2 ? 'X' : ' ',
                faithPath.getFaithPoints() >= 3 ? 'X' : ' ',
                faithPath.getFaithPoints() >= 4 ? 'X' : ' ',
                faithPath.getFaithPoints() >= 5 ? 'X' : ' ',
                faithPath.getFaithPoints() >= 6 ? 'X' : ' ',
                faithPath.getFaithPoints() >= 7 ? 'X' : ' ',
                faithPath.getFaithPoints() >= 8 ? 'X' : ' ',
                faithPath.getFaithPoints() >= 9 ? 'X' : ' ',
                faithPath.getFaithPoints() >= 10 ? 'X' : ' ',
                faithPath.getFaithPoints() >= 11 ? 'X' : ' ',
                faithPath.getFaithPoints() >= 12 ? 'X' : ' ',
                faithPath.getFaithPoints() >= 13 ? 'X' : ' ',
                faithPath.getFaithPoints() >= 14 ? 'X' : ' ',
                faithPath.getFaithPoints() >= 15 ? 'X' : ' ',
                faithPath.getFaithPoints() >= 16 ? 'X' : ' ',
                faithPath.getFaithPoints() >= 17 ? 'X' : ' ',
                faithPath.getFaithPoints() >= 18 ? 'X' : ' ',
                faithPath.getFaithPoints() >= 19 ? 'X' : ' ',
                faithPath.getFaithPoints() >= 20 ? 'X' : ' ',
                faithPath.getFaithPoints() >= 21 ? 'X' : ' ',
                faithPath.getFaithPoints() >= 22 ? 'X' : ' ',
                faithPath.getFaithPoints() >= 23 ? 'X' : ' ',
                faithPath.getFaithPoints() >= 24 ? 'X' : ' ',
                faithPath.getPopeFavor(0) ? '+' : ' ',
                faithPath.getPopeFavor(1) ? '+' : ' ',
                faithPath.getPopeFavor(2) ? '+' : ' ');
    }

    public static String render(ClientWarehouse warehouse) {
        // FIXME fix return output when warehouse is not initialized
        /*
            Pizza, ho creato questa funzione isWarehouseInitialized giusto per non far crashare il client
            Prova a sistemare il costruttore per inizializzare il warehouse ma senza le risorse al suo interno
            E in seguito togli pure lo schifo che ho fatto ahahahah
         */
        if(warehouse.isInitialized()) {
            ResourceDepot depot1 = warehouse.getResourceDepot(0);
            ResourceDepot depot2 = warehouse.getResourceDepot(1);
            ResourceDepot depot3 = warehouse.getResourceDepot(2);
            return String.format(WAREHOUSE_FORMAT,
                    depot1.getQuantity() == 1 ? depot1.getResourceType().toString() : " ",
                    depot2.getQuantity() >= 1 ? depot2.getResourceType().toString() : " ",
                    depot2.getQuantity() == 2 ? depot2.getResourceType().toString() : " ",
                    depot3.getQuantity() >= 1 ? depot3.getResourceType().toString() : " ",
                    depot3.getQuantity() >= 2 ? depot3.getResourceType().toString() : " ",
                    depot3.getQuantity() == 3 ? depot3.getResourceType().toString() : " "
            );
        }
        return String.format(WAREHOUSE_FORMAT,
                " ",
                " ",
                " ",
                " ",
                " ",
                " "
        );
    }
    public static String render(ClientStrongbox strongbox) {
        if(!strongbox.isInitialized()){
            return String.format(STRONGBOX_FORMAT, 0, 0, 0, 0);
        }
        int coinCount = strongbox.gerResourceStock(0).getQuantity();
        int servantCount = strongbox.gerResourceStock(1).getQuantity();
        int shieldCount = strongbox.gerResourceStock(2).getQuantity();
        int stoneCount = strongbox.gerResourceStock(3).getQuantity();
        return String.format(STRONGBOX_FORMAT, coinCount, servantCount, shieldCount, stoneCount);
    }

    public static String getAsset() {
        // TODO complete
        return null;
    }

    private static String hiddenText(String text) {
        return CLI.ANSI_BRIGHT_BG_WHITE + CLI.ANSI_BRIGHT_RED + text + CLI.ANSI_RESET;
    }

    private static String negativeText(String text) {
        return CLI.ANSI_BRIGHT_BG_RED + CLI.ANSI_BRIGHT_WHITE + text + CLI.ANSI_RESET;
    }

    private static String positiveText(String text) {
        return CLI.ANSI_BRIGHT_BG_GREEN + CLI.ANSI_BRIGHT_WHITE + text + CLI.ANSI_RESET;
    }

    private static String redText(String text) {
        return CLI.ANSI_BRIGHT_RED + text + CLI.ANSI_RESET;
    }

    private static String greenText(String text) {
        return CLI.ANSI_BRIGHT_GREEN + text + CLI.ANSI_RESET;
    }

    private static String blueText(String text) {
        return CLI.ANSI_BRIGHT_BLUE + text + CLI.ANSI_RESET;
    }

    private static String purpleText(String text) {
        return CLI.ANSI_BRIGHT_PURPLE + text + CLI.ANSI_RESET;
    }

    private static String yellowText(String text) {
        return CLI.ANSI_BRIGHT_YELLOW + text + CLI.ANSI_RESET;
    }

    private static String cyanText(String text) {
        return CLI.ANSI_BRIGHT_CYAN + text + CLI.ANSI_RESET;
    }

    private static String whiteText(String text) {
        return CLI.ANSI_BRIGHT_WHITE + text + CLI.ANSI_RESET;
    }

    private static String underlineText(String text) {
        return CLI.ANSI_UNDERLINE + text + CLI.ANSI_RESET;
    }

    private static String boldText(String text) {
        return CLI.ANSI_BOLD + text + CLI.ANSI_RESET;
    }

    private static String reverseText(String text) {
        return CLI.ANSI_REVERSED + text + CLI.ANSI_RESET;
    }
}
