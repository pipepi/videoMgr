seajs.use([ 'bootstrap', '$', 'underscore', 'widget', 'validator' ], function(
		bootstrap, $, _, Widget, Validator) {

	/**
	 * 全局函数，用于展示提示框，提示框自动关闭。
	 *
	 * 传入参数： 1.content：传入提示框提示内容； 2.times:定时关闭时间（毫秒：ms），可不传，默认为1500ms。
	 *
	 * 返回值： 无。
	 */
	window.showTipsModal = function(content, times) {
		if (!times || times <= 0) {
			times = 1500;
		}

		var sTmpl = $('#j-modal-tips').html();
		var sUnitStr = new Date().getTime();
		var domId = 'j-dom' + sUnitStr;

		var title = "温馨提示";

		var oData = {
			domId : domId,
			title : title,
			data : content
		};
		$('body').append(_.template(sTmpl, oData));
		var $modal = $('#' + domId);

		setTimeout(function() {
			$modal.modal('hide');
		}, times);// 自动关闭

		$modal.modal('show').on('hidden.bs.modal', function() {
			// 关闭后移除元素
			$modal.remove();
		});
	}

	/**
	 * 全局函数：用于展示确认提示框。
	 *
	 * 传入参数： 1.content：确认框展示文字内容；2.confirmCallback：回调函数；
	 * 3.callbackParamsArr：回调函数参数数组。
	 *
	 * 返回值： 无。
	 */
	window.showConfirmModal = function(content, confirmCallback,
			callbackParamsArr) {
		var sTmpl = $('#j-modal-confirm').html();
		var sUnitStr = new Date().getTime();
		var domId = 'j-dom' + sUnitStr;
		var confirmId = 'j-confirm' + sUnitStr;

		var title = "温馨提示";

		var oData = {
			domId : domId,
			title : title,
			content : content,
			cancelText : '取消',
			confirmText : '确定',
			confirmId : confirmId
		};
		$('body').append(_.template(sTmpl, oData));
		var $modal = $('#' + domId);
		$modal.find('#' + confirmId).click(function() {
			// 执行回调
			execFunction(confirmCallback, callbackParamsArr);

			// 把窗口关闭
			$modal.modal('hide');
		});
		$modal.modal('show').on('hidden.bs.modal', function() {
			// 关闭后移除元素
			$modal.remove();
		});
	}

	/**
	 * 动态执行函数方法
	 *
	 * 传入参数：1.func：函数对象（注意是函数对象而不是函数名称字符串）；2.paramsArr：需执行的函数参数数组
	 *
	 * 返回值： 无
	 */
	window.execFunction = function(func, paramsArr) {
		// 函数未定义，直接返回。
		if (!func) {
			return;
		}

		var execStr = "func.call(this,@)";
		if (paramsArr) {
			for ( var i = 0; i < paramsArr.length; i++) {
				execStr = execStr.replace("@", "paramsArr[" + i + "],@");
			}
		}
		execStr = execStr.replace(",@", "");
		eval(execStr);
	}

	window.deleteRecord = function(url, data, searchListPage) {
		$.ajax({
			url : url,
			type : 'POST',
			data : data,
			dataType : 'json',
			success : function(data) {
				if (data.result == 0) {
					showTipsModal(data.msg);
					if (typeof (searchListPage) == 'function') {
						searchListPage();
					}
				} else {
					showTipsModal(data.msg);
				}
			},
			error : function(err) {
				showTipsModal("error系统错误!" + err);
			}
		});
	}

	/**
	 * 全局函数，渲染select2下拉框。(不可为空)
	 *
	 * 传入参数： 1.id：需要渲染成select2下拉框的元素id； 2.url:下拉框数据获取json访问链接。
	 *
	 * 返回值： 无。
	 */
	window.initDropNotNull = function(id, url, onchangeCallback, callbackParamsArr) {
		$('#' + id).select2({
			allowClear : false,
			formatNoMatches : '没有找到相关信息',
			formatSearching : '正在加载数据',
			formatLoadMore : '正在加载更多数据',
			formatAjaxError : '加载数据失败',
			minimumInputLength : 1,
			ajax : {
				url : url,
				dataType : 'json',
				quietMillis : 250,
				data : function(term, page) {
					term = $.trim(term);
					term = term === '' ? '' : term;
					return {
						type : $(this).data('type'),
						q : term,
						page : page
					};
				},
				results : function(data, page) {
					var more = (page * 10) < data.iTotalRecords;
					return {
						results : data.aaData,
						more : more
					};
				},
				cache : true
			},
			formatResult : function(repo) {
				return repo.key;
			},
			formatSelection : function(repo) {
				return repo.key;
			},
			initSelection : function(element, callback) {
				var id = $(element).val();
				if (id <= 0) {
					return;
				}

				var key = "";
				var record = getDropName(id, url);
				if (record) {
					key = record.key;
				}

				if (id != '' && key != "") {
					callback({
						id : id,
						key : key
					});
				}
			}
		}).on('change', function(e) {
			execFunction(onchangeCallback, callbackParamsArr);
		}).on('select2-open', function() {
			// 初始化,传一空格，表示初始化
			$(this).select2('search', ' ');

		});
	}

	/**
	 * 全局函数，渲染select2下拉框。
	 *
	 * 传入参数： 1.id：需要渲染成select2下拉框的元素id； 2.url:下拉框数据获取json访问链接。
	 *
	 * 返回值： 无。
	 */
	window.initDrop = function(id, url, onchangeCallback, callbackParamsArr) {
		$('#' + id).select2({
			allowClear : true,
			formatNoMatches : '没有找到相关信息',
			formatSearching : '正在加载数据',
			formatLoadMore : '正在加载更多数据',
			formatAjaxError : '加载数据失败',
			minimumInputLength : 1,
			ajax : {
				url : url,
				dataType : 'json',
				quietMillis : 250,
				data : function(term, page) {
					term = $.trim(term);
					term = term === '' ? '' : term;
					return {
						type : $(this).data('type'),
						q : term,
						page : page
					};
				},
				results : function(data, page) {
					var more = (page * 10) < data.iTotalRecords;
					return {
						results : data.aaData,
						more : more
					};
				},
				cache : true
			},
			formatResult : function(repo) {
				return repo.key;
			},
			formatSelection : function(repo) {
				return repo.key;
			},
			initSelection : function(element, callback) {
				var id = $(element).val();
				if (id <= 0) {
					return;
				}

				var key = "";
				var record = getDropName(id, url);
				if (record) {
					key = record.key;
				}

				if (id != '' && key != "") {
					callback({
						id : id,
						key : key
					});
				}
			}
		}).on('change', function(e) {
			execFunction(onchangeCallback, callbackParamsArr);
		}).on('select2-open', function() {
			// 初始化,传一空格，表示初始化
			$(this).select2('search', ' ');

		});
	}

	/**
	 * 根据id值获取下拉框对应的label名称。
	 *
	 * 传入参数： 1.id：下拉框value值；2.url：ajax访问链接。
	 *
	 * 返回值： id对应的下拉lable名称。
	 */
	window.getDropName = function(id, url) {
		var record = null;
		var data = {
			"id" : id
		};

		$.ajax({
			url : url,
			type : "POST",
			async : false,
			data : data,
			dataType : "JSON",
			success : function(data) {
				record = data.aaData[0];
			},
			error : function(data) {
			}
		});
		return record;
	}

	/**
	 * 初始化时间选择器。
	 *
	 * 传入参数：1.id：时间控件元素id。
	 *
	 * 返回值：无。
	 */
	window.initTimeOption = function(id, callbackFunc, callbackParamsArr) {
		var oTimeOption = {
			format : 'YYYY-MM-DD',
			separator : '~',
			minDate : '2012-01-01',
			showDropdowns : true,
			dropdownAdjusts : true,
			applyClass : 'btn btn-small btn-success',
			opens : 'left',
			locale : {
				applyLabel : '筛选',
				clearLabel : '重置',
				cancelLabel : '重置',
				fromLabel : '开始时间',
				toLabel : '结束时间',
				customRangeLabel : 'Custom Range',
				daysOfWeek : [ '日', '一', '二', '三', '四', '五', '六' ],
				monthNames : [ '1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月',
						'9月', '10月', '11月', '12月' ],
				firstDay : 1
			}
		};
		$('#' + id).daterangepicker(oTimeOption).on('apply.daterangepicker',
				function(ev, picker) {
					execFunction(callbackFunc, callbackParamsArr);
				}).on('cancel.daterangepicker', function() {
			$(this).val('');

			execFunction(callbackFunc, callbackParamsArr);
		});
	}

	/**
	 * 渲染boostrap 时间控件（时间区段）
	 *
	 * 传入参数：1.startDateOptionId：开始日期控件ID；2.endDateOptionId：结束日期控件ID。
	 *
	 * 返回值：无
	 */
	window.initBoostrapDateRangeOption = function(startDateOptionId,
			endDateOptionId) {
		// 时间段选择器
		var oTimeOption = {
			format : 'yyyy-mm-dd',
			language : 'zh-CN',
			autoclose : true
		};
		var timeAreaPicker = function(startSelector, endSelector) {

			var $startSelector = $(startSelector);
			var $endSelector = $(endSelector);

			$startSelector.datepicker(oTimeOption).on('changeDate',
					function(event) {
						$endSelector.datepicker('setStartDate', event.date);
					});
			$endSelector.datepicker(oTimeOption).on('changeDate',
					function(event) {
						$startSelector.datepicker('setEndDate', event.date);
					});

			// 如果控件初始化有值时，限制选择时间时不能选择开始时间>结束时间。
			var startDateStr = $startSelector.val();
			if (startDateStr) {
				var startDate = new Date(startDateStr);
				$endSelector.datepicker('setStartDate', startDate);
			}
			var endDateStr = $endSelector.val();
			if (endDateStr) {
				var endDate = new Date(endDateStr);
				$startSelector.datepicker('setEndDate', endDate);
			}

		};
		timeAreaPicker('#' + startDateOptionId, '#' + endDateOptionId);
	}

	/**
	 * 渲染boostrap 时间控件
	 *
	 * 传入参数：1.dateOptionId：日期控件id
	 *
	 * 返回值：无
	 */
	window.initBoostrapDateOption = function(dateOptionId) {
		// 时间段选择器
		var oTimeOption = {
			format : 'yyyy-mm-dd',
			language : 'zh-CN',
			autoclose : true
		};
		$('#' + dateOptionId).datepicker(oTimeOption);
	}

	// 更多选项
	var $moreOptionItem = $('#j-more-option-item');
	$('#j-more-option').toggle(function() {
		$(this).text('收起');
		$moreOptionItem.css('display', 'inline');
	}, function() {
		$(this).text('更多');
		$moreOptionItem.css('display', 'none');
	});

});

