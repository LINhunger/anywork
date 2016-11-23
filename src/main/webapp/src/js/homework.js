
var URL_INFO = getUrlInformation();
var USER =  getUserInfo();
var O = getHomewrokById(URL_INFO.homeworkId,URL_INFO.authorId);

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

/**
 * 打开预处理
 */
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
        },200);
    }else{
        setTimeout(function(){
            $("#issue_body")[0].value='';
        },200)
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

var question = {
    template:
 		'<div id="question">\
    		<p class="qs_title">{{homeworkTitle}}</p>\
    		<p class="qs_time">{{timeSpan}}</p>\
    		<input type="button" value="返回时间轴" class="return_tree" @click="returnTree">\
    		<div readonly class="qs_mark" v-html="homeworkMark"></div>\
    	</div>',
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
    template:
        '<div id="grade">\
	    	<div id="score"><i class="fa fa-star"></i><p>{{score}}</p></div>\
    		<div id="twig">\
    			<div class="mg"><span class="tip">五星</span><span class="border"><span class="percent" v-bind:style="percent5"></span></span></div>\
	    		<div class="mg"><span class="tip">四星</span><span class="border"><span class="percent" v-bind:style="percent4"></span></span></div>\
	    		<div class="mg"><span class="tip">三星以下</span><span class="border"><span class="percent" v-bind:style="percent3"></span></span></div>\
	    	</div>\
	    	<div id="star">\
	    		<p>评价该同学的作业吧</p>\
				<div id="wrap">\
					<div id="rating">\
						<i class="fa fa-star-o fa-2x" number="5"  @click="toGrade"></i>\
						<i class="fa fa-star-o fa-2x" number="4"  @click="toGrade"></i>\
						<i class="fa fa-star-o fa-2x" number="3"  @click="toGrade"></i>\
						<i class="fa fa-star-o fa-2x" number="2"  @click="toGrade"></i>\
						<i class="fa fa-star-o fa-2x" number="1"  @click="toGrade"></i>\
					</div>\
				</div>\
	    	</div>\
    	</div>',
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
            this.$off('click');
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
    template:
          '<div id="write">\
                <textarea placeholder="回复:" v-model="comment"></textarea>\
                <span>{{number}}/120</span>\
                <input  type="button" value="发布" @click="sends">\
            </div>',
    data: function(){
        return {
            comment: '',
        }
    },
    methods: {
        sends: function(){
            var r = sendComment(1,HOMEWORK.homeworkId,this.comment);
            /*            if(r){
             var o = {
             content: this.comment,
             recomment:[],
             sender:{
             userName: MY_ANSWER.author.userName,
             },
             }
             this.$emit('sendcomment',o);
             }
             */        }

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
    template:
            '<div class="one_comment">\
                <div class="remark">\
                    <p><img src="../images/close.png"><span>{{authorName}}</span>\
                    <p class="ct">{{content}}</p>\
                </div>\
                <div class="replay">\
                    <div class="item" v-for="item in recomments">\
                        <rpd :item="item"  :pCommentId="item.aCommentId"></rpd>\
                    </div>\
                </div>\
                <textarea class="write_back" placeholder="回复:" v-model="recomment"></textarea>\
                <input type="button" value="回复" @click="send">\
            </div>',
    props:['item'],
    data: function(){
        return {
            recomment: '',
            authorName: this.item.sender.userName,
            content: this.item.content,
        }
    },
    methods: {
        send: function(){
            sendResponse(this.item.sender.userId,HOMEWORK.homeworkId,3,this.recomment,this.item.commentId);
        }
    },
    computed: {
        recomments: function(){
            return  this.item.recomments;
        }
    },
    components:{
        rpd: {
            template:
                '<div class="item"><span>{{sender.userName}}</span> 回复 <span>{{receiver.userName}}</span>{{item.content}}\
                <input type="button" value="回复" class="rp" @click="toshow" v-show="sb">\
                <div v-if="show">\
                    <textarea placeholder="回复:" class="write_back" v-model="recomment"></textarea> \
                    <input type="button" value="回复" @click="sendrp">\
                <div>\
            </div>',
            props:['item','pCommentId'],
            data: function(){
                return {
                    show: false,
                    sb: true,
                    sender: this.item.sender,
                    receiver: this.item.receiver,
                    recomment: '',
                }
            },
            methods: {
                toshow: function(){
                    this.show = true;
                    this.sb = false;
                },
                sendrp: function(){
                    this.show= false;
                    this.sb = true;
                    sendResponse(this.item.sender.userId,HOMEWORK.homeworkId,3,this.recomment,this.pCommentId);
                }
            }
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
        answers: ALL_ANSWER,
        answer: MY_ANSWER,
        comments: O.comments
    },
    components: {
        question : question,
        grade: grade,
        myWrite: write,
        oneComment: oneComment,
        myAnswer: answer,
    }
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
                location.reload()
            }else{
                my_alert(stateInfo);
            }
        },
        dataType: "json"
    })
}

function sendResponse(receiverId,targetId,type,content,commentId){
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
                location.reload()
            }else{
                my_alert(stateInfo);
            }
        },
        dataType: "json"
    })
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
                        text = "!["+file.name+"](/anywork/upload/"+filename+")";
                    }else{
                        text = "\n!["+file.name+"](/anywork/upload/"+filename+")";
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
/**
 * pdf预览
 */
$(function(){

    var array = new Array();
    //通过链接模拟点击input file----------------------------------
    $("#upload_file").click(function(e){
        if($("#input")[0]){
            $("#input").click();
        }
        e.preventDefault();
    });

    //显示上传按钮----------------------------------------------
    $("#input").change(function(){
        var fileList = this.files;
        if(fileList.length>0){
            $("#upload").show();
        }
    });


    //点击上传按钮上传文件---------------------------------------------
    $("#upload").click(function(){
        var file = $("#input")[0].files[0];
        var formData = new FormData();
        formData.append("file",file);
        $.ajax({
            url:"/anywork/timeline/submit/"+URL_INFO.homeworkId,
            type:"POST",
            data:formData,
            cache: false,
            processData: false,
            contentType: false,
            success:function(data){
                var state = data.state;
                var stateInfo = data.stateInfo;
                if(state==153){
                    location.reload();
                }
                my_alert(stateInfo);
            },
            dataType:"json"
        });
    });
})

var fileList = getFileList(URL_INFO.homeworkId,URL_INFO.authorId);

var str='';
for(var i=0;i<fileList.length;i++){
    str += '<p class="file_items" v-for="item in sss"><span>'+fileList[i]+'</span><input type="button" value="预览" class="preview"  fileurl="'+fileList[i]+'"><input type="button" value="下载" class="down_load"></p>'
    $("#file_list").html(str);
}

$(".preview").click(function(){
    var url = $(this).attr('fileurl');
    my_alert2('',1000,600);
    setTimeout(function(){
        var success = new PDFObject({url: url}).embed("pdf");
    }, 100)
})