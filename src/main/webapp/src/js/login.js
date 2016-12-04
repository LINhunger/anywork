var options = {
	template:
		'<div id="options">\
			<span class="option" v-bind:class="{select: isLogin}" @click="login">登录</span><span class="option" v-bind:class="{select: !isLogin}" @click="register">注册</span>\
		</div>',
	data: function(){
		return {
			isLogin: true,
		}
	},
	methods:{
		login: function(){
			this.isLogin = true;
			this.$emit('a');
		},
		register: function(){
			this.isLogin = false;
			this.$emit('b');
		}
	}
};

var login = {
	template:
		'<div id="login">\
			<div class="left-angle"></div>\
			<div class="right-angle"></div>\
			<div class="margin-top">\
				<i class="email-logo"></i><span class="logo-i">邮箱</span>\
				<input v-model="email" type="text" class="input-one" @keyup.enter="login">\
				<span v-html="emailInfo"></span>\
			</div>\
			<div class="relative">\
				<i class="lock-logo"></i><span class="logo-i">密码</span>\
				<input type="password" v-model="password" class="input-one" @keyup.enter="login">\
				<span v-html="pwInfo"></span>\
			</div>\
			<div class="margin-bottom">\
				<i class="barcode-logo"></i></i><span class="logo-i">验证码</span>\
				<input v-model="barcode" type="text" class="input-two"  @keyup.enter="login">\
				<img v-bind:src="barcodeUrl" class="barkcode-img" @click="getBarcode">\
				<span v-html="barcodeInfo"></span>\
			</div>\
			<div class="whiteness">\
				<span class="forget-password" @click="forgetPw">忘记密码</span>\
				<input type="button" class="button" value="登录" @click="login">\
			</div>\
		</div>',
	data: function(){
		return {
			barcodeUrl:'/anywork/verification',
			email: '',
			password: '',
			barcode:'',
			emailInfo: '',
			pwInfo: '',
			barcodeInfo:'',
			pass: '<i class="fa fa-check-circle fa-fw fa-2x" style="color:white; position:absolute; top:-1px;"></i>',
			error: '<i class="fa fa-play fa-rotate-180" style="color: white"></i><span class="error-info">',
		}
	},
	watch: {
		email: function(val){
			var regEmail =  /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/; 
			if(val===''){
				this.emailInfo = this.error + "请输入邮箱</span>";
			}else if(!regEmail.test(val)){
				this.emailInfo = this.error + "邮箱格式不对</span>";
			}else{
				this.emailInfo = this.pass;
			}
		},
		password: function(val){
			if(val.length>0){
				this.pwInfo = this.pass;
			}else if(val===''){
				this.pwInfo = this.error + "请输入密码</span>";
			}
		},
		barcode: function(val){
			if(val.length>0) this.barcodeInfo = this.pass;
			else this.barcodeInfo = this.error + "请填写验证码</span>";
		}
	},
	methods: {
		getBarcode: function(){
			this.barcodeUrl = "/anywork/verification?"+new Date().getTime();
		},
		forgetPw: function(){
			if(this.email===''){
				this.emailInfo = this.error + "请填写邮箱账号</span>";
			}else if(document.querySelector('.margin-top .fa-check-circle')!==null){
				var info = {
					valcode: this.barcode,
					type: 2,
					email: this.email,
				}

				$.ajax({
					type: "POST",
					url: "/anywork/user/verification",
					contentType: "application/json; charset=utf-8",
					dataType: "json",
					data: JSON.stringify(info),
					success: function(data){
						var state = data.state,
						 	stateInfo = data.stateInfo;
						 	
						if(state=='101') my_alert('邮件发送成功，请前往确认!');
						else {
							my_alert(stateInfo);
							return;
						}
					},
				})
			}
		},
		login: function(){
			if(this.barcode==='') this.barcodeInfo = this.error + "请填写验证码</span>";
			if(this.email==='') this.emailInfo = this.error + "请输入邮箱</span>";
			if(this.password==='') this.pwInfo = this.error + "请输入密码</span>";
			if(document.querySelectorAll('.fa-check-circle').length===3){
				var info = {
					email: this.email,
					password: this.password,
					valcode: this.barcode
				}

				$.ajax({
					type: "POST",
					url: "/anywork/user/login",
					contentType: "application/json; charset=utf-8",
					dataType: "json",
					data: JSON.stringify(info),
					success: function(data){
						var state = data.state,
							stateInfo = data.stateInfo;
						if(state=='121'){
							window.location.href='homepage.html?email';
						}else{
							my_alert(stateInfo);
							return;
						}
					},
				})
			}
		}
	}
};

