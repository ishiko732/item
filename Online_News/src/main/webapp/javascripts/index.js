var host="http://localhost:8081";
var token=docCookies.getItem('Authorization');
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
    $(window).scroll(function(){
        var s = $(window).scrollTop();
        if( s > 400){
            $(".goTop").fadeIn(200);
        }else{
            $(".goTop").fadeOut(300);
        };
    });
    $(".goTop").fadeOut(1);
    new Vue({
        el:"#app",
        data:{
            categories:[],
            articles:[],
            user:[],
            role:[],
            now:"",
        },
        methods:{
            getCategories(){
                var that=this;
                $.ajax({
                    type: 'post',
                    url: host+'/category/categoryList',
                    xhrFields:{
                        withCredentials:true
                    },
                    async:true,
                    success: function (data) {
                        that.categories=data;
                        that.$nextTick(function(){//加载完数据后执行
                            that.loadCSS();
                        })
                    }
                });
            },
            getUser(){
                var that=this;
                $.ajax({
                    type: 'post',
                    url: host+"/user/get",
                    xhrFields:{
                        withCredentials:true
                    },
                    success: function (data) {
                        that.user=data.user;
                        that.role=data.user.role;
                        console.log(that.user);
                        that.$nextTick(function(){//加载完数据后执行
                            $(".usermsg").removeClass("usermsg");
                        })
                    },
                    error:function(jqXHR){
                       console.log(jqXHR.responseText); 
                    }
                });

            },
            getAbstractArticle(){
                var that=this;
                $.ajax({
                    type: 'get',
                    url: host+'/article/abstractList?aid=1',
                    xhrFields:{
                        withCredentials:true
                    },
                    success: function (data) {
                        // console.log(data);
                        that.articles=data;
                        for(var i=0;i<that.articles.length;i++){
                            that.articles[i].time=getFormattedDate(that.articles[i].time);
                            // console.log(that.articles[i].time);
                        }
                        that.$nextTick(function(){//加载完数据后执行
                            // loadCSS();
                        })
                    },
                    error:function(jqXHR){
                        
                    }
                });
            },
            getArticle(index){
                var that=this;
                $.ajax({
                    type: 'get',
                    url: host+'/article/list?aid='+this.categories[index].id,
                    xhrFields:{
                        withCredentials:true
                    },
                    success: function (data) {
                        that.articles=data;
                        for(var i=0;i<that.articles.length;i++){
                            that.articles[i].time=getFormattedDate(that.articles[i].time);
                            // console.log(that.articles[i].time);
                        }
                        that.$nextTick(function(){//加载完数据后执行
                            $a=$('.tabs a');
                            $a_index=$($a[index]);
                            $a.removeClass("active");
                            $a_index.addClass('active');
                            var activeWidth =$a_index.innerWidth();
                            var itemPos = $a_index.position();
                            $(".selector").css({
                                "left":itemPos.left + "px", 
                                "width": activeWidth + "px"
                            })
                            $("#content").fadeIn(100);
                            $(".showCategory").removeClass("showCategory").addClass("category");
                            // console.log(that.articles.length==0);
                            if(that.articles.length==0){
                                $(".Null").removeClass("Null").addClass("valueNull");
                            }else{
                                $(".valueNull").removeClass("valueNull").addClass("Null");
                            }
                            $category=$("#"+that.categories[index].name);
                            $category.addClass("showCategory");
                            console.log($("#content").innerWidth());
                        })
                    },
                    error:function(jqXHR){
                        msg=$.parseJSON(jqXHR.responseText);
                        alert(msg.info);
                        window.location.href="/index.html"
                        that.$nextTick(function(){//加载完数据后执行
                            $("#content").fadeIn(100);
                        })
                    }
                });
            },
            getDate(){
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
                this.now=now;
            },
            loadCSS(){
                var tabs = $('.tabs');
                var selector = $('.tabs').find('a').length;
                var activeItem = tabs.find('.active');
                var activeWidth = activeItem.innerWidth();
                $(".selector").css({
                "left": activeItem.position.left + "px", 
                "width": activeWidth + "px"
                });
                $category=$("#"+this.categories[0].name);
                $category.addClass("showCategory");
            },
            transfer(index){
                this.getArticle(index);
                $("#content").fadeOut(100);
            },
            edit(){
                window.open("/edit/edit.html")
            },
            login(){
                window.location.href="/login.html"
            },
            logout(){
                var that=this;
                $.ajax({
                    type: 'post',
                    url: host+'/logout',
                    xhrFields:{
                        withCredentials:true
                    },
                    success: function (data) {
                        console.log(data);
                        if(data.status==true){
                            alert("登出成功！");
                            window.location.href="/index.html"
                        }
                    },
                    error:function(jqXHR){
                        
                    }
                });
            },
            goTop(){
                $('body,html').stop().animate({scrollTop:0});
                // return true;
            }
        }, 
        created(){
            this.getCategories();
            this.getUser();
            this.getAbstractArticle();
            this.getDate();
        },
        mounted(){
            // console.log(this.categories);
        },
    });

});
