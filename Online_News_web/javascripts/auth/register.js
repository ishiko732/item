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
        register_ajax(json);
    });
};