/**--------------------------------------------------------------
 *                      微弱的分割线
 --------------------------------------------------------------*/

// 扩展插件

jQuery.extend({

    /**
     * 全局图表函数
     * @param id 传入id
     * @param type ajax请求类型
     * @param url 地址
     * @param dataType 返回类型
     */
    drawChart: function (id, type, url, dataType) {
        type = type || 'POST';
        dataType = dataType || 'json';
        jQuery.ajax({
            type: type,
            url: url,
            dataType: dataType,
            success: function (data) {
                if (data != null) {
                    Highcharts.setOptions({
                        global: {
                            useUTC: false
                        }
                    });
                    var chart = new Highcharts.Chart({
                        chart: {
                            renderTo:id,
                            zoomType: 'xy',
                            type: 'spline', // 设置图表样式，可以为line,spline, scatter, splinearea bar,pie,area,column
                            height: 480,
                            width: 760,
                            defaultSeriesType: 'column', //图表的默认样式
                            alignTicks: true
                        },
                        title: {text: data.cTitle},
                        subtitle: {text: data.cSubtitle},
                        xAxis: [
                            {
                                type: 'datetime',
                                dateTimeLabelFormats: { // don't display the dummy year
                                    millisecond: '%m月-%e日 %H:%M',
                                    second: '%m月-%e日 %H:%M',
                                    minute: '%m月-%e日 %H:%M',
                                    hour: '%m月-%e日 %H',
                                    year: '%Y年',
                                    month: '%Y年-%m月',
                                    day: '%m月-%e日'
                                },
                                title: {
                                 text: data.xTitle
                                 },
                                 lineColor: '#388338',
                                 tickColor: '#388338'
                            }
                        ],
                        yAxis: data.columns,
                        tooltip: {
                            formatter: function() {
                                var s = '<b>' + moment(this.x).format("YYYY年MM月DD日") + '</b>';
                                jQuery.each(this.points, function (i, point) {
                                    s += '<br/>' + point.series.name + ': ' + point.y ;
                                });

                                return s;
                            }
                        },
                        exporting: {enabled: false},   // 导出
                        series: data.data
                    })
                }else{
                    console.log('没有找到数据！')
                }
            },
            error:function(msg){
                console.log(msg);
            }
        })
    },
    /**
     * 饼形图
     * @param id 传入id
     * @param type ajax请求类型
     * @param url 地址
     * @param dataType 返回类型
     */
    drawPieChart: function (id, type, url, dataType) {
        type = type || 'POST';
        dataType = dataType || 'json';
        jQuery.ajax({
            type: type,
            url: url,
            dataType: dataType,
            success: function (data) {
                if (data != null) {
                    jQuery('#' + id).highcharts({
                        chart: {
                            plotBackgroundColor: null,
                            plotBorderWidth: null,
                            plotShadow: false
                        },
                        title: {
                            text: data.cTitle
                        },
                        subtitle: {
                            text:data.cSubtitle
                        },
                        tooltip: {
                            crosshairs: false,
                            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                        },
                        plotOptions: {
                            pie: {
                                allowPointSelect: true,
                                cursor: 'pointer',
                                dataLabels: {
                                    enabled: true,
                                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                                    style: {
                                        color: (Highcharts.theme && Highcharts.theme.colors[0]) || 'black'
                                    }
                                }
                            }
                        },
                        exporting: {enabled: false},   // 导出
                        series: data.data
                    })
                }else{
                    console.log('没有找到数据！')
                }
            },
            error:function(msg){
                console.log(msg);
            }
        })
    }
});
