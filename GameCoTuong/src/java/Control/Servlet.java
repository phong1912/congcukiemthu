/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import ChineseChess.Board;
import ChineseChess.State;
import ChineseChess._AI;
import java.awt.Point;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author conrongchautien
 */
@WebServlet(name = "Servlet", urlPatterns = {"/Servlet"})
public class Servlet extends HttpServlet {

    private HttpSession session;

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String tb, url = null, yc = request.getParameter("yc");
            if (yc.equals("dangky")) {
                tb = DangKy(request, response);
                url = "/dangky.jsp?tb=" + tb;
            } else if (yc.equals("dangnhap")) {
                tb = DangNhap(request, response);
                url = "/dangnhap.jsp?tb=" + tb;
            } else if (yc.equals("lobby")) {
                tb = Lobby(request, response);
                if (tb.equals("start")) {
                    url = "/chess.jsp";
                } else {
                    url = "/dangnhap.jsp?tb=" + tb;
                }
            } else if (yc.equals("chess")) {
                Chess(request, response);
            }
            if (url != null) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
                dispatcher.forward(request, response);
            }
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private String DangKy(HttpServletRequest request, HttpServletResponse response) {
        String tb = "";
        try {
            request.setCharacterEncoding("UTF-8");
            String username = request.getParameter("username").trim();
            String password = request.getParameter("password").trim();
            String cpassword = request.getParameter("cpassword").trim();
            if (username.equals("")) {
                tb = "Bạn phải nhập Tài khoản";
                return tb;
            }
            if (username.length() < 6 || username.length() > 24) {
                tb = "Tên đăng nhập không hợp lệ";
                return tb;
            }
            if (password.length() < 6 || password.length() > 24) {
                tb = "Vui lòng nhập mật khẩu từ 6 đến 32 ký tự";
                return tb;
            }
            if (!password.equals(cpassword)) {
                tb = "Nhập lại mật khẩu không giống";
                return tb;
            }
            Account.Open();
            if (Account.Find(username)) {
                tb = "Tài khoản này đã tồn tại";
            } else {
                Account tk = new Account(username, password);
                tk.Insert();
                tb = "Đăng ký thành công";
                session = request.getSession();
                session.setAttribute("user", username);
            }
            Account.Close();
        } catch (Exception ex) {
            tb = "Đăng kí có lỗi!<br>" + ex;
        } finally {
        }
        return tb;
    }

    private String DangNhap(HttpServletRequest request, HttpServletResponse response) {
        String tb = "";
        try {
            request.setCharacterEncoding("UTF-8");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            if (username.equals("") || password.equals("")) {
                tb = "Vui lòng nhập Tài khoản và Mật khẩu!";
                return tb;
            }
            Account.Open();
            if (Account.Find(username, password)) {
                tb = "Đăng nhập thành công";
                session = request.getSession();
                session.setAttribute("user", username);
            } else {
                tb = "Tài khoản hoặc mật khẩu không đúng !";
            }
            Account.Close();
        } catch (Exception ex) {
            tb = "Đăng nhập lỗi!<br>" + ex;
        } finally {
        }
        return tb;
    }

    private String Lobby(HttpServletRequest request, HttpServletResponse response) {
        String tb = "start";
        return tb;
    }

    private String Chess(HttpServletRequest request, HttpServletResponse response) {
        String tb = "";
        try {
            String para1 = request.getParameter("select");
            boolean select = Boolean.parseBoolean(para1);
            String para2 = request.getParameter("move");
            boolean move = Boolean.parseBoolean(para2);
            String para3 = request.getParameter("red");
            boolean red = Boolean.parseBoolean(para3);
            String para4 = request.getParameter("prex");
            int prex = Integer.parseInt(para4);
            String para5 = request.getParameter("prey");
            int prey = Integer.parseInt(para5);
            String para6 = request.getParameter("x");
            int x = Integer.parseInt(para6);
            String para7 = request.getParameter("y");
            int y = Integer.parseInt(para7);
            String para8 = request.getParameter("cell");
            String para9 = request.getParameter("listMove");

            String[] s = para8.split(",");
            byte[][] b = new byte[10][9];
            int n = s.length;
            for (int k = 0; k < n; k++) {
                int i = k / 9;
                int j = k % 9;
                b[i][j] = Byte.parseByte(s[k]);
            }

            String[] s1 = para9.split(",");
            ArrayList<Point> listmove = new ArrayList<Point>();
            int n1 = s1.length;
            for (int k = 0; k < n1 - 3; k += 2) {
                int posx = Integer.parseInt(s1[k]);
                int posy = Integer.parseInt(s1[k + 1]);
                listmove.add(new Point(posx, posy));
            }

            Board board = new Board(b, select, move, red, new Point(x, y), new Point(prex, prey));
            board.solve();

            boolean over = board.IsGameOver(red);
            red = board.RED;

            if (!over && red) {
                board = new Board(board.cell, true, true, red, new Point(x, y), new Point(prex, prey));
                _AI _ai = new _AI(board);
                State state = _ai.GenerateMove(red);
                if (state != null) {
                    board.CurrMove = state.curr;
                    board.PrevMove = state.prev;
                    board.solve();
                }
                over = board.IsGameOver(red);
            }

            JSONArray cell = new JSONArray();
            for (int i = 0; i <= 9; i++) {
                JSONArray array = new JSONArray();
                for (int j = 0; j <= 8; j++) {
                    array.add(board.cell[i][j]);
                }
                cell.add(array);
            }

            JSONArray allMove = new JSONArray();
            if (board.allMove != null) {
                n = board.allMove.length;
                for (int i = 0; i < n; i++) {
                    JSONArray array = new JSONArray();
                    array.add(board.allMove[i][1]);
                    array.add(board.allMove[i][0]);
                    allMove.add(array);
                }
            }
            JSONArray listMove = new JSONArray();
            n = listmove.size();
            for (int i = 0; i < n; i++) {
                JSONArray array = new JSONArray();
                array.add(listmove.get(i).x);
                array.add(listmove.get(i).y);
                listMove.add(array);
            }

            select = board.select;
            move = board.move;
            x = board.CurrMove.x;
            y = board.CurrMove.y;
            prex = board.PrevMove.x;
            prey = board.PrevMove.y;
            red = board.RED;

            JSONObject obj = new JSONObject();
            obj.put("cell", cell);
            obj.put("allMove", allMove);
            obj.put("listMove", listMove);
            obj.put("move", move);
            obj.put("select", select);
            obj.put("_x", x);
            obj.put("_y", y);
            obj.put("prx", prex);
            obj.put("pry", prey);
            obj.put("red", red);
            obj.put("over", over);
            response.getWriter().println(obj);
        } catch (IOException ex) {
        }
        return tb;
    }
}
