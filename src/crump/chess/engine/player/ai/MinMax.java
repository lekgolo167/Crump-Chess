package crump.chess.engine.player.ai;

import crump.chess.engine.board.Board;
import crump.chess.engine.board.Move;
import crump.chess.engine.player.MoveStatus;
import crump.chess.engine.player.MoveTransition;

import java.lang.Math;

public class MinMax implements MoveStrategy {

    private final BoardEvaluator boardEvaluator;
    private final int searchDepth;
    private int boardsLookedAt;

    public MinMax(final int searchDepth) {
        this.boardEvaluator = new StandardBoardEvaluator();
        this.searchDepth = searchDepth;
        this.boardsLookedAt = 0;
    }

    @Override
    public String toString() {
        return "MiniMax";
    }

    @Override
    public Move execute(Board board) {
        final long startTime = System.currentTimeMillis();
        Move bestMove = null;
        int highestSeenValue = Integer.MIN_VALUE;
        int lowestSeenValue = Integer.MAX_VALUE;
        int currentValue;

        int numMoves = board.currentPlayer().getLegalMoves().size();
        System.out.println(board.currentPlayer() + "Thinking with depth = " + this.searchDepth + ", current moves size " + numMoves);



        for(final Move move : board.currentPlayer().getLegalMoves()) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if(moveTransition.getMoveStatus() == MoveStatus.DONE) {
                currentValue = board.currentPlayer().getAlliance().isWhite() ?
                        min(moveTransition.getTransitionBoard(), Integer.MIN_VALUE, Integer.MAX_VALUE, this.searchDepth - 1) :
                        max(moveTransition.getTransitionBoard(), Integer.MIN_VALUE, Integer.MAX_VALUE, this.searchDepth - 1);
                if(board.currentPlayer().getAlliance().isWhite() && currentValue >= highestSeenValue) {
                    highestSeenValue = currentValue;
                    bestMove = move;
                } else if(board.currentPlayer().getAlliance().isBlack() && currentValue <= lowestSeenValue) {
                    lowestSeenValue = currentValue;
                    bestMove = move;
                }
            }
        }

        final long executionTime = System.currentTimeMillis() - startTime;
        System.out.println(board.currentPlayer() + " AI finished thinking in  " + executionTime / 1000 + " seconds, boards looked at: " + this.boardsLookedAt);

        return bestMove;
    }

    public int min(final Board board, int alpha, int beta, final int depth) {

        if(depth == 0 || isEndGameScenario(board)) {
            return this.boardEvaluator.evaluate(board, depth);
        }

        int minEval = Integer.MAX_VALUE;
        for(final Move move : board.currentPlayer().getLegalMoves()) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if(moveTransition.getMoveStatus() == MoveStatus.DONE) {
                final int eval = max(moveTransition.getTransitionBoard(), alpha, beta, depth-1);
                this.boardsLookedAt++;
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if(beta <= alpha) {
                    break;
                }
            }
        }
        return minEval;
    }

    public int max(final Board board, int alpha, int beta,  final int depth) {

        if(depth == 0 || isEndGameScenario(board)) {
            return this.boardEvaluator.evaluate(board, depth);
        }

        int maxEval = Integer.MIN_VALUE;
        for(final Move move : board.currentPlayer().getLegalMoves()) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if(moveTransition.getMoveStatus() == MoveStatus.DONE) {
                this.boardsLookedAt++;
                final int eval = min(moveTransition.getTransitionBoard(), alpha, beta, depth-1);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if(beta <= alpha) {
                    break;
                }
            }
        }
        return maxEval;
    }

    private static boolean isEndGameScenario(final Board board) {
        return board.currentPlayer().isInCheckMate() || board.currentPlayer().isInStaleMate();
    }
}
