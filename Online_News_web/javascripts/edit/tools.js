Stoolbar=[{
        name:"heading-1",
        action:SimpleMDE.toggleHeading1,
        className:"fa fa-header fa-header-x fa-header-1",
        title:"一级标题"
    },{
        name:"heading-2",
        action:SimpleMDE.toggleHeading2,
        className:"fa fa-header fa-header-x fa-header-2",
        title:"二级标题"
    },{
        name:"heading-3",
        action:SimpleMDE.toggleHeading2,
        className:"fa fa-header fa-header-x fa-header-3",
        title:"三级标题"
    },
     "|",
     {
        name:"bold",
        action:SimpleMDE.toggleBold,
        className:"fa fa-bold",
        title:"加粗"
     }, 
     {
        name:"italic",
        action:SimpleMDE.toggleItalic,
        className:"fa fa-italic",
        title:"斜体"
     },  
     {
        name:"strikethrough",
        action:SimpleMDE.toggleStrikethrough,
        className:"fa fa-strikethrough",
        title:"删除线"
     },  
      "|" ,
    {
        name:"unordered-list",
        action:SimpleMDE.toggleUnorderedList	,
        className:"fa fa-list-ul",
        title:"无序列表"
    },{
        name:"ordered-list",
        action:SimpleMDE.toggleoOrderedList	,
        className:"fa fa-list-ol",
        title:"有序列表"
    },{
        name:"clean-block",
        action:SimpleMDE.cleanBlock	,
        className:"fa fa-eraser fa-clean-block",
        title:"清除块"
    },
    "|" , 
    {
        name:"quote",
        action:SimpleMDE.toggleBlockquote	,
        className:"fa fa-quote-left",
        title:"引用"
    },{
        name:"image",
        action:function uploadimage(editor){
            var input = document.createElement("input");
            input.type = "file";
            input.click();
            input.onchange = function(){
                var file = input.files[0];
                var form = new FormData();
                form.append("file", file); //第一个参数是后台读取的请求key值
                $.ajax({
                    type: 'post',
                    url: host+"/uploadArticle",
                    xhrFields:{
                        withCredentials:true
                    },
                    data:form,
                    cache: false,
                    contentType: false,
                    processData: false,
                    success: function (data) {
                        console.log(data);
                        var cm = editor.codemirror;
                        $.ajax({
                            type: 'get',
                            url: host+"/file/get",
                            data:{
                                "fid":data.fid
                            },
                            xhrFields:{
                                withCredentials:true
                            },
                            success: function (data) {
                                file_html="!["+data.fileMsg.id+"]("+data.url+")"//ajax获取文件名 filename = cm.getSelection();
                                var startPoint = cm.getCursor("start");
                                var endPoint = cm.getCursor("end");
                                cm.replaceSelection(file_html);
                                // cm.setSelection(startPoint, endPoint); 焦点位置
                                cm.focus();
                            }
                        })
                    },
                    error:function(jqXHR){
                       console.log("失败"); 
                    }
                });
            }
        },
        className:"fa fa-picture-o",
        title:"图像",
    }
    ,"link","code","table", 
        "|","preview","side-by-side","fullscreen",
        "|","guide"
]; 
//工具栏定义