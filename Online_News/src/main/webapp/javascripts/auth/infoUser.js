host="http://localhost:8081";
var token;
var onloadCallback = function() {
    $("#name").on("focus",function(){
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
        if($("#password1").val()==$("#password2").val()){
            var json = {
                "phone":$("#phone").val(),
                "name":$("#name").val(),
                "password":$("#password1").val(),
                "token":token
            };
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
        }else{
            alert("密码不一致！");
        }
    });
};