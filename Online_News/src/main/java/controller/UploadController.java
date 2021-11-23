package controller;

import bean.ArticleFile;
import bean.UploadFile;
import mapper.ArticleMapper;
import mapper.UserMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
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
    Map<String,Object> upload(HttpServletRequest request, UploadFile file, int uid) throws IllegalStateException, IOException {
        String path="/image/user/";
        String newFileName = upload(request, file, path);

        boolean status = userMapper.setImage(uid,path+newFileName)== 1;

        Map<String,Object> ret=new HashMap<>();
        ret.put("name",newFileName);
        ret.put("path",path+newFileName);
        ret.put("uid",String.valueOf(uid));
        ret.put("status",status);
        System.out.println(ret);
        return ret;
    }

    @RequestMapping(value = "/uploadArticle",method = RequestMethod.POST)
    public @ResponseBody
    Map<String,Object> uploadArticle(HttpServletRequest request, UploadFile file, int uid,int aid) throws IllegalStateException, IOException {
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

    @RequestMapping(value = "/file/{fid}",method = RequestMethod.GET)
    public @ResponseBody
    ArticleFile uploadArticle(@PathVariable int fid) throws IllegalStateException, IOException {
        return articleMapper.getFile(fid);
    }

}