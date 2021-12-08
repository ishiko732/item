var host="http://localhost:8081";
function logout_ajax(){
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
    });
}

// 种类
function getCategories_ajax(that){
    $.ajax({
        type: 'post',
        url: host+'/category/categoryList',
        xhrFields:{
            withCredentials:true
        },
        async:true,
        success: function (data) {
            that.categories=data;
        }
    });
}
//register
function register_ajax(json){
    $.ajax({
        type: 'post',
        data: json,
        url: host+'/user/register',
        cache: false,
        dataType: 'text',
        xhrFields:{
            withCredentials:true
        },
        success: function (data) {
            msg=$.parseJSON(data);
            if (msg.status==true) {
                alert("注册成功");
                window.location.href = "/auth/login.html"
            } else {
                alert("注册失败："+msg.info);
                grecaptcha.reset();
            }
        },
        error:function(data){
            msg=$.parseJSON(data.responseText);
            console.log(msg);
            alert("注册失败："+msg.info);
            grecaptcha.reset();
        }
    })
}
//login
function login_ajax(json){
    $.ajax({
        type: 'post',
        data: json,
        url: host+'/login',
        cache: false,
        dataType: 'text',
        xhrFields:{
            withCredentials:true
        },
        async:true,
        success: function (data) {
            msg=$.parseJSON(data);
            console.log(msg);
            if (msg.status==true) {
                alert("登录成功");
                window.location.href = "/index.html"
            } else {
                alert("登录失败:"+msg.info);
                grecaptcha.reset();
            }
        },
        error:function(data){
            msg=$.parseJSON(data.responseText);
            console.log(msg);
            alert("登录失败："+msg.info);
            grecaptcha.reset();
        }
    })
}
//resetPassword
function resetPassword_ajax(json){
    $.ajax({
        type: 'post',
        data: json,
        url: host+'/user/resetPassword',
        cache: false,
        dataType: 'text',
        xhrFields:{
            withCredentials:true
        },
        success: function (data) {
            msg=$.parseJSON(data);
            if (msg.status==true) {
                alert("重置成功");
                window.location.href = "/auth/login.html"
            } else {
                alert("重置失败"+msg.info);
                grecaptcha.reset();
            }
        },
        error:function(data){
            msg=$.parseJSON(data.responseText);
            console.log(msg);
            alert("重置失败："+msg.info);
            grecaptcha.reset();
        }
    })
}
// getUser
function getUser_ajax(that){
    $.ajax({
        type: 'post',
        url: host+"/user/get",
        xhrFields:{
            withCredentials:true
        },
        success: function (data) {
            that.user=data.user;
            that.role=data.user.role;
            getArticle_ajax(that,0,data.user);
            that.$nextTick(function(){//加载完数据后执行
                $(".usermsg").removeClass("usermsg");
                that.loadCSS();
            })
        },
        error:function(jqXHR){
            getArticle_ajax(that,0,null);
           that.$nextTick(function(){//加载完数据后执行
              that.loadCSS();
            })
        }
    });
}


//article
function getArticle_ajax(that,index,user){
    if(user==null){
        $.ajax({
            type: 'get',
            url: host+'/article/abstractList?aid=1',
            xhrFields:{
                withCredentials:true
            },
            success: function (data) {
                that.articles=data;
                for(var i=0;i<that.articles.length;i++){
                    that.articles[i].time=getFormattedDate(that.articles[i].time);
                }
                that.$nextTick(function(){//加载完数据后执行
                    that.loadTab(0);
                    that.loadArticleCSS(0);
                })
            }
        });
    }else{
        $.ajax({
            type: 'get',
            url: host+'/article/list?aid='+that.categories[index].id,
            xhrFields:{
                withCredentials:true
            },
            success: function (data) {
                that.articles=data;
                for(var i=0;i<that.articles.length;i++){
                    that.articles[i].time=getFormattedDate(that.articles[i].time);
                }
                that.$nextTick(function(){//加载完数据后执行
                    that.loadTab(index);
                    that.loadArticleCSS(index);
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
    }
}


