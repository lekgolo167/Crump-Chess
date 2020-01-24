package crump.chess.engine.pieces;

import crump.chess.engine.Alliance;
import crump.chess.engine.board.Move;
import crump.chess.engine.board.Board;

import java.util.Collection;
import java.util.List;

public abstract class Piece {

    protected final int piecePosition;
    protected final Alliance pieceAlliance;

    Piece(final int piecePosition, final Alliance pieceAlliance) {
        this.pieceAlliance = pieceAlliance;
        this.piecePosition = piecePosition;
    }

    public Alliance getPieceAlliance() {
        return this.pieceAlliance;
    }

    public abstract Collection<Move> calculateLegalMoves(final Board board);
}
