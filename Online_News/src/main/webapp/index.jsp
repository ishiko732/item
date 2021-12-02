<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="web.User"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
<style>
#lunbo{
    width: 1000px;
    height: 400px;
    margin: 0 auto;
    position: relative;
    overflow: hidden;
}

#div{
    width: 1000px;
    height: 400px;
    display: flex; 				<!-- 子元素自动成为容器成员-->
    justify-content: left;   <!-- 主轴对齐 -->
    align-items: center;          <!-- 侧轴（纵轴）方向上的对齐方式。-->
    background-color: rgb(204, 47, 47);
    position: relative;
    left: 0px;
}

#lunbo div img{
    width: 1000px;
    height: 400px;
    cursor: pointer;
}

#lunbo ul li{
    width: 20px;
    height: 20px;
    border-radius: 50%;
    list-style: none;
    float: left;
    border: 2px solid rgb(79, 123, 247);
    margin-left: 10px;
    cursor: pointer;
    position: relative;
    top: -50px;

}
.selectli{
    background-color: rgb(84, 215, 233);
}
a{text-decoration:none}
</style>
</head>
<body>
 <% 
 		User user = (User) session.getAttribute("user");
		 if (user ==null) {
			response.getWriter().print("<div style='display:inline;margin-left:200px;'>您还没有登录，请<a href='/web/login.jsp'>登录</a></div>");
		} 
		else {
     		response.getWriter().print("<div style='display:inline;margin-left:200px;'>您已登录，欢迎你，" + user.getUsername()  + "！</div>");
			response.getWriter().print("<a href='/web/LogoutServlet'>退出</a>");
			// 创建Cookie存放Session的标识号
			Cookie cookie = new Cookie("JSESSIONID", session.getId());
			cookie.setMaxAge(60 * 30);
			cookie.setPath("/web");
			response.addCookie(cookie);
		}
 %>
 	<div style="display:inline;float:right;" >
 		<div id='tie' style="display:inline" ></div> 
 		<div id='wh'  style="display:inline;margin-left:30px;margin-right:100px;" ></div>
	</div>
<div align="center" style="background-color:#FFD700">
 	<div style="margin-bottom:20px;width:60%;height:60px;" >
 	<br>
 		<div style="display:inline;float:left;border-width: 0px 1px 0px 2px;border-style: solid; border-color: black;"><font size='5px'><a href='#历史简介'>&nbsp历史简介&nbsp</a></font></div>
 		<div style="display:inline;float:left;border-width: 0px 0px 0px 2px;border-style: solid; border-color: black;" ><font size='5px'><a href='#发展历程'>&nbsp发展历程&nbsp</a></font></div>
 		<div style="display:inline;float:left;border-width: 0px 0px 0px 2px;border-style: solid; border-color: black;"><font size='5px'><a href='#伟大人物'>&nbsp伟大人物&nbsp</a></font></div>
 		<div style="display:inline;float:left;border-width: 0px 0px 0px 2px;border-style: solid; border-color: black;"><font size='5px'><a href='#红色精神'>&nbsp红色精神&nbsp</a></font></div>
 		<div style="display:inline;float:left;border-width: 0px 2px 0px 2px;border-style: solid; border-color: black;"><font size='5px'><a href='#联系我们'>&nbsp联系我们&nbsp</a></font></div>
 		<div style="display:inline;margin-left:30px;">&nbsp </div>
 	</div>
</div>

