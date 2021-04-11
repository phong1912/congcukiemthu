package ChineseChess;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public final class Board {

    public final static byte[][] cellStartup = {
        {19, 18, 17, 16, 15, 16, 17, 18, 19},
        {0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 20, 0, 0, 0, 0, 0, 20, 0},
        {21, 0, 21, 0, 21, 0, 21, 0, 21},
        {0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0},
        {14, 0, 14, 0, 14, 0, 14, 0, 14},
        {0, 13, 0, 0, 0, 0, 0, 13, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0},
        {12, 11, 10, 9, 8, 9, 10, 11, 12}};
    //public byte[][] mask;
    public byte[][] cell;
    public Point CurrMove;
    public Point PrevMove;
    public boolean select;
    public boolean move;
    public boolean RED;
    public Piece piece;
    public final static int ROW = 9;
    public final static int COL = 8;
    public ArrayList<State> listUndo;
    public int[][] allMove;
    public boolean gameOver = false;

    public Board(byte[][] cell, boolean select, boolean move, boolean RED, Point CurrMove, Point PrevMove) {
        listUndo = new ArrayList<State>();
        setBoard(cell);
        this.CurrMove = new Point(CurrMove);
        this.PrevMove = new Point(PrevMove);
        this.select = select;
        this.move = move;
        this.RED = RED;
    }

    public void solve() {
        if (move && select) {
            moveTo(CurrMove.x, CurrMove.y);
        } else {
            byte value = cell[CurrMove.x][CurrMove.y];
            if ((RED && value > 14) || (!RED && value >= 8 && value <= 14)) {
                select(CurrMove.x, CurrMove.y);
                getMoves(piece.FindAllPossibleMoves());
            }
        }
    }

    public int[][] getMoves(ArrayList<State> possibleMove) {
        int n = possibleMove.size();
        if (n != 0) {
            allMove = new int[n][2];
            int k = 0;
            for (State state : possibleMove) {
                allMove[k][0] = state.curr.x;
                allMove[k++][1] = state.curr.y;
            }
        }
        return allMove;
    }

    public void setBoard(byte[][] board) {
        cell = new byte[ROW + 1][COL + 1];
        for (int i = 0; i <= ROW; i++) {
            for (int j = 0; j <= COL; j++) {
                cell[i][j] = board[i][j];
            }
        }
    }

    public ArrayList<Point> FindPieces(boolean _RED) {
        ArrayList<Point> allpiece = new ArrayList<Point>();
        allpiece.add(0, new Point(-1, -1));
        for (int i = 0; i <= ROW; i++) {
            for (int j = 0; j <= COL; j++) {
                byte val = cell[i][j];
                if (val >= 8 && val <= 21) {
                    if ((_RED && val == 15) || (!_RED && val == 8)) {
                        allpiece.remove(0);
                        allpiece.add(0, new Point(i, j));
                    }
                    if ((_RED && val <= 14) || (!_RED && val > 14)) {
                        allpiece.add(new Point(i, j));
                    }
                }
            }
        }
        return allpiece;
    }

    public ArrayList<State> allMove(boolean _RED) {
        ArrayList<Point> allpiece = FindPieces(_RED);
        ArrayList<State> arrMoves = new ArrayList<State>();
        allpiece.get(0);
        for (int i = 1; i < allpiece.size(); i++) {
            Point pos = allpiece.get(i);
            byte val = cell[pos.x][pos.y];
            switch (val) {
                case 8:
                case 15:
                    CKing king = new CKing(this, pos);
                    arrMoves.addAll(king.FindAllPossibleMoves());
                    break;
                case 9:
                case 16:
                    CBishop bishop = new CBishop(this, pos);
                    arrMoves.addAll(bishop.FindAllPossibleMoves());
                    break;
                case 10:
                case 17:
                    CElephant elephant = new CElephant(this, pos);
                    arrMoves.addAll(elephant.FindAllPossibleMoves());
                    break;
                case 11:
                case 18:
                    CKnight knight = new CKnight(this, pos);
                    arrMoves.addAll(knight.FindAllPossibleMoves());
                    break;
                case 12:
                case 19:
                    CRook rook = new CRook(this, pos);
                    arrMoves.addAll(rook.FindAllPossibleMoves());
                    break;
                case 13:
                case 20:
                    CCannon cannon = new CCannon(this, pos);
                    arrMoves.addAll(cannon.FindAllPossibleMoves());
                    break;
                case 14:
                case 21:
                    CPawn pawn = new CPawn(this, pos);
                    arrMoves.addAll(pawn.FindAllPossibleMoves());
                    break;
            }
        }
        return arrMoves;
    }

    public boolean IsGameOver(boolean _RED) {
        ArrayList<Point> allpiece = FindPieces(_RED);
        allpiece.get(0);
        for (int i = 1; i < allpiece.size(); i++) {
            Point pos = allpiece.get(i);
            ArrayList<State> arrMoves = null;
            byte val = cell[pos.x][pos.y];
            switch (val) {
                case 8:
                case 15:
                    CKing king = new CKing(this, pos);
                    arrMoves = king.FindAllPossibleMoves();
                    break;
                case 9:
                case 16:
                    CBishop bishop = new CBishop(this, pos);
                    arrMoves = bishop.FindAllPossibleMoves();
                    break;
                case 10:
                case 17:
                    CElephant elephant = new CElephant(this, pos);
                    arrMoves = elephant.FindAllPossibleMoves();
                    break;
                case 11:
                case 18:
                    CKnight knight = new CKnight(this, pos);
                    arrMoves = knight.FindAllPossibleMoves();
                    break;
                case 12:
                case 19:
                    CRook rook = new CRook(this, pos);
                    arrMoves = rook.FindAllPossibleMoves();
                    break;
                case 13:
                case 20:
                    CCannon cannon = new CCannon(this, pos);
                    arrMoves = cannon.FindAllPossibleMoves();
                    break;
                case 14:
                case 21:
                    CPawn pawn = new CPawn(this, pos);
                    arrMoves = pawn.FindAllPossibleMoves();
                    break;
            }
            if (arrMoves != null && !arrMoves.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public void select(int x, int y) {
        byte value = cell[x][y];
        PrevMove = new Point(x, y);
        switch (value) {
            case 8:
            case 15:
                piece = new CKing(this, new Point(x, y));
                break;
            case 9:
            case 16:
                piece = new CBishop(this, new Point(x, y));
                break;
            case 10:
            case 17:
                piece = new CElephant(this, new Point(x, y));
                break;
            case 11:
            case 18:
                piece = new CKnight(this, new Point(x, y));
                break;
            case 12:
            case 19:
                piece = new CRook(this, new Point(x, y));
                break;
            case 13:
            case 20:
                piece = new CCannon(this, new Point(x, y));
                break;
            case 14:
            case 21:
                piece = new CPawn(this, new Point(x, y));
                break;
        }
        select = true;
        move = false;
    }

    public void moveTo(int x, int y) {
        byte val = cell[PrevMove.x][PrevMove.y];
        cell[x][y] = val;
        cell[PrevMove.x][PrevMove.y] = 0;
        CurrMove = new Point(x, y);
        select = false;
        move = true;
        switchPlayer();
    }

    public State RandomMove(boolean _RED) {
        ArrayList<State> arrMoves = allMove(_RED);
        Random rand = new Random();
        if (arrMoves.size() > 0) {
            int x = rand.nextInt(arrMoves.size());
            return arrMoves.get(x);
        }
        return null;
    }

    public void switchPlayer() {
        RED = !RED;
    }
}
