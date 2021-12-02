host="http://localhost:8081";
var token;
var onloadCallback = function() {
    if(docCookies.getItem('Authorization')!=null){
        alert("您已经登录了！");
        window.location.href = "/index.html"
    }
    $("#password").on("focus",function(){
        $(".hide_captcha_submit").removeClass("hide_captcha_submit")
        grecaptcha.render('captcha', {
            'sitekey' : '6LfxzEgdAAAAAK7M_COlrc7XbVFu0evkxHI8LJ0f',
            'callback' : function(response) {
                    token=response;
                    },
            });
        $(this).off("focus");
    }),
    $('#submit').click(function () {
        var json = {
            "name":$("#name").val(),
            "password":$("#password").val(),
            "token":token
        };
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
                    //写cookite 
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
    });
};