host="http://localhost:8081";
function getQueryVariable(variable)
{
       var query = window.location.search.substring(1);
       var vars = query.split("&");
       for (var i=0;i<vars.length;i++) {
               var pair = vars[i].split("=");
               if(pair[0] == variable){return pair[1];}
       }
       return(false);
}
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
$(function(){
  new Vue({
    el:'#app',
    data:{
      article:[],
      user:[],
      html:"",
    },
    methods:{
      getArticle(){
        var that=this;
        $.ajax({
            type: 'get',
            url: host+'/article/'+getQueryVariable("id"),
            xhrFields:{
                withCredentials:true
            },
            success: function (data) {
              data.time=getFormattedDate(data.time);
              that.article=data;
              that.user=data.user;
              document.title=that.article.title||"不存在文章";
              console.log(that.article.title);
              that.html=SimpleMDE.prototype.markdown(that.article.content);
              that.$nextTick(function(){
                $(".site-wrap").find(':header').attr("data-splitting",'');
                $(".site-wrap").find('img').addClass("p_img")
                // $(".site-wrap").find('ol').attr("data-splitting",'');
                // $(".site-wrap").find('li').attr("data-splitting",'');
                Splitting();
              })
            },
            error:function(jqXHR){
              try {
                data=$.parseJSON(jqXHR.responseText);
                if(data.info=="请先登录"){
                  document.title="请先登录";
                  that.html="<h1>Error:请先登录！</h1>"
                }
              } catch (error) {
                document.title="不存在文章";
                that.html="<h1>Error:不存在该文章!</h1>"
              }
            }
        });
      },
    },
    created(){
      this.getArticle();
    },
    mounted(){
      
      // Splitting();
    }
  });
});