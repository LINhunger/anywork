
var URL_INFO = getUrlInformation();
var USER =  getUserInfo();
var O = getHomewrokById(URL_INFO.homeworkId,URL_INFO.authorId);

/*var URL_INFO = {homeworkId: 1}
 var USER =  {userId: 1}
 var O =
 JSON.parse(`{
 "allAnswers": [
 {
 "hAnswerId": 2,
 "homeworkId": 1,
 "author": {
 "userId": 1,
 "userName": "zhesheng",
 "email": "466400960@qq.com",
 "password": null,
 "phone": null,
 "school": null,
 "isWeChat": 0,
 "openid": null,
 "createTime": null,
 "organs": null,
 "picture": "1.jpg"
 },
 "mark": "抱歉我不会饿",
 "grade5": 1,
 "grade4": 0,
 "grade3": 0,
 "note": null,
 "createTime": 1479210086000,
 "status": 0
 },
 {
 "hAnswerId": 1,
 "homeworkId": 1,
 "author": {
 "userId": 2,
 "userName": "蔡泽胜",
 "email": "123456789@qq.com",
 "password": null,
 "phone": null,
 "school": null,
 "isWeChat": 0,
 "openid": null,
 "createTime": null,
 "organs": null,
 "picture": "default.jpg"
 },
 "mark": "。。。。。",
 "grade5": 0,
 "grade4": 0,
 "grade3": 0,
 "note": null,
 "createTime": 1479209978000,
 "status": 0
 }
 ],
 "comments": [
 {
 "commentId": 1,
 "sender": {
 "userId": 4,
 "userName": "张国洪",
 "email": "666666@qq.com",
 "password": "b25c11129d72a31311a99ff0d68dd26a",
 "phone": "15626198155",
 "school": null,
 "isWeChat": 0,
 "openid": null,
 "createTime": 1479136580000,
 "organs": null,
 "picture": null
 },
 "targetId": 1,
 "type": 0,
 "content": "厉害了，我的歌",
 "recomments": null,
 "createTime": 1479210788000
 },
 {
 "commentId": 2,
 "sender": {
 "userId": 4,
 "userName": "张国洪",
 "email": "666666@qq.com",
 "password": "b25c11129d72a31311a99ff0d68dd26a",
 "phone": "15626198155",
 "school": null,
 "isWeChat": 0,
 "openid": null,
 "createTime": 1479136580000,
 "organs": null,
 "picture": null
 },
 "targetId": 1,
 "type": 0,
 "content": "厉害了，我的肾",
 "recomments": null,
 "createTime": 1479210881000
 },
 {
 "commentId": 3,
 "sender": {
 "userId": 4,
 "userName": "张国洪",
 "email": "666666@qq.com",
 "password": "b25c11129d72a31311a99ff0d68dd26a",
 "phone": "15626198155",
 "school": null,
 "isWeChat": 0,
 "openid": null,
 "createTime": 1479136580000,
 "organs": null,
 "picture": null
 },
 "targetId": 1,
 "type": 0,
 "content": "大神好厉害",
 "recomments": null,
 "createTime": 1479210927000
 },
 {
 "commentId": 4,
 "sender": {
 "userId": 4,
 "userName": "张国洪",
 "email": "666666@qq.com",
 "password": "b25c11129d72a31311a99ff0d68dd26a",
 "phone": "15626198155",
 "school": null,
 "isWeChat": 0,
 "openid": null,
 "createTime": 1479136580000,
 "organs": null,
 "picture": null
 },
 "targetId": 1,
 "type": 0,
 "content": "还是你厉害",
 "recomments": null,
 "createTime": 1479210948000
 }
 ],
 "homework": {
 "homeworkId": 1,
 "name": "homework",
 "organId": 1,
 "homeworkTitle": "离散数学",
 "mark": "设R和S是A上的任意关系，若R和S是传递的，则RUS是传递的。这个结论是否成立？",
 "author": {
 "userId": 2,
 "userName": "蔡泽胜",
 "email": "123456789@qq.com",
 "password": null,
 "phone": null,
 "school": null,
 "isWeChat": 0,
 "openid": null,
 "createTime": null,
 "organs": null,
 "picture": "default.jpg"
 },
 "createTime": 1479122845000,
 "endingTime": 1479641231000,
 "submitCount": 0,
 "status": "&"
 },
 "myAnswer": {
 "hAnswerId": 2,
 "homeworkId": 1,
 "author": {
 "userId": 1,
 "userName": "zhesheng",
 "email": "466400960@qq.com",
 "password": null,
 "phone": null,
 "school": null,
 "isWeChat": 0,
 "openid": null,
 "createTime": null,
 "organs": null,
 "picture": "1.jpg"
 },
 "mark": "抱歉我不会饿",
 "grade5": 0,
 "grade4": 0,
 "grade3": 0,
 "note": null,
 "createTime": 1479210086000,
 "status": 0
 }
 }`);
 */

