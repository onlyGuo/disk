/**
 * Created by AnLuTong on 2016-12-21.
 */
var url = $("#list_api_dir").val();
$(function(){
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
			$(this).find(".fileWork").show();
		}, function(){
			$(".fileWork").hide();
		});
	}

	init();

    
	$(".btnGroup").click(function () {
		var $selects = $("tr .chickBox.fa.fa-check");
		var selectKeys = [];
		for (var i = 0; i < $selects.length; i++){
			selectKeys.push($($selects[i]).attr("fileId"));
		}
		parent.layer.msg('正在删除请稍候...', {icon: 16,time: 0,shade : [0.5 , '#000' , true]});
		H.post(url + "/../deletes", selectKeys, function(){
			for (var i = 0; i < $selects.length; i++){
				$($selects[i]).parent().parent().remove();
			}
			$(".workBtns").hide();
			parent.layer.msg("批量删除成功");
		});
	});

});

/**
 * 取消分享
 * @param fileId
 * @returns
 */
function deleteShare(fileId, t){
	parent.layer.load();
	H.post($("#ctx").val() + '/share/deleteShare/' + fileId, function(response){
		parent.layer.closeAll('loading');
		parent.layer.msg("已取消分享");
		location.href = location.href;
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

/**
 * 查看分享连接
 * @param fileId
 * 		分享文件Id
 */
function lookShare(fileId){
	var link = window.location.protocol+"//"+window.location.host;
	link += $("#ctx").val() + "/share/" + fileId;
	parent.layer.alert("连接地址: " + link);
}