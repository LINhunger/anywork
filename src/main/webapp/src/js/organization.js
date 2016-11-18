var URL_INFO = getUrlInformation();
var USER =  getUserInfo();
var ORGAN =  getOrganById(URL_INFO.organId)


$(function(){
	$("#issue_title").val('');
	$("#issue_body").text('');
})
/**
 * 导航栏的
 */
var my_nav = {
	template: `
		<div @click="stopP">
			<span class="page-logo" @click='toHomePage'></span>
	    	<ul>
	    		<li class="border-left"><i class="bell-logo logo"></i></li>
	    		<li class="border-left at"><img class="head_img logo" v-bind:src="headImg"><span class="username">{{userName}}</span></li>
	    		<li ><i class="close-logo logo" @click="close"></i></li>
	    		<li class="border-left"><i class="back-logo logo" @click="goBack"></i></li>
	    	</ul>
		</div>`,
	data: function(){
		return {
			userName: USER.userName,
			headImg: '/anywork/picture/'+USER.picture,
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
		},
		goBack: function(){
			window.location.href='homepage.html';
		}
	}
}

var nav = new Vue({
	el: "#nav",
	components: {
		myNav: my_nav,
	}
})

/**
 * 时间轴的
 *
 */
var homework = {
	template:`
		<div class="home_work" v-on:click="doHomework" :class=[itStyle]>
			<span class="home_title"> {{homeworkTitle}}</span>
			<span class="number">已交:{{item.submitCount}}</span>
			<p class="time">{{timeSpan}}<i></i></p>
			<p class="info"> {{markInfo}} </p>
			<div class="lietTop"></div>
			<div class="rightBottom"></div>
		</div>`,
	props: ['item'],
	data: function(){
		return {
			itStyle: "home_work"+this.item.status,
		}
	},
	methods: {
		doHomework: function(){
			window.location.href="homework.html?homeworkId="+this.item.homeworkId+"&authorId="+USER.userId;
		}
	},
	computed: {
		timeSpan: function(){
			var creat = formatTime(this.item.createTime,'md');
			var ending =formatTime(this.item.endingTime,'md');
			return creat+"-"+ending;
		},
		markInfo: function(){
			return my_slice(this.item.mark,36);
		},
		homeworkTitle: function(){
			return my_slice(this.item.homeworkTitle,4);
		}
	}
}

var inform = {
	template: '<div class="inform" v-on:click="say">\
			<p class="title">{{informTitle}}</p>\
			<p class="info">{{markInfo}}</p>\
			<div class="lietTop"></div>\
			<div class="rightBottom"></div>\
			<div class="leftBottom"></div>\
		</div>',

	props: ['item'],

	methods: {
		say: function(){
			my_alert(
				`<h2>`+this.item.informTitle+`</h2>
					<p>`+this.item.mark+`</p>`
			);
		}
	},
	computed: {
		markInfo: function(){
			return my_slice(this.item.mark,36);
		},
		informTitle: function(){
			return my_slice(this.item.informTitle,4);
		},
	}
}

var one_day = {
	template: '\
		<div class="day" v-on:click="nextDay">\
			<p class="month">{{month}}</p>\
			<p class="dayTime">{{day}}</p>\
		</div>',
	props: ['time'],
	methods: {
		nextDay: function(event){
			$("html, body").delay(100).animate({scrollTop: $(event.target).offset().top-40 }, ($(event.target).offset().top-$(window).scrollTop())*2);
		}
	},
	computed: {
		day: function(){
			return formatTime(this.time,'d')
		},
		month: function(){
			if(this.day=='1') return formatTime(this.time,'m')+"月"
		}
	}
}

var question = {
	template:
		'<div class="quest" v-on:click="say" :class=[itStyle]>\
            <div class="tip">求助</div>\
            <div class="content">\
                <p class="quest_name">{{questTitle}}</p>\
                <p class="quest_mark">{{questMark}}</p>\
            </div>\
            <div class="rightAngle"></div>\
        </div>',
	props: ['item'],
	data: function(){
		return {

		}
	},
	methods: {
		say : function(){
			alert("i am"+this.item.author)
		}
	},
	computed: {
		questMark: function(){
			return my_slice(this.item.mark,6);
		},
		questTitle: function(){
			return my_slice(this.item.questTitle,4);
		},
		itStyle: function(){
			var i = this.item.author.userId==USER.userId?1:0;
			return "quest"+ i;
		}
	}
}

