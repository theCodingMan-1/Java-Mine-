package uk.ac.bris.cs.scotlandyard.model;

import com.google.common.collect.ImmutableList;

import javax.annotation.Nonnull;
import javax.management.timer.TimerMBean;

import uk.ac.bris.cs.scotlandyard.model.Board.GameState;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard.Factory;

// own importing
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableMap;
import java.util.*;
import uk.ac.bris.cs.scotlandyard.model.Move.*;
import uk.ac.bris.cs.scotlandyard.model.Piece.*;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard.*;

/**
 * cw-model
 * Stage 1: Complete this class
 */
public final class MyGameStateFactory implements Factory<GameState> {

    @Nonnull @Override public GameState build(
            GameSetup setup,
            Player mrX,
            ImmutableList<Player> detectives) {
        return new MyGameState(setup, ImmutableSet.of(MrX.MRX), ImmutableList.of(), mrX, detectives);
    }

    private final class MyGameState implements GameState {

        // attributes
        private GameSetup setup;
        private ImmutableSet<Piece> remaining;
        private ImmutableList<LogEntry> log;
        private Player mrX;
        private List<Player> detectives;
        private ImmutableSet<Move> moves;
        private ImmutableSet<Piece> winner;

        // constructor
        private MyGameState(
                final GameSetup setup,
                final ImmutableSet<Piece> remaining,
                final ImmutableList<LogEntry> log,
                final Player mrX,
                final List<Player> detectives){

            // checking for non-null
            if (setup.moves.isEmpty()) throw new IllegalArgumentException("Moves is empty");
            if (setup.graph.edges().isEmpty()) throw new IllegalArgumentException("Graph is empty");
            if (setup.graph.nodes().isEmpty()) throw new IllegalArgumentException("Graph is empty");
            if (mrX == null) throw new NullPointerException("MrX is null");
            if (detectives.isEmpty()) throw new IllegalArgumentException("Detectives is empty");

            // checking detectives don't have special tickets or duplicates
            for (Player det1 : detectives) {
                if ((det1.has(Ticket.DOUBLE) || (det1.has(Ticket.SECRET)))) throw new IllegalArgumentException("Detective has a double ticket");

                int count = 0;
                for (Player det2 : detectives) {
                    if (!det1.equals(det2) && (det1.location() == det2.location())) throw new IllegalArgumentException("Disitnct detectives have the same location");
                    if (det1.equals(det2)) count++;
                }
                if (count > 1) throw new IllegalArgumentException("Duplicate detectives");
            }

            // initialisation
            this.setup = setup;
            this.remaining = remaining;
            this.log = log;
            this.mrX = mrX;
            this.detectives = detectives;
            this.moves = ImmutableSet.copyOf(movesForThisState(this.setup, this.detectives, this.remaining, this.log));
            this.winner = findWinner();

            // setting moves to empty if there is a winner
            if (!winner.equals(ImmutableSet.of())) moves = ImmutableSet.of();

        }

        // getters

        @Nonnull @Override public GameSetup getSetup() {  return setup; }

        @Nonnull @Override  public ImmutableSet<Piece> getPlayers() {
            Set<Piece> players = new HashSet<>();
            for (Player player : detectives) {
                players.add(player.piece());
            }
            players.add(mrX.piece());
            return ImmutableSet.copyOf(players);
        }

        @Override @Nonnull public ImmutableSet<Move> getAvailableMoves() { return moves; }

        @Nonnull @Override public ImmutableList<LogEntry> getMrXTravelLog() { return log; }

        @Nonnull @Override public ImmutableSet<Piece> getWinner() { return winner; }

        @Nonnull @Override public Optional<Integer> getDetectiveLocation(Detective detective) {
            for (Player player : detectives ) {
                if ((player.piece()).equals(detective)) return Optional.of(player.location());
            }
            return Optional.empty();
        }

        @Nonnull @Override public Optional<TicketBoard> getPlayerTickets(Piece piece) {

            if (piece.isMrX()) { return Optional.of(new Counting(mrX.tickets())); }

            for (Player player : detectives ) {
                if ((player.piece()).equals(piece)) return Optional.of(new Counting(player.tickets()));
            }
            return Optional.empty();
        }

        private class Counting implements TicketBoard { // for getPlayerTickets

            Counting(ImmutableMap<Ticket, Integer> tickets) {
                this.tickets = tickets;
            }

            ImmutableMap<Ticket, Integer> tickets;

