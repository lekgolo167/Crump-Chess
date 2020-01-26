package crump.chess.engine.player;

import crump.chess.engine.Alliance;
import crump.chess.engine.board.Board;
import crump.chess.engine.board.Move;
import crump.chess.engine.pieces.Piece;

import java.util.Collection;

public class WhitePlayer extends Player {

    public WhitePlayer(Board board, Collection<Move> whiteStandardLegalMoves, Collection<Move> blackStandardLegalMoves) {
        super(board, whiteStandardLegalMoves, blackStandardLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return board.getWhitePieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.BLACK;
    }

    @Override
    public Player getOpponent() {
        return this.board.blackPlayer();
    }
}