<div align="center" style="background-color:#FF5151;" >
	<div style="float:left;">
		<div style="margin-top:500px;"><img src="img\8.jpg " width=200px></div>
		<div style=" margin-top:10px;">党标</div>
	</div>
	<div style="float:right;">
		<div style="margin-top:1400px;margin-right:30px;"><img src="img\9.jpg " width=300px></div>
		<div style=" margin-top:10px;">毛泽东等历史人物</div>
	</div>
	<div style="width:60%;background-color: #FCFCFC;padding:0px 15px 10px 15px	" >
 	<div style="background-color: #FF5151;">
 		<div id="lunbo" >
        	<div id="div">
            	<img src="img\1.jpg" alt="1" >
            	<img src="img\2.jpg" alt="2" >
            	<img src="img\3.jpg" alt="3" >
            	<img src="img\4.jpg" alt="4" >
            	<img src="img\5.jpg" alt="5" >
        	</div>
       		<ul id="ul">
            
       		</ul>
    	</div>
    </div>
 	<div style="" id='历史简介'>
 		<br>
 		<br>
 		<div style="display:inline;"><h1><a onclick="a(this)" href='Subpage' style="margin-left:180px" id="中国共产党">中国共产党</a><font size=1px>&nbsp&nbsp&nbsp&nbsp点击标题可看详细>>></font></h1></div>
 		<div style="text-align:left; ">
 			&nbsp&nbsp简称中共，中国共产党创建于1921年，中国共产党破天荒第一次提出反帝反封建的革命纲领。
		<br>&nbsp&nbsp1924年，在国共合作的条件下，中国共产党掀起第一次国民革命高潮，但由于大资产阶级的叛变和党内发生投降主义的错误，这次革命遭到失败。
		<br>&nbsp&nbsp1927年大革命失败后，中国共产党实行土地革命和武装起义的总方针。以毛泽东为代表的中国共产党人，开辟农村包围城市、武装夺取政权的道路，掀起了第一次土地革命，最后以长征结束告终。
		<br>&nbsp&nbsp1937年，日本发动全面的侵华战争，国共第二次合作，党领导敌后军民坚决同日本侵略者浴血奋战，经过八年的艰苦奋斗，终于取得了抗日战争的伟大胜利。
		<br>&nbsp&nbsp1946年6月底，国民党悍然发动全面内战，人民解放军在中国共产党的领导下，经过三年解放战争，取得了反帝反封建的新民主主义革命胜利，建立新中国。
		<br>&nbsp&nbsp建国后，党领导各族人民治愈战争创伤，使国民经济得到全面恢复，在党的总路线指引下，我国大规模地开展了社会主义改造，建立了崭新的社会主义制度，1956年社会主义改造的基本完成。
		<br>&nbsp&nbsp1956年后，我国在社会主义建设中经历了许多曲折和失误，1978年党的十一届三中全会，才将党和国家工作的着重点转移到现代化建设上，从而实现了新中国成立以来党和国家历史上具有深远意义的伟大转折。
		<br>&nbsp&nbsp中国共产党是中国工人阶级的先锋队，同时是中国人民和中华民族的先锋队，是中国特色社会主义事业的领导核心，代表中国先进生产力的发展要求，代表中国先进文化的前进方向。</div>
	</div>
	
	
 	<div id="发展历程" style="">
 		<br>
 		<br>
 		<div><h1>发展历程</h1></div>
 		<div><div style="text-align:left; ">&nbsp&nbsp中国共产党走过了整整九十年光辉的历程。九十年来，党团结带领全国各族人民进行了艰苦卓绝的奋斗，战胜了各种艰难险阻，取得了令世人瞩目的伟大的成就。</div></div>
 		<div style="display:inline;float:left;"><img src="img\6.jpg" ></div>
 		<div style="display:inline;">
 			<div style="text-align:left; ">&nbsp&nbsp根据《中国共产党简史》2001年版本资料简介，中共建党90多年来共经历了如下十<br>&nbsp&nbsp个主要阶段：</div>
 			<div>&nbsp</div>

 			<table border="1px" width="500px">
 				<tr>
 					<td>1、大革命阶段</td>
 				</tr>
 				<tr>
 					<td>2、土地革命阶段</td>
 				</tr>
 				<tr>
 					<td>3、抗战阶段 </td>
 				</tr>
 				<tr>
 					<td>4、民主革命阶段</td>
 				</tr>
 				<tr>
 					<td>5、新民主主义阶段</td>
 				</tr>
 				<tr>
 					<td>6、社会主义建设阶段</td>
 				</tr>
 				<tr>
 					<td>7、文化大革命阶段</td>
 				</tr>
 				<tr>
 					<td>8、十一届三中全会后的社会主义发展新时期</td>
 				</tr>
 				<tr>
 					<td>9、建设有中国特色社会主义阶段 </td>
 				</tr>
 				<tr>
 					<td>10、改革开放和现代化建设新阶段</td>
 				</tr>
 			</table>

 		</div>
 		<div style="display:inline;">&nbsp  </div>
 		<div><div style="text-align:left; ">&nbsp&nbsp中国共产党的诞生是中国革命发展的客观需要，是马克思主义同中国工人运动相结合的产物。1840年鸦片战争以后，半殖民地半封建社会。从鸦片战争到五四运动，中国人民为了反对帝国主义和封建统治进行了英勇不屈的斗争，其中主要的是太平天国农民战争和资产阶级领导的辛亥革命，但都相继失败了。历史证明，中国的农民阶级和民族资产阶级由于他们的历史局限性和阶级局限性，都不能领导民主革命取得胜利</div></div>
 	</div>
 	
 	<div id="伟大人物" style="">
 		<br>
 		<br>
 		<div><h1>伟大人物</h1></div>
 		<div>
 			<table style="border-style:solid; " rules=rows>
				<tr>
					<th width="20px">伟人</th>
					<th  Colspan=2>简介</th>
					<th Colspan=2>评价</th>
				</tr>
				<tr >
					<td width="50px" >李大钊</td>
					<td>&nbsp  </td>
					<td>&nbsp&nbsp李大钊（1889年10月29日-1927年4月28日），字守常，河北乐亭人。1907年考入天津北洋法政专门学校 ，1913年毕业后东渡日本，入东京早稻田大学政治本科学习。</td>
					<td>&nbsp  </td>
					<td>&nbsp&nbsp李大钊同志是中国共产主义的先驱，伟大的马克思主义者、杰出的无产阶级革命家、中国共产党的主要创始人之一，他不仅是我党早期卓越的领导人，而且是学识渊博、勇于开拓的著名学者，在中国共产主义运动和民族解放事业中，占有崇高的历史地位。</td>
				</tr>
				<tr>
					<td width="50px" >毛泽东</td>
					<td>&nbsp  </td>
					<td>&nbsp&nbsp毛泽东（1893年12月26日-1976年9月9日），字润之（原作咏芝，后改润芝），笔名子任。湖南湘潭人。中国人民的领袖，伟大的马克思主义者，无产阶级革命家、战略家和理论家，中国共产党、中国人民解放军和中华人民共和国的主要缔造者和领导人，诗人，书法家。 </td>
					<td>&nbsp  </td>
					<td>&nbsp&nbsp1949至1976年，毛泽东担任中华人民共和国最高领导人。他对马克思列宁主义的发展、军事理论的贡献以及对共产党的理论贡献被称为毛泽东思想。因毛泽东担任过的主要职务几乎全部称为主席，所以也被人们尊称为“毛主席”。</td>
				</tr>
				<tr>
					<td>周恩来</td>
					<td>&nbsp  </td>
					<td>&nbsp&nbsp周恩来 ，原籍浙江绍兴，1898年3月5日生于江苏淮安。1921年加入中国共产党，是伟大的马克思主义者，伟大的无产阶级革命家、政治家、军事家、外交家，党和国家主要领导人之一，中国人民解放军主要创建人之一，中华人民共和国的开国元勋。</td>
					<td>&nbsp</td>
					<td>&nbsp&nbsp1976年1月8日在北京逝世。他的逝世受到极广泛的悼念。由于他一贯勤奋工作，严于律己，关心群众，被称为“人民的好总理”。他的主要著作收入《周恩来选集》</td>
				</tr>
				<tr>
					<td>邓小平</td>
					<td>&nbsp</td>
					<td>&nbsp&nbsp邓小平（1904年8月22日-1997年2月19日），原名邓先圣，学名邓希贤，四川广安人。从土地革命、抗日战争到解放战争，先后担任党和军队的重要领导职务，为党中央重大战略决策的实施，为新民主主义革命的胜利和新中国的诞生，建立了赫赫功勋，成为中华人民共和国的开国元勋。</td>
					<td>&nbsp</td>
					<td>&nbsp&nbsp邓小平是全党全军全国各族人民公认的享有崇高威望的卓越领导人，伟大的马克思主义者，伟大的无产阶级革命家、政治家、军事家、外交家，久经考验的共产主义战士，中国社会主义改革开放和现代化建设的总设计师，中国特色社会主义道路的开创者，邓小平理论的主要创立者</td>
				</tr>
				<tr>
					<td Colspan=5 align=right>等众多伟人，为我们中华民族付出了伟大贡献，我们都要学习他们的伟大精神！</td>
				</tr>
 			</table>
 		</div>
 	</div>

 	<div id="红色精神" style="height:417px">
 		<br>
 		<br>
 		<div><h1>红色精神</h1></div>
 		<div style="display:inline;float:right"><img src="img/7.jpg"  ></div>
 		<div style="display:inline;"><div style="text-align:left; ">&nbsp&nbsp红色精神是指中国共产党领导中国人民在革命、建设、改革各个时期所形成的伟大革命精神。在中国共产党100年的历史中，形成了很多可歌可泣的“红色精神”。
 			<br>&nbsp&nbsp红色精神已经深深融入中华民族的血脉和灵魂，成为鼓舞和激励中国人民不断攻坚克难、不断前进的强大精神动力。如“军民团结、艰苦奋斗”的井冈山精神，“不怕艰难险恶”的长征精神，“改变作风、提高素质”的延安精神，“艰苦奋斗、勇于开拓”的北大荒精神，“谦虚谨慎、戒骄戒躁、艰苦奋斗”的西柏坡精神，“自力更生、艰苦奋斗、勇攀科学高峰”的两弹一星精神，还有红船精神、抗战精神、大庆精神、抗洪精神、抗震救灾精神、抗疫精神 等等。</div></div>
 		<br>
 		<div> <div style="text-align:left; ">例如：<br><br>井冈山精神:军民团结、艰苦奋斗<br><br>长征精神:&nbsp不怕艰难险恶<br><br>延安精神:&nbsp改变作风、提高素质</div></div>
 	</div>
 </div>
 </div>
 
 <div id="联系我们" style="text-align:center;background-color:#FFD700">
 	<div style="display:inline;margin-left:150px"><font size='4px'>联系我们</font></div>
 	<div style="display:inline;margin-left:200px;"><font size='4px'>电话：13790107821</font></div>
 	<div style="display:inline;margin-left:200px;"><font size='4px'>邮箱:1512910839@qq.com</font></div>
 	<div style="display:inline;margin-left:300px;"><font size='4px'>图片文字均参考百度百科，若有侵权，请联系我们。谢谢！</font></div> 
 </div>
 	
 
 
 
 
