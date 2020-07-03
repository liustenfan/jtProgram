<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
	href="/js/jquery-easyui-1.4.1/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="/js/jquery-easyui-1.4.1/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="/css/jt.css" />
<script type="text/javascript"
	src="/js/jquery-easyui-1.4.1/jquery.min.js"></script>
<script type="text/javascript"
	src="/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
</head>
<body class="easyui-layout">

	<div data-options="region:'west',title:'菜单',split:true"
		style="width: 10%;">
		
		<!--规则: 引入树class="easyui-tree"
				  如果需要嵌套 标题元素需要使用<span></span>标签进行包裹
		  -->
		<ul class="easyui-tree">
			<li>
				<span>一级菜单</span> 
				<ul>
					<li>1.1 入门</li>
					<li>1.2掌握</li>
					<li>
						<span>1.3 熟练使用</span>
						<ul>
							<li>1.3.1 第一步</li>
							<li>1.3.2 第2步</li>
							<li>1.3.2 第3步</li>
						</ul>
					</li>
				</ul>
			</li>
			<li>二级菜单</li>
			<li>三级菜单</li>
		</ul>
	</div>
	<div data-options="region:'center',title:'首页'"></div>
</body>

</html>