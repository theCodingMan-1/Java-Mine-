package uk.ac.bris.cs.scotlandyard.model;

import com.google.common.collect.ImmutableList;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.google.common.collect.ImmutableSet;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard.Factory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * cw-model
 * Stage 2: Complete this class
 */
public final class MyModelFactory implements Factory<Model> {

    @Nonnull @Override public Model build(GameSetup setup,
                                          Player mrX,
                                          ImmutableList<Player> detectives) {
        return new MyModel(setup, mrX, detectives);

    }

    private final class MyModel implements Model {

        // attributes
        @Nonnull private final GameSetup setup;
        @Nonnull private final Player mrX;
        @Nonnull private final ImmutableList<Player> detectives;
        @Nonnull private final MyGameStateFactory gameStateFactory;
        @Nonnull private Board.GameState gameState;
        @Nonnull private final Set<Observer> observers;


        // constructor
        private MyModel(GameSetup setup,
                        Player mrX,
                        ImmutableList<Player> detectives) {
            this.setup = setup;
            this.mrX = mrX;
            this.detectives = detectives;
            this.gameStateFactory = new MyGameStateFactory();
            this.gameState = gameStateFactory.build(setup, mrX, detectives);
            this.observers = new HashSet<>();
        }

        @Nonnull
        @Override
        public Board getCurrentBoard() { return gameState; }

        @Override
        public void registerObserver(@Nonnull Observer observer) {
            Objects.requireNonNull(observer);
            if (observers.contains(observer)) throw new IllegalArgumentException("Observer already registered");
            observers.add(observer);
        }

        @Override
        public void unregisterObserver(@Nonnull Observer observer) {
            Objects.requireNonNull(observer);
            if (!observers.remove(observer)) throw new IllegalArgumentException("Observer is not registered");
            observers.remove(observer);
        }

        @Nonnull
        @Override
        public ImmutableSet<Observer> getObservers() {
            return ImmutableSet.copyOf(observers);
        }

        @Override public void chooseMove(@Nonnull Move move){

            gameState = gameState.advance(move);

            if (!gameState.getWinner().equals(ImmutableSet.of())) {
                for (Observer observer : observers) {
                    observer.onModelChanged(gameState, Observer.Event.GAME_OVER); // break?
                }
            }
            else {
                for (Observer observer : observers) {
                    observer.onModelChanged(gameState, Observer.Event.MOVE_MADE);
                }
            }
        }
    }
}
