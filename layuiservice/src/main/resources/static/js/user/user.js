//新增用户与修改用户标识,新增时弹出是无数据的，而修改时，就给表单赋值
var adds;
//查询记号,当等于1 ，回车键时属于单向搜索，当等于而时，属于高级查询。
var searchtype = 1;
var loadedPage = true;
var data = {
		pageNumber:1,
		pageSize:10
};
var table;
$(function() {
	showUserList();
	$("#add-btn").on("click", addBtn);
	$("#search-btn").on("click", userSearch);
	$("#precise-query-btn").on("click", btnSeniorQuery_click);
	$("#user-Senior-Search").on("click", userSeniorSearch);
	$("#batch-del-btn").on("click", batchDelUser);
	$("#exportExcel").on("click", exprotUserExcel);
	$("#importExcel").on("click", importUserExcel);
});

//获取数据
function showUserList(){
	 BMS.requestDefaultByUrlAndParam(BMS.USERLIST,JSON.stringify(data)).then(function (result) {
		 var userData = result.rows;
		 var totalCount = result.total;
		 showUserTbale(userData);
		 if(loadedPage){
			 userPage(totalCount);
         }
     });
}

//渲染表格
function showUserTbale(userData){
	layui.use('table', function(){
		  table = layui.table,
		  form = layui.form;
		  table.render({
		    elem: '#user-table',
		    data:userData,
		    limit:userData.length,
		    cols: [[
		      {type: 'checkbox'},
		      //{field:'indexs', title: '序号',align:"center"},
		      {field:'id', title: '账号',align:"center"},
		      {field:'userName', title: '用户名',align:"center"},
		      {title:'操作', toolbar: '#bar-btn',align:"center"}
		    ]]
		  });
		  //表格行操作
		  table.on('tool(user)', function (obj) {
              if (obj.event === 'del') {
            	  top.layer.confirm('是否删除?', {
                      icon: 3,
                      offset: '100px',
                      title: '提示'
                  }, function (index) {
                	  var datas = obj.data;
                 	 	deleteUser(datas);
                      top.layer.closeAll();
                  });
              } else if (obj.event === 'edit') {
            	  adds = '修改用户';
            	  var result = obj.data;
            	  form.val("resultForm", result);
            	  addUserOpen(result);
              } 
          });
		});
}

function addBtn(){
	adds = '新增用户';
	addUserOpen();
}
//弹窗
function addUserOpen(){
	layer.open({
	type: 1,
	area: ['380px', '500px'], //宽高
	title :adds,
	content: $('#add'),//添加的form表单在add.html的页面中
	offset: '100px',
	btn: ['确定', '取消'],
	yes: function(index, layero){
		layer.closeAll();
		if(adds == '新增用户'){
			userAdd();
		}else{
			updateUserInfo();
		}
	},
	end:function(){
		document.getElementById("user-form").reset();
	}
	});

}

function userAdd(){
	var us = $("#user-form").serializeObject();
	BMS.requestDefaultByUrlAndParam(BMS.ADDUSER,JSON.stringify(us)).then(function (result) {
		layer.msg("添加成功!");
		showUserList();
    });
}

function deleteUser(datas){
	BMS.requestDefaultByUrlAndParam(BMS.DELETEUSER,JSON.stringify(datas)).then(function (result) {
		layer.msg("删除成功!");
		showUserList();
    });
}

function batchDelUser(){
	layui.use('table', function(){
		var checkStatus = table.checkStatus("user-table");
		top.layer.confirm('是否删除?', {
            icon: 3,
            offset: '100px',
            title: '提示'
        }, function (index) {
        	for(var i=0;i<checkStatus.data.length;i++){
    			var datas = checkStatus.data[i];
    			console.log(datas);
    			deleteUser(datas);
            }
        });
		
	});
}

