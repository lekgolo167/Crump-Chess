package crump.chess.engine.player.ai;

import crump.chess.engine.board.Board;

public interface BoardEvaluator {

    int evaluate(Board board, int depth);
}
