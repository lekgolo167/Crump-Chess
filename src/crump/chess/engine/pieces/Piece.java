package crump.chess.engine.pieces;

import crump.chess.engine.Alliance;
import crump.chess.engine.board.Move;
import crump.chess.engine.board.Board;

import java.util.Collection;
import java.util.List;

public abstract class Piece {

    protected  final PieceType pieceType;
    protected final int piecePosition;
    protected final Alliance pieceAlliance;
    protected final boolean isFirstMove;
    private final int cachedHashCode;

    Piece(final PieceType pieceType, final int piecePosition, final Alliance pieceAlliance, final boolean isFirstMove) {
        this.pieceType = pieceType;
        this.pieceAlliance = pieceAlliance;
        this.piecePosition = piecePosition;
        this.isFirstMove = isFirstMove;
        this.cachedHashCode = this.computeHashCode();
    }

    private int computeHashCode() {
        int result = pieceType.hashCode();
        result = 31 * result + pieceAlliance.hashCode();
        result = 31 * result + piecePosition;
        result = 31 * result + (isFirstMove ? 1 : 0);
        return result;
    }

    @Override
    public boolean equals(final Object other) {
        if(this == other) {
            return true;
        }
        if(!(other instanceof Piece)) {
            return false;
        }
        final Piece otherPiece = (Piece) other;
        return piecePosition == otherPiece.getPiecePosition() && pieceType == otherPiece.getPieceType() &&
                pieceAlliance == otherPiece.getPieceAlliance() && isFirstMove == otherPiece.isFirstMove();
    }

    @Override
    public int hashCode(){
        return this.cachedHashCode;
    }

    public PieceType getPieceType() {
        return  this.pieceType;
    }

    public int getPiecePosition() {
        return piecePosition;
    }

    public Alliance getPieceAlliance() {
        return this.pieceAlliance;
    }

    public boolean isFirstMove() {
        return this.isFirstMove;
    }

    public abstract Collection<Move> calculateLegalMoves(final Board board);

    public abstract Piece movePiece(Move move);

    public int getPieceValue() {
        return this.pieceType.getPieceValue();
    }

    public enum PieceType {

        PAWN("P", 10),
        KNIGHT("N", 30),
        BISHOP("B", 30),
        ROOK("R", 50),
        QUEEN("Q", 90),
        KING("k", 1000);

        private String pieceName;
        private int pieceValue;

        PieceType(final String pieceName, int value) {
            this.pieceName = pieceName;
            this.pieceValue = value;
        }

        @Override
        public String toString() {
            return this.pieceName;
        }

        public int getPieceValue() {
            return this.pieceValue;
        }
    }
}
