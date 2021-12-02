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
$(function(){
    new Vue({
        el:"#app",
        data:{
            user:[],
            role:[],
            update:{},
            icon:false,
            pwd:false,
        },
        methods:{
            getUser(){
                var that=this;

                $.ajax({
                    type: 'post',
                    url: host+"/user/get",
                    data:{
                        "id":getQueryVariable("id"),
                    },
                    xhrFields:{
                        withCredentials:true
                    },
                    success: function (data) {
                        that.user=data.user;
                        that.role=data.user.role;
                        console.log(data.user)
                    },
                    error:function(jqXHR){
                        alert("请先登录！");
                        window.location.href="/login.html";
                       console.log(jqXHR.responseText); 
                    }
                });
            },
            upload(){
                console.log(this.icon);
                if(this.icon){
                    return false;
                }
                var input = document.createElement("input");
                var that=this;
                input.type = "file";
                input.click();
                input.onchange = function(){
                    var file = input.files[0];
                    var form = new FormData();
                    form.append("file", file); //第一个参数是后台读取的请求key值
                    $.ajax({
                        type: 'post',
                        url: host+"/uploadImage",
                        xhrFields:{
                            withCredentials:true
                        },
                        data:form,
                        cache: false,
                        contentType: false,
                        processData: false,
                        success: function (data) {
                            console.log(data);
                            if(data.status==true){
                                alert("更新成功！");
                                $("#uploadIcon").addClass('disabled');
                                that.icon=true;
                            }
                        },
                        error:function(jqXHR){
                           console.log("失败"); 
                        }
                    });
                }
            },
            updatePwd(){
                if(this.pwd){
                    return false;
                }
                orginpwd=prompt("请输入原密码:");
                pwd1=prompt("请输入新密码:");
                pwd2=prompt("请再次输入新密码:");
                if(pwd1==pwd2){
                    if(confirm("确认修改密码为："+pwd2+" 吗？")){
                        update={
                            "phone":this.user.phone,
                            "origin":orginpwd,
                            "password":pwd2,
                        }
                        this.update=update;  
                        $("#uploadPwd").addClass('disabled');
                        this.pwd=true;

                    }
                }else{
                    alert("二次密码不正确！");
                }
            },
            updateMsg(){
                this.update["phone"]=$("#phone").val();
                console.log(this.update);
                $.ajax({
                    type: 'post',
                    url: host+"/user/update",
                    xhrFields:{
                        withCredentials:true
                    },
                    data:this.update,
                    success: function (data) {
                        console.log(data);
                        if(data.status==true){
                            alert("更新成功！");
                            window.location.href="/index.html"
                        }
                    },
                    error:function(jqXHR){
                       console.log("失败"); 
                    }
                });
            }

        }, 
        created(){
            this.getUser();
        },

    });
});
