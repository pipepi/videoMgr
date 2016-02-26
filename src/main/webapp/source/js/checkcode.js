var cc={
		ctx:'',//系统接口根路径
		imgId:'',//校验码图片id [codeImage]
		inputId:'',//输入校验码文本框id [checkCode]
		inputPreText:'',//输入校验码文本框提示文本 [请输入验证码]
		flag:false,
		init:function(ctx,imgId,inputId,inputPreText){
			cc.ctx = ctx;
			cc.imgId = imgId;
			cc.inputId = inputId;
			cc.inputPreText = inputPreText;
			cc.flag = false;
			$('#'+cc.inputId).attr("disabled",false);
			$('#'+cc.inputId).keyup(function(event){
				if(event.which!=9){
						cc.checkCode();
					}
				}
			);
		},
		changeCode:function(){
			$("#"+cc.imgId).attr("src",cc.ctx+"/checkcode/check_code?d="+new Date());
		},
		checkCode:function(){
			if(cc.flag)return;
			var value = $('#'+cc.inputId).val();
			cc.flag = false;
			if(value==''){
				//$('#'+cc.inputId).val(cc.inputPreText);
				return;
			}
			if(isNaN(parseInt(value,10))){
				return;
			}
			if(value.length<4||value.length>4){
				return;
			}
			//alert(value);
			cc.doValiCode(value,{
				success:function(){
					cc.flag = true;
					//$('#'+cc.inputId).val("验证码正确");
					$('#'+cc.inputId).attr("disabled",true);
				},
				error:function(msg){
					//alert(msg);
					cc.changeCode();
				}
			});
		},
		doValiCode:function (value, callback) {
			if (value) {
				$.post(cc.ctx+"/checkcode/vali_code", "code=" + value, function (data) {
					if (data.status == 0) {
						callback.success();
					} else {
						$('#'+cc.inputId).val('');
						callback.error(data.errMSG);
					}
				}, "json");
			} else {
				callback.error('\u9a8c\u8bc1\u7801\u4e0d\u80fd\u4e3a\u7a7a');
			}
		},
		
		clearCode:function(){
			var value = $('#'+cc.inputId).val();
			if(value==cc.inputPreText){
				$('#'+cc.inputId).val('');
			}
		}
};