/**
 * Created by AnLuTong on 2016-12-21.
 */
$(function(){

	function listFiles(){
		var url = $("#list_api_dir").val();
		var reqData = {
			make: $("#list_make").val(),
			keywork: $("#list_keyword").val(),
			dir: $("#list_dir").val()
		};
		H.post(url, reqData, function (res) {
			for(var i in res.list){
				var item = res.list[i];

				var fileName = item.name;
				var indexOf = fileName.indexOf(".");
				var fa = "fa-file-o";
				if (item.type === 0){
					fa = "fa-folder-o"
				}else{
					if (indexOf !== 0){
						fileName = fileName.substring(indexOf);
						if (fileName){
							if (fileName.toUpperCase() === '.ZIP' || fileName.toUpperCase() === '.RAR'
								|| fileName.toUpperCase() === '.7Z' || fileName.toUpperCase() === '.TAR'
								|| fileName.toUpperCase() === '.ISO' || fileName.toUpperCase() === '.IMG'){
								fa = "fa-file-zip-o";
							}else if (fileName.toUpperCase() === '.JPG' || fileName.toUpperCase() === '.JPEG'
								|| fileName.toUpperCase() === '.GIF' || fileName.toUpperCase() === '.PNG'
								|| fileName.toUpperCase() === '.ICO' || fileName.toUpperCase() === '.BMP'){
								fa = "fa-file-image-o";
							}else if (fileName.toUpperCase() === '.TXT' || fileName.toUpperCase() === '.DOC'
								|| fileName.toUpperCase() === '.DOCX' || fileName.toUpperCase() === '.TIF'){
								fa = "fa-file-word-o";
							}else if (fileName.toUpperCase() === '.JS' || fileName.toUpperCase() === '.JAVA'
								|| fileName.toUpperCase() === '.CPP' || fileName.toUpperCase() === '.XML'
								|| fileName.toUpperCase() === '.HTML' || fileName.toUpperCase() === '.JSON'
								|| fileName.toUpperCase() === '.CSS' || fileName.toUpperCase() === '.DART'
								|| fileName.toUpperCase() === '.BAT' || fileName.toUpperCase() === '.SH'
								|| fileName.toUpperCase() === '.CMD' || fileName.toUpperCase() === '.H'){
								fa = "fa-file-code-o";
							}else if (fileName.toUpperCase() === '.MP3' || fileName.toUpperCase() === '.MP4'
								|| fileName.toUpperCase() === '.RMVB' || fileName.toUpperCase() === '.RM'
								|| fileName.toUpperCase() === '.AVI' || fileName.toUpperCase() === '.3GP'
								|| fileName.toUpperCase() === '.MPEG1-4' || fileName.toUpperCase() === '.MOV'
								|| fileName.toUpperCase() === '.MTV' || fileName.toUpperCase() === '.DAT'
								|| fileName.toUpperCase() === '.WMV' || fileName.toUpperCase() === '.AMV'
								|| fileName.toUpperCase() === '.FLV' || fileName.toUpperCase() === '.DMV'){
								fa = "fa-file-video-o";
							}
						}
					}
				}

				var id = i + res.make;
				$("tbody").append("<tr itemId=\"" + id + "\">\n" +
					"                <td>\n" +
					"                    <div class=\"chickBox\" fileId=\"" + id + "\"></div>\n" +
					"                    <span class=\"fielName\">\n" +
					"                        <i class=\"fa " + fa + "\"></i>\n" +
					"                        <a href=\"" + url + "/" + item.ossKey + "\">" + item.name + "</a>\n" +
					"                    </span>\n" +
					"                    <span class=\"fileWork\" hidden id=\"wk_" + id + "\">\n" +
					"                        <a href=\"javascript:void(0);\"><i class=\"fa fa-cloud-download\"></i></a>\n" +
					"                        <a href=\"javascript:void(0);\"><i class=\"fa fa-trash-o\"></i></a>\n" +
					"                        <a href=\"javascript:void(0);\"><i class=\"fa fa-share-alt-square\"></i></a>\n" +
					"                    </span>\n" +
					"                </td>\n" +
					"                <td>" + item.size + "</td>\n" +
					"                <td> - </td>\n" +
					"            </tr>");
			}
			init();
		});
	}

	listFiles();

	function init(){
		$(".workBtns").hide();
		$(".chickBox").click(function(){
			var isSelected = false;
			if($(this).attr("class") == "chickBox"){
				$(this).attr("class", "chickBox fa fa-check")
			}else{
				$(this).attr("class", "chickBox")
			}
			if($(this).attr("fileId") == 'all'){
				//全选
				$(".chickBox").attr("class", $(this).attr("class"));
			}

			var $box = $(".chickBox");
			for(var i = 0; i < $box.length; i++){
				if($($box[i]).attr("class") == "chickBox fa fa-check"){
					isSelected = true;
				}
			}

			if (isSelected){
				$(".workBtns").show();
			}else{
				$(".workBtns").hide();
			}
		});


		$(".fileList tbody tr").hover(function(){
			$(".fileWork").hide();
			$("#wk_" + $(this).attr("itemId")).show();
		}, function(){
			$(".fileWork").hide();
		});
	}

    
    
    //新建文件夹
    $("#createFolder").click(function(){
    	parent.layer.prompt({title: '新建文件夹名', formType: 0}, function(text, index){
    		$.post($("#thisPage").val() + "/createFolder", {name:text}, function(response){
    			if(response.result){
    				location = location;
    				parent.layer.close(index);
    			}else{
    				parent.layer.msg(response.message);
    			}
    		}).error(function(){
    			parent.layer.msg("文件夹创建失败");
    		});
    	});
    });
    
    //上传文件
    $("#uploadFile").click(function(){
		layer.open({
			type : 2,
			title : false,
			closeBtn : 1, // 不显示关闭按钮
			shade : [ 0 ],
			area : [ '500px', '400px' ],
			offset : 'rb', // 右下角弹出
			anim : 2,
			content : [ $("#thisPage").val() + '/upload', 'yes' ], // iframe的url，no代表不显示滚动条
		});
    });
    
});