            @Override public int getCount(@Nonnull Ticket ticket) {
                return tickets.get(ticket);
            }
        }

        // methods for getAvailableMoves

        private Set<Move> movesForThisState(GameSetup setup, List<Player> detectives, ImmutableSet<Piece> remaining, ImmutableList<LogEntry> log) {

            Set<Move> moves = new HashSet<>();

            if (remaining.contains(MrX.MRX)) { // it's Mr. X's turn
                Set<SingleMove> singleMoves = makeSingleMoves(setup, detectives, mrX, mrX.location());
                // casting the single moves to more general Move type and adding to moves
                for (SingleMove singleMove : singleMoves) {
                    moves.add((Move) singleMove);
                }

                if (log.size() <= setup.moves.size() - 2) {
                    Set<DoubleMove> doubleMoves = makeDoubleMoves(setup, detectives, mrX, mrX.location());
                    // casting the double moves to more general Move type and adding to moves
                    for (DoubleMove doubleMove : doubleMoves) {
                        moves.add((Move) doubleMove);
                    }
                }

            } else { // it's the detectives' turn

                Set<Piece> detPieces = new HashSet<>();

                for (Piece piece : remaining) {
                    for (Player player : detectives) {
                        detPieces.add(player.piece());
                        if (player.piece().equals(piece)) {
                            Set<SingleMove> singleMoves = makeSingleMoves(setup, detectives, player, player.location());
                            // casting the single moves to more general Move type and adding to moves
                            for (SingleMove singleMove : singleMoves) {
                                moves.add((Move) singleMove);
                            }
                        }
                    }
                }
                if (moves.isEmpty() && !remaining.equals(ImmutableSet.copyOf(detPieces))) moves = movesForThisState(setup, detectives, ImmutableSet.of(mrX.piece()), log);
            }
            return moves;
        }

        private static Set<SingleMove> makeSingleMoves(GameSetup setup, List<Player> detectives, Player player, int source){

            HashSet<SingleMove> moves = new HashSet<>();

            for(int destination : setup.graph.adjacentNodes(source)) {

                boolean possible = true;

                // checking place is not filled
                for (Player other : detectives) {
                    if (destination == other.location()) possible = false;
                }

                if (possible) {

                    // checking if player has regular ticket
                    for (Transport t : setup.graph.edgeValueOrDefault(source, destination, ImmutableSet.of())) {
                        if (player.has(t.requiredTicket())) moves.add(new SingleMove(player.piece(), source, t.requiredTicket(), destination));
                    }

                    // checking if player has secret ticket
                    if (player.has(Ticket.SECRET)) moves.add(new SingleMove(player.piece(), source, Ticket.SECRET, destination));

                }
            }
            return moves;
        }

        private static Set<DoubleMove> makeDoubleMoves(GameSetup setup, List<Player> detectives, Player player, int source) {

            HashSet<DoubleMove> moves = new HashSet<>();

            if (player.has(Ticket.DOUBLE)) {

                // making the set of possible first moves
                Set<SingleMove> firstMoves = makeSingleMoves(setup, detectives, player, source);

                // making the sets of possible next moves for each possible first move
                for (SingleMove firstMove : firstMoves) {
                    Set<SingleMove> nextMoves = makeSingleMoves(setup, detectives, player, firstMove.destination);
                    for (SingleMove nextMove : nextMoves) {
                        if (firstMove.ticket.equals(nextMove.ticket)) {
                            if (player.hasAtLeast(firstMove.ticket, 2))
                                moves.add(new DoubleMove(player.piece(), source, firstMove.ticket, firstMove.destination, nextMove.ticket, nextMove.destination));
                        } else {
                            moves.add(new DoubleMove(player.piece(), source, firstMove.ticket, firstMove.destination, nextMove.ticket, nextMove.destination));
                        }
                    }
                }
            }
            return moves;
        }