<script type="text/javascript">

//显示时间问候语
	function time(){
 		dt = new Date();
 		var y=dt.getFullYear();
		var h=dt.getHours();
		var m=dt.getMinutes();
		var s=dt.getSeconds();
 		document.getElementById("tie").innerHTML="当前时间："+y+"年"+h+"时"+m+"分"+s+"秒";
    	if (h >= 0 && h <= 6) {
    		document.getElementById("wh").innerHTML = "现在是凌晨，你该继续睡觉！";
    	} else if(h > 6 && h < 12) {
    		document.getElementById("wh").innerHTML = "早上好！记得吃早餐！";
   	 	} else if(h>= 12 && h<= 13){
    		document.getElementById("wh").innerHTML = "中午好，吃午餐了吗？";
    	} else if(h > 13 && h<= 18){
   		 	document.getElementById("wh").innerHTML = "下午好！今天过得愉快吗？";
    	} else if(h >18 && h<= 24){
   	 		document.getElementById("wh").innerHTML = "晚上好！请准时休息哦！";
    	} else{
    		alert("错误！！！");
    	}
 		setTimeout(time,1000);    //每隔一秒执行一次time（）
		}
	
//判断是否登录
	function a(obj){
		var url=obj.id;
		var url1=document.getElementById(url).attributes["href"].value;
		//alert(url1) 测试能不能拿到网址
		document.getElementById(url).href = "#"; 
		window.location.href ="/web/State?url="+url1; 
	}
	

