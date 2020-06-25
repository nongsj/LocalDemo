var data = {
		pageNumber:1,
		pageSize:10
};
$(function() {
	showList();
});

//获取数据
function showList(){
	 BMS.requestDefaultByUrlAndParam(BMS.USERLIST,JSON.stringify(data)).then(function (result) {
		 var list = result.rows;
		 console.log(list);
		 showTbale(list);
     });
}

//渲染表格
function showTbale(list){
	//colspan	单元格所占列数（默认：1）。一般用于多级表头
	//rowspan	单元格所占行数（默认：1）。一般用于多级表头
	layui.use('table', function(){
		  var table = layui.table;
		  table.render({
		    elem: '#complex_table',
		    data:list,
		    limit:list.length,
		    cols: [
		    	//一级表头
		    		[
		    			{rowspan:2,title: '账号',field:'userId',align:"center"},
		    			{rowspan:2,title: '用户名',field:'userName',align:"center"},
		    			{title: '职位',colspan:2,align:"center"},
		    			{title: '序号',colspan:1,align:"center"},
		    			{title: '称呼',colspan:2,align:"center"}
		      		],
		      		//二级表头
		      		[
		    			{rowspan:2,title: '级别',field:'userPosition',align:"center"},
		    			{rowspan:2,title: '职位类别',field:'userJobCategory',align:"center"},
		    			{rowspan:1,title: '序号',field:'userJobNumber',align:"center"},
		    			{rowspan:2,title: '小名',field:'userNamePy',align:"center"},
		    			{rowspan:2,title: '昵称',field:'userNameZm',align:"center"}
		      		]
		    	]
		  });
		});
}



