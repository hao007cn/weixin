/**
 * 字符串formatter
 * 
 * @author sunzhi
 *
 */
String.prototype.format = function(args) {
	var result = this;
	if (arguments.length > 0) {
		if (arguments.length == 1 && typeof (args) == "object") {
			for ( var key in args) {
				if (args[key] != undefined) {
					var reg = new RegExp("({" + key + "})", "g");
					result = result.replace(reg, args[key]);
				}
			}
		} else {
			for ( var i = 0; i < arguments.length; i++) {
				if (arguments[i] != undefined) {
					var reg = new RegExp("({)" + i + "(})", "g");
					result = result.replace(reg, arguments[i]);
				}
			}
		}
	}
	return result;
};

S = {
	// ajax setting
	ajax: function(options){
		var defaultOpt = {
			dataType: 'json',
			type: 'post',
			beforeSend: this.ajaxBeforeSend,
			complete: this.ajaxComplate,
			success: this.ajaxSuccess,
			error: this.ajaxError
		};
		
		options = $.extend({}, defaultOpt, options);
		
		$.ajax(options);
	},
	ajaxBeforeSend: function(XHR) {
		if ($('#weLoadingDialog').length <= 0) {
			var html = '<div class="modal fade" id="weLoadingDialog" tabindex="-1" role="dialog" remote="false">'+
							'<div class="modal-dialog">'+
								'<div class="modal-content">'+
									'<div class="modal-body">'+
									'	正在加载...'+
									'</div>'+
								'</div><!-- /.modal-content -->'+
							'</div><!-- /.modal-dialog -->'+
						'</div><!-- /.modal -->';
			var dialog = $(html);
			dialog.appendTo($('body'));
		}
		$('#weLoadingDialog').modal({
			keyboard: false,
			backdrop: 'static'
		});
	},
	ajaxComplate: function(XHR, TS) {
		$('#weLoadingDialog').modal('hide');
	}, 
	ajaxSuccess: function(data, textStatus) {
		var msg = data.message;
		if (data.success == 1) {
			if (!msg || msg == '') {
				msg = "操作成功！";
			}
			bootbox.message('<div class="alert senyint-alert alert-success" role="alert">' + msg + '</div>');
			return;
		} else if (data.success == 0) {
			if (!msg || msg == '') {
				msg = "操作失败，请重试！";
			}
			bootbox.alert('<div class="alert senyint-alert alert-warning" role="alert">' + msg + '</div>');
			return;
		}
	},

	ajaxError: function(XMLHttpRequest, textStatus, errorThrown) {
		bootbox.alert('<div class="alert senyint-alert alert-warning" role="alert">系统异常，请稍候再试！</div>');
	},
	
	enabledFormatter: function(cellvalue, options, rowObject) {
		if (options && options.value) {
			var valarr = value.replace(/\s/g, "").split(";");
			for (var i = 0; i < valarr.length; i++) {
				var kvarr = valarr[i].split(":");
				if (kvarr[0] == cellvalue) {
					return kvarr[1];
				}
			}
		} else {
			if (cellvalue == true) {
				return "启用";
			} else if(cellvalue == false) {
				return "禁用";
			}
		}
		return cellvalue || "";
	},
	
	sexFormatter: function(cellvalue, options, rowObject) {
		if (cellvalue) {
			if (cellvalue == '1' || cellvalue.toLowerCase() == "male") {
				return "男";
			} else if (cellvalue == '2' || cellvalue.toLowerCase() == "female") {
				return "女";
			} else if (cellvalue == '0' || cellvalue.toLowerCase() == "unknown") {
				return "保密";
			}
		}
		return cellvalue || "";
	},
	alert: function(type, msg) {
		if (type == 'success') {
			bootbox.message('<div class="alert senyint-alert alert-success" role="alert">' + msg + '</div>');
		} else if (type == 'success') {
			bootbox.message('<div class="alert senyint-alert alert-success" role="alert">' + msg + '</div>');
		} else if (type == 'error') {
			bootbox.alert('<div class="alert senyint-alert alert-warning " role="alert">'+msg+'</div>');
		}
	}
};

//将一个表单的数据返回成JSON对象
(function($){
	$.fn.serializeJson = function(){
		var serializeObj = new Object();
		var array = this.serializeArray();
		$(array).each(function(){
			if(serializeObj[this.name] !== undefined){
				if($.isArray(serializeObj[this.name])){
					serializeObj[this.name].push(this.value);
				}else{
					serializeObj[this.name] = [serializeObj[this.name], this.value];
				}
			}else{
				serializeObj[this.name] = this.value;	
			}
		});
		return serializeObj;
	};
})(jQuery);

$(function(){
	$.ajaxSetup({
		contentType: "application/x-www-form-urlencoded; charset=utf-8"
	});
});
!function(win, doc, $) {
	$(function() {
		var $rocketTopBtn = $('#rocket-to-top'),
			$rocketTopAnim = $rocketTopBtn.find('.anim'),
			rocketAnimTime = 1000,
			fire = 'fire',
			shot = 'shot',
			inRocketAnim = false,
			clickedTop = false;
		
		// 监听window滚动事件
		$(win).scroll(function() {
			
			// 手机屏幕不做处理
			if (innerWidth < 768) return;
			if ($(doc).scrollTop() == 0) {
				if (clickedTop && !inRocketAnim) {
					
					// 点击返回按钮返回顶部后
					inRocketAnim = true;
					
					// 开始发射动画
					$rocketTopBtn.addClass(shot);
					
					// 结束并移除各动画class
					setTimeout(function() {
						$rocketTopBtn.removeClass(shot).hide();
						$rocketTopAnim.removeClass(fire);
						inRocketAnim = false;
						clickedTop = false;
					}, rocketAnimTime * 1.5);
					
				} else {
					$rocketTopBtn.fadeOut(rocketAnimTime);
				}
			} else {
				$rocketTopBtn.fadeIn(rocketAnimTime);
			}
		});
		
		$rocketTopAnim.click(function() {
			if (inRocketAnim) return;
			clickedTop = true;
			
			// 开始点火动画
			$rocketTopAnim.addClass(fire);
			
			// 滚动耗时
			var scrollTime = $(doc).scrollTop() * 0.3;
			$('html,body').animate({
				scrollTop: 0
			}, scrollTime > rocketAnimTime ? scrollTime : rocketAnimTime);
		});

	});
}(window, document, jQuery);