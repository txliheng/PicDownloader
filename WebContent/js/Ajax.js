
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
	$('#divprogressbar').progressbar("option","value",val);
	if(val<=60){
		setTimeout(progress,500);
	}
}
function download(){
	
	var keyword=document.getElementById("keyword");
	var pagenow=document.getElementById("pagenow");
	//alert(keyword.value);
	//alert(pagenow.value);
	//alert($("#keyword").val()) ;
	
	//$.get("http://localhost:8080/DownloadPic/Download?keyword=" + keyword.value+"&pagenow="+pagenow.value,null,callback);
	$.post("http://localhost:8080/DownloadPic/Download",{keyword:keyword.value,pagenow:pagenow.value},callback);
	setTimeout(progress, 1000);
}
function callback(data){
}