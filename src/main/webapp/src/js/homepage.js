
var user = getUserInfo();

/**
 * 圆盘的逻辑
 */
	$(function(){
		var list = [1, 5, 9, 4, 8, 3, 7, 2, 6];
		var organs = getAllOrganById(user.userId);
		for(var i=0; i<organs.length; i++){
			if(i>9) black;
			var headImg = "url(/anywork/photo/"+ organs[i].organId + ".jpg)";
			$("#"+list[i]).attr("info",organs[i].organId).css("background-image", headImg).show();
		}

		$('#rotate').click(function(e){
			var e = $(e.target);
			if( !e.hasClass("star") || e.attr("info")==undefined) return;
			e.show();
			var organId = e.attr("info");
			window.location.href="organization.html?organId="+organId;
		})
		
		$(".star").mouseenter(function(){
			$(this).parent('div').css('animation-play-state','paused');
			$(this).parent('div').find('.star').css('animation-play-state','paused');
		}).mouseout(function(){
			$(this).parent('div').css('animation-play-state','running');
			$(this).parent('div').find('.star').css('animation-play-state','running');
		});
	})

var my_nav = {
	template: `
	<div @click="stopP">
		<span class="page-logo" @click='toHomePage'></span>
    	<ul>
    		<li class="border-left"><i class="bell-logo logo"></i></li>
    		<li class="border-left"><img class="head_img logo" v-bind:src="headImg"><span class="username">{{userName}}</span></li>
    		<li class="border-left"><i class="close-logo logo" @click="close"></i></li>
    	</ul>
	</div>`,
	data: function(){
		return {
			userName: user.userName,
			headImg: '/anywork/picture/'+user.picture,
		}
	},
	methods: {
		close: function(){
			$.get("/anywork/user/signout",function(data){
				window.location.href="login.html";
			},'json');
		},
		toHomePage: function(){
			window.location.href="homepage.html";
		},
		stopP: function(e){
			e.stopPropagation();
		}
	}
}

var my_info = {
	template: `
	<div id="my_info" @click="stopP">
		<div class="head"><i class="my_info_icon"></i>我的信息</div>
		<div class="head_img" @click="toChange">
			<input type = "file" id="input_file" accept="image/*" @change="fileChange">
			<img :src="headImg">
			<p>点击图片更换头像</p>
			<div id="preview-pane">
			    <div class="preview-container">
			       <img src="../images/day.png" class="jcrop-preview" alt="Preview" />
			    </div>
		  	</div>
		</div>
		<div class="info">
			<div class="info_space"><span class="name_1">昵称</span><input type="text" v-model="username"></div>
			<div class="info_space"><span class="name_1">邮箱</span><span class="info_email">{{email}}</span></div>
			<div class="info_space"><span class="name_1">电话</span><input type="text" v-model="phone"></div>
			<div id="weichar">
				<span class="name_1">绑定微信</span>
				<div v-if="isLink" class="is_weichar_bg"></div>
				<div v-else class="no_weichar_bg"></div>
			</div>
		</div>
		<div class="button">
			<input type="button" value="保存" @click="save">
		</div>
	</div>`,

	data: function(){
		var email = user.email,
			isLink = true,
			username = '',
			phone = '',
			headImg = '/anywork/picture/default.jpg';

		return {
			previewImg: '',
			isFile: false,
			isLink: user.isWechat,
			username: user.userName,
			phone: user.phone,
			email: email,
			headImg: '/anywork/picture/'+user.picture,
		}

	},
	methods: {
		save: function(){
			/**
			 * 文本信息上传
			 * @type {String}
			 */
			var errorInfo ='';
			var regPhone =  /^1[3|4|5|7|8]\d{9}$/;
			if(this.username===''){
				errorInfo += "昵称不能为空";
				my_alert(errorInfo);
				return;
			}
			if(this.phone===''){
				errorInfo += "请输入电话";
				my_alert(errorInfo);
				return;
			}
			if(!regPhone.test(this.phone)){
				errorInfo += "电话格式不正确";
				my_alert(errorInfo);
				return;
			}
			var info = {
				email: this.email,
				userName : this.username,
				phone: this.phone,
				isWechat: this.isLink,
				phone: this.phone,
			}

			$.ajax({
				type: "POST",
				url: '/anywork/user/update',
				contentType: "application/json; charset=utf-8",
				dataType: "json",
				data: JSON.stringify(info),
				success: function(data){
					var state = data.state,
						stateInfo = data.stateInfo;
					my_alert(stateInfo);
					if(state===141){
						user = getUserInfo();
						user.picture = user.picture +"?"+ new Date().valueOf();
					}else{
						this.isLink=user.isWechat;
						this.username=user.userName;
						this.phone=user.phone;
						this.email=user.email;
						this.headImg='/anywork/picture/'+user.picture;
						return;
					}
				},
			})

			/**
			 * 头像文件上传：
			 */
			if(this.isFile){
				var file = $("#input_file")[0].files[0];
				console.log(file.type);
				if(file.type.indexOf('image')==-1){
					my_alert("格式不正确,请上传图片格式的文件!");
					return ;
				}
				var formData = new FormData();
				formData.append("file",file);
				formData.append("x",jcropInfo.x);
				formData.append("y",jcropInfo.y);
				formData.append("width",jcropInfo.w);
				formData.append("height",jcropInfo.h);
				$.ajax({
					url:"/anywork/user/portait",
					type:"POST",
					data: formData,
					cache: false,
					processData: false,
					contentType: false,
					success: function(data){
								var state = data.state,
								    stateInfo = data.stateInfo;
								if(state=="151"){
									this.isFile = false;
								}else{
									my_alert(stateInfo);
								}
							},
					dataType:"json"
				});
			}
		},
		toChange: toChange,
		fileChange: fileChange,
		stopP: stopP,
	},
}

