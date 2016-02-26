jQuery.divselect = function(divselectid,inputselectid) {
	var inputselect = $(inputselectid);
	$(divselectid+" cite").click(function(){
		var ul = $(divselectid+" ul");
		if(ul.css("display")=="none"){
			ul.slideDown("fast");
		}else{
			ul.slideUp("fast");
		}
	});
	$(divselectid+" ul li a").click(function(){
		var txt = $(this).text();
		$(divselectid+" cite").html(txt);
		var value = $(this).attr("selectid");
		inputselect.val(value);
		$(divselectid+" ul").hide();
		
	});
	$(document).click(function(){
		$(divselectid+" ul").hide();
	});
};


jQuery.divselects = function(divselectids,inputselectids) {
	var inputselect = $(inputselectids);
	$(divselectids+" cite").click(function(){
		var ul = $(divselectids+" ul");
		if(ul.css("display")=="none"){
			ul.slideDown("fast");
		}else{
			ul.slideUp("fast");
		}
	});
	$(divselectids+" ul li a").click(function(){
		var txt = $(this).text();
		$(divselectids+" cite").html(txt);
		var value = $(this).attr("selectid");
		inputselect.val(value);
		$(divselectids+" ul").hide();
		
	});
	$(document).click(function(){
		$(divselectids+" ul").hide();
	});
};
