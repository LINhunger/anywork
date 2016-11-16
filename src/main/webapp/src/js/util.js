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