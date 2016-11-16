var user = getUserInfo();

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
				window.location.href="homework.html?homeworkId="+this.item.homeworkId
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
				alert(this.item.mark);
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
				var i = this.item.author.userId==user.userId?1:0;
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
 		})
		//排序每一天的时间
		for(var d in list){
			var message = list[d].message;
			var j,t;
			for(var i=0; i<message.length-1; i++){
				for(j=i;j<message.length-1; j++){
					if(message[j].createTime<message[j+1].createTime){
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

	var URLInfo = getUrlInformation();
	var TreeTime = getTimeLine( getZeroTime(), URLInfo.organId)

	/**
	 * 格式化日期
	 */
	 	function formatTime(time,t){
			var time = new Date(time);
			var y = time.getFullYear();
			var m = time.getMonth()+1;
			var d = time.getDate();
			var h = time.getHours();
			var mm = time.getMinutes();
			var s = time.getSeconds();
	 		if(t=='md') return add0(m)+'.'+add0(d);
	 		if(t=='m') return m;
	 		if(t=='d') return d;
			function add0(m){return m<10?'0'+m:m }
		}

	/**
	 * 获取今天零点毫秒
	 */
	 	function getZeroTime(){
	 		var date = new Date();
			var str = date.getFullYear()+'/'+(date.getMonth()+1)+'/'+date.getDate();
			return  Date.parse(str); 
	 	}

	/**
	 * 截取字符串
	 */ 
		function my_slice(str, n){
			 if(str.length>n){
			 	str = str.slice(0,n)+"...";
			 }
			 return str;
		}

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
		window.onscroll= function(){
			var docHeight = $(document).height();          //所以内容的总高度
		    var winHeight  = $(window).height();             //浏览器窗口的高度
		    var scrollHeight = $(window).scrollTop();       //下拉条距离顶部的高度
		 
		    if(scrollHeight+winHeight>=docHeight-30){
		    	TreeTime -= 86400000*3 ;
		    	TreeTime = getTimeLine(TreeTime, URLInfo.organId)    
		    }
		}

	/**
	 * 回到顶部                                          
	 */
	    $("#goTop").click(function(){
	    	$("html, body").delay(100).animate({scrollTop: 20 }, $(window).scrollTop()<2800?$(window).scrollTop():2800);
	    })
