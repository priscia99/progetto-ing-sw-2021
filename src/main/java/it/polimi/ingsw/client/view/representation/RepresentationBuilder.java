package it.polimi.ingsw.client.view.representation;

import it.polimi.ingsw.client.model.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

// FIXME fix toString methods

public class RepresentationBuilder {

    private static final String LEADER_CARD_FORMAT = "%d VP | %s | Requirement: %s | Effect: %s";
    private static final String DEVELOPMENT_CARD_FORMAT = "Color: %s | Lv %d | %d VP | Cost: %s | Production: %s";
    private static final String LEADER_CARD_DECK_FORMAT = "Leader cards:\n%s";
    private static final String DEVELOPMENT_CARD_DECK_FORMAT = "Development cards:\nSlot #1\n%s\nSlot #2\n%s\nSlot #3\n%s";
    private static final String CARD_MARKET_FORMAT = "> GREEN:\nLv1) %s\nLv2) %s\nLv3) %s\n> YELLOW:\nLv1) %s\nLv2) %s\nLv3) %s\n> PURPLE:\nLv1) %s\nLv2) %s\nLv3) %s\n> BLUE:\nLv1) %s\nLv2) %s\nLv3) %s";
    private static final String MARBLE_MARKET_FORMAT = "  ||     1     |     2     |     3     |     4     |\n==||===========|===========|===========|===========|\n1 || %1$9s | %2$9s | %3$9s | %4$9S |\n2 || %5$9s | %6$9s | %7$9s | %8$9S |\n3 || %9$9s | %10$9s | %11$9s | %12$9S |\n\nNOT FOR SALE > %13$s";
    private static final String FAITH_PATH_FORMAT =  "Faith Points: %d\n[%c][%c][%c][%c]<[%c][%c][%c]{%c}>[%c][%c][%c]<[%c][%c][%c][%c]{%c}>[%c][%c]<[%c][%c][%c][%c][%c]{%c}>\nPope favors: [%c][%c][%c]";

    public static String render(ClientDevelopmentCard card) {
        String requirementString = card.getRequirement().toString();
        String effectString = card.getEffect().toString();
        return String.format(DEVELOPMENT_CARD_FORMAT,
                card.getColor().toString(),
                card.getLevel(),
                card.getVictoryPoints(),
                requirementString,
                effectString);
    }

    public static String render(ClientLeaderCard card) {
        String requirementString = card.getRequirement().toString();
        String effectString = card.getEffect().toString();
        return String.format(LEADER_CARD_FORMAT,
                card.getVictoryPoints(),
                card.isActive() ? "ACTIVE" : "INACTIVE",
                requirementString,
                effectString);
    }

    public static String render(ClientLeaderCardDeck deck) {
        String tempFormat =  "%d - %s\n";
        StringBuilder tempString = new StringBuilder();
        ArrayList<String> renderCards = deck.getClientLeaderCards()
                .stream()
                .map(RepresentationBuilder::render)
                .collect(Collectors.toCollection(ArrayList::new));
        for (int i = 0; i < renderCards.size(); i++) {
            tempString.append(String.format(tempFormat, i, renderCards.get(i)));
        }
        tempString.delete(tempString.length()-1, tempString.length());
        return String.format(LEADER_CARD_DECK_FORMAT, tempString.toString());
    }

    public static String render(ClientDevelopmentCardDecks decks) {
        String tempFormat =  "%d - %s\n";
        ArrayList<String> tempStrings = new ArrayList<>();
        for (ArrayList<ClientDevelopmentCard> deck: decks.getDevelopmentCards()) {
            StringBuilder tempString = new StringBuilder();
            ArrayList<String> renderCards = deck
                    .stream()
                    .map(RepresentationBuilder::render)
                    .collect(Collectors.toCollection(ArrayList::new));
            for (int i = 0; i < renderCards.size(); i++) {
                tempString.append(String.format(tempFormat, i, renderCards.get(i)));
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

    public static String getAsset() {
        // TODO complete
        return null;
    }
}