var HOMEWORK = O.homework;
var MY_ANSWER = O.myAnswer!=null?O.myAnswer:{
    grade5:0,
    grade4:0,
    grade3:0,
    mark: '',
    hAnswerId:0,
};
var COMMENTS = O.comments;
var ALL_ANSWER = O.allAnswers;

//如果第一次做作业后提交时显示grate

$(function(){

    //显示星星数 预处理。
    if(MY_ANSWER.status!=0){
        setRating(MY_ANSWER.status);
    }

    //markdown内容填充。
    $("#issue_title").val('答:');
    if(MY_ANSWER.mark!=''){
        $("#issue_body").text(MY_ANSWER.mark);
        setTimeout(function(){
            $("#preview-hg").click();
        },100);
    }

    //还没发布作业时不能评分
    if(MY_ANSWER.hAnswerId==0){
        $("#grade").hide();
    }

    //不是登录本人则隐藏write 和 submit 按钮
    if(USER.userId != MY_ANSWER.author.userId){
        setTimeout(function(){
            $("#preview-hg").click();
        },100);
        $(".write-tab").hide();
        $(".btn-primary").hide();
    }else{
        $(".write-tab").show();
        $(".btn-primary").show();
    }

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

var question = {
    template: `
 		<div id="question">
    		<p class="qs_title">{{homeworkTitle}}</p>
    		<p class="qs_time">{{timeSpan}}</p>
    		<input type="button" value="返回时间轴" class="return_tree" @click="returnTree">
    		<textarea readonly class="qs_mark">{{homeworkMark}}</textarea>
    	</div>`,
    data: function(){
        return{
            homeworkTitle: HOMEWORK.homeworkTitle,
            homeworkMark: HOMEWORK.mark,
        }
    },
    computed: {
        timeSpan: function(){
            var creat = formatTime( HOMEWORK.createTime,'md');
            var ending =formatTime( HOMEWORK.endingTime,'md');
            return creat+"-"+ending;
        },
    },
    methods: {
        returnTree: function(){
            window.location.href="organization.html?organId="+HOMEWORK.organId;
        }
    }
}

var grade = {
    template: `
 		<div id="grade">
	    	<div id="score"><i class="fa fa-star"></i><p>{{score}}</p></div>
    		<div id="twig">
    			<div class="mg"><span class="tip">五星</span><span class="border"><span class="percent" v-bind:style="percent5"></span></span></div>
	    		<div class="mg"><span class="tip">四星</span><span class="border"><span class="percent" v-bind:style="percent4"></span></span></div>
	    		<div class="mg"><span class="tip">三星以下</span><span class="border"><span class="percent" v-bind:style="percent3"></span></span></div>
	    	</div>
	    	<div id="star">
	    		<p>评价该同学的作业吧</p>
				<div id="wrap">
					<div id="rating">
						<i class="fa fa-star-o fa-2x" number="5"  @click="toGrade"></i>
						<i class="fa fa-star-o fa-2x" number="4"  @click="toGrade"></i>
						<i class="fa fa-star-o fa-2x" number="3"  @click="toGrade"></i>
						<i class="fa fa-star-o fa-2x" number="2"  @click="toGrade"></i>
						<i class="fa fa-star-o fa-2x" number="1"  @click="toGrade"></i>
					</div>
				</div>
	    	</div>
    	</div>`,
    props: ['answer'],
    data: function(){
        return {
            s3 :  this.answer.grade3,
            s4 :  this.answer.grade4,
            s5 :  this.answer.grade5,
        }
    },
    computed: {
        score: function(e){
            if((this.s3*20 + this.s4*20 + this.s5*20 )==0){
                return 0
            }else{
                return Math.floor((this.s3*20*3 + this.s4*20*4 + this.s5*20*5) / (this.s3+this.s4+this.s5) );
            }
        },
        percent5: function(){
            var p ;
            if(this.s5==0) p = "1px";
            else{
                p = Math.floor(this.s5 / (this.s3+this.s4+this.s5) * 100) + "%"
            }
            return {
                width: p,
            }
        },
        percent4: function(){
            var p ;
            if(this.s4==0) p = "1px";
            else{
                p = Math.floor(this.s4 / (this.s3+this.s4+this.s5) * 100 ) + "%"
            }
            return {
                width: p,
            }
        },
        percent3: function(){
            var p ;
            if(this.s3==0) p = "1px";
            else{
                p = Math.floor( this.s3 / (this.s3+this.s4+this.s5) * 100 ) + "%"
            }
            return {
                width: p,
            }
        }

    },
    methods: {
        toGrade: function(e){
            var target = e.target;
            light(target);
            var num = $(target).attr("number");
            if (this.answer.status!=0) {
                return ;
            };
            if(num==5) this.s5 += 1;
            if(num==4) this.s4 += 1;
            if(num<=3) this.s3 += 1;
            this.toGrade = null;
            setScore(HOMEWORK.homeworkId,this.answer.hAnswerId,num);
        },
    }
}

var write = {
    template: `
            <div id="write">
                <textarea placeholder="回复:" v-model="comment"></textarea>
                <span>{{number}}/120</span>
                <input  type="button" value="发布" @click="sends">
            </div>`,
    data: function(){
        return {
            comment: '',
        }
    },
    methods: {
        sends: function(){
            var r = sendComment(1,HOMEWORK.homeworkId,this.comment);
            if(r){
                O = getHomewrokById(URL_INFO.homeworkId);
                COMMENTS = O.comments;
            }
        }

    },
    computed: {
        number: function(){
            var n = this.comment.length;
            if(n>120){
                this.comment = this.comment.slice(0,120);
            }
            return n;
        }
    },
}

var oneComment = {
    template: `
            <div class="one_comment">
                <div class="remark">
                    <p><img src="../images/close.png"><span>{{item.sender.userName}}</span>
                    <p class="ct">{{item.content}}</p>
                </div>
                <div class="replay">
                    <div class="item" v-for="item in recomments">
                        <span>{{item.sender.userName}}</span> 回复 <span>{{item.receiver.userName}}</span> : {{item.content}}
                    </div>
                </div>
                <textarea class="write_back" placeholder="回复:" v-model="recomment"></textarea>
                <input type="button" value="回复" @click="send">
            </div>`,
    props:['item'],
    data: function(){
        return {
            recomment: '',
        }
    },
    methods: {
        send: function(){
            sendResponse(this.item.sender.userId,HOMEWORK.homeworkId,3,this.recomment,this.item.commentId);
        },
    },
    computed: {
        recomments: function(){
            return  this.item.recomments;
        }
    }
}

var answer = {
    template: '<p class="aname" @click="showMyAnswer">{{userName}}</p>',
    props: ['item'],
    computed: {
        userName : function(){
            return this.item.author.userName
        }
    },
    methods: {
        showMyAnswer: function(){
            var url = "homework.html?homeworkId="+URL_INFO.homeworkId+"&authorId="+this.item.author.userId;
            window.location.href= url;
        }
    }
}

var homework = new Vue({
    el: "#homework",
    data: {
        comments: COMMENTS,
        answers: ALL_ANSWER,
        answer: MY_ANSWER,
    },
    components: {
        question : question,
        grade: grade,
        myWrite: write,
        oneComment: oneComment,
        myAnswer: answer,
    },
})


/**
 * 星星评分
 */
$('#rating i').mouseenter(function(e){
    $(this).removeClass('fa-star-o').addClass('fa-star')
        .nextAll().removeClass('fa-star-o').addClass('fa-star');
}).mouseout(function(e){
    $(this).parent().find('i').removeClass('fa-star').addClass('fa-star-o');;
});


function light(el){
    $(el).removeClass('fa-star-o').addClass('fa-star').css('color','#2c7bcd')
        .nextAll().removeClass('fa-star-o').addClass('fa-star').css('color','#2c7bcd');
    $('#rating i').unbind();
}

function setRating(number){
    if(number<=0||number>5) return;
    var el = $("#rating i[number="+number+"]")[0];
    $("#rating i[number="+number+"]").click();
}


function sendComment(type, targetId, content){
    var result = false;
    var info = {
        type: type,
        targetId: targetId,
        content: content
    };

    $.ajax({
        type: "POST",
        url: "/anywork/comment/announce",
        async: false,
        data: JSON.stringify(info),
        contentType: "application/json; charset=utf-8",
        success: function(data){
            var state = data.state;
            var stateInfo = data.stateInfo;
            if(state==162){
                result = true;
            }
            my_alert(stateInfo);
        },
        dataType: "json"
    })
    return result;
}

function sendResponse(receiverId,targetId,type,content,commentId){
    var result = false;
    info = {
        receiverId: receiverId,
        targetId: targetId,
        type: type,
        content: content,
        commentId: commentId,
    }

    $.ajax({
        type: "POST",
        url: "/anywork/comment/response",
        async: false,
        data: JSON.stringify(info),
        contentType: "application/json; charset=utf-8",
        success: function(data){
            var state = data.state;
            var stateInfo = data.stateInfo;
            if(state==163){
                result = true;
            }
            my_alert(stateInfo);
        },
        dataType: "json"
    })
    return result;
}

function getAllComments(type,id){
    var commentInfo;
    $.ajax({
        type: "POST",
        url: "/anywork/allcomments/"+type+"/"+id,
        async: false,
        success: function(data){
            var state = data.state,
                stateInfo = data.stateInfo;
            if(state=='161'){
                commentInfo = data.data;
            }
            else my_alert(stateInfo);
        },
        dataType: 'json',
    })
    return commentInfo;
}

function submitAnswer(hAnswerId,homeworkId,mark,note){
    var info = {
        hAnswerId: hAnswerId,
        homeworkId: homeworkId,
        mark: mark,
        note: note
    }

    $.ajax({
        type: "POST",
        url: "/anywork/timeline/hanswer/submit",
        data: JSON.stringify(info),
        contentType: "application/json; charset=utf-8",
        success: function(data){
            var state = data.state,
                stateInfo = data.stateInfo;
            if(state==-1){
                my_alert('作业已过期,提交失败!')
            }else{
                location.reload();
            }
        },
        dataType: 'json',
    })
}

function setScore(homeworkId,answerId,grade){
    var info= {
        homeworkId: homeworkId,
        answerId: answerId,
        grade: grade,
    }
    $.ajax({
        type: "POST",
        url: "/anywork/timeline/hanswer/score",
        data: JSON.stringify(info),
        contentType: "application/json; charset=utf-8",
        success: function(data){
            // my_alert(data.stateInfo);
        },
        dataType: 'json',
    })
}

//关于动效
$(function(){
    $("#comment .one_comment textarea").focus(function(e){
        $(this).css("height","100px");
    }).blur(function(e){
        $(this).css("height","60px");
    })
})

//关于markdown的
$(function(){
    //markdown 选择图片上传change事件
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

    //预览按钮点击
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

    //markdown提交按钮
    $(".btn-primary").click(function(){
        submitAnswer(MY_ANSWER.hAnswerId,HOMEWORK.homeworkId,$("#issue_body")[0].value,'');

        return false;
    })

    //markdown 预处理
    $(".write-tab").click();
    setTimeout(function(){
        $("#issue_title").focus();
    }, 100);
})

//关于星星的
$(function(){


})

//6-9
//705