var day_tree = {
	template: '<div class="tree"><slot></slot></div>',
	data: function(){
		return {

		}
	}
}

var tree = new Vue({
	el: "#tree",
	components: {
		oneDay: one_day,
		homework: homework,
		inform: inform,
		question: question,
		tree: day_tree
	},
	data: {
		day: [],
	},
	computed: {
	}
})

/**
 * 获取时间树列表
 */
function getTimeLine(time,organId){
	var list;
	$.ajax({
		type: 'GET',
		url: "/anywork/timeline/"+time+"/timeline/"+organId,
		async: false,
		success: function(data){
			var state = data.state,
				stateInfo = data.stateInfo,
				info = data.data;
			if(state!=201){
				my_alert(stateInfo);
			}else{
				list = info;
			}
		},
		dataType: 'json',
	});
	//排序每一天的时间
	for(var d in list){
		var message = list[d].message;
		var j,t;
		for(var i=0; i<message.length-1; i++){
			for(j=i;j<message.length-1; j++){
				if(message[j].createTiem<message[j+1].createTime){
					t = message[j];
					message[j] = message[j+1];
					message[j+1] = t;
				}
			}
		}
	}
	tree.day.push(list.day03);
	tree.day.push(list.day02);
	tree.day.push(list.day01);
	return time;
}


var TreeTime = getTimeLine( getZeroTime(), URL_INFO.organId)


/**
 * 打开页面上划过渡
 */
setTimeout(function(){
	$(window).scrollTop($(document).height());
	$("html, body").delay(100).animate({scrollTop: 20 }, $(window).scrollTop());
}, 50);

/**
 * 下滑刷新
 */
$(window).scroll(function(){
	var docHeight = $(document).height();          //所以内容的总高度
	var winHeight  = $(window).height();             //浏览器窗口的高度
	var scrollHeight = $(window).scrollTop();       //下拉条距离顶部的高度

	if(scrollHeight+winHeight>=docHeight-30){
		TreeTime -= 86400000*3 ;
		TreeTime = getTimeLine(TreeTime, URL_INFO.organId)
	}
})

/**
 * 回到顶部
 */
$("#goTop").click(function(){
	$("html, body").delay(100).animate({scrollTop: 20 }, $(window).scrollTop()<2800?$(window).scrollTop():2800);
})


/**
 * 选项卡的
 */
var mySelect = {
	template: `<div>
			<p class="organ_name">{{organName}}</p>
			<p class="organ_describe">{{description}}</p>
			<ul @click="select">
				<li id="tree_option"><i class="tree_logo"></i><span>时间轴</span></li>
				<li id="quest_option"><i class="quest_logo"></i><span>我的求助</span></li>
				<li id="send_option"><i class="send_logo"></i><span>发出公告/题目</span></li>
				<li id="manage_option"><i class="manage_logo"></i><span>组织管理</span></li>
			</ul>
 		</div>`,
	data: function(){
		return {
			organName: ORGAN.organName,
			description: ORGAN.description
		}
	},
	methods: {
		select: function(e){
			var target = $(e.target).closest('Li');
			target.css("background-color","#248edb").
			siblings().css("background-color","rgba(173, 173, 173, 0)")
			if(target.attr("id") === "tree_option" ){
				$("#tree").fadeIn(2000);
				$("#sendInfo").hide();
			}else if(target.attr("id") === "send_option" ){
				$("#tree").hide();
				$("#sendInfo").fadeIn(2000);
			}else if(target.attr("id") === "manage_option" ){
				var url = "/anywork/admin/"+ORGAN.organId+"/list";
				window.open (url)
			}else{
				$("#tree").show(1500);
				$("#sendInfo").hide();
			}
		}
	}
}

var option = new Vue({
	el: "#option",
	components: {
		mySelect: mySelect,
	},
})


/**
 * sendInfo
 */
var isInform = true;
var options = {
	template: `
			<div id="options">
				<span class="option" v-bind:class="{'select': isInform}" @click="inform">公告</span><span class="option" v-bind:class="{'select': !isInform}" @click="homework">作业</span>
			</div>`,
	data: function(){
		return {
			isInform: true,
		}
	},
	methods:{
		inform: function(){
			this.isInform = true;
			isInform = true;
			$("#deadline").hide();
			$(".discussion-topic-header").css("margin-bottom","0");
		},
		homework: function(){
			this.isInform = false;
			isInform = false;
			$("#deadline").show();
			$(".discussion-topic-header").css("margin-bottom","60px");
		}
	}
};

