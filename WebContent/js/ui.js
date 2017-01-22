$(document).ready(function(){
	$("#header_nav li a").click(function(){
		linkbox=$(this).parent("li");
		linkbox.nextUntil("#quitLink").removeClass();
		linkbox.prevAll().removeClass();
		linkbox.addClass("li1");
	})
	$("#aside_nav li a").click(function(){
		linkbox=$(this).parent("li");
		linkbox.nextAll().removeClass("li1");
		linkbox.prevAll().removeClass("li1");
		linkbox.addClass("li1");
	})
	$(".table_style tr").mouseover(function(){
		row=$(this);
		row.addClass("chooserow");
	})
	$(".table_style tr").mouseout(function(){
	row=$(this);
	row.removeClass("chooserow");

})
	$(".table_style tr:even td").parent().addClass('row_gray');
	$("#intLogName").focus(function(){
		$(this).css("border-color","#f29906");
	})
	$("#intLogName").blur(function(){
		$(this).css("border-color","#999");
	})
		$("#intLogPw").focus(function(){
		$(this).css("border-color","#f29906");
	})
	$("#intLogPw").blur(function(){
		$(this).css("border-color","#999");
	})
	$("#login_btn_sub").mousedown(function(){
		$(this).addClass("clickbtn")
	})
	$("#aside_nav .tit").click(function(){
		if($(this).next('p').css('display') == 'block') $(this).next('p').css('display','none');
		else $(this).next('p').css('display','block');
	})
})
