var token=docCookies.getItem('Authorization');
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
                getCategories_ajax(this);
            },
            getUser(){
                getUser_ajax(this);
            },
            getDate(){
                this.now=hello();
            },
            getArticle(index){
                getArticle_ajax(this,index,this.user);
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
            },
            loadTab(index){
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
            },
            loadArticleCSS(index){
                $("#content").fadeIn(100);
                $showCategory=$(".showCategory");
                $showCategory.removeClass("showCategory").addClass("category");
                if(this.articles.length==0){
                    $(".Null").removeClass("Null").addClass("valueNull");
                }else{
                    $(".valueNull").removeClass("valueNull").addClass("Null");
                }
                $category=$("#"+this.categories[index].name);
                if($category!=$showCategory){
                    $category.addClass("showCategory");
                }
            },
            transfer(index){
                this.getArticle(index);
                $("#content").fadeOut(100);
            },
            edit(){
                window.open("/edit/edit.html")
            },
            login(){
                window.location.href="/auth/login.html"
            },
            logout(){
                logout_ajax();
            },
            goTop(){
                $('body,html').stop().animate({scrollTop:0});
            }
        }, 
        created(){
            this.getCategories();
            this.getUser();//->Article
            this.getDate();
        }
    });

});
