<%-- 
    Document   : index
    Created on : May 8, 2017, 4:34:53 PM
    Author     : mohammedh.mourad
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    
    <style type="text/css">
    
    #SearchEngine {
/*    display: table-cell;
    position: relative;
    width:100%;
    height: 100px;
    background-color: rgba(89,144,222,.6);
    vertical-align:middle;*/
                     margin: auto;
               width: 50%;
                  padding: 10px;

}

#SearchEngine h2{
    text-shadow: 2px 2px black;
    color: red;
    font-size: 100px;
    text-align: center;
    font-family:"Impact";
} 

/*    	#SearchEngine{
                             margin: auto;
               width: 50%;
                  padding: 10px;
                  background: red;

            
        }
    #SearchEngine .h2{
        
                margin: auto;
                width: 50%;
                  padding: 10px;
                  font-size: 500px;
    }*/
	#tfheader{
                 margin: auto;
               width: 50%;
                  padding: 10px;
        }
	#tfnewsearch{
		float:right;
		padding:20px;
                 width:500px;
                 left: 50%;
                 right:50%;

	}
	.tftextinput{
		margin: 0;
		padding: 5px 15px 5px 15px;
		font-family: Arial, Helvetica, sans-serif;
		font-size:14px;
		border:1px solid #0076a3; border-right:0px;
		border-top-left-radius: 5px 5px;
		border-bottom-left-radius: 5px 5px;
                width: 70%;
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
        <title>JSP Page</title>
    </head>
<body>
	<!-- HTML for SEARCH BAR -->
        <div id="SearchEngine">
            <h2>Google</h2>
        </div>
	<div id="tfheader">
		<form id="tfnewsearch" method="get" action="/SearchEngine/NewServlet">
		        <input type="text" class="tftextinput" name="q" size="21" maxlength="500"><input type="submit" value="search" class="tfbutton">
		</form>
	<div class="tfclear"></div>
	</div>
</body>
</html>
