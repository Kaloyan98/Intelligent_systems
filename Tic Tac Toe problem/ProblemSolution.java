class ProblemSolution {
    public static void alphaBetaPruning(CellState currentPlayer, Board board, int alpha, int beta, int depth) {
        maxValue(currentPlayer, board, alpha, beta, depth);
    }

    private static int maxValue(CellState currentPlayer, Board board, int alpha, int beta, int depth) {
        if (board.gameIsOver()) {
            return evaluateTerminalState(currentPlayer, board, depth);
        }

        Move bestMove = null;
        int value = Integer.MIN_VALUE;
        for (Move move : board.getAvailableMoves()) {
            Board modifiedBoard = new Board(board);
            modifiedBoard.move(move.getRow(), move.getColumn());

            int minResult = minValue(currentPlayer, modifiedBoard, alpha, beta, depth + 1);
            if (minResult > value) {
                value = minResult;
                bestMove = move;
            }

            if (value >= beta) {
                return value;
            }

            alpha = Math.max(alpha, value);
        }

        if (bestMove != null) {
            board.move(bestMove.getRow(), bestMove.getColumn());
        }

        return alpha;
    }

    private static int minValue(CellState currentPlayer, Board board, int alpha, int beta, int depth) {
        if (board.gameIsOver()) {
            return evaluateTerminalState(currentPlayer, board, depth);
        }

        Move bestMove = null;
        int value = Integer.MAX_VALUE;
        for (Move move : board.getAvailableMoves()) {
            Board modifiedBoard = new Board(board);
            modifiedBoard.move(move.getRow(), move.getColumn());

            int maxResult = maxValue(currentPlayer, modifiedBoard, alpha, beta, depth + 1);
            if (maxResult < value) {
                value = maxResult;
                bestMove = move;
            }

            if (value <= alpha) {
                return value;
            }

            beta = Math.min(beta, value);
        }

        if (bestMove != null) {
            board.move(bestMove.getRow(), bestMove.getColumn());
        }

        return beta;
    }

    private static int evaluateTerminalState(CellState currentPlayer, Board board, int depth) {
        CellState opponent = (currentPlayer == CellState.X) ? CellState.O : CellState.X;
        if (board.getWinner() == currentPlayer) {
            return 10 - depth;
        } else if (board.getWinner() == opponent) {
            return -10 + depth;
        } else {
            return 0;
        }
    }
}
