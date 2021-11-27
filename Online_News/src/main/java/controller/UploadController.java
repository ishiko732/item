package controller;

import bean.ArticleFile;
import bean.UploadFile;
import mapper.ArticleMapper;
import mapper.UserMapper;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import utils.MimeTypeEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Controller
public class UploadController {
    final UserMapper userMapper;
    final ArticleMapper articleMapper;

    public UploadController(UserMapper userMapper, ArticleMapper articleMapper) {
        this.userMapper = userMapper;
        this.articleMapper = articleMapper;
    }

    private String upload(HttpServletRequest request, UploadFile file, String path) throws IOException {
        String originalFilename = file.getFile().getOriginalFilename();
        String uuid= UUID.randomUUID().toString().replace("-","");

        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);//后缀名
        String newFileName= String.format("%s.%s", uuid,suffix);
        File newFile=new File(request.getServletContext().getRealPath(path),newFileName);
        newFile.getParentFile().mkdirs();
        file.getFile().transferTo(newFile);
        return newFileName;
    }

    @RequestMapping(value = "/uploadImage",method = RequestMethod.POST)
    public @ResponseBody
    Map<String,Object> upload(HttpServletRequest request, HttpServletResponse response,UploadFile file ) throws IllegalStateException, IOException {
        String path="/image/user/";
        String newFileName = upload(request, file, path);
        Integer uid = UserController.tokenGetUserId();
        Map<String,Object> ret=new HashMap<>();
        if(!Objects.isNull(uid)){
            boolean status = userMapper.setImage(uid,path+newFileName)== 1;
            ret.put("name",newFileName);
            ret.put("path",path+newFileName);
            ret.put("uid",String.valueOf(uid));
            ret.put("status",status);
        }else{
            ret.put("status",false);
            ret.put("info","请求出错！");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);//设置状态码 400
        }

        System.out.println(ret);
        return ret;
    }

    @RequestMapping(value = "/uploadArticle",method = RequestMethod.POST)
    public @ResponseBody
    Map<String,Object> uploadArticle(HttpServletRequest request, UploadFile file) throws IllegalStateException, IOException {
        Integer uid = UserController.tokenGetUserId();
        String aid_S =request.getParameter("aid");
        Integer aid=Objects.isNull(aid_S)?null:Integer.valueOf(aid_S);
        String path="/image/article/";

        String newFileName = upload(request, file, path);
        Timestamp timestamp = ArticleController.currentTime();
        ArticleFile articleFile = new ArticleFile(null, uid, aid, path, newFileName, timestamp);
        boolean status = articleMapper.uploadFile(articleFile)== 1 ;
        Map<String,Object> ret=new HashMap<>();
        ret.put("name",newFileName);
        ret.put("path",path+newFileName);
        ret.put("uid",String.valueOf(uid));
        ret.put("aid",String.valueOf(aid));
        ret.put("fid",String.valueOf(articleFile.getId()));
        ret.put("time",timestamp.toString());
        ret.put("status",status);
        System.out.println(ret);
        return ret;
    }

    @RequestMapping(value = "/file/get",method = RequestMethod.GET)
    public @ResponseBody
    Map<String,Object> getFile(HttpServletRequest request) throws IllegalStateException, IOException {
        int fid = Integer.parseInt(request.getParameter("fid"));
        Map<String,Object> map=new HashMap<>();
        ArticleFile file = articleMapper.getFile(fid);
        map.put("fileMsg", file);
        String url = "http://"+request.getServerName() + ":" + request.getServerPort()+"/file/"+file.getId();
        map.put("url", url);
        return map;
    }

    @RequestMapping(value = "/file/{fid}",method = RequestMethod.GET)
    public @ResponseBody
    byte[] get(HttpServletRequest request, HttpServletResponse response, @PathVariable int fid) throws IllegalStateException, IOException {
        ArticleFile msg = articleMapper.getFile(fid);
//        System.out.println(request.getServletContext().getRealPath(msg.getDir())+ msg.getFilename());//实际地址
        String ext = msg.getFilename().substring(msg.getFilename().lastIndexOf(".") + 1);
        InputStream in = new FileInputStream(new File(request.getServletContext().getRealPath(msg.getDir())+ msg.getFilename()));;
        response.setContentType(MimeTypeEnum.getContentType(ext));//设置contentType
        return IOUtils.toByteArray(in);

    }
}