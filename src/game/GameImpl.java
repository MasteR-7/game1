package game;


import units.Element;
import units.Position;
import units.Unit;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static units.Element.Name.FARMER;
import static units.Element.Name.GOAT;
import static units.Element.Name.CABBAGE;
import static units.Element.Name.WOLF;

public class GameImpl implements Game {

    final Map<Element.Name, Unit> elements = new HashMap<>(3);

    @Override
    public void startGame() {
        final Unit farmer = new Unit(FARMER);
        final Unit wolf = new Unit(WOLF);
        final Unit goat = new Unit(GOAT);
        final Unit cabbage = new Unit(CABBAGE);
        elements.put(farmer.getName(), farmer);
        elements.put(wolf.getName(), wolf);
        elements.put(goat.getName(), goat);
        elements.put(cabbage.getName(), cabbage);
    }

    @Override
    public State move(final Element.Name name, final Position position) {
        State state;
        if (someoneOnBoat() && position == Position.BOAT) {
            return State.BAD_MOVE;
        }

        if (elements.get(name)
                .getPosition() == Position.LEFT_COAST && position == Position.RIGHT_COAST
                || elements.get(name)
                .getPosition() == Position.RIGHT_COAST && position == Position.LEFT_COAST) {
            state = State.BAD_MOVE;
        } else {
            elements.get(name)
                    .setPosition(position);
            elements.get(FARMER)
                    .setPosition(position);
            state = notifyStateChanged();
        }
        System.out.println(state);
        return state;
    }

    private State notifyStateChanged() {
        State state = State.GO_ON;
        if (getPosition(WOLF) == getPosition(GOAT) && getPosition(WOLF) != getPosition(FARMER)
                || getPosition(GOAT) == getPosition(CABBAGE) && getPosition(GOAT) != getPosition(FARMER)) {

            state = State.LOSE;
        } else if (isOnRightCoast(WOLF) && isOnRightCoast(GOAT) && isOnRightCoast(CABBAGE)) {
            state = State.WIN;
        }
        return state;
    }

    private Position getPosition(final Element.Name name) {
        return elements.get(name)
                .getPosition();
    }

    private boolean isOnRightCoast(final Element.Name name) {
        return elements.get(name)
                .getPosition() == Position.RIGHT_COAST;
    }

    private boolean someoneOnBoat() {
        return !elements.values()
                .stream()
                .filter(element -> element.getPosition() == Position.BOAT && element.getName() != FARMER)
                .collect(Collectors.toList())
                .isEmpty();
    }
}


enum State {
    GO_ON,
    WIN,
    LOSE,
    BAD_MOVE
}