function updateUserInfo(){
	var us = $("#user-form").serializeObject();
	BMS.requestDefaultByUrlAndParam(BMS.SAVEUSERINFO,JSON.stringify(us)).then(function (result) {
		layer.msg("修改成功!");
		showUserList();
    });
}
//获取表单数据
$.fn.serializeObject = function () {
		var ct = this.serializeArray();
		var obj = {};
		    $.each(ct, function () {
		        if (obj[this.name] !== undefined) {
		            if (!obj[this.name].push) {
		                obj[this.name] = [obj[this.name]];
		            }
		            obj[this.name].push(this.value || "");
		        } else {
		            obj[this.name] = this.value || "";
		        }
		    });
	return obj;
};

//分页
function userPage(totalCount) {
	loadedPage = false;
    layui.use('laypage', function () {
        var laypage = layui.laypage; //分页
        laypage.render({
            elem: 'user-page',
            count: totalCount,
            limit: 10,
            limits: [10, 20, 30, 40, 50, 60],
            layout: ['count', 'prev', 'page', 'next', 'limit', 'refresh', 'skip'],
            jump: function (obj, first) {
                //点击非第一页页码时的处理逻辑。比如这里调用了ajax方法，异步获取分页数据
            		if (!first) {
            			data.pageNumber = obj.curr;
            			data.pageSize = obj.limit;
            			showUserList();
                    }
            }
        });
    });
}

//普通查询
function userSearch(){
	searchtype = 1;
	var txtUserNameSearch = $("#txt-search").val().trim();
	data['userName'] = txtUserNameSearch;
	loadedPage = true;
	showUserList();
}
//当监测到回车时。
$(document).keydown(function (event) {
    if (event.keyCode == 13) {
    	if(searchtype == 1){
    		userSearch();
    	}else{
    		userSeniorSearch();
    	}
    }
});
//当展开高级查询时.
function btnSeniorQuery_click() {
    if($(".resultSeniorQuery").is(":hidden")){
    	searchtype = 2;
    	$(".resultSeniorQuery").show();
    	 $("#txt-search").attr("disabled","disabled");
    		$("#txt-search").val("");
    		$("#search-btn").attr("disabled","disabled");
    	    layui.use('laydate', function () {
    	    	var laydate = layui.laydate;
    	        laydate.render({
    			    elem: '#createdAt'
    			    ,type: 'date'
    			    ,range: '至'
    			    ,format: 'yyyy-MM-dd'
    			  });
    	    });
    }else{
    	$(".resultSeniorQuery").hide();
    	$("#txt-search").attr("disabled",null);
    	$("#search-btn").attr("disabled",null);
    	searchtype = 1;
    }
}
//高级查询
function userSeniorSearch(){
	var createdAt = $("#createdAt").val().trim();
	var datesplit = createdAt.split("至");
	var sTime = datesplit[0];
	var eTime = datesplit[1];
	if(datesplit != ""){
		sTime = datesplit[0].trim();
		eTime = datesplit[1].trim();
	}else{
		sTime = "";
		eTime = "";
	}
	
	var userId = $("#userId").val().trim();
	var userName = $("#userName").val().trim();
	//var documentType=$("#txtMeetingType option:selected").val();
	var userAccounts = $("#userAccounts").val().trim();
	
	//模糊查询
	loadedPage = true;
	data['userId'] = userId;
	data['userName'] = userName;
	data['userAccounts'] = userAccounts;
	data['sTime'] = sTime;
	data['eTime'] = eTime;
	showUserList();
}

function exprotUserExcel(){
	window.location.href = BMS.EXPROTUSEREXCEL;
	layer.msg("成功导出", {offset: '100px'});
}

function importUserExcel(){
	document.getElementById("file").click();
}

//监听文件上传
$("#file").change(function(e){
	uploadFile();
});

function uploadFile(){
	var formData = new FormData();
	formData.append("file", document.getElementById("file").files[0]);
		$.ajax({
			"url": BMS.IMPROTUSEREXCEL,
			"data": formData,
			"type": "POST",
			"dataType": "json",
			"contentType": false,
			"processData": false,
			"success": function(json) {
				console.log(json);
				layer.msg("成功导入", {offset: '100px'});
				loadedPage = true;
				showUserList();
			}
		});
}
