package cn.bload.forum.utils;

import java.io.UnsupportedEncodingException;
import java.net.HttpCookie;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.Data;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/8 20:28
 * @describe 类描述:
 */
public class Test {
    public static void main(String[] args){
        BaiduPan baiduPan = new BaiduPan("https://pan.baidu.com/s/1FuS0kuFQazWtgkC7U8unPA", "srnr");
        baiduPan.init();
        for (int i = 0; i < 10; i++) {
            System.out.println(baiduPan.getTreeStr());
        }
    }
}


// 后续或许可以考虑扫描密码
class BaiduPan{
    private final Integer APPID = 250528;
    private String url;
    private String surl;
    private String password;
    private List<HttpCookie> cookies;

    BaiduPan(String url,String password){
        this.url = url;
        this.password = password;
    }

    public boolean init(){
        HttpRequest request = HttpUtil.createGet(this.url);
        HttpResponse response = request.execute();
        this.cookies = response.getCookies();
        HttpCookie cookie = response.getCookie("BDCLND");
        String BDCLND;
        if (cookie == null){
            if (response.header("Location").indexOf("https://pan.baidu.com/share/init") == -1){
                return false;
            }
            cookie = this.verify(this.password);
            if (cookie == null){
                return false;
            }
            System.out.println("需要密码验证");
        }
        cookies.add(cookie);
        System.out.println(this.getShortUrl());
        return true;
    }

    //获取分享状态
    public void checkStatus(){
        HttpRequest request = HttpUtil.createGet("https://pan.baidu.com/share/linkstatus?web=5&app_id="
                + APPID
                + "&channel=chunlei&clienttype=5&shorturl="
                + this.getShortUrl()
        );
        request.cookie(cookies.toArray(new HttpCookie[cookies.size()]));
        HttpResponse response = request.execute();
        //status 0 正常，6取消分享
    }




    private String doTreeStr(List<PanTree> panTrees,int level){
        String meet = "┣━━";
        String str = "";
        for (PanTree panTree : panTrees) {
            for (int i = 0; i < level - 1; i++) {
                str += "\t";
            }
            str += meet + panTree.getName() + "\n";
            if (panTree.getChildrens() != null){
                str += doTreeStr(panTree.getChildrens(),level + 1);
            }
        }
        return str;
    }

    //获取解析树文本
    public String getTreeStr(){
        List<PanTree> tree = getTree(1,3);
        String treeStr = doTreeStr(tree,1);
        System.out.println(JSONUtil.toJsonStr(tree));
        return treeStr;
    }

    //获取目录树
    public List<PanTree> getTree(int level,int maxLevel){
        try {
            return this.getSonTree("",level,maxLevel);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取子目录列表
    private List<PanTree> getSonTree(String path,int nowLevel,int maxLevel) throws UnsupportedEncodingException {
        List<PanTree> tree = new ArrayList<>();
        if (nowLevel > maxLevel) return tree;
        String url = "https://pan.baidu.com/share/list?&web=5&app_id="
                + APPID
                + "&channel=chunlei&clienttype=5&desc=1&showempty=0&page=1&num=100000&order=time&shorturl="
                + getShortUrl()
                + ("".equals(path) ? "&root=1" : "&dir=" + URLEncoder.encode(path,"UTF-8"));
        HttpRequest request = HttpUtil.createGet(url);
        request.cookie(cookies.toArray(new HttpCookie[cookies.size()]));
        HttpResponse response = request.execute();
        JSONObject object = JSONUtil.parseObj(response.body());
        JSONArray list = object.getJSONArray("list");
        System.out.println(response.body());
        for (int i = 0; i < list.size(); i++) {
            JSONObject o = (JSONObject) list.get(i);
//            PanTree panTree = o.toBean(PanTree.class);
            PanTree panTree = new PanTree();
            panTree.setName(o.get("server_filename").toString());
            panTree.setSize(o.get("size").toString());
            panTree.setFsId(o.get("fs_id").toString());
            panTree.setMd5(o.get("md5")!=null? o.get("md5").toString():null);
            panTree.setLocalCtime(o.get("local_ctime").toString());
            panTree.setLocalMtime(o.get("local_mtime").toString());
            panTree.setServerCtime(o.get("server_ctime").toString());
            panTree.setServerMtime(o.get("server_mtime").toString());
            panTree.setIsDir(o.get("isdir").toString());
            if ("1".equals(panTree.getIsDir())){
                panTree.setChildrens(this.getSonTree(path + "/" + panTree.getName(),nowLevel+1,maxLevel));
            }
            tree.add(panTree);
        }
        return tree;
    }

    //验证密码
    public HttpCookie verify(String password){
        String url = "https://pan.baidu.com/share/verify?surl="
                + this.getShortUrl()
                + "&web=5&app_id="
                + APPID
                + "&channel=chunlei&clienttype=5";
        HttpRequest request = HttpUtil.createPost(url);
        request.form("pwd",password);
        request.form("vcode","");
        request.form("vcode_str","");
        request.header("Referer","https://pan.baidu.com/wap/init?surl=" + this.getShortUrl());
//        request.cookie(cookies.toArray(new HttpCookie[cookies.size()]));
        HttpResponse response = request.execute();
        System.out.println(request);
        // 2 请求头错误
        // -62需要验证码
        // -12密码错误
        HttpCookie cookie = response.getCookie("BDCLND");
        if (cookie == null){
            System.out.println("错误" + response.body());
        }
        return cookie;
    }

    private String getShortUrl(){
        return this.url.substring(this.url.lastIndexOf("/") + 2);
    }
}

@Data
class PanTree{
    //文件名
    private String name;
    //文件大小
    private String size;
    //文件md5
    private String md5;
    //文件id
    private String fsId;
    //本地创建时间
    private String localCtime;
    //本地修改时间
    private String localMtime;

    //服务端创建时间
    private String serverCtime;
    //服务端修改时间
    private String serverMtime;

    //是否是目录 1是
    private String isDir;
    private List<PanTree> childrens;
}
