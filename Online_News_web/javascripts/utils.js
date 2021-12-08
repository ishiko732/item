function getFormattedDate(timestamp) {
    var date = new Date(timestamp);
    var month = date.getMonth() + 1;
    var day = date.getDate();
    var hour = date.getHours();
    var min = date.getMinutes();
    var sec = date.getSeconds();
  
    month = (month < 10 ? "0" : "") + month;
    day = (day < 10 ? "0" : "") + day;
    hour = (hour < 10 ? "0" : "") + hour;
    min = (min < 10 ? "0" : "") + min;
    sec = (sec < 10 ? "0" : "") + sec;
  
    var str = date.getFullYear() + "-" + month + "-" + day + " " +  hour + ":" + min + ":" + sec;
    return str;
}

function hello(){
    var now=(new Date()).getHours();
    if(now>0&&now<=6){
        now="午夜好";
    }else if(now>6&&now<=11){
        now="早上好";
    }else if(now>11&&now<=14){
        now="中午好";
    }else if(now>14&&now<=18){
        now="下午好";
    }else{
        now="晚上好";
    }  
    return now;
}

function getQueryVariable(variable) //分割 ？id=2
{
       var query = window.location.search.substring(1);
       var vars = query.split("&");
       for (var i=0;i<vars.length;i++) {
               var pair = vars[i].split("=");
               if(pair[0] == variable){return pair[1];}
       }
       return(false);
}
