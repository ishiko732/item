host="http://localhost:8081";
// @import("./tools")
$(function(){
    var simplemde =null;
    new Vue({
        el:"#app",
        data:{
            categories:[],
            user:[],
            role:[],
            selectedIndex: 0,
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
                    },
                    error:function(jqXHR){
                        that.user={
                            "name":"null",
                            "id":"null",
                        }
                       console.log(jqXHR.responseText); 
                    }
                });
            },
            view(){
                // simplemde.toggleSideBySide();//打开实时全屏预览
                $("#showHTML").html(SimpleMDE.prototype.markdown(simplemde.value()));
            },
            switchView(e){
                this.selectedIndex=e.target.selectedIndex;// 选择项的index索引
            },
            submit(e){
                // SimpleMDE.prototype.markdown(simplemde.value()); 显示内容
                const cid=this.categories[this.selectedIndex].id;
                const title=$("#title").val();
                const description=$("#description").val();
                const content=simplemde.value();
                const json={
                    "cid":cid,
                    "title":title,
                    "description":description,
                    "content":content
                }
                console.log(json)
                if(this.user.id==null){
                    alert("请先登录！");
                    return;
                }
                $.ajax({
                    type: 'post',
                    url: host+"/article/add",
                    xhrFields:{
                        withCredentials:true
                    },
                    data:json,
                    success: function (data) {
                        // console.log(data);
                        if(data.status==true){
                            alert("提交成功！");
                            $("#title").val("");
                            $("#description").val("");
                            simplemde.value("");
                            $("#showHTML").text("");
                        }

                    },
                    error:function(jqXHR){
                       console.log(jqXHR.responseText); 
                    }
                });
            },
        }, 
        created(){
            this.getCategories();
            this.getUser();
        },
        mounted(){
            //https://github.com/sparksuite/simplemde-markdown-editor
            simplemde = new SimpleMDE({
                element: $("#demo")[0],
                spellChecker: false,
                autofocus: true,//自动聚焦
                autoDownloadFontAwesome: true,// 下载图标
                autosave: {
                    enabled: true,
                    uniqueId: "demo",//必须设置
                    delay: 10000,//时间间隔默认 10s
                },
                placeholder: "请输入内容...",
                autosave: {
                    enabled: true,
                    uniqueId: "demo",
                    delay: 1000,
                },
                toolbar : Stoolbar, 
                tabSize: 4,
                status: false,
                lineWrapping: false,
                renderingConfig: {
                    codeSyntaxHighlighting: true
                },
                });
        }
    });
});
