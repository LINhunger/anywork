var URL_INFO = getUrlInformation();
var USER =  getUserInfo();

/*var URL_INFO ={organId: 2}
var USER = {userId: 2,
			userName: '寒戈'}*/

/**
 * 导航栏的
 */
var my_nav = {
	template:
		'<div @click="stopP">\
			<span class="page-logo" @click="toHomePage"></span>\
			<ul>\
				<li class="border-left"><i class="bell-logo logo"></i></li>\
				<li class="border-left at"><img class="head_img logo" v-bind:src="headImg"><span class="username">{{userName}}</span></li>\
				<li ><i class="close-logo logo" @click="close"></i></li>\
				<li class="border-left"><i class="back-logo logo" @click="goBack"></i></li>\
			</ul>\
		</div>',
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
 * 出卷
 */
var setInfo = {
	template: 
		'<div id="set_info">\
    		<ul class="paper_type type1">\
    			<li>\
    				<span class="pay_list_c1 on">\
						<input type="radio" name="paperType" value="practise" id="practise" papertype="1" class="radioclass" checked="checked" @click="isCheck">\
					</span>\
					<label for="practise">练习题</label></li>\
    			<li>\
    				<span class="pay_list_c1">\
						<input type="radio" name="paperType" value="prepare" id="prepare"  papertype="2" class="radioclass" checked="checked" @click="isCheck">\
					</span>\
					<label for="prepare">预习题</label></li>\
    			</li>\
    			<li class="margin-right-0">\
    				<span class="pay_list_c1">\
						<input type="radio" name="paperType" value="test" id="test"  papertype="3" class="radioclass" checked="checked" @click="isCheck">\
					</span>\
					<label for="test">测试题</label></li>\
    			</li>\
    		</ul>\
    		<div id="left_info">\
    			<div><span>作者</span><input type="text" v-model="author" readonly class="readonly"></div>\
    			<div><span>标题</span><input type="text" v-model="title"></div>\
    			<div><span>做题次数</span><input type="text" v-model="uploadNumber"></div>\
    			<div><span>开始时间</span><input type="text" class="form_datetime" id="startTime" readonly></div>\
    			<div><span>结束时间</span><input type="text" class="form_datetime" id="endTime" readonly></div>\
    		</div>\
    		<div id="right_info">\
    			<div>\
	    			<span>难度</span>\
	    			<div id="rating_wrap">\
						<div id="rating">\
							<i class="fa fa-star-o fa-2x" number="5"  @click="toGrade"></i>\
							<i class="fa fa-star-o fa-2x" number="4"  @click="toGrade"></i>\
							<i class="fa fa-star-o fa-2x" number="3"  @click="toGrade"></i>\
							<i class="fa fa-star-o fa-2x" number="2"  @click="toGrade"></i>\
							<i class="fa fa-star-o fa-2x" number="1"  @click="toGrade"></i>\
						</div>\
					</div>\
				</div>\
				<div><span>选择题</span><input type="text" v-model="chooseNumber"></div>\
				<div><span>填空题</span><input type="text" v-model="padNumber"></div>\
				<div><span>判断题</span><input type="text" v-model="judgeNumber"></div>\
				<div><span>简答题</span><input type="text" v-model="issueNumber"></div>\
				<input type="file"  id="input" accept="application/vnd.ms-excel，application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" style="display: none">\
				<input type="button" value="下载模板" class="b1" @click="downTemplate">\
				<input type="button" value="上传模板" class="b2" @click="upTemplate">\
				<input type="button" value="创建试卷" class="b3" @click="createPaper">\
				<input type="button" value="确定" class="submit_Template" @click="sendTemplate">\
    		</div>\
    	</div>', 
    data: function(){
    	return {
    		paperType: 1,
    		title:'',
    		author: USER.userName,
			startTime:'',
			endTime:'',
    		uploadNumber: 1,
    		ratNum: 0,
    		chooseNumber: 0,
    		padNumber: 0,
    		judgeNumber: 0,
    		issueNumber: 0,
    	}
    },
   	methods: {
   		toGrade: function(e){
            var target = e.target;
            $(target).removeClass('fa-star-o').addClass('fa-star').css('color','#2c7bcd')
            .nextAll().removeClass('fa-star-o').addClass('fa-star').css('color','#2c7bcd');
            $(target).prevAll().removeClass('fa-star').addClass('fa-star-o');
            this.ratNum = $(target).attr("number");
        },
        isCheck: function(e){
			$(e.target).closest('ul').find('span').removeClass("on");
			$(e.target).parent().addClass("on");
			this.paperType = Number($(e.target).attr('papertype'));
		},
		downTemplate: function(){
			downloadFile("试题模板 .xlsx","/anywork/excel/试题模板 .xlsx");
		},
		upTemplate: function(){
			var $input = $("#input");
			if($input[0]){
					$input.click();
			}
			$input.change(function(){
				var fileList = this.files;
				if(fileList.length>0){
					$("#set_info .submit_Template").show(500);
				}
			});
		},
		sendTemplate: function(){
			this.startTime = $('#startTime').val();
			this.endTime = $('#endTime').val();
			var setInfo = {
				textpaperType: this.paperType,
				textpaperTitle: this.title.trim(),
				textpaperTime: Number(this.uploadNumber),
				createTime: new Date(this.startTime).valueOf(),
				endingTime: new Date(this.endTime).valueOf(),
				difficulty: this.ratNum,
				chooseNumber: this.chooseNumber,
				padNumber: this.padNumber,
				judgeNumber: this.judgeNumber,
				issueNumber: this.issueNumber,
			}
			if(setInfo.textpaperTitle===""||setInfo.textpaperTime===0||$('#startTime').val()===""||$('#endTime').val()===""||setInfo.difficulty=="0"){
				my_alert("试卷的相关信息不能为空!");
				return;
			}
			var file =  $("#input")[0].files[0];
			console.log(file.type);  //"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
			var formData = new FormData();
			formData.append("file",file);
			formData.append("paperInfo",JSON.stringify(setInfo));
			$.ajax({
				url:"/anywork/excel/send",
				type:"POST",
				data: formData,
				cache:false,
				processData: false,
				contentType: false,
				success: function(data){
					var state = data.state;
					var stateInfo = data.stateInfo;
					my_alert(stateInfo);
					$("#set_info .submit_Template").hide();
					$("#checkTemplate").show(500);
					$("#submitTemplate").show(500);
					var f =  $("#input");
					f.after(f.clone(true).val(""));
					f.remove();
				},
				dataType:"json"
			});
			/*var f =  $("#input");
			 f.after(f.clone(true).val(""));
			 f.remove();
			$("#set_info .submit_Template").hide();
			$("#checkTemplate").show(500);
			$("#submitTemplate").show(500);*/
		},
		createPaper: function(){
			this.startTime = $('#startTime').val();
			this.endTime = $('#endTime').val();
			this.$emit('setinfo',{
				textpaperType: this.paperType,
	    		textpaperTitle: this.title.trim(),
	    		textpaperTime: Number(this.uploadNumber),
				createTime: new Date(this.startTime).valueOf(),
				endingTime: new Date(this.endTime).valueOf(),
	    		difficulty: this.ratNum,
	    		chooseNumber: this.chooseNumber,
	    		padNumber: this.padNumber,
	    		judgeNumber: this.judgeNumber,
	    		issueNumber: this.issueNumber,
			});
			if(setInfo.textpaperTitle===""||setInfo.textpaperTime===0||$('#startTime').val()===""||$('#endTime').val()===""||setInfo.difficulty=="0"){
				my_alert("试卷的相关信息不能为空!");
				return;
			}
			$("#checkTemplate").hide(500);
			$("#submitTemplate").hide(500);
		}
   	}
}

var choose = {
	template: 
		'<div class="choose question_wrap">\
    		<p class="title-number">题号:{{index+1}}</p>\
    		<span><span class="setScore">分数设置</span><input type="text" v-model="score" class="setScoreInput"></span>\
    		<textarea v-model="question"></textarea>\
    		<div>\
	    		<span class="option">A.</span>\
	    		<input type="text" v-model="answerA" class="chooseInput">\
    		</div>\
    		<div>\
	    		<span class="option">B.</span>\
	    		<input type="text" v-model="answerB" class="chooseInput">\
    		</div>\
    		<div>\
	    		<span class="option">C.</span>\
	    		<input type="text" v-model="answerC" class="chooseInput">\
    		</div>\
    		<div>\
	    		<span class="option">D.</span>\
	    		<input type="text" v-model="answerD" class="chooseInput">\
    		</div>\
    		<span class="answer_tip">正确答案</span>\
    		<span class="answer_option">\
				<input type="radio" value="a" :id="a" checked="checked" :name="this.index" v-on:click="addRightAnswer">\
				<label :for="a" class="on">A</label>\
			</span>\
    		<span class="answer_option">\
				<input type="radio" value="b" :id="b" :name="this.index" v-on:click="addRightAnswer">\
				<label :for="b">B</label>\
			</span>\
    		<span class="answer_option">\
				<input type="radio" value="c" :id="c" :name="this.index" v-on:click="addRightAnswer">\
				<label :for="c">C</label>\
			</span>\
    		<span class="answer_option">\
				<input type="radio" value="d" :id="d" :name="this.index" v-on:click="addRightAnswer">\
				<label :for="d">D</label>\
			</span>\
    	</div>',
    props:['index'],
    data: function(){
    	return {
    		score: 5,
    		a: this.index+'a',
    		b: this.index+'b',
    		c: this.index+'c',
    		d: this.index+'d',
    		question: '',
    		answerA: '',
    		answerB: '',
    		answerC: '',
    		answerD: '',
    		rightAnswer: this.index+'a',
    	}
    },
    computed: {
    	info: function(){
    		return {
    			type: 1,
    			score: this.score,
    			titleIndex: this.index,
	    		content: this.question,
	    		a: this.answerA,
	    		b: this.answerB,
	    		c: this.answerC,
	    		d: this.answerD,
	    		key: this.rightAnswer.slice(1),
    		}
    	}
    },
    watch: {
    	info: function(){
    		this.$emit('chooseitem',this.info);
    	}
    },
    methods:{
    	addRightAnswer: function(e){
            var target = e.target;
            $(target).parent().find('label').addClass('on');
            $(target).parent().siblings().find('label').removeClass('on');
            this.rightAnswer = $(target).attr("id");
        },
    }
}

var pad = {
	template:
		'<div class="fill_pad question_wrap">\
			<p class="title-number">题号:{{index+1}}</p>\
			<span><span class="setScore">分数设置</span><input type="text" v-model="score" class="setScoreInput"></span>\
			<span id="add_pad" @click="addPad">插入空格</span>\
			<textarea v-model="question"></textarea>\
			<span class="answer_tip">正确答案</span>\
			<div class="pads">\
				<div v-for="(item,padIndex) in pads.length">\
					{{padIndex+1}}.<input type="text" v-model="pads[padIndex].x">\
					<i class="delete_pad" @click="deletePad(padIndex)">X</i>\
				<div>\
			</div>\
		</div>',

	props:['index'],

	data: function(){
		return{
			score: 5,
			question:'',
			pads: [],
		}
	},
	methods: {
		addPad: function(e){
			var num = Number(this.pads.length) + 1;
			var text='(___'+num+'___)';
			var o={x:''};
            this.pads.push(o);

            var obj = $(e.target).parent().find('textarea')[0]; //obj为文本框
            if (obj.selectionStart || obj.selectionStart == '0'){
                var startPos = obj.selectionStart;
                var endPos = obj.selectionEnd;
                this.pads[num-1].x = obj.value.substring(startPos,endPos);
                this.question = obj.value.substring(0, startPos)
                    + text
                    + obj.value.substring(endPos, obj.value.length);
                obj.selectionStart = startPos + text.length;
                obj.selectionEnd = startPos + text.length;
            } else {
                this.question += text;
            }
            obj.focus();
            obj.setSelectionRange(this.question.length,this.question.length)
		},
		deletePad: function(num){
			this.pads.splice(num,1);
		}
	},
	computed: {
		key: function(){
			var r='';
			for(var i=0;i<this.pads.length;i++){
				r +=this.pads[i].x+'`';
			}
			return r.substr(0,r.length-1);
		},
    	info: function(){
    		return {
    			type: 3,
    			score: this.score,
    			titleIndex: this.index,
	    		content: this.question,
				key: this.key,
    		}
    	}
    },
 	watch: {
    	info: function(){
    		this.$emit('paditem',this.info);
    	},

    },
}

var judge = {
	template: 
		'<div class="judge question_wrap">\
			<p class="title-number">题号:{{index+1}}</p>\
			<span><span class="setScore">分数设置</span><input type="text" v-model="score" class="setScoreInput"></span>\
			<textarea v-model="question"></textarea>\
			<span class="answer_tip">正确答案</span>\
			<span class="pay_list_c1 yes on" result="yes" @click="toJudge">\
			</span>\
			<span class="pay_list_c1 no" result="no"  @click="toJudge">\
			</span>\
		</div>',
	props:['index'],
    data: function(){
    	return {
    		score: 5,
    		question: '',
    		result: 'yes',
    	}
    },
    computed: {
    	info: function(){
    		return {
    			type: 2,
    			score: this.score,
    			titleIndex: this.index,
	    		content: this.question,
	    		key: this.result,
    		}
    	}
    },
    methods: {
    	toJudge: function(e){
    		$(e.target).addClass('on').siblings('.pay_list_c1').removeClass('on');
    		this.result = $(e.target).attr('result') ;
    	}
    },
    watch: {
    	info: function(){
    		this.$emit('judgeitem',this.info);
    	}
    },
}

var issue = {
	template:
		'<div class="issue question_wrap">\
			<p class="title-number">题号:{{index+1}}</p>\
			<span><span class="setScore">分数设置</span><input type="text" v-model="score" class="setScoreInput"></span>\
			<textarea v-model="question"></textarea>\
			<span class="answer_tip">正确答案</span>\
			<textarea class="question_answer" v-model="result"></textarea>\
		</div> ',
	props:['index'],
    data: function(){
    	return {
    		score: 5,
    		question: '',
    		result: '',
    	}
    },
    computed: {
    	info: function(){
    		return {
    			type: 4,
    			score: this.score,
    			titleIndex: this.index,
	    		content: this.question,
	    		key: this.result,
    		}
    	}
    },
    watch: {
    	info: function(){
    		this.$emit('issueitem',this.info);
    	}
    },
}

var wrap = new Vue({
	el: "#wrap",
	data: {
		info: {},
		show:[1,0,0,0],
		chooseNumber: 0,
		chooseItems: [],
		padNumber: 0,
		padItems:[],
		judgeNumber:0,
		judgeItems:[],
		issueNumber:0,
		issueItems:[],
		textpaperId: 0,
	},
	components: {
		setInfo: setInfo,
		choose: choose,
		pad: pad,
		judge: judge,
		issue: issue,
	},
	computed: {
		paperInfo: function(){
			var topics = this.chooseItems.concat(this.padItems,this.judgeItems,this.issueItems);
			var paperInfo = this.info;
			paperInfo.topics = topics;
			paperInfo.textpaperId = this.textpaperId;
			return paperInfo;
		}
	},
	methods: {
		setInfo: function(info){
			this.info = info;
			this.chooseNumber = Number(info.chooseNumber);
			this.padNumber = Number(info.padNumber);
			this.judgeNumber = Number(info.judgeNumber);
			this.issueNumber = Number(info.issueNumber);
		},
		selectIt: function(e){
		 	var target = e.target;
            $(target).addClass('select').siblings().removeClass('select');
            var n = $(target).attr("number");
            for(var i=0;i<4;i++){
            	this.show.splice(i, 1, 0);
            }
            this.show.splice(n, 1, 1);
		},
		addChooseItem: function(item){
			var titleIndex = item.titleIndex;
			this.chooseItems.splice(titleIndex, 1, item);
		},
		addPadItem: function(item){
			var titleIndex = item.titleIndex;
			this.padItems.splice(titleIndex, 1, item);
		},
		addJudgeItem: function(item){
			var titleIndex = item.titleIndex;
			this.judgeItems.splice(titleIndex, 1, item);
		},
		addIssueItem: function(item){
			var titleIndex = item.titleIndex;
			this.issueItems.splice(titleIndex, 1, item);
		},
		createPaper: function(){
			console.log(JSON.parse(JSON.stringify(this.paperInfo)));
			var textpaperId;
			$.ajax({
				url:"/anywork/test/textpaper/submit",
				type:"POST",
				async: false,
				data: JSON.stringify(this.paperInfo),
				contentType: "application/json; charset=utf-8",
				dataType: "json",
				success: function(data){
					var state = data.state;
					var stateInfo = data.stateInfo;
					if(state===421){
						textpaperId = data.data;
						$("#checkPaper").show();
						$("#changePaper").show();
						$("#submitPaper").hide();
					}
					my_alert(stateInfo);
				}
			});
			this.textpaperId = textpaperId;
		},
		changePaper: function(){
			console.log(this.paperInfo);
			var textpaperId;
			$.ajax({
				url:"/anywork/test/textpaper/update",
				type:"POST",
				async: false,
				data: JSON.stringify(this.paperInfo),
				contentType: "application/json; charset=utf-8",
				dataType: "json",
				success: function(data){
					var state = data.state;
					var stateInfo = data.stateInfo;
					if(state===421){
						my_alert('修改试卷成功!');
						textpaperId = data.data;
						$("#checkPaper").show(500);
						$("#changePaper").show(500);
						$("#submitPaper").hide();
					}else {
						my_alert(stateInfo);
					}
				}
			});
			this.textpaperId = textpaperId;
		},
		checkPaper: function(){
			 window.open("doHomework.html?textpaperId="+this.textpaperId);
		},
		checkTemplate: function(){
			 window.open("doHomework.html?textpaperId=0");
		},
		submitTemplate:function(){
			$.post("/anywork/excel/submit",function(data){
				my_alert(data.stateInfo);
			})
		},
	},
})


$(".form_datetime").datetimepicker({
 	format: 'yyyy-mm-dd hh:ii',
    language: 'zh-CN',
    pickDate: true,
    pickTime: true,
    autoclose: true,
});


function downloadFile(fileName, content){
	var aLink = document.createElement('a');
	aLink.download = fileName;
	aLink.href = content;
	aLink.click();
}