var create_team = {
	template : `
		<div id="create_team" @click="stopP">
			<div class="head"><i class="create_team_icon"></i>创建组织</div>
			<div class="head_img" @click="toChange">
				<input type = "file" id="input_file" accept="image/*" @change="fileChange">
				<img :src="teamImg">
				<p>点击图片上传组织logo</p>
				<div id="preview-pane">
				    <div class="preview-container">
				       <img src="../images/day.png" class="jcrop-preview" alt="Preview" />
				    </div>
			  	</div>
			</div>	
			<div class="info">
				<div class="info_space"><span class="name_1">名字</span><input type="text" v-model="teamname"></div>
				<div class="info_space"><span class="name_1 pt2">描述</span><textarea v-model="describe"></textarea></div>
			</div>
			<div class="button">
				<input type="button" value="创建" @click="create">
			</div>
		</div>`,
	data: function(){
		teamImg = '/anywork/picture/default.jpg';
		return {
			isFile: false,
			teamname: "",
			describe: "",
			teamImg: teamImg,
		}
	},
	methods: {
		create: function(){
			var info = {
				organName: this.teamname,
				description: this.describe,
			};
			var organId;
			$.ajax({
				type: "POST",
				url: "/anywork/organ/create",
				async: false,
				data: JSON.stringify(info),
				contentType: "application/json; charset=utf-8",
				success: function(data){
					var state = data.state,
						stateInfo = data.stateInfo;
						organId = data.data;
				},
				dataType: "json",
			});

			if(this.isFile){
				var file = $("#input_file")[0].files[0];
				console.log(file.type);
				if(file.type.indexOf('image')==-1){
					my_alert("格式不正确,请上传图片格式的文件!");
					return ;
				}
				var formData = new FormData();
				formData.append("file",file);
                formData.append("x",jcropInfo.x);
                formData.append("y",jcropInfo.y);
                formData.append("width",jcropInfo.w);
                formData.append("height",jcropInfo.h);
                formData.append("organId",organId);

				$.ajax({
	                url:"/anywork/organ/update",
					type:"POST",
					data: formData,
					cache: false,
					processData: false,
					contentType: false,
					success: function(data){
								var state = data.state,
								    stateInfo = data.stateInfo;
								if(state=="151"){
									this.isFile = false;
									my_alert('文件上传成功!');
								}else{
									my_alert(stateInfo);
								}
							},
					dataType:"json"
				});
			}
		},
		toChange: toChange,
		fileChange: fileChange,
		stopP: stopP,
	},
}

var search = {
	template: `
		<div id="search" @click="stopP">   
			<div class="head"><i class="search_icon"></i>搜索</div>
			<div id="find_input">
				<span class="pay_list_c1 on">
					<input type="radio" name="foundTeam" value="atID" id="atID" class="radioclass" checked="checked" @click="isCheck">
				</span>
				<label for="atID">按ID</label>
				<span class="pay_list_c1">
					<input type="radio" name="foundTeam" value="atName" id="atName" class="radioclass" @click="isCheck">
				</span>
				<label for="atName">按名字</label>
				<input type="text" v-model="searchInfo">
				<i class="find_icon" @click="search"></i>
			</div>
			<ul id="find_result">
				<organ v-for="item in organs" :item="item"></organ>
            </ul>
		</div>`,

	data: function(){
		return {
			isID: true,	
			organs: [],
			searchInfo: '',
		}
	},
	methods: {
		isCheck: function(e){
			$(e.target).parent().addClass("on").siblings().removeClass("on");
			var id = $(e.target).attr('id');
			if(id==="atID") this.isID = true;
			else this.isID = false;
		},
		search: function(){
			this.organs = [];
			if(this.isID){
				this.organs.push(getOrganById(this.searchInfo));
			}else{
				this.organs.push( getOrgansByName(this.searchInfo));
			}
		},
		stopP: stopP,
	},
	components: {
		organ: {
			template: `
				<li :item="item">
	                <img :src="headImg" alt="头像" class="head_img"><span class="name">{{item.organName}}</span>
	                <div class="button">
	                    <input type="button" v-if="isJoin" class="isJoin" value="已加入">
	                    <input type="button" v-else class="Join" value="加入" @click="join">
	                </div>
	            </li>`,
	        props:['item'],
	        data: function(){
	        	return  {                //需要比较有没有加入了。
	        		isJoin: false,
	        	}
	        },
	        methods: {
	        	join: function(){
	        		applyJoin(this.item.organId);
	        	}
	        },
	        computed: {
	        	headImg : function(){
	        		return  "/anywork/photo/"+ this.item.organId + ".jpg";
	        	}
	        }

		}
	},
}

