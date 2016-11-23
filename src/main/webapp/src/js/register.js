var register_info = {
	template: '<div id="register-info">\
			<div class="left-angle"></div>\
			<div class="right-angle"></div>\
			<div class="relative margin-first">\
				<i class="fa fa-user fa-fw" style="font-size:22px; color: #404040"></i><span class="logo-i">昵称</span>\
				<input v-model="username" type="text" readonly class="input-three">\
				<i class="fa fa-check-circle fa-fw fa-2x" style="color:white; position:absolute; top:-1px;"></i>\
			</div>\
			<div class="relative margin">\
				<i class="email-logo"></i><span class="logo-i">邮箱</span>\
				<input v-model="email" type="text" readonly class="input-three" >\
				<i class="fa fa-check-circle fa-fw fa-2x" style="color:white; position:absolute; top:-1px;"></i>\
			</div>\
			<div class="relative margin">\
				<i class="fa fa-phone fa-fw fa-flip-vertical" style="font-size:22px; color: #404040"></i><span class="logo-i">手机号码</span>\
				<input v-model="phone" type="text" class="input-four"  @keyup.enter="register">\
				<span v-html="phoneInfo"></span>\
			</div>\
			<div class="relative margin">\
				<i class="lock-logo"></i><span class="logo-i">密码</span>\
				<input type="password" v-model="password" class="input-three"  @keyup.enter="register">\
				<span v-html="pwInfo"></span>\
			</div>\
			<div class="relative margin">\
				<i class="lock2-logo"></i><span class="logo-i">确认密码</span>\
				<input type="password" v-model="repassword" class="input-four"  @keyup.enter="register">\
				<span v-html="repwInfo"></span>\
			</div>\
			<div class="whiteness margin">\
				<input type="button" class="button" value="注册" @click="register">\
			</div>\
		</div>',
		data: function(){
			var list = getUrlInformation();
			return {
				list: list,
				username: list.userName,
				email: list.email,
				phone: '',
				password: '',
				repassword: '',
				phoneInfo: '',
				pwInfo: '',
				repwInfo: '',
				pass: '<i class="fa fa-check-circle fa-fw fa-2x" style="color:white; position:absolute; top:-1px;"></i>',
				error: '<i class="fa fa-play fa-rotate-180" style="color: white"></i><span class="error-info">',
			}
		},
		watch: {
			phone: function(val){
				var regPhone =  /^1[3|4|5|7|8]\d{9}$/;
				if(val===''){
					this.phoneInfo = this.error + "请输入电话</span>";
				}else if(!regPhone.test(val)){
					this.phoneInfo = this.error + "电话格式不正确</span>";
				}else{
					this.phoneInfo = this.pass;
				}
			},
			password: function(val){
				if(val.length>0){
					this.pwInfo = this.pass;
				}else if(val===''){
					this.pwInfo = this.error + "请输入密码</span>";
				}
				if(this.repassword!==val){
					this.repwInfo = this.error + "密码不一致</span>";
				}else{
					this.repwInfo = this.pass;
				}
			},
			repassword: function(val){
				if(val===this.password){
					this.repwInfo = this.pass;
				}else{
					this.repwInfo = this.error + "密码不一致</span>";
				}
			}
		},
		methods: {
			register: function(){
				if(this.phone==='') this.phoneInfo = this.error + "请输入电话</span>";
				if(this.password==='') this.pwInfo = this.error + "请输入密码</span>";
				if(this.repassword==='') this.repwInfo = this.error + "请确定密码</span>";
				if(document.querySelectorAll('.fa-check-circle').length===5){

					var info = {
						email: this.email,
						userName: this.username,
						password: this.password,
						phone: this.phone
					}

					$.ajax({
						type: "POST",
						url: '/anywork/user/'+this.list.ciphertext+'/register',
						contentType: "application/json; charset=utf-8",
						dataType: "json",
						data: JSON.stringify(info),
						success: function(data){
							var state = data.state,
								stateInfo = data.stateInfo;
							if(state  =='111'){
								var second = 4;
								var timer = setInterval(function(){
									my_alert(stateInfo+","+second+"秒后将自动登录");
									second--;
									if(second<0) clearInterval(timer);
								}, 1000);
								setTimeout(function(){
									window.location.href='homepage.html?email='+data.data;
								}, 4000);
							}else{
								my_alert(stateInfo);
								return;
							}
						},
					})
				}
			}
		}
}

var form = new Vue({
	el: "#form",
	data: {
		show: true,
	},
	components: {
		registerInfo: register_info
	},
})
