<%@page import="java.net.CookieStore"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ page import="com.shoppinglist.entity.Items" %>
<%@ page import="com.shoppinglist.dao.ItemsDao" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'details.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
    <style type="text/css">
	   div{
	      float:left;
	      margin-left: 30px;
	      margin-right:30px;
	      margin-top: 5px;
	      margin-bottom: 5px;
	   }
	   div dd{
	      margin:0px;
	      font-size:10pt;
	   }
	   div dd.dd_name
	   {
	      color:blue;
	   }
	   div dd.dd_city
	   {
	      color:#000;
	   }
	</style>
  </head>
  
  <body>
    <h1>商品详情</h1>
    <hr>
    <center>
      <table width="750" height="60" cellpadding="0" cellspacing="0" border="0">
        <tr>
          <!-- 商品详情 -->
          <%
          	ItemsDao itemsDao = new ItemsDao();
          	Items item = itemsDao.getItemsById(Integer.parseInt(request.getParameter("id")));
          	if(item!=null){
          %>
          <td width="70%" valign="top">
             <table>
               <tr>
                 <td rowspan="4"><img src="images/<%=item.getPicture() %>" width="200" height="160"/></td>
               </tr>
               <tr>
                 <td><B><%=item.getName() %></B></td> 
               </tr>
               <tr>
                 <td>产地：<%=item.getCity() %></td>
               </tr>
               <tr>
                 <td>价格：￥ <%=item.getPrice() %></td>
               </tr> 
             </table>
          </td>
          <%
          	}
          %>
          <!-- 浏览过的商品 -->
          <td width="30%" bgcolor="#EEE" align="center">
             <br>
             <b>您浏览过的商品</b><br>
             <%
             	String list="";
             //读取Cookie对象,从客户端获得Cookie集合
             	Cookie[] cookies = request.getCookies();
             //遍历Cookie集合
             if(cookies!=null&&cookies.length>0){
             	for(Cookie c:cookies){
             		if(c.getName().equals("ListViewCookie")){
             			list = c.getValue();
             		}
             	}
             }
             	list+=request.getParameter("id")+",";
             //如果浏览记录超过10条，则系统自动清空浏览记录
             String[] arr = list.split(",");
             if(arr!=null&&arr.length>0){
            	 if(arr.length>=10){
            		 list="";
            	 }
             }
             Cookie cookie = new Cookie("ListViewCookie",list);
             response.addCookie(cookie);
             %>
             <!-- 循环开始 -->
             <%
             	ArrayList<Items> itemList = itemsDao.getListView(list);
             	if(itemList!=null&&itemList.size()>0){
             		for(Items i : itemList){
             %>
             <div>
             <dl>
               <dt>
                 <a href="details.jsp?id=<%=i.getId() %>"><img src="images/<%=i.getPicture()  %>" width="120" height="90" border="1"/></a>
               </dt>
               <dd class="dd_name"><%=i.getName() %></dd> 
               <dd class="dd_city">产地:<%=i.getCity() %>&nbsp;&nbsp;价格:￥<%=i.getPrice() %></dd> 
             </dl>
             </div>
             <%
             		}
             	}
             %>
             <!-- 循环结束 -->
          </td>
        </tr>
      </table>
    </center>
  </body>
</html>
