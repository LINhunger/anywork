	Vue.component('myDialog',
		{
			template: `<div id="dialog" @click="stopP" v-bind:style="theStyle">
							<div id="dialog-title" 
							@mousedown="mousedown" 
							>
								<i @click="close">X</i>{{style}}
							</div>
							<div id="dialog-content">
								<slot></slot>
								<input type="button" value="确定" @click="close">
							</div>
						</div>`,
			props:['style'],
			data: function(){
				return {
					theStyle : this.style,
					dialogInstace: {
						couldMove: false,
						dragElement: null,
						moveElement: null,
						mouseOffsetLeft : 0,
						mouseOffsetTop : 0,
					},
					mousePos :{x:0,y:0},
					stopId : 0,
				}
			},
			methods: {
				close: function(){
					this.$emit('close');
				},
				mousedown: function(e){
					document.onmouseup = this.mouseup;
					document.onmousemove = this.mousemove;
					this.dialogInstace.couldMove = true;
					this.dialogInstace.dragElement = document.querySelector('#dialog-title');
					this.dialogInstace.moveElement = document.querySelector('#dialog');
					this.dialogInstace.mouseOffsetLeft = e.pageX - this.dialogInstace.moveElement.offsetLeft 
					+ document.documentElement.clientWidth / 2 ;  //为了适应动画
					this.dialogInstace.mouseOffsetTop  = e.pageY - this.dialogInstace.moveElement.offsetTop;
				},
				mouseup: function(e){
					this.dialogInstace.couldMove = false;
				},
				mousemove: function(e){
					if(this.dialogInstace.couldMove===false) return;
					this.mousePos.x = e.clientX;
					this.mousePos.y = e.clientY;
					var maxX = document.documentElement.clientWidth -  this.dialogInstace.moveElement.offsetWidth ;
			    	var maxY = document.documentElement.clientHeight - this.dialogInstace.moveElement.offsetHeight ;

					this.dialogInstace.moveElement.style.left = Math.min( Math.max( ( this.mousePos.x - this.dialogInstace.mouseOffsetLeft) , /*为了适应动画 */ - document.documentElement.clientWidth / 2 ) , maxX - document.documentElement.clientWidth / 2) + "px";
					this.dialogInstace.moveElement.style.top  = Math.min( Math.max( ( this.mousePos.y /*为了适应动画 */- this.dialogInstace.mouseOffsetTop ) , 0 ) , maxY) + "px";

				},
				stopP: function(e){
					e.stopPropagation();
				}
			},			
		}
	)	

	var _al = new Vue({
		el: '#alert',
		data: {
			show: false,
			info: '',
			top: 0,
		},
		methods:{
			close: function(){
				this.show = false;
			}
		},
		computed: {
			style: function(){
				var scrollHeight = $(window).scrollTop();
				var o = {   //默认样式
					width: '500px',
					height: '250px',
					top: (this.top+200)+"px",
				}
				return o;
			}
		}
	})
	$(window).scroll(function(){
		var scrollHeight = $(window).scrollTop();
		_al.top = scrollHeight;
	})


	function my_alert(str){
		_al.show = true,
		_al.info=str;
		if(arguments[1]) _al.style.width =  arguments[1]+"px";
		if(arguments[2]) _al.style.height = arguments[2]+"px";
		
	}
	/**
	 *  自定义弹出框
	 * 	str ： string
	 * 	width: int [可选]
	 * 	heigth: int [可选]
	 */



