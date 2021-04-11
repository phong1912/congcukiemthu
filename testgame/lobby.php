<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="webgame.aspx.cs" Inherits="DoAnGame.webgame" %>

<!DOCTYPE html>
<link rel="stylesheet" href="lobby.css" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>
    <script src="draw.js"></script>
</head>
    <body onload="draw(cellStartup);">
    
    <div class="context">
            <div class="status">
                <span class="status_menu"><b>Chào <%=user%>,&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b><i><a href="dangnhap.jsp?yc=thoat" style="color: graytext;">Thoát</a>&nbsp;&nbsp;</i></span>
            </div>

            <div class="board">
                <div class="sever1" id="server1" onclick="server(1)">
                    <img id="img1" src="images/level_tapsu.jpg" alt="Server1"><br>
                    <span class="list">Tập sự</span>
                </div>
                <div class="sever2" id="server2" onclick="server(2)">
                    <img id="img2" src="images/level_nangcao.jpg" alt="Server2"><br>
                    <span class="list">Nâng cao</span>
                </div>
                <div class="sever3" id="server3" onclick="server(3)">
                    <img id="img3" src="images/level_caothu.jpg" alt="Server3"><br>
                    <span class="list">Cao thủ</span>
                </div>
            </div>            

            <div class="info"><br>
                <div class="list"><b><%=user_short%></b></div><br>
                <img src="images/opponent.jpg" alt="opponent">
                <div class="list"><b>Danh hiệu: Cao thủ</b></div>
                <div class="list"><b>Tỉ lệ thắng: 73%</b></div>
            </div>

            <div class="friend"><br>
                <div class="list"><b>Friend List</b></div>
                <div class="desc"> - <a href="#">HaiHomHinh</a></div>
                <div class="desc"> - <a href="#">LinhLieuLinh</a></div>
                <div class="desc"> - <a href="#">ThieuThatTha</a></div>
                <div class="desc"> - <a href="#">Nobita</a></div>
                <div class="desc"> - <a href="#">PhucPhuPhang</a></div>
            </div>
                
            <form action="chess" method="POST">
                <input type="hidden" name="yc" value="lobby">
                <input class="control" type="submit" value="Chơi">
            </form>
                
            <div class="audio">
                <audio controls autoplay loop>
                    <source src="audios/myPlayCoTuong.mp3" type="audio/mp3">
                </audio>
            </div>
        </div>
   
</body>
    