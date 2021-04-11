/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

var cell = cellStartup;
var _select = false;
var _move=false;
var over=false;
var RED = false;
var x = -1;
var y = -1;
var preX = -1;
var preY = -1;
var listMove=[[]];
var posibleMove=[[]];
var win = 0;
var lose = 0;
var _timer=120;
var clock1=15*60;
var clock2=15*60;
$(function()
{
    $('#canvas').mousedown(function(e){
        x = Math.round((e.pageY - this.offsetTop - 30)/60);
        y = Math.round((e.pageX - this.offsetLeft - 30)/60);
        getqueryMsg();
        
        if(over){
            return;
        }
        
        value = cell[x][y];
        if(value!=0 && isCheck(value))
        {
            draw(cell);
            select(x, y);
            getData('Servlet?yc=chess');
            _select = true;
            _move=false;
        }        
        else if(_select)
        {
            for(var i=0; i<posibleMove.length; i++)
            {
                if(posibleMove[i][0] == y && posibleMove[i][1] == x){
                    _move=true;
                    break;
                }
            }
            if(_move){
                getData('Servlet?yc=chess');
                _select = false;
                _timer=120;
            }
        }
    });
    $('#canvas').mousemove(function(){
        });
    $('#canvas').mouseenter(function(){
        });
    $("#canvas").mouseup(function(){
        });
    $('#canvas').mouseleave(function(){
        });
});
function isCheck(value)
{
    if((RED && value > 14) || (!RED && value >= 8 && value <= 14))
        return true;
    return false;
}
function select(x, y)
{
    drawPiece('images/Cselect.png', y * 60, x * 60);
}
function move(x, y, preX, preY)
{
    var pictureBox = new Array('images/bk.png', 'images/ba.png', 'images/bb.png', 'images/bn.png',
        'images/br.png', 'images/bc.png', 'images/bp.png', 'images/rk.png', 'images/ra.png',
        'images/rb.png', 'images/rn.png', 'images/rr.png', 'images/rc.png', 'images/rp.png');
    drawPiece('images/Cselect.png', y * 60, x * 60);
    var canvas = document.getElementById('canvas');
    var value = cell[x][y];
    if (canvas.getContext){ 
        var ctx = canvas.getContext('2d'); 
        var imageObj = new Image(); 
        imageObj.onload = function(){
            ctx.drawImage(imageObj, preY*60 + 20, preX*60 + 20, 20, 20); 
        }; 
        imageObj.src = pictureBox[value-8]; 
    }
}
function closeIt()
{
    return "";
}
window.onbeforeunload = closeIt;
function enter(event){
    var keycode = (event.keyCode ? event.keyCode : event.which);
    if(keycode == '13'){
        document.forms["formMain"].send.click();
    }
}
function reset()
{
    cell = cellStartup;
    _select = false;
    _move=false;
    over=false;
    RED = false;
    x = -1;
    y = -1;
    preX = -1;
    preY = -1;
    listMove=[[]];
    posibleMove=[[]];
    _timer=120;
    clock1=15*60;
    clock2=15*60;
    document.getElementById("alert").style.display="none";
    document.getElementById("canvas").style.opacity="1";
    draw(cellStartup);
}
function showWin()
{
    if(RED){
        document.getElementById("w-l").innerHTML="You win";
        win++;
    }
    else{
        document.getElementById("w-l").innerHTML="You lose";
        lose++;
    }
    document.getElementById("alert").style.display="block";
    document.getElementById("canvas").style.opacity="0.3";
            
    document.getElementById("sc").innerHTML=lose;
    document.getElementById("su").innerHTML=win;
    
    document.getElementById("timer1").innerHTML="---";
    document.getElementById("timer2").innerHTML="---";
    
    document.getElementById("clock1").innerHTML="--:--";
    document.getElementById("clock2").innerHTML="--:--";
}
function timer()
{
    if(!over){
        if(_timer==0 || clock1==0 || clock2==0){
            over=true;
            showWin();
            return;
        }
        du=clock1%60;
        if(du < 10) du="0"+du;
        document.getElementById("clock1").innerHTML=Math.floor(clock1/60)+":"+du;        
        du=clock2%60;
        if(du < 10) du="0"+du;
        document.getElementById("clock2").innerHTML=Math.floor(clock2/60)+":"+du;
        if(RED){
            document.getElementById("timer2").innerHTML="---";
            document.getElementById("timer1").innerHTML=_timer;
            clock1--;
        }
        else{
            document.getElementById("timer1").innerHTML="---";
            document.getElementById("timer2").innerHTML=_timer;
            clock2--;
        }
        _timer--;
    }
}
setInterval("timer()",1000);