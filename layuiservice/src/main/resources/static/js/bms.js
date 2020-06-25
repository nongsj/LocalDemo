jQuery.cookie = function (name, value, options) {
    if (typeof value != 'undefined') { // name and value given, set cookie
        options = options || {};
        if (value === null) {
            value = '';
            options.expires = -1;
        }
        var expires = '';
        if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
            var date;
            if (typeof options.expires == 'number') {
                date = new Date();
                date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
            } else {
                date = options.expires;
            }
            expires = '; expires=' + date.toUTCString(); // use expires attribute, max-age is not supported by IE
        }
        var path = options.path ? '; path=' + options.path : '';
        var domain = options.domain ? '; domain=' + options.domain : '';
        var secure = options.secure ? '; secure' : '';
        document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
    } else { // only name given, get cookie
        var cookieValue = null;
        if (document.cookie && document.cookie != '') {
            var cookies = document.cookie.split(';');
            for (var i = 0; i < cookies.length; i++) {
                var cookie = jQuery.trim(cookies[i]);
                // Does this cookie string begin with the name we want?
                if (cookie.substring(0, name.length + 1) == (name + '=')) {
                    cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                    break;
                }
            }
        }
        return cookieValue;
    }
};

(function ($) {
    $.getUrlParam = function (name) {
        var url = decodeURI(window.location.search);
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = url.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    }
})(jQuery);

