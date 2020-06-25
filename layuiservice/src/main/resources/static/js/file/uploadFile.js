
var fileinput = $("#file");
var fileList = {pageNumber:1,pageSize:10};
var vm=null;
$(function() {
	$("#uploadBtn").on("click", clickUpload);
	initVue();
});
//当点击上传按钮，则执行点击上传文件的input框
function clickUpload(){
	document.getElementById("file").click();
}
//监听文件上传
$("#file").change(function(e){
	uploadFile();
});

function uploadFile(){
	var file = document.getElementById("file").files[0];
	var formData = new FormData();
	formData.append("file", document.getElementById("file").files[0]);
		$.ajax({
			"url": BMS.UPLOAD_FILE,
			"data": formData,
			"type": "POST",
			"dataType": "json",
			"contentType": false,
			"processData": false,
			"success": function(json) {
				var fileData = json.result[0];
				addAttachment(fileData);
			}
		});
}

function addAttachment(fileData){
	fileData['uploadtime'] = new Date();
	fileData['uid'] = "admin";
	BMS.requestDefaultByUrlAndParam(BMS.ATTACHMENT_ADD,JSON.stringify(fileData)).then(function (result) {
		attachmentList();
    });
}

function initVue(){
	vm = new Vue({
		el: '#attachment',
		data: {
			files: []
		},
		mounted:function(){
			this.attachmentList();
		}
	});
}

function attachmentList(){
	BMS.requestDefaultByUrlAndParam(BMS.ATTACHMENT_LIST,JSON.stringify(fileList)).then(function (result) {
		var item_arr = new Array();
		for(var i = 0;i<result.rows.length;i++){
			var item = result.rows[i];
			item.show = isShow(item.id);
			item.icon = showIcon(item.filename);
			item_arr.push(item);
		}
		vm.files=item_arr;
    });
}

function isShow(authority){
	if(authority!=null){
		return true;
	}
	return false;
}

var icons={"doc":"../../images/file/doc.png"}
function showIcon(filename){
	var index=filename.lastIndexOf(".");
	var namelength=filename.length;
	var suffix = filename.substring(index+1,namelength);//后缀名
	var iconpath = icons[suffix];
	return iconpath != undefined ? iconpath : "../../images/file/doc.png";
}

function downloadFile(fileInfo){
	var path = fileInfo.filepath;
	var filename = fileInfo.filename;
	BMS.downfile(path, filename);
}

function btnRemoveFile(param){
	var id =param.id;
	var data = {id:id};
	BMS.requestDefaultByUrlAndParam(BMS.ATTACHMENT_DEL,JSON.stringify(data)).then(function (result) {
		attachmentList();
    });
}