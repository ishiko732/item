host="http://localhost:8081";

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
  });
});