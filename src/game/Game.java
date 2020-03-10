package game;

import units.Element;
import units.Position;
import units.Unit;

import java.util.List;

public interface Game {
    void startGame();
    State move(Element.Name name, Position position);
}