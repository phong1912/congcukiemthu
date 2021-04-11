<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="webgame.aspx.cs" Inherits="DoAnGame.webgame" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>
    <script src="draw.js"></script>
</head>
    <body onload="draw(cellStartup);">
        

    <div class="board" id="board">
                    <img  >
                    <canvas id="canvas" id="canvas" width="540" height="600"></canvas>
                    <!--<span class="cursor" id="cursor"></span>-->
                    <div class="alert" id="alert" style="display: none">
                        <span class="w-l" id="w-l">You win</span>
                        <span class="continue" id="continue" onclick="reset()">Continue</span>
                    </div>
        <h1>huy</h1>
        <h2></h2>
                </div>
   
</body>
    