var frame = new Vue({
	el: "#frame",
	data: {
		frame: '' //更换信息框
	},
	components: {
		myInfo: my_info,
		createTeam: create_team,
		search: search,
	}
})

var nav = new Vue({
	el: "#nav",
	components: {
		myNav: my_nav,
	}
})

function getOrganById( id ){
	var organ;
	$.ajax({
		type: "GET",
		url: "/anywork/organ/searchId",
		async: false,
		data: {organId:id},
		success: function(data){
			var state = data.state,
				stateInfo = data.stateInfo;
			if(state=='183'){
				organ = data.data;
			}	
			else my_alert(stateInfo);
		},
		dataType: 'json',
	})
	return organ;
}

function getOrgansByName( name ){
	var organs;
	$.ajax({
		type: "GET",
		url: "/anywork/organ/searchName",
		async: false,
		data: {organName:name},
		success: function(data){
			var state = data.state,
				stateInfo = data.stateInfo;
			if(state=='184'){
				oragns = data.data;
			}	
			else my_alert(stateInfo);
		},
		dataType: 'json',
	})
	return organs;
}

function applyJoin (id){
	var info = {
		organId : id,
	};
	$.ajax({
		type: "POST",
		url: "/anywork/apply/send",
		data: JSON.stringify(info),
		contentType: "application/json; charset=utf-8",
		success: function(data){
			my_alert(data.stateInfo);
		},
		dataType: "json",
	})
}

/**
 * 截图js
 */
 	var jcropInfo = {};
 	var jcrop_api,
	    	boundx,
	    	boundy,
	        $preview,
	        $pcnt,
	        $pimg,
	        xsize, //预览框大小
	        ysize;

	function prepareJcrop(){

        $preview = $('#preview-pane');
        $pcnt = $('#preview-pane .preview-container');
        $pimg = $('#preview-pane .preview-container img');

        xsize = $pcnt.width(); //预览框大小
        ysize = $pcnt.height();

		$('#target').Jcrop({
			aspectRatio: xsize / ysize,
			createHandles : ['se'],	
	    	onChange: updatePreview,   //预览框实现函数
				onSelect: updatePreview,
	   		minSize :[10,10]
		},function(){
			var bounds = this.getBounds();
		    boundx = bounds[0];
		    boundy = bounds[1];
		    // Store the API in the jcrop_api variable
		    jcrop_api = this;
		    // Move the preview into the jcrop container for css positioning
			jcrop_api.setSelect([150,80,150+100,80+100]);
		});
	}		

	function updatePreview(c){
		if (parseInt(c.w) > 0){
			var rx = xsize / c.w;
			var ry = ysize / c.h;
			$pimg.css({
				width: Math.round(rx * boundx) + 'px',
				height: Math.round(ry * boundy) + 'px',
				marginLeft: '-' + Math.round(rx * c.x) + 'px',
				marginTop: '-' + Math.round(ry * c.y) + 'px'
			});
		}
       jcropInfo = {
        	w: c.w,
        	h: c.h,
        	x: c.x,
        	y: c.y,
        }
	};

	function toChange(e){
		$('#input_file').click(function(e){
			e.stopPropagation();
		})
		if($('#input_file')[0]){
			var a = $('#input_file')[0];
			var e = document.createEvent('MouseEvent');  
	        e.initEvent('click', false, false);  
	         	a.dispatchEvent(e);
			}
			e.stopPropagation();
	}

	function fileChange(){
			var fileList = $("#input_file")[0].files;
			if(fileList.length>0){
				if(fileList[0].type.indexOf('image')==-1){
					my_alert("文件: 《"+ fileList[i].name +"》 不是图片文件,请上传图片格式的文件!");
					return;
				}
				var reader = new FileReader();
				reader.onload = function(e){
					var dataURL = reader.result;
					//jcrop
						$(".jcrop-preview").attr("src",dataURL);
						my_alert('<img src='+dataURL+' id="target" style="width: 600px;" />',600,400);

						setTimeout(function(){
							$("#preview-pane").show();
							$("#dialog-title i").click(function(){
								$("#preview-pane").hide();
							})
							prepareJcrop();		
						}, 10);
				}
				reader.readAsDataURL(fileList[0]);
				this.isFile = true;
			}
	}

	function stopP(e){
		e.stopPropagation();
	}

/**
 *  转盘停止                                                                                                                                                                                                                                                                                                                                                              [description]
 */
	$(function(){
		$(".star").mouseenter(function(){
			$(this).parent('div').css('animation-play-state','paused');
			$(this).parent('div').find('.star').css('animation-play-state','paused');
		}).mouseout(function(){
			$(this).parent('div').css('animation-play-state','running');
			$(this).parent('div').find('.star').css('animation-play-state','running');
		});
	})