var sendInfo = new Vue({
	el: "#sendInfo",
	components: {
		options: options,
	},
})

/**
 * 生日下拉框
 */
$("#year").change(showDay);
$("#month").change(showDay);
function getYearSelect(){
	var now = new Date();
	var year = now.getFullYear();
	var str = "<option selected value='0'></option>";
	//用一个方法循环输出年份;
	for(i=year-100; i<=year; i++){
		str +="<option value="+i+">"+i+"</option>";
	}

	document.getElementById("year").innerHTML=str;
}
function getMonthSelect(){
	var str = "<option selected value='0'></option>";
	//用一个方法循环输出年份;
	var jj;
	for(j=1;j<=12;j++){
		if(j<10){
			jj="0"+j;
		}else{
			jj=j;
		}
		str +="<option value="+jj+">"+jj+"</option>";
	}
	document.getElementById("month").innerHTML=str;
}
function showDay(){
	var y = document.getElementById("year").value;
	var m = document.getElementById("month").value;
	switch(m){
		case "0":day=0; break;
		case "01":
		case "03":
		case "05":
		case "07":
		case "08":
		case "10":
		case "12":
			day = 31;
			break;
		case "04":
		case "06":
		case "09":
		case "11":
			day = 30;
			break;
		case "02":
			if(y%4==0&&y!=0||y%400==0){
				day = 29;
			} else {
				day = 28;
			}
	}
	str = "<option value='0'></option>";
	var jj;
	for(a=1;a<=day;a++){
		if(a<10){
			jj="0"+a;
		}else{
			jj=a;
		}
		str += "<option value="+jj+">"+jj+"</option>";
	}
	document.getElementById("day").innerHTML = str;
}
getMonthSelect();
getYearSelect();

/**
 * 发公告
 */
function sendInform(informId,organId,informTitle,mark){
	var info = {
		informId: informId,
		organId: organId,
		informTitle: informTitle,
		mark: mark
	}
	$.ajax({
		type: "POST",
		url: "/anywork/timeline/inform/release",
		data: JSON.stringify(info),
		contentType: "application/json; charset=utf-8",
		success: function(data){
			var state = data.state,
				stateInfo = data.stateInfo;
			setTiemTo(3, location.href, stateInfo, "退回时间轴");
		},
		dataType: "json",
	})
}

/**
 * 发作业
 */
function sendHomework(homeworkId,organId,homeworkTitle,mark,endingTime){
	var info = {
		homeworkId: homeworkId,
		organId: organId,
		homeworkTitle: homeworkTitle,
		mark: mark,
		endingTime: endingTime,
	}
	$.ajax({
		type: "POST",
		url: "/anywork/timeline/homework/release",
		data: JSON.stringify(info),
		contentType: "application/json; charset=utf-8",
		success: function(data){
			var state = data.state,
				stateInfo = data.stateInfo;
			setTiemTo(3, location.href, stateInfo, "退回时间轴");
		},
		dataType: "json",
	})
}

/**
 * 绑定提交公告和作业事件
 */
$(function(){
	$(".btn-primary").click(function(){
		if(isInform){
			sendInform(0, ORGAN.organId, $("#issue_title")[0].value, $("#issue_body")[0].value);
			if($("#isWetchar input").is(":checked")){
				//my_alert('微信群发的接口时什么啊')
			}
		}else{
			var d_year = $("#year").val();
			var d_month = $("#month").val();
			var d_day = $("#day").val();
			var deadline = d_month+"/"+d_day+"/"+d_year;
			if(d_month==0 || d_day==0 || d_year==0){
				alert('请选择截止日期')
				return false;
			}
			var endingTime = new Date(deadline).valueOf();

			sendHomework(0, ORGAN.organId, $("#issue_title")[0].value, $("#issue_body")[0].value, endingTime);
			if($("#isWetchar input").is(":checked")){
				//my_alert('微信群发的接口时什么啊')
			}
		}
		return false;
	})
})


