import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;
import io.atlassian.fugue.Pair;
import uk.ac.bris.cs.scotlandyard.model.*;

public class Moriarty implements Ai {

    // attributes
    private final String name;
    private GameSetup gameSetup;
    private List<Move> moves;
    private ImmutableList<LogEntry> logBook;
    private boolean nextLog;
    private Board.TicketBoard tickets;
    private List<Integer> scores;

    public Moriarty() {
        this.name = "Moriarty";
    }

    @Nonnull
    @Override
    public String name() {
        return this.name;
    }

    @Nonnull
    @Override
    public Move pickMove(
            @Nonnull Board board,
            Pair<Long, TimeUnit> timeoutPair) {
        // returns a random move, replace with your own implementation
//		return moves.get(new Random().nextInt(moves.size()));

        // intialising variables
        this.moves = board.getAvailableMoves().asList();
        this.gameSetup = board.getSetup();
        this.logBook = board.getMrXTravelLog();
        this.nextLog = gameSetup.moves.get(logBook.size());
        this.tickets = board.getPlayerTickets(Piece.MrX.MRX).get();


        for (Move move : moves) {
            Integer weight = move.accept(new Move.Visitor<Integer>() {
                @Override
                public Integer visit(Move.SingleMove move) {
                    Integer weighting = 0;
                    weighting += nextToDet(move.destination);
                    weighting += ticketType(move.ticket);
                    //TODO - ALL OF THE METHODS :)

                    return weighting;
                }


                @Override
                public Integer visit(Move.DoubleMove move) {
                    Integer weighting = 0;
                    weighting += nextToDet(move.destination2);
                    weighting += ticketType(move.ticket1);
                    weighting += ticketType(move.ticket2);
                    weighting -= -logBook.size() + (40 / (tickets.getCount(ScotlandYard.Ticket.DOUBLE)));
                    //TODO - ALL OF THE METHODS :)

                    return weighting;
                }

                private Integer nextToDet(int destination) {
                    Set<Integer> adjacent = gameSetup.graph.adjacentNodes(destination);
                    if (checkDets(adjacent)) {
                        if (nextLog) return -1000;
                        else return -50;
                    } else return 0;
                }

                private boolean checkDets(Set<Integer> adjacent) {
                    for (Piece piece : board.getPlayers()) {
                        if (piece.isDetective()) {
                            return !(adjacent.contains(board.getDetectiveLocation((Piece.Detective) piece)));// Note: add check that det has necessary ticket
                        }
                    }
                    return false;
                }

                private Integer ticketType(ScotlandYard.Ticket ticket) {
                    if (ticket.equals(ScotlandYard.Ticket.TAXI))
                        return 10 * tickets.getCount(ScotlandYard.Ticket.TAXI) + logBook.size();
                    if (ticket.equals(ScotlandYard.Ticket.BUS))
                        return 5 * tickets.getCount(ScotlandYard.Ticket.BUS) + logBook.size();
                    if (ticket.equals(ScotlandYard.Ticket.UNDERGROUND))
                        return 2 * tickets.getCount(ScotlandYard.Ticket.UNDERGROUND) + logBook.size();
                    if (ticket.equals(ScotlandYard.Ticket.SECRET))
                        return 1 * tickets.getCount(ScotlandYard.Ticket.SECRET) + logBook.size();
                    else return 0;

                }
            });

            scores.add(weight);
        }
        int index = 0;
        int currentMax = -10000;
        for (int i = 0; i < scores.size(); i++) {
            if (scores.get(i) > currentMax) {
                currentMax = scores.get(i);
                index = i;
            }
        }
        return moves.get(index);
    }
}