/**
 * 获取文件
 * @param fileId
 * @returns
 */
function download(fileId){
	window.open($("#rootPage").val() + '/manager/download/' + fileId); 
}

/**
 * 删除文件
 * @param fileId
 * @returns
 */
function deleteFile(fileId){
	parent.layer.load();
	
	$.get($("#rootPage").val() + '/manager/delete/' + fileId, function(response){
		if(response.result){
			parent.layer.closeAll('loading');
			parent.layer.msg("删除成功");
			location = location;
		}else{
			parent.layer.closeAll('loading');
			layer.msg(response.message);
		}
	}).error(function(){
		parent.layer.closeAll('loading');
		layer.msg("删除遇到错误");
	});
}

/**
 * 彻底删除文件或文件夹
 * @param itemId
 * @returns
 */
function deleteItem(itemId){
	parent.layer.load();
	
	$.get($("#rootPage").val() + '/manager/deleteItem/' + itemId, function(response){
		if(response.result){
			parent.layer.closeAll('loading');
			parent.layer.msg("删除成功");
			location = location;
		}else{
			parent.layer.closeAll('loading');
			layer.msg(response.message);
		}
	}).error(function(){
		parent.layer.closeAll('loading');
		layer.msg("删除遇到错误");
	});
}

/**
 * 删除文件夹
 * @param fileId
 * @returns
 */
function deleteFolder(fileId){
	var index = parent.layer.confirm('删除文件夹后，其内的子文件夹以及文件都将被删除。是否继续？', {
		  btn: ['继续','取消'] //按钮
		}, function(){
			//删除开始
			parent.layer.load();
			$.get($("#rootPage").val() + '/manager/delete/' + fileId, function(response){
				if(response.result){
					parent.layer.closeAll('loading');
					parent.layer.msg("删除成功");
					location = location;
				}else{
					parent.layer.closeAll('loading');
					layer.msg(response.message);
				}
			}).error(function(){
				parent.layer.closeAll('loading');
				layer.msg("删除遇到错误");
			});
			
		}, function(){
			parent.layer.close(index);
		});
}

/**
 * 分享文件
 * @param fileId
 * @returns
 */
function shareFile(fileId){
	parent.layer.load();
	$.get($("#rootPage").val() + '/manager/share/' + fileId, function(response){
		parent.layer.closeAll('loading');
		parent.layer.alert(response,{
			area:['520px', '350px'],
			title:"创建分享链接"
		});
	}).error(function(){
		parent.layer.closeAll('loading');
		layer.msg("分享遇到错误");
	});
}

/**
 * 取消分享
 * @param fileId
 * @returns
 */
function deleteShare(fileId){
	parent.layer.load();
	$.get($("#rootPage").val() + '/manager/deleteShare/' + fileId, function(response){
		parent.layer.closeAll('loading');
		if(response.result){
			parent.layer.msg("已取消分享");
			location = location;
		}else{
			parent.layer.msg(response.message);
		}
	}).error(function(){
		parent.layer.closeAll('loading');
		layer.msg("分享遇到错误");
	});
}

/**
 * 创建分享密码
 * @param fileId
 * @returns
 */
function createPassword(fileId){
	parent.layer.load();
	$.get($("#rootPage").val() + '/manager/createSharePswd/' + fileId, function(response){
		parent.layer.closeAll('loading');
		parent.layer.alert(response,{
			area:['520px', '350px'],
			title:"创建分享链接"
		});
	}).error(function(){
		parent.layer.closeAll('loading');
		layer.msg("操作遇到错误");
	});
}

function deletePassword(fileId){
	parent.layer.load();
	$.get($("#rootPage").val() + '/manager/deleteSharePassword/' + fileId, function(response){
		parent.layer.closeAll('loading');
		if(response.result){
			parent.layer.msg("已取消分享密码");
			location = location;
		}else{
			parent.layer.msg(response.message);
		}
	}).error(function(){
		parent.layer.closeAll('loading');
		layer.msg("分享遇到错误");
	});
}