<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
  <head>
    <style type="text/css">
    	h3,h5 {
    		margin:auto;
    		text-align:center;
    	}
    	p {
    		margin-left:auto;
    		margin-right:auto;
    	}
    	.intro{
    		font-size:0.75em;
    	}
    	
    </style>
  </head>
  
  <body>
    	<h3>${news.title }</h3>
    	<h5>来源：${news.source }&nbsp;作者：${news.author }</h5>
    	<p class="intro">简介：${news.intro }</p>
    	<p>${news.content }</p>
  </body>
</html>
