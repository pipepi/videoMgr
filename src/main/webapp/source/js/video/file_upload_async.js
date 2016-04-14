/**
 * lanker
 * 2015-08-04
 */
var v = {
		canUpVideo:true,
		canSub:true,
		interval:0,
		per:0,
		totalUpTime:0,
		countTime:0,//已上传时间计数
		options:{
			upurl_video:ctx+'/video/async/upload',//视频上传接口地址
			up_video_file_input_id:'up_video',//视频上传页面input标签id
			uploaded_video_div_id:'uploaded_video',//已上传视频展示框
			delurl_video:ctx+'/video/async/remove',//删除视频接口
			big_img_cover:ctx+'/video/upimgcover',//上传图片接口
			big_img_input_id:'upload-pic',//上传选择文件input
			cover_img_big:'cover_img_big',//被裁剪图img标签id
			cover_div_small:'cover_div_small',//裁剪预览小框
			hidden_cover_img_small:'hidden_cover_img_small',//裁剪预览小图下面被遮挡的图
			cuted_img:'cuted_img',
			x1:0,
			y1:0,
			x2:0,
			y2:0,
			src_img_id:'',
			width:0,
			height:0,
			scale:1,
			small_size:120,//预览图宽
			small_size_height:67.5,//预览图高
			big_size:270,//被裁剪图宽
			big_size_height:152,//被裁剪图高
			img_cut:ctx+'/video/cutimg',//图片裁剪接口
			cut_img_id:'',//裁剪后的图片Id
			cutnum:0,//裁剪次数，用于更新同名图片
			oldSrcImgId:'',
			oldCutImgId:''
		},
		progress:function(){
			
			v.countTime++;
			if(v.countTime<v.totalUpTime){
				if(v.per<99){
					v.per =  Math.round(v.countTime/v.totalUpTime*100);
					$('.progress-number').text(v.per+"%");
					$('.progress-bar-cons-up').css('width',v.per+"%");
				}
			}
			v.interval = window.setTimeout(v.progress, 1000);
		},
		getFile:function(){
			var file;
			if($.browser.msie){
				var fileobject = new ActiveXObject ("Scripting.FileSystemObject");//获取上传文件的对象  
				file = fileobject.GetFile ($('#'+v.options.up_video_file_input_id).val());//获取上传的文件  
			}else{
				file = $('#'+v.options.up_video_file_input_id).get(0).files[0];
			}
			return file;
		},
		getFileSizeInt:function(file){
			return Math.round(file.size * 100 / (1024 * 1024)) / 100;
		},
		getFileSizeStr:function(file){
			var fileSize;
			if (file.size > 1024 * 1024) {
				fileSize = (Math.round(file.size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
				}else {
			fileSize = (Math.round(file.size * 100 / 1024) / 100).toString() + 'KB';}
			return fileSize;
			
		},
		getTotalUpTime:function(file){
			var _file_size = 0;
            if(file.size<1024 * 1024){
            	_file_size = 1024 * 1024;
            }else{
            	_file_size = file.size;
            }
            return Math.round(_file_size * 100 / (1024 * 1024) / 100*10);
		},
		upload:{
			video:function(){
				if(!v.canUpVideo) {
					v.tip('video-tip',"已上传视频");
					return;	
				}
				var file = v.getFile();
				if(file){
					if(v.getFileSizeInt(file)>=200){
						v.tip('video-tip',"视频大小为"+filesizeInt+"MB,要求＜200MB");
	                	return;
					}
	                var filetype = file.type;
	                if(!v.isVideoType(file.name.toLowerCase())){
	                	v.tip('video-tip',"格式不支持");
	                	return;
	                }
	                v.totalUpTime = v.getTotalUpTime(file);
				}else{
					$('#video-tip').hide();
					return;
				}
				v.countTime = 0;
				v.interval = window.setTimeout(v.progress, 1000);
				var tipstr = $('#video-tip').text();
				var reg=new RegExp("^图片大小为");   
				if(tipstr=='请选择视频'||tipstr=='格式不支持'||tipstr=='已上传视频'||reg.test(tipstr)){$('#video-tip').hide();}
				$('.progress-bar').toggle();
				var _video_name =  file.name;
				_video_name = _video_name.substring(0,_video_name.lastIndexOf("."))
				//console.log("_video_name:"+_video_name);
				v.canUpVideo = false;
				//保存按钮致灰
				v.submit_disable();
				$.ajaxFileUpload({
					url:v.options.upurl_video,
					secureuri:false,
					fileElementId:v.options.up_video_file_input_id,
					dataType:'string',
					success:function(data,status){
						if(data){
			    		  var attr = eval('('+data+')');
			    		  if(attr.success == true){
			    			  $('input[name="video_file_name"]').val(attr.name);
			    			  $('input[name="video_url"]').val(attr.url);
			    			  //console.log(attr);
			    			  window.clearTimeout(v.interval);
			    			  v.per = 0;
			    			  var uploaded_em = $('#'+v.options.uploaded_video_div_id).find('em');
			    			  uploaded_em.text(attr.name); 
			    			  $('.progress-bar').toggle();
			    			  $('#'+v.options.uploaded_video_div_id).show();
			    			  
			    			  $('#'+v.options.up_video_file_input_id).attr("disabled",true);
			    			  $('#'+v.options.up_video_file_input_id).parent().css('background-color','#999999');
			    			  $('input[name = "video-name"]').val(_video_name);
			    		  }else{
			    			  v.tip('video-tip',"上传失败");
			    			  $('.progress-bar').toggle();
			  				v.canUpVideo = true;
			    		  }
						}else{
							v.tip('video-tip',"上传失败");
							$('.progress-bar').toggle();
							v.canUpVideo = true;
						}
						v.submit_able();
					},
					error:function(data,status,e){
						v.tip('video-tip',"上传失败");
						$('.progress-bar').toggle();
						v.canUpVideo = true;
						v.submit_able();
					}
				});
			},
			img_cover:function(){
				if($.browser.msie){
					var fileobject = new ActiveXObject ("Scripting.FileSystemObject");//获取上传文件的对象  
					file = fileobject.GetFile ($('#'+v.options.big_img_input_id).val());//获取上传的文件  
				}else{
					file = $('#'+v.options.big_img_input_id).get(0).files[0];
				}
				if(file){
					var fileSize = 0;
					var filesizeInt = Math.round(file.size * 100 / (1024 * 1024)) / 100;
					if(filesizeInt>=3){
						v.tip('img-tip',"图片大小为"+filesizeInt+"MB,要求＜3MB");
	                	return;
					}
	                if (file.size > 1024 * 1024) fileSize = (Math.round(file.size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
	                else fileSize = (Math.round(file.size * 100 / 1024) / 100).toString() + 'KB';
	                //console.log("filesize= "+fileSize+"  name="+file.name+"  type= "+file.type);
	                var filetype = file.type;
	                if(!v.isImgType(file.name.toLowerCase())){
	                	v.tip('img-tip',"格式不支持");
	                	return;
	                }
				}
				var tipstr = $('#img-tip').text();
				var reg=new RegExp("^图片大小为");   
				if(tipstr=='请选择图片'||tipstr=='格式不支持'||reg.test(tipstr)){$('#img-tip').hide();}
				var data = "?oldSrcImgId="+v.options.oldSrcImgId+"&oldCutImgId="+v.options.oldCutImgId;
				v.submit_disable();
				$.ajaxFileUpload({
					url:v.options.big_img_cover+data,
					secureuri:false,
					fileElementId:v.options.big_img_input_id,
					dataType:'string',
					success: function (data, status){     
				    	  if(data){
				    		  //console.log(data);
				    		  var datas = data.substring(data.indexOf('{')+1,data.lastIndexOf('}'));
				    		  datas = "{" + datas + "}";
				    		  var attr = eval('('+datas+')');
				    		  if(attr.status==0){
				    			  $('#img-tip').hide();
				    			  //根据被裁剪图高宽计算缩放比
				    			  var scaleW = v.options.big_size / (attr.width || 1);
				    			  var scaleH = v.options.big_size_height / (attr.height || 1); 
					    		  if(scaleW<scaleH) {
					    			  zoom = scaleH;
					    		  } else {
					    			  zoom = scaleW;
					    		  }
					    		  zmax = cutx/1004;
					    		  zminx = cutx/(attr.width||1);
					    		  zminy = cuty/(attr.width||1);
					    		  zmin = zminx>zminy?zminx:zminy;
					    		  if(zoom>=zmax){
					    			  zoom=zmax;
					    		  }
					    		  if(zoom<=zmin){
					    			  zoom=zmin;
					    		  }
					    		  v.options.scale = zoom;
					    		  var width = attr.width * zoom;
					    		  var height = attr.height * zoom;
					    		  v.options.width = attr.width;
					    		  v.options.height = attr.height;
					    		  v.options.src_img_id = attr.imageId;
					    		  v.options.oldSrcImgId = attr.imageId;
					    		  $('input[name="src_img_id"]').val(v.options.src_img_id);
					    		  
					    		  //var imgurl = "file:\/\/\/"+attr.imageUrl.replace(new RegExp("\\\\","gm"),"\/");
					    		  var imgurl = ctx+"/video/downimg?img_url="+attr.imageId;
					    		  //console.log(imgurl+"    width="+width+"  height="+height);
					    		  //显示被裁剪图
					    		  $('#cut_img').attr('src',imgurl);
					    		  $('#cut_img').attr('width', width);
					    		  $('#cut_img').attr('height', height);
					    		  $('#cut_img').css('top', (divy-height)/2-divy);
					    		  $('#cut_img').css('left', (divx-width)/2);
					    		  $('#cut_img').show();
					    		  imgdefw=v.options.width;
					    		  imgdefh=v.options.height;
					    		  //设置滑块位置
					    		  var imgTrackWidth = $('#img_track').width();
					    		  var slidleft = ((zoom-zmin)/((zmax-zmin)||1))*imgTrackWidth;
					    		  //console.log("zoom="+zoom+"   zmin="+zmin+"   zmax="+zmax);
					    		  //console.log("imgTrackWidth:"+imgTrackWidth+"   slidleft:"+slidleft);
					    		  $('#img_grip').css('left',slidleft-92);
					    		 // console.log( $('#cut_big_img_div').css('background-color'));
					    		  $('#cut_big_img_div').removeClass('cut_btn').addClass('cut_btn1');
					    		  getTransform();
					    		  /* 
					    		  $('#'+v.options.cover_img_big).attr('src',imgurl);
					    		  $('#'+v.options.cover_img_big).attr('width', width);
					    		  $('#'+v.options.cover_img_big).attr('height', height);
					    		  $('#img-title').remove();
					    		  $('#'+v.options.cover_img_big).show();
					    		  $('#cut_big_img_div').css('background-color','#e3393c');
					    		  $(function () {
					    			  $('#'+v.options.cover_div_small).empty();
					    			  var viewImageWidth = Math.round(v.options.small_size*width/(1004*scale));
					    			  $('#'+v.options.cover_div_small).append($('<div><img id="'+v.options.hidden_cover_img_small+'" src="'+imgurl+'" style="position: relative;width:'+viewImageWidth+'px;height:'+height*viewImageWidth/width+'px;" /></div>')
				    		          .css({
				    		              float: 'center',
				    		              position: 'relative',
				    		              overflow: 'hidden',
				    		              width: v.options.small_size+'px',
				    		              height: v.options.small_size_height+'px'
				    		          }));
					    			  var x1=0;
					    			  var y1=0;
					    			  //var x2 = width<v.options.small_size?width:v.options.small_size;
					    			  var x2 = 1004*scale;
					    			  v.options.x2 = Math.round(x2);
					    		      //var y2 = height<v.options.small_size_height?height:v.options.small_size_height;
					    			  var y2 = x2/16*9;
					    		      v.options.y2 = Math.round(y2);
					    		      //console.log("x2="+x2+"   y2="+y2);
					    		      $('#'+v.options.cover_img_big).imgAreaSelect({ x1: 0, y1: 0, x2: x2, y2: y2, aspectRatio: '16:9',resizable:true,persistent:true, handles:true, onSelectChange: v.preview, onSelectEnd: v.next});
					    		  });*/
				    		  }else{
				    			  v.tip('img-tip',attr.errMSG);
				    		  }
				    	  }
				    	  v.submit_able();
					},
					error: function (data, status, e){
				    	  v.tip('img-tip',"请求错误，原因：" + data);
				    	  v.submit_able();
				    }
				});
			},
			img_cut:function(){
				//console.log("click cut");
				//console.log("v.options.src_img_id="+v.options.src_img_id);
				if(v.options.src_img_id==''){
					v.tip('img-tip',"请选择图片");
					return;
				}
				var data = "?" +
							"x1="+v.options.x1
							+"&y1="+v.options.y1
							+"&x2="+v.options.x2
							+"&y2="+v.options.y2
							+"&srcImageId="+v.options.src_img_id
							+"&scale="+v.options.scale;
				$.ajaxFileUpload({
				      url:v.options.img_cut+data,//需要链接到服务器地址
				      secureuri:false,
				      fileElementId:v.options.big_img_input_id,//文件选择框的id属性
				      //data:data,
				      dataType: 'string',//服务器返回的格式，可以是json
				      success: function (data, status){
				    	  var datas = data.substring(data.indexOf('{')+1,data.lastIndexOf('}'));
			    		  datas = "{" + datas + "}";
			    		  var attr = eval('('+datas+')');
			    		  //console.log(datas);
				    	  if(attr.status==0){
				    		  v.options.cut_img_id = attr.cutImgId;
				    		  v.options.oldCutImgId = attr.cutImgId;
				    		  $('input[name="cut_img_id"]').val(v.options.cut_img_id);
				    		  v.tip('img-tip',"裁剪成功");
				    	  }else{
				    		  v.tip('img-tip',"裁剪失败");
				    	  }
				      },
				      error: function (data, status, e){
				      }
				});
			}
		},
		preview:function(img, selection) {
		    if (!selection.width || !selection.height)
		        return;
		    var scaleX = v.options.small_size/selection.width;
		    var scaleY = v.options.small_size_height/selection.height;
		    var width = v.options.scale * v.options.width * scaleX;
		    var height = v.options.scale * v.options.height * scaleY;
		    //console.log("scaleX="+scaleX+"  scaleY="+scaleY+"   width="+width+"  height="+height);
		    $('#'+v.options.hidden_cover_img_small).css({
		        width: Math.round(width) + 'px',
		        height: Math.round(height) + 'px',
		        marginLeft: '-' + Math.round(scaleX * selection.x1) + 'px',
		        marginTop: '-' + Math.round(scaleY * selection.y1) + 'px'
		    });
		},
		next:function(img, selection) {
		    v.options.x1=selection.x1;
		    v.options.y1=selection.y1;
		    v.options.x2=selection.x2;
		    v.options.y2=selection.y2;
		},
		del:function(obj){
			var uaid = $('input[name="upload_asset_id"]').val();//微软云视频上传id
			var eaid = $('input[name="encode_asset_id"]').val();//微软云视频编码id
			var video_file_name = $('input[name="video_file_name"]').val();//本地文件名称
			var video_url = $('input[name="video_url"]').val();//本地地址
			var flag = 1;//删除标签 1:微软云  2:本地
			if($.trim(uaid).length==0){
				uaid = video_file_name;
				eaid = video_url;
				flag = 2;
			}
			$.ajax({
				type: "POST",
				url: v.options.delurl_video,
				data: {"upload_asset_id":uaid,"encode_asset_id":eaid,flag:flag},
				dataType:"json",
				success: function(data){
					var videodiv = $('#'+v.options.uploaded_video_div_id);
					videodiv.toggle();
					$('#'+v.options.up_video_file_input_id).attr("disabled",false);
					$('#'+v.options.up_video_file_input_id).parent().css('background-color','#4a9ced');
					v.canUpVideo = true;
					$('input[name="upload_asset_id"]').val('');//微软云视频上传id
					$('input[name="encode_asset_id"]').val('');//微软云视频编码id
					$('input[name="video_file_name"]').val('');//本地文件名称
					$('input[name="video_url"]').val('');//本地地址
				},
				error : function (XMLHttpRequest, textStatus) {
					v.tip('video-tip',"请求服务器失败,请刷新页面后再重试");
				}
			});
		},
		submit_able:function(){
			v.canSub=true;
			$('#sub-btn').removeClass('video-btnsInfor-2');
			$('#sub-btn').addClass('video-btnsInfor');
		},
		submit_disable:function(){
			v.canSub=false;
			$('#sub-btn').removeClass('video-btnsInfor');
			$('#sub-btn').addClass('video-btnsInfor-2');
		},
		submit_all:function(){
			if(!v.canSub)return;
			//1：获取值
			var id = $('input[name = "id"]').val();//视频名称
			var role = $('input[name = "role"]').val();//视频名称
			var name = $('input[name = "video-name"]').val();//视频名称
			var desc = $('textarea[name = "video-desc"]').val();//视频描述
			var video_file_name = $('input[name="video_file_name"]').val();//本地文件名称
			var video_url = $('input[name="video_url"]').val();//本地文件路径
			var src_imgid = $('input[name="src_img_id"]').val();//裁剪前源图id
			var cut_imgid = $('input[name="cut_img_id"]').val();//裁剪后图片id
			
			
			//2：校验
			var focus = false;
			var vali = true;
			if(v.empty(name)||name=="请输入视频名称"||$.trim(name).length<6||$.trim(name).length>20){
					v.tip('name-tip','请输入名称[6-20]');
					$('input[name = "video-name"]').focus();
					focus = true;
					vali = false;
				}
			if(v.empty(desc)||desc=="请输入视频描述"||$.trim(desc).length<1||$.trim(desc).length>140){
					v.tip('desc-tip','请输入描述[1-140]');
					if(!focus){
						$('input[name = "video-desc"]').focus();
						focus = true;
					}
					vali = false;
				}
			if(v.empty(video_file_name)||v.empty(video_url)){
					v.tip('video-tip','请选择视频');
					if(!focus){
						$('input[name = "up-video"]').focus();
						focus = true;
					}
					vali = false;
				}
			var upimg = true;
			if(v.empty(src_imgid)&&v.empty(id)){
					v.tip('img-tip','请选择图片')
					upimg = false;
					if(!focus){
						$('input[name = "upload-pic"]').focus();
						focus = true;
					}
					vali = false;
				}
			if(v.empty(cut_imgid)&&v.empty(id)){
					if(upimg){
						v.tip('img-tip','请裁剪图片')
					}
					if(!focus){
						$('input[name = "cut-img"]').focus();
						focus = true;
					}
					vali = false;
				}
			var posturl = ctx;
			if(v.empty(id)){
				posturl+='/video/async/save';
			}else{
				posturl+='/video/async/update';
			}
			if(!vali)return;
			//3：post提交
			v.canSub = false;
			$.post(posturl,
					{
					 id:id,
					 role:role,
					 title:name,
					 desc:desc,
					 active:0,
					 video_file_name:video_file_name,
					 video_url:video_url,
					 src_img_id:src_imgid,
					 cut_img_id:cut_imgid
					},function(data){
						 var datas = data.substring(data.indexOf('{')+1,data.lastIndexOf('}'));
			    		  datas = "{" + datas + "}";
			    		  var attr = eval('('+datas+')');
			    		  if(attr.error_desc){
			    			  if(new RegExp("^no login").test(attr.error_desc)){
			    				  $(".video-kuangs-new").show();
			    				  return ;
			    			  }else{
			    				  alert(attr.error_desc);
			    				  return;
			    			  }
			    		  }
			    		 if(attr.success==0){
			    			 if(attr.role==1){
			    				 window.location = ctx+"/video/listhm";
			    			 }else{
			    				 window.location = ctx+"/video/checkpage";
			    			 }
			    		 }
			});
		},
		empty:function(str){
			if(str==null||$.trim(str).length<1){
				return true;
			}else{
				return false;
			}
		},
		isVideoType:function(s){
			s = s.substring(s.lastIndexOf(".")).toLowerCase();
			 var patrn = /(.mp4|.mov|.avi|.wmv|.mkv)$/;
			    if (!patrn.exec(s)) return false;
			    return true;
		},
		isImgType:function(s){
			s = s.substring(s.lastIndexOf(".")).toLowerCase();
			var patrn = /(.jpg|.png)$/;
		    if (!patrn.exec(s)) return false;
		    return true;
		},
		
		tip:function(id,c){
			$('#'+id).text(c);
			$('#'+id).show();
		}
}