$(function(){

	$("#selectIt").change(function(){
		var file = this.files[0];
		var filename;
		var type = file.type.split('\/')[1];
		if(file){
			console.log(file.name);

			filename = new Date().valueOf()+"."+type;
			if(file.type.indexOf('image')==-1){
				alert("文件: 《"+ fileList[i].name +"》 不是图片文件,请上传图片格式的文件!");
				return ;
			}
			var formData = new FormData();
			formData.append('file',file,filename);
			$.ajax({
				url: "/anywork/timeline/upload",
				type: "POST",
				data: formData,
				processData: false,
				contentType: false,
				success: function(data, textStatus){
					// 插入的数据
					var text = document.querySelector("#issue_body").value;
					if(text.trim()===''){
						text = "!["+file.name+"](https://localhost:80/anywork/upload/"+filename+")";
					}else{
						text = "\n!["+file.name+"](https://localhost:80/anywork/upload/"+filename+")";
					}

					// 从光标位置插入数据
					var obj = $("#issue_body")[0]; //obj为文本框
					if (obj.selectionStart || obj.selectionStart == '0') {
						var startPos = obj.selectionStart;
						var endPos = obj.selectionEnd;
						if(endPos<obj.value.length && obj.value.charAt(endPos)!='\n'){
							text+="\n";
						}
						obj.value = obj.value.substring(0, startPos)
							+ text
							+ obj.value.substring(endPos, obj.value.length);
						obj.selectionStart = startPos + text.length;
						obj.selectionEnd = startPos + text.length;
					} else {
						obj.value += text;
					}
					obj.focus();
					obj.setSelectionRange(obj.value.length,obj.value.length)
					// 情况input type="file" 的值
					var f = $("#selectIt")
					f.after(f.clone(true).val(""));
					f.remove();
				},
				dataType: "json"
			})
		}
	})

	$("#preview-hg").click(function(){
		var text = document.querySelector("#issue_body").value;
		var info= {
			text : text
		};var URL_INFO = getUrlInformation();
		var USER =  getUserInfo();
		var ORGAN =  getOrganById(URL_INFO.organId)


		/**
		 * 导航栏的
		 */
		var my_nav = {
			template: `
		<div @click="stopP">
			<span class="page-logo" @click='toHomePage'></span>
	    	<ul>
	    		<li class="border-left"><i class="bell-logo logo"></i></li>
	    		<li class="border-left at"><img class="head_img logo" v-bind:src="headImg"><span class="username">{{userName}}</span></li>
	    		<li ><i class="close-logo logo" @click="close"></i></li>
	    		<li class="border-left"><i class="back-logo logo" @click="goBack"></i></li>
	    	</ul>
		</div>`,
			data: function(){
				return {
					userName: USER.userName,
					headImg: '/anywork/picture/'+USER.picture,
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
				},
				goBack: function(){
					window.location.href='homepage.html';
				}
			}
		}

		var nav = new Vue({
			el: "#nav",
			components: {
				myNav: my_nav,
			}
		})

		/**
		 * 时间轴的
		 *
		 */
		var homework = {
			template:`
		<div class="home_work" v-on:click="doHomework" :class=[itStyle]>
			<span class="home_title"> {{homeworkTitle}}</span>
			<span class="number">已交:{{item.submitCount}}</span>
			<p class="time">{{timeSpan}}<i></i></p>
			<p class="info"> {{markInfo}} </p>
			<div class="lietTop"></div>
			<div class="rightBottom"></div>
		</div>`,
			props: ['item'],
			data: function(){
				return {
					itStyle: "home_work"+this.item.status,
				}
			},
			methods: {
				doHomework: function(){
					window.location.href="homework.html?homeworkId="+this.item.homeworkId+"&authorId="+USER.userId;
				}
			},
			computed: {
				timeSpan: function(){
					var creat = formatTime(this.item.createTime,'md');
					var ending =formatTime(this.item.endingTime,'md');
					return creat+"-"+ending;
				},
				markInfo: function(){
					return my_slice(this.item.mark,36);
				},
				homeworkTitle: function(){
					return my_slice(this.item.homeworkTitle,4);
				}
			}
		}

		var inform = {
			template: '<div class="inform" v-on:click="say">\
			<p class="title">{{informTitle}}</p>\
			<p class="info">{{markInfo}}</p>\
			<div class="lietTop"></div>\
			<div class="rightBottom"></div>\
			<div class="leftBottom"></div>\
		</div>',

			props: ['item'],

			methods: {
				say: function(){
					my_alert(
						`<h2>`+this.item.informTitle+`</h2>
					<p>`+this.item.mark+`</p>`
					);
				}
			},
			computed: {
				markInfo: function(){
					return my_slice(this.item.mark,36);
				},
				informTitle: function(){
					return my_slice(this.item.informTitle,4);
				},
			}
		}

		var one_day = {
			template: '\
		<div class="day" v-on:click="nextDay">\
			<p class="month">{{month}}</p>\
			<p class="dayTime">{{day}}</p>\
		</div>',
			props: ['time'],
			methods: {
				nextDay: function(event){
					$("html, body").delay(100).animate({scrollTop: $(event.target).offset().top-40 }, ($(event.target).offset().top-$(window).scrollTop())*2);
				}
			},
			computed: {
				day: function(){
					return formatTime(this.time,'d')
				},
				month: function(){
					if(this.day=='1') return formatTime(this.time,'m')+"月"
				}
			}
		}

		var question = {
			template:
				'<div class="quest" v-on:click="say" :class=[itStyle]>\
                    <div class="tip">求助</div>\
                    <div class="content">\
                        <p class="quest_name">{{questTitle}}</p>\
                        <p class="quest_mark">{{questMark}}</p>\
                    </div>\
                    <div class="rightAngle"></div>\
                </div>',
			props: ['item'],
			data: function(){
				return {

				}
			},
			methods: {
				say : function(){
					alert("i am"+this.item.author)
				}
			},
			computed: {
				questMark: function(){
					return my_slice(this.item.mark,6);
				},
				questTitle: function(){
					return my_slice(this.item.questTitle,4);
				},
				itStyle: function(){
					var i = this.item.author.userId==USER.userId?1:0;
					return "quest"+ i;
				}
			}
		}

		var day_tree = {
			template: '<div class="tree"><slot></slot></div>',
			data: function(){
				return {

				}
			}
		}

		var tree = new Vue({
			el: "#tree",
			components: {
				oneDay: one_day,
				homework: homework,
				inform: inform,
				question: question,
				tree: day_tree
			},
			data: {
				day: [],
			},
			computed: {
			}
		})

		/**
		 * 获取时间树列表
		 */
		function getTimeLine(time,organId){
			var list;
			$.ajax({
				type: 'GET',
				url: "/anywork/timeline/"+time+"/timeline/"+organId,
				async: false,
				success: function(data){
					var state = data.state,
						stateInfo = data.stateInfo,
						info = data.data;
					if(state!=201){
						my_alert(stateInfo);
					}else{
						list = info;
					}
				},
				dataType: 'json',
			});
			//排序每一天的时间
			for(var d in list){
				var message = list[d].message;
				var j,t;
				for(var i=0; i<message.length-1; i++){
					for(j=i;j<message.length-1; j++){
						if(message[j].createTiem<message[j+1].createTime){
							t = message[j];
							message[j] = message[j+1];
							message[j+1] = t;
						}
					}
				}
			}
			tree.day.push(list.day03);
			tree.day.push(list.day02);
			tree.day.push(list.day01);
			return time;
		}


		var TreeTime = getTimeLine( getZeroTime(), URL_INFO.organId)


		/**
		 * 打开页面上划过渡
		 */
		setTimeout(function(){
			$(window).scrollTop($(document).height());
			$("html, body").delay(100).animate({scrollTop: 20 }, $(window).scrollTop());
		}, 50);

		/**
		 * 下滑刷新
		 */
		$(window).scroll(function(){
			var docHeight = $(document).height();          //所以内容的总高度
			var winHeight  = $(window).height();             //浏览器窗口的高度
			var scrollHeight = $(window).scrollTop();       //下拉条距离顶部的高度

			if(scrollHeight+winHeight>=docHeight-30){
				TreeTime -= 86400000*3 ;
				TreeTime = getTimeLine(TreeTime, URL_INFO.organId)
			}
		})

		/**
		 * 回到顶部
		 */
		$("#goTop").click(function(){
			$("html, body").delay(100).animate({scrollTop: 20 }, $(window).scrollTop()<2800?$(window).scrollTop():2800);
		})


		/**
		 * 选项卡的
		 */
		var mySelect = {
			template: `<div>
			<p class="organ_name">{{organName}}</p>
			<p class="organ_describe">{{description}}</p>
			<ul @click="select">
				<li id="tree_option"><i class="tree_logo"></i><span>时间轴</span></li>
				<li id="quest_option"><i class="quest_logo"></i><span>我的求助</span></li>
				<li id="send_option"><i class="send_logo"></i><span>发出公告/题目</span></li>
				<li id="manage_option"><i class="manage_logo"></i><span>组织管理</span></li>
			</ul>
 		</div>`,
			data: function(){
				return {
					organName: ORGAN.organName,
					description: ORGAN.description
				}
			},
			methods: {
				select: function(e){
					var target = $(e.target).closest('Li');
					target.css("background-color","#248edb").
					siblings().css("background-color","rgba(173, 173, 173, 0)")
					if(target.attr("id") === "tree_option" ){
						$("#tree").fadeIn(2000);
						$("#sendInfo").hide();
					}else if(target.attr("id") === "send_option" ){
						$("#tree").hide();
						$("#sendInfo").fadeIn(2000);
						$("#issue_title").val('');
						$("#issue_body")[0].value='';

					}else if(target.attr("id") === "manage_option" ){
						var url = "/anywork/admin/"+ORGAN.organId+"/list";
						window.open (url)
					}else{
						$("#tree").show(1500);
						$("#sendInfo").hide();
					}
				}
			}
		}

		var option = new Vue({
			el: "#option",
			components: {
				mySelect: mySelect,
			},
		})


		/**
		 * sendInfo
		 */
		var isInform = true;
		var options = {
			template: `
			<div id="options">
				<span class="option" v-bind:class="{'select': isInform}" @click="inform">公告</span><span class="option" v-bind:class="{'select': !isInform}" @click="homework">作业</span>
			</div>`,
			data: function(){
				return {
					isInform: true,
				}
			},
			methods:{
				inform: function(){
					this.isInform = true;
					isInform = true;
					$("#deadline").hide();
					$(".discussion-topic-header").css("margin-bottom","0");
				},
				homework: function(){
					this.isInform = false;
					isInform = false;
					$("#deadline").show();
					$(".discussion-topic-header").css("margin-bottom","60px");
				}
			}
		};

		var sendInfo = new Vue({
			el: "#sendInfo",
			components: {
				options: options,
			},
		})

		/**
		 * 生日下拉框
		 */
		$("#year").change(showDay);
		$("#month").change(showDay);
		function getYearSelect(){
			var now = new Date();
			var year = now.getFullYear();
			var str = "<option selected value='0'></option>";
			//用一个方法循环输出年份;
			for(i=year-100; i<=year; i++){
				str +="<option value="+i+">"+i+"</option>";
			}

			document.getElementById("year").innerHTML=str;
		}
		function getMonthSelect(){
			var str = "<option selected value='0'></option>";
			//用一个方法循环输出年份;
			var jj;
			for(j=1;j<=12;j++){
				if(j<10){
					jj="0"+j;
				}else{
					jj=j;
				}
				str +="<option value="+jj+">"+jj+"</option>";
			}
			document.getElementById("month").innerHTML=str;
		}
		function showDay(){
			var y = document.getElementById("year").value;
			var m = document.getElementById("month").value;
			switch(m){
				case "0":day=0; break;
				case "01":
				case "03":
				case "05":
				case "07":
				case "08":
				case "10":
				case "12":
					day = 31;
					break;
				case "04":
				case "06":
				case "09":
				case "11":
					day = 30;
					break;
				case "02":
					if(y%4==0&&y!=0||y%400==0){
						day = 29;
					} else {
						day = 28;
					}
			}
			str = "<option value='0'></option>";
			var jj;
			for(a=1;a<=day;a++){
				if(a<10){
					jj="0"+a;
				}else{
					jj=a;
				}
				str += "<option value="+jj+">"+jj+"</option>";
			}
			document.getElementById("day").innerHTML = str;
		}
		getMonthSelect();
		getYearSelect();

		/**
		 * 发公告
		 */
		function sendInform(informId,organId,informTitle,mark){
			var info = {
				informId: informId,
				organId: organId,
				informTitle: informTitle,
				mark: mark
			}
			$.ajax({
				type: "POST",
				url: "/anywork/timeline/inform/release",
				data: JSON.stringify(info),
				contentType: "application/json; charset=utf-8",
				success: function(data){
					var state = data.state,
						stateInfo = data.stateInfo;
					setTiemTo(3, location.href, stateInfo, "退回时间轴");
				},
				dataType: "json",
			})
		}

		/**
		 * 发作业
		 */
		function sendHomework(homeworkId,organId,homeworkTitle,mark,endingTime){
			var info = {
				homeworkId: homeworkId,
				organId: organId,
				homeworkTitle: homeworkTitle,
				mark: mark,
				endingTime: endingTime,
			}
			$.ajax({
				type: "POST",
				url: "/anywork/timeline/homework/release",
				data: JSON.stringify(info),
				contentType: "application/json; charset=utf-8",
				success: function(data){
					var state = data.state,
						stateInfo = data.stateInfo;
					setTiemTo(3, location.href, stateInfo, "退回时间轴");
				},
				dataType: "json",
			})
		}

		/**
		 * 绑定提交公告和作业事件
		 */
		$(function(){
			$(".btn-primary").click(function(){
				if(isInform){
					sendInform(0, ORGAN.organId, $("#issue_title")[0].value, $("#issue_body")[0].value);
					if($("#isWetchar input").is(":checked")){
						//my_alert('微信群发的接口时什么啊')
					}
				}else{
					var d_year = $("#year").val();
					var d_month = $("#month").val();
					var d_day = $("#day").val();
					var deadline = d_month+"/"+d_day+"/"+d_year;
					if(d_month==0 || d_day==0 || d_year==0){
						alert('请选择截止日期')
						return false;
					}
					var endingTime = new Date(deadline).valueOf();

					sendHomework(0, ORGAN.organId, $("#issue_title")[0].value, $("#issue_body")[0].value, endingTime);
					if($("#isWetchar input").is(":checked")){
						//my_alert('微信群发的接口时什么啊')
					}
				}
				return false;
			})
		})


		$(function(){

			$("#selectIt").change(function(){
				var file = this.files[0];
				var filename;
				var type = file.type.split('\/')[1];
				if(file){
					console.log(file.name);

					filename = new Date().valueOf()+"."+type;
					if(file.type.indexOf('image')==-1){
						alert("文件: 《"+ fileList[i].name +"》 不是图片文件,请上传图片格式的文件!");
						return ;
					}
					var formData = new FormData();
					formData.append('file',file,filename);
					$.ajax({
						url: "/anywork/timeline/upload",
						type: "POST",
						data: formData,
						processData: false,
						contentType: false,
						success: function(data, textStatus){
							// 插入的数据
							var text = document.querySelector("#issue_body").value;
							if(text.trim()===''){
								text = "!["+file.name+"](https://localhost:80/anywork/upload/"+filename+")";
							}else{
								text = "\n!["+file.name+"](https://localhost:80/anywork/upload/"+filename+")";
							}

							// 从光标位置插入数据
							var obj = $("#issue_body")[0]; //obj为文本框
							if (obj.selectionStart || obj.selectionStart == '0') {
								var startPos = obj.selectionStart;
								var endPos = obj.selectionEnd;
								if(endPos<obj.value.length && obj.value.charAt(endPos)!='\n'){
									text+="\n";
								}
								obj.value = obj.value.substring(0, startPos)
									+ text
									+ obj.value.substring(endPos, obj.value.length);
								obj.selectionStart = startPos + text.length;
								obj.selectionEnd = startPos + text.length;
							} else {
								obj.value += text;
							}
							obj.focus();
							obj.setSelectionRange(obj.value.length,obj.value.length)
							// 情况input type="file" 的值
							var f = $("#selectIt")
							f.after(f.clone(true).val(""));
							f.remove();
						},
						dataType: "json"
					})
				}
			})

			$("#preview-hg").click(function(){
				var text = document.querySelector("#issue_body").value;
				var info= {
					text : text
				};
				$.ajax({
					type: "POST",
					url: "/anywork/timeline/priview",
					data: JSON.stringify(info),
					contentType: "application/json; charset=utf-8",
					success:function(data){
						$(".preview-content p").html(data.data);
					},
					dataType: 'json',
				})

			})
		})

		$.ajax({
			type: "POST",
			url: "/anywork/timeline/priview",
			data: JSON.stringify(info),
			contentType: "application/json; charset=utf-8",
			success:function(data){
				$(".preview-content p").html(data.data);
			},
			dataType: 'json',
		})

	})
})
