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
            resetPassword_ajax(json);
        }else{
            alert("密码不一致！");
        }
    });
};