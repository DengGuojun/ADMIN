<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.bean.*"  %>
<%@ page import="com.lpmas.framework.util.*"  %>
<%@ page import="com.lpmas.framework.web.*"  %>
<%@ page import="com.lpmas.admin.bean.*"  %>
<%@ page import="com.lpmas.admin.business.*"  %>
<%@ page import="com.lpmas.admin.config.*"  %>
<%
//AdminUserHelper adminHelper = (AdminUserHelper)request.getAttribute("AdminUserHelper");
String treeStr = (String)request.getAttribute("TreeStr");

String fromTag = ParamKit.getParameter(request, "fromTag", "");
String callbackFun = ParamKit.getParameter(request, "callbackFun", "callbackFun");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单列表</title>
<%@ include file="../include/header.jsp" %>
<link rel="stylesheet" href="/js/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="/js/zTree/js/jquery.ztree.all-3.4.min.js"></script>
<script type="text/javascript" >
	function ajaxDataFilter(treeId, parentNode, childNodes) {
	    if(childNodes) {
	      for(var i =0; i < childNodes.length; i++) {
	        //filter something.
	      }
	    }
	    return childNodes;
	};
	function zTreeOnClick(event, treeId, treeNode) {
	};
	function zTreeOnDblClick(event, treeId, treeNode) {
	};
	function zTreeBeforeExpand(treeId, treeNode) {
		if(zTreeObj==null || treeNode==null) return;
		zTreeObj.reAsyncChildNodes(treeNode, "refresh", true);
	};
	function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
		if(treeNode && treeNode.children && treeNode.children.length>0){
			for(var i=0; i<treeNode.children.length; i++){
				var child = treeNode.children[i];
				try{
					if(zTreeObj && child.size<=0){
						zTreeObj.expandNode(child, true, false, false, false);
					}
				}catch(e){}
			}
		}
	};
	function zTreeBeforeCheck(treeId, treeNode) {
		//需要时返回true
		return true; 
	};
	
	var zTreeObj;

	//check:{chkStyle: "radio",chkboxType:{ "Y" : "", "N" : ""},enable:true}, 
	setting = {
		check: {enable: true,chkStyle: "radio",radioType: "all"},
		view: {
			selectedMulti: false
		},
		async: {
			enable: true,
			type: "get",
			dataType: "text",
			url: "/admin/AdminMenuInfoTreeList.do?goAction=ajax",
			autoParam: ["id=menuId"],
			dataFilter: ajaxDataFilter
		},
		callback: {
			onClick: zTreeOnClick,
			onDblClick: zTreeOnDblClick,
			beforeExpand: zTreeBeforeExpand,
			onAsyncSuccess: zTreeOnAsyncSuccess,
			beforeCheck: zTreeBeforeCheck
		}
	};
	
	zTreeNodes = <%=treeStr %>;

	$(document).ready(function(){
		zTreeObj = $.fn.zTree.init($("#tree"), setting, zTreeNodes);
		var nodes = zTreeObj.transformToArray(zTreeObj.getNodes());
		for(var i=0; i<nodes.length; i++){
			var node = nodes[i];
			try{
				if(node.size <=0 ){
					zTreeObj.expandNode(node, true, false, false, false);
				}
			}catch(e){}
		}
	});
	
	function callbackTo(){
		var selectNodes = zTreeObj.getCheckedNodes(true);//被勾选的
		self.parent.<%=callbackFun %>(selectNodes[0].id, selectNodes[0].name);
		try{ self.parent.jQuery.fancybox.close(); }catch(e){console.log(e);}
	    try{ jQuery.fancybox.close(); }catch(e){console.log(e);}
	}
  </script>
</head>
<body class="article_bg">
<p class="article_tit">菜单列表</p>
<ul id="tree" class="ztree" style="width:230px; overflow:auto;"></ul>
<input type="button" value="确定" onClick="javascript:callbackTo();"/>
</body>
</html>