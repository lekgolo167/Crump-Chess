package crump.chess.engine.pieces;

import crump.chess.engine.Alliance;
import crump.chess.engine.board.*;
import crump.chess.engine.board.Move.*;

import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATES = { -17, -15, -10, -6, 6, 10, 15, 17};

    Knight(final int piecePosition, final Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board){

        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCandidate : CANDIDATE_MOVE_COORDINATES) {

            final int candidateDestinationCoordinate = this.piecePosition + currentCandidate;

            if (BoardUtils.isValidCoordinate(candidateDestinationCoordinate)) {

                if (isFirstColumnExclustion(this.piecePosition, currentCandidate) ||
                        isSecondColumnExclustion(this.piecePosition, currentCandidate) ||
                        isSeventhColumnExclustion(this.piecePosition, currentCandidate) ||
                        isEighthColumnExclustion(this.piecePosition, currentCandidate)) {
                    continue;
                }
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);

                if (!candidateDestinationTile.isTileOccupied()) {
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                }
                else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();

                    if(this.pieceAlliance != pieceAlliance) {
                        legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                    }
                }
            }
        }

        return Collections.unmodifiableList(legalMoves);
    }

    private static boolean isFirstColumnExclustion(final int currentPostion, final int candadateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPostion] && (candadateOffset == -17 || candadateOffset == -10 ||
                candadateOffset == 6 || candadateOffset == 15);
    }

    private static boolean isSecondColumnExclustion(final int currentPostion, final int candadateOffset) {
        return BoardUtils.SECOND_COLUMN[currentPostion] && (candadateOffset == -10 || candadateOffset == 6);
    }

    private static boolean isSeventhColumnExclustion(final int currentPostion, final int candadateOffset) {
        return BoardUtils.SEVENTH_COLUMN[currentPostion] && (candadateOffset == 10 || candadateOffset == -6);
    }

    private static boolean isEighthColumnExclustion(final int currentPostion, final int candadateOffset) {
        return BoardUtils.EIGHTH_COLUMN[currentPostion] && (candadateOffset == -15 || candadateOffset == -6 ||
                candadateOffset == 10 || candadateOffset == 17);
    }
}