//轮播图加 调用时间函数
    window.onload=function(ev){
	time();
	// 函数获取id
    function $(id){
        return typeof id==="string"?document.getElementById(id):null;
    }

    
	// obj--对象
	// target--步长
	// callback--回调函数

	function animate(obj,target,callback){
   	 	clearInterval(obj.timer);     // 取消由 setInterval() 设置的 timeout。
    	obj.timer=setInterval(function(){
        	//步长值写到定时器里面
        	var step=(target-obj.offsetLeft)/10;
        	step=step>0?Math.ceil(step):Math.floor(step);//大于0 先上取整，小于先 下
        	if(obj.offsetLeft==target){
            	//停止动画 本质是停止定时器
            	clearInterval(obj.timer);
           	 	//回调函数写到定时器结束里面
           		 if(callback){
                	//调用函数
                	callback();
            	}
        	}
        	//每次都增加一个步长值  缓动动画步长公式：（目标值-现在的位置）/10
        	obj.style.left=obj.offsetLeft+step+'px';
    	},15)
	}

	//轮播图的实现

    //1、获取标签
    var box3=$('lunbo');     //表示获取id=“lunbo"的元素，调用了window中的函数
    var div=$('div');
    var allimg=box3.getElementsByTagName('img');
    var allul=$('ul');
    
    

    //2、克隆li标签
    $('div').appendChild(allimg[0].cloneNode(true));

    //3、动态创建页码
    for(var i=0;i<allimg.length-1;i++){
        var li=document.createElement('li');
        $('ul').appendChild(li);
    }
    //首个被选中
    $('ul').children[0].className='selectli';


    //监听鼠标进入页码实现跳转
    var ulli=$('ul').children;
    for(var i=0;i<ulli.length;i++){
        (function(i){
            var u=ulli[i];
        	u.onmouseover=function(){
            	for(var j=0;j<ulli.length;j++){
               	 	ulli[j].className='';
            	}
            this.className='selectli';
            animate(div,-1000*i);
        }
        })(i);

    }

    // 自动播放功能
    var num=0;
    var index=0;
    var timer=setInterval(function(){
        num++;
        //圆圈跟上图片
        for(var i=0;i<ulli.length;i++){
            ulli[i].className='';
        }
        if(num==6){
            num=0;
            div.style.left=0;
        }
        index=num;
        ulli[index].className='selectli';
        animate(div,-1000*num);

    },2000)
    
	// 鼠标进入停止播放器，离开继续播放
    div.onmouseover=function(){
        clearInterval(timer);
    }
    div.onmouseout=function(){
        timer=setInterval(function(){
            num++;
        
        //圆圈跟上图片
        for(var i=0;i<ulli.length;i++){
            ulli[i].className='';
        }
        if(num==6){
            num=0;
            div.style.left=0;
        }
        index=num;
        ulli[index].className='selectli';
        
        
        animate(div,-1000*num);

    },2000)
    }
    
}
</script>
</body>
</html>