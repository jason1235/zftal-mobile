function magnifyImg(e){
	var oImage=$(e);
	var image=$("<img/>");
	image.attr("src",oImage.attr("src"));
	image.attr("width",parseInt(oImage.attr("width"))*2.0);
	image.attr("height",parseInt(oImage.attr("height"))*2.0);
	tips(oImage,image);
}

function showCommImage(e) {
	var url = $(e).closest("td").find("img").attr("src");
	window.open(url);
}