(function () {
    layui.use(["layer", 'element'], function () {
        var element = layui.element,
            layer = layui.layer;
        //layer.load(2,{time: 10*1000});
    });
    Date.prototype.format = function (format) {
        if (isNaN(this.getMonth())) {
            return "";
        }
        var o = {
            "M+": this.getMonth() + 1, //month
            "d+": this.getDate(), //day
            "h+": this.getHours(), //hour
            "m+": this.getMinutes(), //minute
            "s+": this.getSeconds(), //second
            "q+": Math.floor((this.getMonth() + 3) / 3), //quarter
            "S": this.getMilliseconds() //millisecond
        }
        if (/(y+)/.test(format)) format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(format)) format = format.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return format;
    }
    // var DOMAIN = "http://localhost:8022/";
    //本地tomcat部署
    // var DOMAIN = "http://localhost:8080/layuiservice/";
    //服务器部署
     var DOMAIN = "http://39.108.63.147:8080/layuiservice/";
    //全局静态工具
    var BMS = {
        /*****************************************************************************
         * 静态属性
         *****************************************************************************/
        //用户相关
        USERLIST:DOMAIN +"user/find",
        ADDUSER:DOMAIN +"user/add",
        SAVEUSERINFO:DOMAIN +"user/save",
        DELETEUSER:DOMAIN +"user/delete",
        EXPROTUSEREXCEL:DOMAIN +"user/exprotUser",
        IMPROTUSEREXCEL:DOMAIN +"user/importUser",
        
        //文件上传下载
        UPLOAD_FILE:DOMAIN +"file/upload",
        UPLOAD_DOWNLOAD:DOMAIN +"file/download",
        FILE_DOWN:DOMAIN + "file/downloadFile",
        
        //附件相关
        ATTACHMENT_ADD:DOMAIN +"attachment/add",
        ATTACHMENT_LIST:DOMAIN +"attachment/find",
        ATTACHMENT_DEL:DOMAIN +"attachment/delete",
        /*****************************************************************************
         * 函数定义
         *****************************************************************************/
        saveUserCookie: function (username, password, token, uid, opt) {
            $.cookie(this.COOKIE_USERNAME, username, {
                path: '/',
                expires: 7
            });
            $.cookie(this.COOKIE_PASSWOED, password, {
                path: '/',
                expires: 7
            });
            $.cookie(this.COOKIE_TOKEN, token, {
                path: '/',
                expires: 7
            });
            $.cookie(this.COOKIE_UID, uid, {
                path: '/',
                expires: 7
            });
        },
        //登录检查
        checkLogin: function () {
            var username = $.cookie(this.COOKIE_USERNAME);
            var password = $.cookie(this.COOKIE_PASSWOED);
            if (username && username != "" && password && password != "") {
                return true;
            } else {
                window.location.href = "../login.html";
            }
            return false;
        },
        //点击行数据 
        nowResults: function (data) {
            if (data) {
                $.cookie('resultData', JSON.stringify(data), {
                    path: '/',
                    expires: 7
                });
            } else {
                //if(layer){layer.closeAll();}
                var results = $.cookie('resultData');
                $.cookie('resultData', null, {
                    path: '/'
                });
                return JSON.parse(results);
            }

        },
        getUserInfor: function (id) {
            var def = $.Deferred();
            var infor = null;
            if (!id || id == 'null') {
                infor = {
                    username: null,
                    role: null
                };
                def.resolve(infor);
            } else {
                //infor = JSON.parse($.cookie('OAuserinfo'));
                if (!infor) {
                    this.requestDefaultByUrlAndParam(this.SELECT_USER_INFO, JSON.stringify({
                    	id: id
                    })).then(function (data) {
                        var roleArr = [];
                        for (var i in data.result) {
                            roleArr.push(data.result[i].role_name);
                        }
                        infor = {
                        	dept_id: data.result[0].dept_id,
                            username: data.result[0].user_name,
                            role: roleArr.join(','),
                            deptname:data.result[0].dept_name
                        };
                        /*$.cookie('OAuserinfo', JSON.stringify(infor), {
                            path: '/',
                            expires: 7
                        });*/
                        def.resolve(infor);
                    }, function (err) {
                        def.reject(err);
                    });
                } else {
                    def.resolve(infor);
                }
            }
            return def;
        },
        getOperationSys: function () {
            var OS = '';
            var OSArray = {};
            var UserAgent = navigator.userAgent.toLowerCase();
            OSArray.Windows = (navigator.platform == 'Win32') || (navigator.platform == 'Windows');
            OSArray.Mac = (navigator.platform == 'Mac68K') || (navigator.platform == 'MacPPC') ||
                (navigator.platform == 'Macintosh') || (navigator.platform == 'MacIntel');
            OSArray.iphone = UserAgent.indexOf('iphone') > -1;
            OSArray.ipod = UserAgent.indexOf('ipod') > -1;
            OSArray.ipad = UserAgent.indexOf('ipad') > -1;
            OSArray.Android = UserAgent.indexOf('android') > -1;
            for (var i in OSArray) {
                if (OSArray[i]) {
                    OS = i;
                }
            }
            return OS;
        },
        logOut: function () {
            $.cookie(this.COOKIE_USERNAME, null, {
                path: '/'
            });
            $.cookie(this.COOKIE_PASSWOED, null, {
                path: '/'
            });
            window.location.href = "./login.html";
        },
        checkTextInput: function (input) {
            if ($(input).length > 0) {
                var text = $(input).val();
                if (typeof text === 'string' && text.trim() != '') return true;
            }
            return false;
        },

        //获取url参数
        getUrlParam: function (name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) return unescape(r[2]);
            return null;
        },
        downfile:function(path,name){
            var url = BMS.FILE_DOWN;
            // var path =node.getAttribute("path");
            var form = $("<form></form>").attr("action", url).attr("method", "post");
            form.append($("<input></input>").attr("type", "hidden").attr("name", "path").attr("value", path));
            form.append($("<input></input>").attr("type", "hidden").attr("name", "name").attr("value", name));
            form.appendTo('body').submit().remove();
        },
        setIframeHeight:function (iframe) {
        	if (iframe) {
	        	var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
	        	if (iframeWin.document.body) {
	        		iframe.height = iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight;
	        	}
        	}
        },
        
        requestDefaultByUrlAndParam: function (url, args, options, token) {

            var def = $.Deferred();
            var msg = -1;
            var tip = undefined;
            var shade = 0.3;
            var icon = 16;
            var type = "POST";
            var headers = null;
            var async = true;
            if (options) {
                tip = options.tip || tip;
                shade = options.shade || shade;
                icon = options.icon || icon;
                type = options.type || type;
                /*if (options.async == false) {
                    async = false;
                }
                if (options.headers == "json") {
                    headers = {
                        "content-type": "application/json",
                        "token": token
                    };
                } else {
                    headers = {
                        "token": token
                    };
				}*/
            }
            if (!url || url == "") {
                console.log("请检查请求路径");
                return;
            }
            //token
            /* if (token && headers == null) {
                 headers = {
                     "token": token
                 };
             }*/
            if ($) {
                var settings = {
                    "async": async,
                    "url": url,
                    "method": type,
                    "dataType": 'json',
                    "headers": headers,
                    "processData": true,
                    "contentType": "application/json",
                    "data": args,
                    success: function (result, textStatus) {
                        def.resolve(result);
                        //if(layer){layer.closeAll();}
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        def.reject(errorThrown);
                        //if(layer){layer.closeAll();}
                    }
                };
                $.ajax(settings);
            }
            return def;
        }
    };
    window.BMS = BMS;
})();
//
////web页面自适应移动端 
//var cw = jQuery(window).width();
//if (cw > 1280) cw = 1280;
//    var zoom = cw / 640;
//    $('head').append('<style id="htmlzoom">html{font-size:' + (zoom * 50) + 'px !important;}</style>');
//    window.addEventListener('resize', function () {
//        var cw = $(window).width();
//        if (cw > 1280) cw = 1280;
//        zoom = cw / 640;
//        $('#htmlzoom').html('html{font-size:' + (zoom * 50) + 'px !important;}');
// });