        @Nonnull @Override public GameState advance(Move move) {
            // checking move is possible
            if (!moves.contains(move)) throw new IllegalArgumentException("Illegal move: " + move);

            GameState newState = move.accept(new Visitor<GameState>(){
                @Override public GameState visit(SingleMove singleMove){
                    Piece mover = singleMove.commencedBy();
                    if (mover.isDetective()) {

                        List<Player> updatedDet = new ArrayList<>();

                        for (Player player : detectives) {
                            updatedDet.add(player);
                            if (player.piece() == mover) {
                                updatedDet.remove(player);
                                player = player.at(singleMove.destination);
                                player = player.use(singleMove.ticket);
                                updatedDet.add(player);
                            }
                        }
                        mrX = mrX.give(singleMove.ticket);

                        Set<Piece> updateRemaining = new HashSet<>();
                        for (Piece piece : remaining) {
                            if (!piece.equals(mover)) updateRemaining.add(piece);
                        }

                        ImmutableSet<Piece> newRemaining;

                        if (movesForThisState(setup, updatedDet, ImmutableSet.copyOf(updateRemaining), log).isEmpty()) newRemaining = ImmutableSet.of(MrX.MRX);
                        else newRemaining = ImmutableSet.copyOf(updateRemaining);

                        return new MyGameState(setup, newRemaining, log, mrX, updatedDet);

                    } else { // piece is mr.X
                        mrX = mrX.at(singleMove.destination);
                        mrX = mrX.use(singleMove.ticket);

                        // putting all detectives back into remaining
                        Set<Piece> updateRemaining = new HashSet<>();
                        for (Player player : detectives) {
                            updateRemaining.add(player.piece());
                        }

                        ImmutableSet<Piece> newRemaining = ImmutableSet.copyOf(updateRemaining);

                        // logbook updating
                        LogEntry nextEntry;

                        if (setup.moves.get(log.size())) nextEntry = LogEntry.reveal(singleMove.ticket, singleMove.destination);
                        else nextEntry = LogEntry.hidden(singleMove.ticket);

                        List<LogEntry> logSoFar = new ArrayList<>();
                        logSoFar.addAll(log);
                        logSoFar.add(nextEntry);

                        ImmutableList<LogEntry> newLog = ImmutableList.copyOf(logSoFar);

                        return new MyGameState(setup, newRemaining, newLog, mrX, detectives);
                    }
                }
                @Override public GameState visit(DoubleMove doubleMove){

                    mrX = mrX.at(doubleMove.destination2);
                    mrX = mrX.use(Ticket.DOUBLE);
                    mrX = mrX.use(doubleMove.ticket1);
                    mrX = mrX.use(doubleMove.ticket2);

                    // putting all detectives back into remaining
                    Set<Piece> updateRemaining = new HashSet<>(Set.of());
                    for (Player player : detectives) {
                        updateRemaining.add(player.piece());
                    }

                    ImmutableSet<Piece> newRemaining = ImmutableSet.copyOf(updateRemaining);

                    // logbook updating
                    LogEntry firstNextEntry;
                    LogEntry secondNextEntry;

                    if (setup.moves.get(log.size())) firstNextEntry = LogEntry.reveal(doubleMove.ticket1, doubleMove.destination1);
                    else firstNextEntry = LogEntry.hidden(doubleMove.ticket1);

                    if (setup.moves.get(log.size() + 1)) secondNextEntry = LogEntry.reveal(doubleMove.ticket2, doubleMove.destination2);
                    else secondNextEntry = LogEntry.hidden(doubleMove.ticket2);

                    List<LogEntry> logSoFar = new ArrayList<>();
                    logSoFar.addAll(log);
                    logSoFar.add(firstNextEntry);
                    logSoFar.add(secondNextEntry);

                    ImmutableList<LogEntry> newLog = ImmutableList.copyOf(logSoFar);

                    return new MyGameState(setup, newRemaining, newLog, mrX, detectives);
                }
            });
            return newState;
        }

        private ImmutableSet<Piece> findWinner() {

            Set<Piece> detPieces = new HashSet<>();
            boolean detWin = false;
            boolean mrXWin = false;

            // adding detective pieces to a set and also checking if they landed on Mr. X
            for (Player player : detectives) {
                detPieces.add(player.piece());
                if (player.location() == mrX.location()) detWin = true;
            }

            // checking if Mr. X is cornered
            if (remaining.contains(MrX.MRX) && moves.isEmpty()) detWin = true;
            // idk how to do this TT - testDetectiveWinsIfMrXCornered doesn't want to pass

            // checking if log has filled up and it's Mr. X's turn
            if (remaining.contains(MrX.MRX) && log.size() == setup.moves.size()) mrXWin = true;

            // checking if all detectives are stuck
            if (movesForThisState(setup, detectives, ImmutableSet.copyOf(detPieces), log).isEmpty() && !detWin) mrXWin = true;

            if (mrXWin) return ImmutableSet.of(MrX.MRX);
            if (detWin) return ImmutableSet.copyOf(detPieces);
            else return ImmutableSet.of();
        }
    }
}
