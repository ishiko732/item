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
        login_ajax(json);
    });
};