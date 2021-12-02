host="http://localhost:8081";
var token;
var onloadCallback = function() {
    $("#password").on("focus",function(){
        $(".hide_captcha_submit").removeClass("hide_captcha_submit")
        grecaptcha.render('captcha', {
            'sitekey' : '6LfxzEgdAAAAAK7M_COlrc7XbVFu0evkxHI8LJ0f',
            'callback' : function(response) {
                    token=response;
                    },
            });
        $("#password").off("focus");
    }),
    $('#submit').click(function () {
        var json = {
            "phone":$("#phone").val(),
            "name":$("#name").val(),
            "password":$("#password").val(),
            "role":2,
            "token":token
        };
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
                if (msg.status=="success") {
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
    });
};