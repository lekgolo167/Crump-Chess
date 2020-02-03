package crump.chess.tests;

import crump.chess.engine.board.Board;
import crump.chess.engine.board.BoardUtils;
import crump.chess.engine.board.Move;
import crump.chess.engine.pieces.Piece;
import crump.chess.engine.player.MoveStatus;
import crump.chess.engine.player.MoveTransition;
import crump.chess.engine.player.ai.MinMax;
import crump.chess.engine.player.ai.MoveStrategy;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class BoardTest {
    @Test
    public void initialBoard() {

        final Board board = Board.createStandardBoard();
        assertEquals(20, board.currentPlayer().getLegalMoves().size());
        assertEquals(20, board.currentPlayer().getOpponent().getLegalMoves().size());
        assertFalse(board.currentPlayer().isInCheck());
        assertFalse(board.currentPlayer().isInCheckMate());
        assertFalse(board.currentPlayer().isCastled());
//        assertTrue(board.currentPlayer().isKingSideCastleCapable());
//        assertTrue(board.currentPlayer().isQueenSideCastleCapable());
        assertEquals(board.currentPlayer(), board.whitePlayer());
        assertEquals(board.currentPlayer().getOpponent(), board.blackPlayer());
        assertFalse(board.currentPlayer().getOpponent().isInCheck());
        assertFalse(board.currentPlayer().getOpponent().isInCheckMate());
        assertFalse(board.currentPlayer().getOpponent().isCastled());
//        assertTrue(board.currentPlayer().getOpponent().isKingSideCastleCapable());
//        assertTrue(board.currentPlayer().getOpponent().isQueenSideCastleCapable());
        assertTrue(board.whitePlayer().toString().equals("White"));
        assertTrue(board.blackPlayer().toString().equals("Black"));

//        final Iterable<Piece> allPieces = board.getAllPieces();
//        final Iterable<Move> allMoves = Iterables.concat(board.whitePlayer().getLegalMoves(), board.blackPlayer().getLegalMoves());
//        for(final Move move : allMoves) {
//            assertFalse(move.isAttack());
//            assertFalse(move.isCastlingMove());
//            assertEquals(MoveUtils.exchangeScore(move), 1);
//        }
//
//        assertEquals(Iterables.size(allMoves), 40);
//        assertEquals(Iterables.size(allPieces), 32);
//        assertFalse(BoardUtils.isEndGame(board));
//        assertFalse(BoardUtils.isThreatenedBoardImmediate(board));
//        assertEquals(StandardBoardEvaluator.get().evaluate(board, 0), 0);
//        assertEquals(board.getPiece(35), null);
    }

    @Test
    public void testFoolsMate() {
        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.currentPlayer().makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("f2"), BoardUtils.getCoordinateAtPosition("f3")));

        assertTrue(t1.getMoveStatus() == MoveStatus.DONE);

        final MoveTransition t2 = t1.getTransitionBoard().currentPlayer().makeMove((Move.MoveFactory.createMove(t1.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("e7"), BoardUtils.getCoordinateAtPosition("e5"))));

        assertTrue(t2.getMoveStatus() == MoveStatus.DONE);

        final MoveTransition t3 = t2.getTransitionBoard().currentPlayer().makeMove((Move.MoveFactory.createMove(t2.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("g2"), BoardUtils.getCoordinateAtPosition("g4"))));

        assertTrue(t3.getMoveStatus() == MoveStatus.DONE);

        final MoveStrategy strategy = new MinMax(5);

        final Move aiMove = strategy.execute(t3.getTransitionBoard());

        final Move bestMove = Move.MoveFactory.createMove(t3.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d8"), BoardUtils.getCoordinateAtPosition("h4"));

        assertEquals(aiMove, bestMove);
    }


}