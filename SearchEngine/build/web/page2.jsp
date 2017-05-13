<%-- 
    Document   : page2
    Created on : May 8, 2017, 4:42:56 PM
    Author     : mohammedh.mourad
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.io.File"%>
<%@page import="org.jsoup.Jsoup"%>
<%@page import="org.jsoup.nodes.Document"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    
    <style type="text/css">
        
        .span{
            
                          max-width:  92%;

        }
        #cont{
              max-width:  92%;

            
        }
        .tr{
            

            margin-left: 50px;
            margin-right: 50px;
            max-width:  92%;

        }
        .td{
            
          max-width:  92%;

        }
        table.tble{
              max-width:  92%;
             margin-left: 50px;
             margin-right:150px;
             margin-top:10px;
             background-color:white;

        }
        td.orange {
            color: blue;
        }
	#row{
/*		padding:20px;
                margin-left: 250px;
                max-width:  92%;
                margin-right:  250px;*/
    

	}
        
        #TBItem{
            
            background-color: gray;
            
        }
        
	#tfheader{
                 margin: auto;
               width: 100%;
                  padding: 10px;color: red;
                  font-size: 30px;
                  display: inline-block;

        }
	#tfnewsearch{
		float:right;
		padding:20px;
                 width:800px;
                 left: 50%;
                 right:50%;
                 margin-top: 10PX;

	}
	.tftextinput{
		margin: 0;
		padding: 5px 15px 5px 15px;
		font-family: Arial, Helvetica, sans-serif;
		font-size:14px;
		border:1px solid #0076a3; border-right:0px;
		border-top-left-radius: 5px 5px;
		border-bottom-left-radius: 5px 5px;
                width: 50%;
                left: 50%;
                 right:50%;

        }
	.tfbutton {
		margin: 0;
		padding: 5px 15px;
		font-family: Arial, Helvetica, sans-serif;
		font-size:14px;
		outline: none;
		cursor: pointer;
		text-align: center;
		text-decoration: none;
		color: #ffffff;
		border: solid 1px #0076a3; border-right:0px;
		background: #0095cd;
		background: -webkit-gradient(linear, left top, left bottom, from(#00adee), to(#0078a5));
		background: -moz-linear-gradient(top,  #00adee,  #0078a5);
		border-top-right-radius: 5px 5px;
		border-bottom-right-radius: 5px 5px;
	}
	.tfbutton:hover {
		text-decoration: none;
		background: #007ead;
		background: -webkit-gradient(linear, left top, left bottom, from(#0095cc), to(#00678e));
		background: -moz-linear-gradient(top,  #0095cc,  #00678e);
	}
	/* Fixes submit button height problem in Firefox */
	.tfbutton::-moz-focus-inner {
	  border: 0;
	}
	.tfclear{
		clear:both;
	}
</style>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Google</title>
    </head>
    <body style="             background-color:rgb(246,246,246);
">
	<!-- HTML for SEARCH BAR -->
	<div id="tfheader">
            
     <div style="float:left;">

                <h3 style="margin-left:50px">Google</h3>

     </div>

            <div  style="float:left;">
                <form id="tfnewsearch" method="get" action="/firstApp/NewServlet">
                   <input type="text" value="${SS}" class="tftextinput" name="q" size="21" maxlength="500"><input type="submit" value="search" class="tfbutton">
		</form>
                
                </div>

<!--	<div class="tfclear"></div>-->
	</div>


                <div id="cont">                        
            <%
            
             ArrayList<String> links =(ArrayList<String>) request.getAttribute("links");
             ArrayList<String> titles = (ArrayList<String>)request.getAttribute("titles");
             
             for(int i=0;i<links.size();i++){ %>

        <table class="tble" style="float:center;">
             
                 
            

                
                
<!--            %>-->

                <tr>
                    <td class="orange"><a href="<%= links.get(i)%>"><%= titles.get(i)%></a></td>

                </tr>
                   

                <tr id="row"><td><span>s senfjkwdnfnksd senfjkwdnfnksd senfjkwdnfnksd enfjkwdnfnksd senfjkwdnfnksd senfjkwdnfnksd nfkdnknfkdsnknfkd senfjkwdnfnksd nfkdnknfkdsnknfkd sknfkdsnkn fkdskf senfjkwdnfnksd nfkdnknfkdsnknfkd sknfkdsnkn fkdskf sknfkdsnkn fkdskfdsenfjkwdnfnksdnfkdnk nfkdsnskfdsenfjkwdnf nksdnfkdnknfkdsnknfkdskfds vs dv f v ds  vs dv f v ds v v</span></td>
                </tr>
        </table>
                                    <% } %>

</div>

</body>


</html>
