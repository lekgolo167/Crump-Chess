package crump.chess.engine.player.ai;

import crump.chess.engine.board.Board;
import crump.chess.engine.board.Move;

public interface MoveStrategy {

    Move execute(Board board);
}
