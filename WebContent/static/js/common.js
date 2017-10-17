$(document).ready(function() {
	// 动态给loading层计算高度
	var loadheight = document.documentElement.clientHeight;
	loadheight = loadheight / 2;
	//setInterval(resultLoadingSteat,200);
	$(".spinner").css("top", loadheight);

	// 左侧导航栏
	$("#menu").mmenu({
		"classes" : "mm-light",
		/*dragOpen : {
			open : true
		}*/
	}, {
		transitionDuration : 600
	});

	$("#menu").mmenu().on("opening.mm", function() {
		// 打开遮罩
		$("#uifoot").hide();
		$("#showHide").hide();
	}).on("closed.mm", function() {
		// 去除遮罩
		$("#uifoot").show();
		$("#showHide").show();
	});
	//给链接和按钮增加loading效果
	$("a[href][disload!=true],button[onclick][disload!=true],span[onclick][disload!=true],td[onclick][disload!=true],label[onclick][disload!=true]").click(function() {
		loadheight = document.documentElement.clientHeight;
		loadheight = loadheight / 2;
		$(".spinner").css("top", loadheight);
		$(".spinner").show();
	});
});
/**
 * 类似a标签下的href效果
 * @param url 跳转地址
 */
function href(url) {

	location.href = url;
}
/*function resultLoadingSteat()
{
	loadheight = document.documentElement.clientHeight;
	loadheight = loadheight / 2;
	$(".spinner").css("top", loadheight);
}*/
/**
 *自动下拉加载数据
 *@param id
 *数据加载位置
 * @param url
 *请求数据地址
 * @param listsize
 *分页数据总数
 * @param data
 *其他传递参数{} 可不填
 * @param startfn
 *加载前执行function(){} 可不填
 * @param endfn
 *加载后执行function(){} 可不填
 * @param colnum
 *表格分页参数，填写表格跨列数
 */
function ajaxScrollPage(id, url, listsize, data, startfn, endfn,colnum) {

	$(id).scrollPagination(
					{
						'contentPage' : url, // 得到数据到url
						'contentData' : data, // 访问url需要传的参数
						'scrollTarget' : $(window), // 滚动控制对象
						'heightOffset' : 0, // 滚动到底部开始加载到距离
						'beforeLoad' : function() { // 加载之前需要做的事情可以在这里写
							$('.load6').remove();
							if(colnum==""||colnum==undefined){
								$(id).after(
								"<div class=\"load6\"><div class=\"bounce1\"></div><div class=\"bounce2\"></div><div class=\"bounce3\"></div></div>");
							}else{
								$(id).parent().after(
								"<div class=\"load6\"><div class=\"bounce1\"></div><div class=\"bounce2\"></div><div class=\"bounce3\"></div></div>");
							}
							
							if (startfn) {
								startfn();
							}
						},
						'afterLoad' : function(elementsLoaded) { // 加载之后需要做的事情

							$(elementsLoaded).fadeInWithDelay();
							if (listsize < 10) {
								$('.load6').remove();
							}
							if(listsize==0)
							{
								$(id).append("<div style=\"font-size: 16px;color:#4096f0;margin:30px 10px\">暂时没有相关信息哦！</div>");
							}
							if(colnum==""||colnum==undefined)
							{
								if ($(id).children().size() >= listsize) {
									$('.load6').remove();
									// 如果里面的数据大于传过来的数据则停止加载
									$(id).stopScrollPagination();
								}
							}else
								{
								if ($(id).children().size() >= (listsize*2)) {
									$('.load6').remove();
									// 如果里面的数据大于传过来的数据则停止加载
									$(id).stopScrollPagination();
								}
								}
							
							if (endfn) {
								endfn();
							}
						}
					});

	// code for fade in element by element
	$.fn.fadeInWithDelay = function() {
		var delay = 0;
		return this.each(function() {
			$(this).delay(delay).animate({
				opacity : 1
			}, 200);
			delay += 100;
		});
	};
}
/**
 * 自定义方法
 * 
 */
S = {
	/**
	 *弹出alert
	 *@param type
	 *类型，包含（info sucess error）
	 *@param content
	 *内容
	 *@param time
	 *停留时间
	 */
	alert : function(type, content, time) {
		if (type == 'sucess') {
			content = '<div class="alert alert-success" role="alert">'
					+ content + '</div>';
			$.messager.alert(content);
			if (time) {
				setTimeout('$(".modal").modal("hide")', time);
			} else {
				setTimeout('$(".modal").modal("hide")', 2000);
			}
		} else if (type == 'error') {
			content = '<div class="alert alert-warning" role="alert">'
					+ content + '</div>';
			$.messager.alert(content);
		} else {
			content = '<div class="alert alert-info" role="alert">' + content
					+ '</div>';
			$.messager.alert(content);
			if (time) {
				setTimeout('$(".modal").modal("hide")', time);
			} else {
				setTimeout('$(".modal").modal("hide")', 2000);
			}
		}
	},
	/**
	 * 弹出confirm
	 * 
	 * @param content
	 *            内容
	 * @param fn
	 *            回调函数
	 */
	confirm : function(content, fn) {
		
		$.messager.model = {
			ok : {
				text : "确定",
				classed : 'btn-primary'
			},
			cancel : {
				text : "取消",
				classed : 'btn-error'
			}
		};
	
		$.messager.confirm("确认框", content, function() {
			fn();
		});
		
		$('.modal-backdrop').css({"backgroundColor":"#F5F2F2","zIndex":999});
		//$('.modal-backdrop').remove();
		//fade in
	
	},
	// ajax setting
	ajax : function(options) {
		var defaultOpt = {
			dataType : 'json',
			type : 'post',
			beforeSend : this.ajaxBeforeSend,
			complete : this.ajaxComplate,
			success : this.ajaxSuccess,
			error : this.ajaxError
		};

		options = $.extend({}, defaultOpt, options);

		$.ajax(options);
	},
	ajaxBeforeSend : function(XHR) {
		loadheight = document.documentElement.clientHeight;
		loadheight = loadheight / 2;
		$(".spinner").css("top", loadheight);
		$(".spinner").show();
	},
	ajaxComplate : function(XHR, TS) {
		$(".spinner").hide();
	},
	ajaxSuccess : function(data, textStatus) {
		var msg = data.message;
		if (data.success == 1) {
			if (!msg || msg == '') {
				msg = "操作成功！";
			}
			S.alert("success", msg);
			return;
		} else if (data.success == 0) {
			if (!msg || msg == '') {
				msg = "操作失败，请重试！";
			}
			S.alert("error", msg);
			return;
		}
	},

	ajaxError : function(XMLHttpRequest, textStatus, errorThrown) {
		S.alert("error", '系统异常，请稍候再试！');
	}
};
