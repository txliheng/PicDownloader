
function progress() {
	/*
	 * 在chrome中$.get可以用，但在IE8中不可以用
	 * chrome和IE8中$.post都可以用
	 */
//	$.get("http://localhost:8080/DownloadPic/Progress",null,getDownloadImageNo);
	$.post("http://localhost:8080/DownloadPic/Progress",null,getDownloadImageNo);
	$.post("http://localhost:8080/DownloadPic/GetImageUrl",null,getImageUrl);
}
function getImageUrl(data){
	
	$('#imageUrl').append(data);

}

function getDownloadImageNo(data){
	var val = parseInt(data) || 0;
	$('.progress-bar').css({'width':10*val/6+'%'}).find('span').html(val+'/60');
	if(val<60){
		setTimeout(progress,100);
	}else if(val==60){
		$('#btndownload').removeAttr("disabled"); 
	}
}
function download(){
	
	var keyword=document.getElementById("keyword");
	var pagenow=document.getElementById("pagenow");
	//$.get("http://localhost:8080/DownloadPic/Download?keyword=" + keyword.value+"&pagenow="+pagenow.value,null,callback);
	$.post("http://localhost:8080/DownloadPic/Download",{keyword:keyword.value,pagenow:pagenow.value},callback);
	setTimeout(progress, 1000);
	$('#btndownload').attr('disabled',"disabled");
}
function callback(data){
	$("#spanValidateKeyword").html(data).css("color", "red").css('display', '');
}