var register = {
	template: '<div id="register">\
			<div class="left-angle"></div>\
			<div class="right-angle"></div>\
			<div class="margin-top">\
				<i class="user-logo"></i><span class="logo-i">昵称</span>\
				<input v-model="username" type="text" class="input-one" @keyup.enter="register">\
				<span v-html="usernameInfo"></span>\
			</div>\
			<div class="relative">\
				<i class="email-logo"></i><span class="logo-i">邮箱</span>\
				<input v-model="email" type="text" class="input-one" @keyup.enter="register">\
				<span v-html="emailInfo"></span>\
			</div>\
			<div class="margin-bottom">\
				<i class="barcode-logo"></i><span class="logo-i">验证码</span>\
				<input v-model="barcode" type="text" class="input-two" @keyup.enter="register">\
				<img v-bind:src="barcodeUrl" class="barkcode-img" @click="getBarcode" >\
				<span v-html="barcodeInfo"></span>\
			</div>\
			<div class="whiteness">\
				<input type="button" class="button" @click="register" value="验证">\
			</div>\
		</div>',
		data: function(){
			return {
				barcodeUrl:'/anywork/verification',
				email: '',
				username: '',
				barcode:'',
				emailInfo: '',
				usernameInfo: '',
				barcodeInfo:'',
				pass: '<i class="fa fa-check-circle fa-fw fa-2x" style="color:white; position:absolute; top:-1px;"></i>',
				error: '<i class="fa fa-play fa-rotate-180" style="color: white"></i><span class="error-info">',
			}
		},
		watch: {
			username: function(val){
				if(val.length>0){
					this.usernameInfo = this.pass;
				}else if(val===''){
					this.usernameInfo = this.error + "请输入用户名</span>";
				}
			},
			email: function(val){
				var regEmail =  /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/; 
				if(val===''){
					this.emailInfo = this.error + "请输入邮箱</span>";
				}else if(!regEmail.test(val)){
					this.emailInfo = this.error + "邮箱格式不对</span>";
				}else{
					this.emailInfo = this.pass;
				}
			},
			barcode: function(val){
				if(val.length>0) this.barcodeInfo = this.pass;
				else this.barcodeInfo = this.error + "请填写验证码</span>";
			}
		},
		methods: {
			getBarcode: function(){
				this.barcodeUrl = "/anywork/verification?"+new Date().getTime();
			},
			register: function(){
				if(this.barcode==='') this.barcodeInfo = this.error + "请填写验证码</span>";
				if(this.email==='') this.emailInfo = this.error + "请输入邮箱</span>";
				if(this.username==='') this.usernameInfo = this.error + "请输入用户名</span>";
				if(document.querySelectorAll('.fa-check-circle').length===3){
					var info = {
						userName: this.username,
						valcode: this.barcode,
						type: 1,
						email: this.email,
					}

					$.ajax({
						type: "POST",
						url: "/anywork/user/verification",
						contentType: "application/json; charset=utf-8",
						dataType: "json",
						data: JSON.stringify(info),
						success: function(data){
							var state = data.state,
							 	stateInfo = data.stateInfo;
							if(state=='101') my_alert('邮件发送成功，请前往确认!');
							else {
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
		options: options,
		login: login,
		register: register
	},
	methods:{
		showLogin: function(){
			this.show = true;
		},
		showRegister: function(){
			this.show = false;
		}
	}
});
