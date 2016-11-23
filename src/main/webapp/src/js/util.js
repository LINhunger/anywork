
/**
 * 获取URL信息
 */
function getUrlInformation(){
	var qs = (location.search.length>0 ? location.search.substring(1) : "" ),
		args = {},
		items = qs.length ? qs.split("&") : [],
		item = null,
		name = null,
		value = null;
	for(var i=0 ; i<items.length; i++){
		item = items[i].split("=");
		name = decodeURIComponent(item[0]);
		value = decodeURIComponent(item[1]);
		if(name.length){
			args[name]=value;
		}
	}
	return args;
}

/**
 * 获取User信息
 */
function getUserInfo(){
	var user;
	$.ajax({
		url: "/anywork/user/info",
		type: "GET",
		async: false,
		contentType: "application/json; charset=utf-8",
		success: function(data){
			user =  data.data;
		},
		dataType: "json",
	});
	return user;
}

/**
 * 获取Organ信息
 */
function getAllOrganById(id){
	var organs;
	var info = {
		organId : id,
	};
	$.ajax({
		type: "GET",
		url: "/anywork/relation/allorgan",
		async: false,
		data: JSON.stringify(info),
		contentType: "application/json; charset=utf-8",
		success: function(data){
			var state = data.state,
				stateInfo = data.stateInfo;
			if(state===176){
				organs = data.data;
			}
			else{
				my_alert(data.stateInfo);
			}
		},
		dataType: "json",
	})
	return organs;
}

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
				organs = data.data;
			}
			else my_alert(stateInfo);
		},
		dataType: 'json',
	})
	return organs;
}

/**
 * 获取作业信息
 */
function getHomewrokById(homeworkId,authorId){
	var homeworkInfo;
	$.ajax({
		type: "GET",
		url: "/anywork/timeline/homework/"+homeworkId+"/"+authorId,
		async: false,
		success: function(data){
			var state = data.state,
				stateInfo = data.stateInfo;
			if(state=='211'){
				homeworkInfo = data.data;
			}
			else my_alert(stateInfo);
		},
		dataType: 'json',
	})
	return homeworkInfo;
}

/**
 * 微信相关
 */
function bindWetChar(openid,email,password){
	var info={
		openid: openid,
		email: email,
		password: password,
	}

	$.ajax({
		type: "POST",
		url: "/anywork/wechatconfig/binding",
		async: false,
		contentType: "application/json; charset=utf-8",
		data: JSON.stringify(info),
		success: function(data){
			var state = data.state,
				stateInfo = data.stateInfo;
			alert(stateInfo);
		},
		dataType: 'json',
	})
}

function wetCharGetOrgans(openid){
	var organs = null;
	$.ajax({
		type: "POST",
		url: "/anywork/wechat/wechatconfig/organ/"+ openid,
		async: false,
		contentType: "application/json; charset=utf-8",
		success: function(data){
			var state = data.state,
				stateInfo = data.stateInfo;
			if(organs==null)  alert('您还没有加入组织或者没绑定微信');
			else organs = data.data;
		},
		dataType: 'json',
	})
	return organs;
}

function wetCharNews(markid,type){
	var infos=null;
	$.ajax({
		type: "POST",
		url: "/anywork/wechatconfig/read/"+ type +"/"+ markid,
		async: false,
		contentType: "application/json; charset=utf-8",
		success: function(data){
			var state = data.state, //194公告，195作业, 196获取信息失败
				stateInfo = data.stateInfo;
			if(state==196){
				alert(stateInfo)
			}else{
				infos = data.data;
			}
		},
		dataType: 'json',
	})
	return infos;
}

//获取pdf文件夹列表
function getFileList(homeworkId,authorId){
	var files=[];
	$.ajax({
		type: "GET",
		url: "/anywork/timeline/showPDF/"+homeworkId+"/"+authorId,
		async: false,
		success: function(data){
			var state = data.state, //194公告，195作业, 196获取信息失败
				stateInfo = data.stateInfo;
			if(state==156){
				files = data.data;
			}
		},
		dataType: 'json',
	})
	return files;
}


/**
 * 其他函数
 */
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

function setTiemTo(second,url,stateInfo,gotowhere){
	second += 1;
	var timer = setInterval(function(){
		my_alert(stateInfo+","+second+"秒后"+gotowhere);
		second--;
		if(second<0) clearInterval(timer);
	}, 1000);
	setTimeout(function(){
		window.location.href=url;
	}, second*1000);
}
