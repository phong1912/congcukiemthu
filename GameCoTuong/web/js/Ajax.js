/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

//-----------Chat---------------------------------------------------------------
function getChat(strURL) {
    
    var form = document.forms['formMain'];
    var msg = form.msg.value;
    if(msg=="") return;
    
    var self = this;
    // Mozilla/Safari
    if (window.XMLHttpRequest) {
        self.xmlHttpReq = new XMLHttpRequest();
    }
    // IE
    else if (window.ActiveXObject) {
        self.xmlHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
    }
    self.xmlHttpReq.open('POST', strURL, true);
    self.xmlHttpReq.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=utf-8');
    self.xmlHttpReq.onreadystatechange = function() {
        if (self.xmlHttpReq.readyState == 4 && self.xmlHttpReq.status == 200) {
            updatepageChat(self.xmlHttpReq.responseText);
        }
    }
    self.xmlHttpReq.send(getquerystring());
}

function getquerystring() {
    var form = document.forms['formMain'];
    var msg = form.msg.value;
    form.msg.value = "";
    var qstr = 'msg=' + msg;
    return qstr;
}

function updatepageChat(str){
    document.getElementById("result1").innerHTML="";
    document.getElementById("result2").innerHTML = document.getElementById("result3").innerHTML;
    document.getElementById("result3").innerHTML = str;
}

function getqueryMsg() {
    var form = document.forms['formMain'];
    form.msg.value="";
    return null;
}

//-----------Data---------------------------------------------------------------
function getData(strURL) {
    var self = this;
    // Mozilla/Safari
    if (window.XMLHttpRequest) {
        self.xmlHttpReq = new XMLHttpRequest();
    }
    // IE
    else if (window.ActiveXObject) {
        self.xmlHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
    }
    self.xmlHttpReq.open('POST', strURL, true);
    self.xmlHttpReq.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    self.xmlHttpReq.onreadystatechange = function() {
        if (self.xmlHttpReq.readyState == 4 && self.xmlHttpReq.status == 200) {
            updatepageData(self.xmlHttpReq.responseText);
        }
    }
    self.xmlHttpReq.send(getqueryData());
}

function getqueryData() {
    var form = document.forms['formMain'];
    form.msg.value = "";
    
    var board = "";
    var listmove = "";
    for(var i = 0; i <= 9; i++)
    {
        for(var j = 0; j <= 8; j++)
        {
            board += cell[i][j];
            if(i+j!=17) board += ",";
        }
    }
    var n = listMove.length;
    for(i = 0; i < n; i++)
    {
        for(j = 0; j < 2; j++)
        {                
            listmove += listMove[i][j];
            if(i+j != n-1) listmove += ",";
        }
    }
    var qstr = 'cell=' + escape(board);
    qstr += '&listMove=' + escape(listmove);
    qstr += '&select=' + escape(_select);
    qstr += '&move=' + escape(_move);
    qstr += '&red=' + escape(RED);
    qstr += '&prex=' + escape(preX);
    qstr += '&prey=' + escape(preY);
    qstr += '&x=' + escape(x);
    qstr += '&y=' + escape(y);
    return qstr;
}

function updatepageData(xmlDoc){
    var obj= jQuery.parseJSON(xmlDoc);
    preX=obj.prx;
    preY=obj.pry;
    x=obj._x;
    y=obj._y;
    RED=obj.red;
    if(_move){
        getqueryMsg();
        over=obj.over;
        cell=obj.cell;
        listMove=obj.listMove;
        draw(cell);
        if(!RED){        
            move(x, y, preX, preY);
        }
        if(over){
            showWin();
        }
    }
    else{
        posibleMove=obj.allMove;
        drawAllPossibleMove(posibleMove);
    }
}